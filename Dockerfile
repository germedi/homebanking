# Usar una imagen base con JDK 8 y Gradle
FROM gradle:6.7.0-jdk8 AS build

# Establecer un directorio de trabajo
WORKDIR /workspace

# Copiar solo los archivos de configuración de Gradle y el archivo de build.gradle para la etapa de descarga de dependencias
COPY gradle /workspace/gradle
COPY build.gradle /workspace/
COPY settings.gradle /workspace/

# Descargar dependencias para aprovechar la cache de Docker
RUN gradle build -x test --no-daemon || true

# Copiar el resto de los archivos del proyecto al directorio de trabajo
COPY . /workspace

# Verificar el contenido del directorio de trabajo
RUN ls -la /workspace

# Ejecutar Gradle para construir el proyecto
RUN gradle clean build --no-daemon --stacktrace --info

# Crear una nueva imagen basada en OpenJDK 8
FROM openjdk:8-jre-slim

# Exponer el puerto que utilizará la aplicación
EXPOSE 8080

# Copiar el archivo JAR construido desde la etapa anterior
COPY --from=build /workspace/build/libs/homebanking-0.0.1-SNAPSHOT.jar /homebanking-0.0.1-SNAPSHOT.jar

# Establecer el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/homebanking-0.0.1-SNAPSHOT.jar"]

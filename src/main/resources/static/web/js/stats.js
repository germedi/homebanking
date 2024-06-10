   // libreria chart  Esperar a que el DOM esté completamente cargado antes de ejecutar el código de Chart.js
                    document.addEventListener('DOMContentLoaded', function() {
                        // Obtener el contexto 2D del canvas
                        let miCanvas = document.getElementById("Prestamos").getContext("2d");

                        // Crear una nueva instancia de Chart
                        var chart = new Chart(miCanvas, {
                            type: "bar", // Tipo de gráfico
                            data: {
                                labels: ["100000", "200000", "300000", "400000"], // Etiquetas para el eje X
                                datasets: [{
                                    label: "Prestamos en miles", // Etiqueta del conjunto de datos
                                    backgroundColor: "rgb(0,0,0)", // Color de fondo de las barras
                                    borderColor: "rgb(0,255,0)", // Color del borde de las barras
                                    data: [12, 39, 5, 30] // Datos a graficar
                                }]
                            }
                        });
                    });
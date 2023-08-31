package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api")
public class TransactionControllers {

    // Inyectar el repositorio de cuentas
    @Autowired
    private AccountRepository accountRepository;

    // Inyectar el repositorio de transacciones
    @Autowired
    private TransactionRepository transactionRepository;

    // Inyectar el repositorio de clientes
    @Autowired
    private ClientRepository clientRepository;


    /* Anotación para indicar que se debe realizar la transacción dentro de una transacción de base de datos
    La anotación @Transactional es una anotación de Spring que se utiliza para indicar que un método debe ser
    ejecutado dentro de una transacción de base de datos. Esto significa que todas las operaciones realizadas
    dentro del método, como las consultas y las actualizaciones de la base de datos, se ejecutarán como una
    sola transacción.
    En el código que me proporcionaste, la anotación @Transactional se utiliza en el método transaction()
    para asegurarse de que todas las operaciones realizadas dentro del método, como la creación de transacciones
    y la actualización de saldos de cuentas, se realicen dentro de una única transacción de base de datos.
    Esto garantiza que todas las operaciones se realicen correctamente o se deshagan en caso de que ocurra algún error.
    */
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transaction(
            @RequestParam Double amount,
            @RequestParam String description,
            @RequestParam String toAccountNumber,
            @RequestParam String fromAccountNumber,
            Authentication authentication
    ) {
        ResponseEntity<Object> result;
        // Obtener el cliente autenticado
        Client current = clientRepository.findByEmail(authentication.getName());
        // Obtener la cuenta de origen
        Account originAccount = accountRepository.findByNumber(toAccountNumber);
        // Obtener la cuenta de destino
        Account destinyAccount = accountRepository.findByNumber(fromAccountNumber);
        // Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();

        // Verificar que los campos no estén vacíos
        if (amount.isNaN() || amount == 0 || description.isEmpty() || toAccountNumber.isEmpty() || fromAccountNumber.isEmpty()) {
            result = new ResponseEntity<>("Los campos no pueden estar vacíos", HttpStatus.FORBIDDEN);
        }
        // Verificar que las cuentas no estén vacías y sean diferentes
        else if (toAccountNumber.equals(fromAccountNumber)) {
            result = new ResponseEntity<>("La cuenta de origen y la de destino no pueden ser iguales", HttpStatus.FORBIDDEN);
        }
        // Verificar que las cuentas existan
        else if (originAccount == null || destinyAccount == null) {
            result = new ResponseEntity<>("La cuenta ingresada no existe", HttpStatus.FORBIDDEN);
        }
        // Verificar que la cuenta de origen pertenezca al cliente autenticado
        else if (!current.getAccounts().contains(originAccount)) {
            result = new ResponseEntity<>("La cuenta de origen no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }
        // Verificar que el cliente tenga suficientes fondos
        else if (originAccount.getBalance() < amount) {
            result = new ResponseEntity<>("El cliente no tiene suficientes fondos", HttpStatus.FORBIDDEN);
        }
        // Si todo está bien, realizar la transacción
        else {
            // Crear una transacción de débito para la cuenta de origen
            Transaction debitTransaction = new Transaction(TransactionType.DEBIT, -amount, description, LocalDateTime.now());
            // Crear una transacción de crédito para la cuenta de destino
            Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, description, LocalDateTime.now());
            // Asignar la cuenta de origen a la transacción de débito
            debitTransaction.setAccount(originAccount);
            // Asignar la cuenta de destino a la transacción de crédito
            creditTransaction.setAccount(destinyAccount);
            // Actualizar el saldo de la cuenta de origen
            originAccount.setBalance(originAccount.getBalance() - amount);
            // Actualizar el saldo de la cuenta de destino
            destinyAccount.setBalance(destinyAccount.getBalance() + amount);
            // Guardar las transacciones en la base de datos
            transactionRepository.save(debitTransaction);
            transactionRepository.save(creditTransaction);
            // Guardar las cuentas en la base de datos
            accountRepository.save(originAccount);
            accountRepository.save(destinyAccount);

            // Comentario para indicar que la transacción se realizó exitosamente
            result = new ResponseEntity<>("La transacción se realizó exitosamente", HttpStatus.CREATED);
        }
        return result;
    }

    // Obtener una transacción por ID
    @GetMapping("/transaction/{id}")
    public TransactionDTO getTransaction(@PathVariable long id) {
        return new TransactionDTO(transactionRepository.findById(id).orElse(null));
    }

}
/* Dentro de una transacción, pueden ocurrir varios tipos de errores, incluyendo:
1- Errores de concurrencia: Estos errores pueden ocurrir cuando varios usuarios intentan acceder
 a los mismos datos simultáneamente. Por ejemplo, si dos usuarios intentan actualizar la misma fila
 en una tabla al mismo tiempo, puede ocurrir un error de concurrencia.

2- Errores de restricción: Estos errores pueden ocurrir cuando se violan las restricciones de integridad
 de la base de datos. Por ejemplo, si se intenta insertar un registro en una tabla y se viola una
 restricción de clave única, se producirá un error de restricción.

4- Errores de validación: Estos errores pueden ocurrir cuando se intenta insertar o actualizar datos
que no cumplen con las reglas de validación definidas para la tabla. Por ejemplo, si se intenta insertar
un valor no numérico en una columna que solo acepta números, se producirá un error de validación.

5- Errores de sistema: Estos errores pueden ocurrir debido a problemas en el sistema de base de datos
 o en la red. Por ejemplo, si se pierde la conexión a la base de datos mientras se está realizando una
  transacción, se producirá un error de sistema.
  */
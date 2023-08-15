/*
Este código define un tipo de datos enum llamado TransactionType.
Un enum es un tipo de datos que permite definir un conjunto fijo de valores posibles.
En este caso, TransactionType tiene dos valores posibles: CREDIT y DEBIT,
que se utilizan para representar el tipo de transacción que se está realizando (crédito o débito).
Los enums son útiles porque permiten definir un conjunto fijo de valores que se
pueden utilizar en diferentes partes del código sin tener que
preocuparse por errores tipeo o de otro tipo al escribir los valores. */
package com.mindhub.homebanking.models;

public enum TransactionType
{
    CREDIT,
    DEBIT
}

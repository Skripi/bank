package ru.hse.bank.domain.entity

data class BankAccountEntity(
    val name: String,
    val number: Int,
    val balance: Double,
)
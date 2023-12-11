package ru.hse.bank.domain

import ru.hse.bank.presentation.model.OutputModel

sealed class Result

object Success : Result()
class Error(val outputModel: OutputModel) : Result()


interface BankAccountValidator {
    fun validateName(name: String): Result
    fun validateTransferBalance(
        fromBalance: Double,
        transferSum: Double,
    ): Result
}

class BankAccountValidatorImpl : BankAccountValidator {

    override fun validateName(name: String): Result {
        return when {
            name.isEmpty() -> Error(OutputModel("Name incorrect"))
            else -> Success
        }
    }

    override fun validateTransferBalance(
        fromBalance: Double,
        transferSum: Double
    ): Result {
        return when {
            transferSum <= 0 -> Error(OutputModel("Name incorrect"))
            fromBalance < transferSum -> Error(OutputModel("Name incorrect"))
            else -> Success
        }
    }
}
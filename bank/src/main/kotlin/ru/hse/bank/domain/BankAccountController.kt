package ru.hse.bank.domain

import ru.hse.bank.data.BankAccountDao
import ru.hse.bank.presentation.model.OutputModel

interface BankAccountController {
    fun createAccount(name: String): OutputModel
    fun getAccounts(): OutputModel
    fun transfer(fromNumber: Int, toNumber: Int, sum: Double): OutputModel
}

class BankAccountControllerImpl(
    private val bankAccountValidator: BankAccountValidator,
    private val bankAccountDao: BankAccountDao,
) : BankAccountController {

    override fun createAccount(name: String): OutputModel {
        return when (val result = bankAccountValidator.validateName(name)) {
            Success -> {
                bankAccountDao.add(name)
                OutputModel("Success")
            }

            is Error -> {
                result.outputModel
            }
        }
    }

    override fun getAccounts(): OutputModel {
        val accountUI = bankAccountDao.getAll().joinToString()
        return OutputModel(accountUI).takeIf { it.message.isNotEmpty() }
            ?: OutputModel("List is Empty")
    }

    override fun transfer(fromNumber: Int, toNumber: Int, sum: Double): OutputModel {
        val fromAccount = bankAccountDao.get(fromNumber) ?: return OutputModel("Account number $fromNumber not found")
        val toAccount = bankAccountDao.get(toNumber) ?: return OutputModel("Account number $toNumber not found")

        return when (val result = bankAccountValidator.validateTransferBalance(fromAccount.balance, sum)) {
            Success -> {
                val updatedFromAccount = fromAccount.copy(balance = fromAccount.balance - sum)
                val updatedToAccount = toAccount.copy(balance = toAccount.balance + sum)
                bankAccountDao.update(*arrayOf(updatedFromAccount, updatedToAccount))
                OutputModel("Success")
            }

            is Error -> {
                result.outputModel
            }
        }
    }
}
package ru.hse.bank.di

import ru.hse.bank.data.BankAccountDao
import ru.hse.bank.data.RuntimeBankAccountDao
import ru.hse.bank.domain.BankAccountController
import ru.hse.bank.domain.BankAccountControllerImpl
import ru.hse.bank.domain.BankAccountValidator
import ru.hse.bank.domain.BankAccountValidatorImpl

object DI {

    private val bankAccountValidator: BankAccountValidator
        get() = BankAccountValidatorImpl()

    private val bankAccountDao: BankAccountDao by lazy {
        RuntimeBankAccountDao()
    }

    val bankAccountController: BankAccountController
        get() = BankAccountControllerImpl(
            bankAccountValidator = bankAccountValidator,
            bankAccountDao = bankAccountDao,
        )
}
package ru.hse.bank.data

import ru.hse.bank.domain.entity.BankAccountEntity

interface BankAccountDao {
    fun add(name: String)
    fun getAll(): List<BankAccountEntity>
    fun get(number: Int): BankAccountEntity?
    fun update(vararg listAccount: BankAccountEntity)
}

class RuntimeBankAccountDao : BankAccountDao {

    private val accounts = mutableMapOf<Int, BankAccountEntity>()
    private var counter = 0

    override fun add(name: String) {
        val account = BankAccountEntity(
            name = name,
            number = counter,
            balance = 0.0
        )
        accounts[counter] = account
        counter++
    }

    override fun getAll(): List<BankAccountEntity> {
        return accounts.values.toList()
    }

    override fun get(number: Int): BankAccountEntity? {
        return accounts[number]
    }

    override fun update(vararg listAccount: BankAccountEntity) {
        listAccount.forEach { account ->
            accounts[account.number] = account
        }
    }
}
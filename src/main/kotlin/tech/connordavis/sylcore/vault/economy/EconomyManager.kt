package tech.connordavis.sylcore.vault.economy

import tech.connordavis.sylcore.SylCorePlugin

class EconomyManager {
    private val plugin = SylCorePlugin.instance
    private val fileManager = plugin.getFileManager()

    private val accounts: MutableMap<String, Account> = mutableMapOf()
    private val banks: MutableMap<String, Bank> = mutableMapOf()

    fun createAccount(holder: String) {
        this.accounts.putIfAbsent(holder, Account(holder, 0.0))
        saveAccount(this.accounts[holder]!!)
    }

    fun deleteAccount(holder: String) {
        val accountsFile = fileManager.getFile("accounts")
        val accountsConfig = accountsFile!!.getConfig()

        accountsConfig.set(holder, null)
        this.accounts.remove(holder)
    }

    fun getAccount(holder: String): Account {
        return this.accounts[holder]!!
    }

    fun accountHas(holder: String, amount: Double): Boolean {
        return this.accounts[holder]!!.balance.equals(amount)
    }

    fun accountWithdraw(holder: String, amount: Double) {
        this.accounts[holder]!!.balance = this.accounts[holder]!!.balance.minus(amount)
        saveAccount(this.accounts[holder]!!)
    }

    fun accountDeposit(holder: String, amount: Double) {
        this.accounts[holder]!!.balance = this.accounts[holder]!!.balance.plus(amount)
        saveAccount(this.accounts[holder]!!)
    }

    fun accountExists(holder: String): Boolean {
        return try {
            this.accounts[holder]!!
            true
        } catch (exception: NullPointerException) {
            false
        }
    }

    private fun saveAccount(account: Account) {
        val accountsFile = fileManager.getFile("accounts")
        val accountsConfig = accountsFile!!.getConfig()

        accountsConfig.set(account.accountHolder, account)
        accountsFile.saveFile()
    }

    fun createBank(name: String, owner: String) {
        val bankAccounts: MutableMap<String, Account> = mutableMapOf()
        bankAccounts.putIfAbsent(owner, this.accounts[owner]!!)

        val bank = Bank(name, owner, bankAccounts)

        this.banks.putIfAbsent(name, bank)
        saveBank(bank)
    }

    fun deleteBank(name: String) {
        val bank = this.banks[name]!!

        this.banks.remove(bank.name)
        saveBank(bank)
    }

    fun getBank(name: String): Bank {
        return this.banks[name]!!
    }

    fun bankHas(bank: String, amount: Double): Boolean {
        return this.banks[bank]!!.balance.equals(amount)
    }

    private fun saveBank(bank: Bank) {
        val banksFile = fileManager.getFile("banks")
        val banksConfig = banksFile!!.getConfig()

        banksConfig.set(bank.name, bank)
        banksFile.saveFile()
    }
}
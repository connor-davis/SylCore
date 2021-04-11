package tech.connordavis.sylcore.vault.economy

import org.bukkit.configuration.file.YamlConfiguration
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

        accountsConfig.set("${account.accountHolder}.accountHolder", account.accountHolder)
        accountsConfig.set("${account.accountHolder}.balance", account.balance)

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

        banksConfig.set("${bank.name}.name", bank.name)
        banksConfig.set("${bank.name}.owner", bank.owner)

        bank.accounts.forEach { (holder, account) -> transformBankAccounts(banksConfig, holder, bank, account) }

        banksConfig.set("${bank.name}.name", bank.name)

        banksFile.saveFile()
    }

    private fun transformBankAccounts(config: YamlConfiguration, holder: String, bank: Bank, account: Account) {
        config.set("${bank.name}.accounts.${holder}.accountHolder", account.accountHolder)
        config.set("${bank.name}.accounts.${holder}.balance", account.balance)
    }
}
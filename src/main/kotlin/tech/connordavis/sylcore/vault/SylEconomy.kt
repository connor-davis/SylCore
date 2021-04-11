package tech.connordavis.sylcore.vault

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.OfflinePlayer
import tech.connordavis.sylcore.vault.economy.EconomyManager

class SylEconomy : Economy {
    private val economyManager: EconomyManager = EconomyManager()

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getName(): String {
        return "SylEconomy"
    }

    override fun hasBankSupport(): Boolean {
        return false
    }

    override fun fractionalDigits(): Int {
        return 0
    }

    override fun format(amount: Double): String {
        return "$amount pieces"
    }

    override fun currencyNamePlural(): String {
        return "pieces"
    }

    override fun currencyNameSingular(): String {
        return "piece"
    }

    override fun hasAccount(playerName: String?): Boolean {
        return economyManager.accountExists(playerName!!)
    }

    override fun hasAccount(player: OfflinePlayer?): Boolean {
        return economyManager.accountExists(player!!.name!!)
    }

    override fun hasAccount(playerName: String?, worldName: String?): Boolean {
        return economyManager.accountExists(playerName!!)
    }

    override fun hasAccount(player: OfflinePlayer?, worldName: String?): Boolean {
        return economyManager.accountExists(player!!.name!!)
    }

    override fun getBalance(playerName: String?): Double {
        return economyManager.getAccount(playerName!!).balance
    }

    override fun getBalance(player: OfflinePlayer?): Double {
        return economyManager.getAccount(player!!.name!!).balance
    }

    override fun getBalance(playerName: String?, world: String?): Double {
        return economyManager.getAccount(playerName!!).balance
    }

    override fun getBalance(player: OfflinePlayer?, world: String?): Double {
        return economyManager.getAccount(player!!.name!!).balance
    }

    override fun has(playerName: String?, amount: Double): Boolean {
        return economyManager.accountHas(playerName!!, amount)
    }

    override fun has(player: OfflinePlayer?, amount: Double): Boolean {
        return economyManager.accountHas(player!!.name!!, amount)
    }

    override fun has(playerName: String?, worldName: String?, amount: Double): Boolean {
        return economyManager.accountHas(playerName!!, amount)
    }

    override fun has(player: OfflinePlayer?, worldName: String?, amount: Double): Boolean {
        return economyManager.accountHas(player!!.name!!, amount)
    }

    override fun withdrawPlayer(playerName: String?, amount: Double): EconomyResponse {
        economyManager.accountWithdraw(playerName!!, amount)

        return EconomyResponse(amount,
            economyManager.getAccount(playerName).balance,
            EconomyResponse.ResponseType.SUCCESS,
            "Could not process withdrawal.")
    }

    override fun withdrawPlayer(player: OfflinePlayer?, amount: Double): EconomyResponse {
        economyManager.accountWithdraw(player!!.name!!, amount)

        return EconomyResponse(amount,
            economyManager.getAccount(player.name!!).balance,
            EconomyResponse.ResponseType.SUCCESS,
            "Could not process withdrawal.")
    }

    override fun withdrawPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse {
        economyManager.accountWithdraw(playerName!!, amount)

        return EconomyResponse(amount,
            economyManager.getAccount(playerName).balance,
            EconomyResponse.ResponseType.SUCCESS,
            "Could not process withdrawal.")
    }

    override fun withdrawPlayer(player: OfflinePlayer?, worldName: String?, amount: Double): EconomyResponse {
        economyManager.accountWithdraw(player!!.name!!, amount)

        return EconomyResponse(amount,
            economyManager.getAccount(player.name!!).balance,
            EconomyResponse.ResponseType.SUCCESS,
            "Could not process withdrawal.")
    }

    override fun depositPlayer(playerName: String?, amount: Double): EconomyResponse {
        economyManager.accountDeposit(playerName!!, amount)

        return EconomyResponse(amount,
            economyManager.getAccount(playerName).balance,
            EconomyResponse.ResponseType.SUCCESS,
            "Could not process withdrawal.")
    }

    override fun depositPlayer(player: OfflinePlayer?, amount: Double): EconomyResponse {
        economyManager.accountDeposit(player!!.name!!, amount)

        return EconomyResponse(amount,
            economyManager.getAccount(player.name!!).balance,
            EconomyResponse.ResponseType.SUCCESS,
            "Could not process withdrawal.")
    }

    override fun depositPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse {
        economyManager.accountDeposit(playerName!!, amount)

        return EconomyResponse(amount,
            economyManager.getAccount(playerName).balance,
            EconomyResponse.ResponseType.SUCCESS,
            "Could not process withdrawal.")
    }

    override fun depositPlayer(player: OfflinePlayer?, worldName: String?, amount: Double): EconomyResponse {
        economyManager.accountDeposit(player!!.name!!, amount)

        return EconomyResponse(amount,
            economyManager.getAccount(player.name!!).balance,
            EconomyResponse.ResponseType.SUCCESS,
            "Could not process withdrawal.")
    }

    override fun createBank(name: String?, player: String?): EconomyResponse {
        economyManager.createBank(name!!, player!!)

        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "Could not create $name bank.")
    }

    override fun createBank(name: String?, player: OfflinePlayer?): EconomyResponse {
        economyManager.createBank(name!!, player!!.name!!)

        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "Could not create $name bank.")
    }

    override fun deleteBank(name: String?): EconomyResponse {
        economyManager.deleteBank(name!!)

        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "Could not create $name bank.")
    }

    override fun bankBalance(name: String?): EconomyResponse {
        return EconomyResponse(0.0,
            economyManager.getBank(name!!).balance,
            EconomyResponse.ResponseType.SUCCESS,
            "Could not get $name bank balance.")
    }

    override fun bankHas(name: String?, amount: Double): EconomyResponse {
        return EconomyResponse(0.0,
            economyManager.getBank(name!!).balance,
            createResponse { economyManager.bankHas(name, amount) },
            "Could not check if $name bank has $amount")
    }

    override fun bankWithdraw(name: String?, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankDeposit(name: String?, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankOwner(name: String?, playerName: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankOwner(name: String?, player: OfflinePlayer?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankMember(name: String?, playerName: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankMember(name: String?, player: OfflinePlayer?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun getBanks(): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun createPlayerAccount(playerName: String?): Boolean {
        economyManager.createAccount(playerName!!)
        return true
    }

    override fun createPlayerAccount(player: OfflinePlayer?): Boolean {
        economyManager.createAccount(player!!.name!!)
        return true
    }

    override fun createPlayerAccount(playerName: String?, worldName: String?): Boolean {
        economyManager.createAccount(playerName!!)
        return true
    }

    override fun createPlayerAccount(player: OfflinePlayer?, worldName: String?): Boolean {
        economyManager.createAccount(player!!.name!!)
        return true
    }
}

fun createResponse(has: () -> Boolean): EconomyResponse.ResponseType {
    return when (has()) {
        true -> EconomyResponse.ResponseType.SUCCESS
        else -> EconomyResponse.ResponseType.FAILURE
    }
}
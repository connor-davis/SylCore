package tech.connordavis.sylcore.vault.economy

data class Bank(
    var name: String,
    var owner: String,
    var accounts: MutableMap<String, Account>,
    var balance: Double = totalBalance { accounts },
)

fun totalBalance(action: () -> MutableMap<String, Account>): Double {
    return action.invoke().entries.sumByDouble { selector -> selector.value.balance }
}

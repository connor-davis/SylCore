package tech.connordavis.sylcore.vault.permissions

data class Player(
    var name: String,
    var groups: MutableList<String>,
    var permissions: MutableList<String>,
)

package tech.connordavis.sylcore.vault.permissions

data class Group(
    var name: String,
    var players: MutableList<String>,
    var permissions: MutableList<String>,
)

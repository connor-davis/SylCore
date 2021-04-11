package tech.connordavis.sylcore.vault.permissions

data class Player(
    var name: String,
    var groups: MutableList<Group>,
    var permissions: MutableList<String>,
)

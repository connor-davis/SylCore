package tech.connordavis.sylcore.vault.permissions

data class Group(
    var prefix: String,
    var name: String,
    var permissions: MutableList<String>,
)

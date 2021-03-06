package tech.connordavis.sylcore.utils

data class CommandInfo(
    val name: String,
    val desc: String,
    val permissions: Array<String> = emptyArray(),
    val usage: String = "",
    val aliases: Array<String> = emptyArray(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CommandInfo

        if (name != other.name) return false
        if (desc != other.desc) return false
        if (!permissions.contentEquals(other.permissions)) return false
        if (usage != other.usage) return false
        if (!aliases.contentEquals(other.aliases)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + desc.hashCode()
        result = 31 * result + permissions.contentHashCode()
        result = 31 * result + usage.hashCode()
        result = 31 * result + aliases.contentHashCode()
        return result
    }
}
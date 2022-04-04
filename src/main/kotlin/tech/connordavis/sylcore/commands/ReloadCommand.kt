package tech.connordavis.sylcore.commands

import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.utils.Command
import tech.connordavis.sylcore.utils.CommandInfo

class ReloadCommand : Command(CommandInfo("sylreload",
    "This command reloads sylcore.",
    aliases = arrayOf("sylr"),
    permissions = arrayOf("sylcore.command.sylreload", "sylcore.command.sylr"))) {
    private val plugin: SylCorePlugin = SylCorePlugin.instance

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (checkPermissions(sender, commandLabel)) when (commandLabel) {
            "sylreload" -> plugin.performReload()
            "sylr" -> plugin.performReload()
        }

        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: org.bukkit.command.Command,
        label: String,
        args: Array<out String>,
    ): MutableList<String>? {
        return null
    }
}
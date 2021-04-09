package tech.connordavis.sylcore.commands

import org.bukkit.command.CommandSender
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.utils.CommandInfo
import tech.connordavis.sylcore.utils.Command


class TestCommand : Command(CommandInfo("test", "this is a test command")) {
    private val plugin = SylCorePlugin.instance

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        plugin.logger.info { "$commandLabel was used by ${sender.name}" }
        return false
    }
}
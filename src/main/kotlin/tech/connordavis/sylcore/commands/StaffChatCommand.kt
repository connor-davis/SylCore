package tech.connordavis.sylcore.commands

import org.bukkit.command.CommandSender
import tech.connordavis.sylcore.utils.Command
import tech.connordavis.sylcore.utils.CommandInfo
import tech.connordavis.sylcore.utils.from
import tech.connordavis.sylcore.utils.Prefixes

class StaffChatCommand :
        Command(CommandInfo("staffchat", "Staff Chat Commands", aliases = arrayOf("sc"))) {
    override fun execute(
            sender: CommandSender,
            commandLabel: String,
            args: Array<String>
    ): Boolean {
        if (!sender.hasPermission("sylcore.command.$commandLabel")) {
            sender.from(Prefixes.CORE, "You do not have permission to access that command.")
            return false
        } else {
            
        }
        
        return false
    }

    override fun onTabComplete(
            sender: CommandSender,
            command: org.bukkit.command.Command,
            commandLabel: String,
            args: Array<out String>,
    ): MutableList<String> {
        return mutableListOf("day", "night", "midnight", "afternoon", "dawn")
    }
}

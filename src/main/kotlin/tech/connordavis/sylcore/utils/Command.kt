package tech.connordavis.sylcore.utils

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

abstract class Command(info: CommandInfo) :
    Command(info.name, info.desc, info.usage, listOf(*info.aliases)) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        return false
    }
}
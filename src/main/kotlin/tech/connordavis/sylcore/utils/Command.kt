package tech.connordavis.sylcore.utils

import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.command.defaults.BukkitCommand

abstract class Command(info: CommandInfo) :
    BukkitCommand(info.name, info.desc, info.usage, listOf(*info.aliases)), TabCompleter {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        return false
    }
}
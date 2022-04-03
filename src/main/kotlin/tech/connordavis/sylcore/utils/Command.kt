package tech.connordavis.sylcore.utils

import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.command.defaults.BukkitCommand

abstract class Command(info: CommandInfo) :
    BukkitCommand(info.name, info.desc, info.usage, listOf(*info.aliases)), TabCompleter {
    fun checkPermissions(sender: CommandSender, commandLabel: String): Boolean {
        if (sender.isOp || sender.hasPermission("*") || sender.hasPermission("sylcore.*") || sender.hasPermission("sylcore.command.*") || sender.hasPermission(
                "sylcore.command.$commandLabel")
        ) return true
        return false
    }
}
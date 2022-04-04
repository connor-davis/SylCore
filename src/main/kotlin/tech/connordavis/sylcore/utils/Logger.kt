package tech.connordavis.sylcore.utils

import net.md_5.bungee.api.ChatColor
import org.bukkit.command.ConsoleCommandSender
import tech.connordavis.sylcore.SylCorePlugin

class Logger(private val prefix: String) {
    private val plugin: SylCorePlugin = SylCorePlugin.instance
    private val console: ConsoleCommandSender = plugin.server.consoleSender

    fun info(message: String) {
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9$prefix&8] &7$message"))
    }

    fun warn(message: String) {
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6$prefix&8] &7$message"))
    }

    fun error(message: String) {
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4$prefix&8] &7$message"))
    }
}
package tech.connordavis.sylcore.utils

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.util.logging.Logger

fun CommandSender.from(prefix: Prefixes, message: String) {
    return this.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9${prefix.self}> &7$message"))
}

fun Player.from(prefix: Prefixes, message: String) {
    return this.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9${prefix.self}> &7$message"))
}

fun Logger.from(prefix: Prefixes, message: String) {
    return this.info { ChatColor.translateAlternateColorCodes('&', "&9${prefix.self}> &7$message") }
}

fun ConsoleCommandSender.from(prefix: Prefixes, message: String) {
    return this.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9${prefix.self}> &7$message"))
}

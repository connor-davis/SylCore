package tech.connordavis.sylcore.utils

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class Message {
    companion object {
        fun CommandSender.from(prefix: Prefixes, message: String) {
            return this.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9${prefix.self}> &7$message"))
        }
    }
}
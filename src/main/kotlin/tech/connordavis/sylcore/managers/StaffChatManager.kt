package tech.connordavis.sylcore.managers

import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class StaffChatManager(private val plugin: JavaPlugin) {
    private var staffChatPlayers: MutableMap<String, Player> = mutableMapOf()

    fun addStaffChatPlayer(channel: String, player: Player) {
        this.staffChatPlayers[channel] = player
    }

    fun removeStaffChatPlayer(channel: String) {
        this.staffChatPlayers.remove(channel)
    }

    fun removeStaffChatPlayer(channel: String, player: Player) {
        this.staffChatPlayers.remove(channel, player)
    }

    fun getStaffChatPlayers(): MutableMap<String, Player> {
        return this.staffChatPlayers
    }

    fun getPlugin(): JavaPlugin {
        return plugin
    }
}

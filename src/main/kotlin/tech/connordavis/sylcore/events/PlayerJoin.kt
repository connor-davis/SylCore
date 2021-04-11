package tech.connordavis.sylcore.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.utils.Prefixes
import tech.connordavis.sylcore.utils.from

class PlayerJoin : Listener {
    private val plugin = SylCorePlugin.instance

    @EventHandler
    fun playerJoin(event: PlayerJoinEvent) {
        if (!plugin.economy.hasAccount(event.player)) {
            plugin.economy.createPlayerAccount(event.player)
            plugin.server.consoleSender.from(Prefixes.ECONOMY, "Created a new account for ${event.player.name}")
        }
    }
}

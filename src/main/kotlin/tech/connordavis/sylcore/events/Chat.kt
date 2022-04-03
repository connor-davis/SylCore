package tech.connordavis.sylcore.events

import net.md_5.bungee.api.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.utils.Prefixes
import tech.connordavis.sylcore.utils.from

object Chat : Listener {
    private val plugin = SylCorePlugin.instance
    private val permissionsManager = plugin.getPermissionsManager()
    private val staffChatManager = plugin.getStaffChatManager()

    @EventHandler
    fun chatListener(event: AsyncPlayerChatEvent) {
        val name = event.player.displayName
        val rank = permissionsManager.playerGroups(name).last()
        val prefix = permissionsManager.getGroups().getValue(rank).prefix
        val message = event.message

        event.recipients.forEach { chatPlayer ->
            if (!staffChatManager.getStaffChatPlayers().containsValue(chatPlayer)) {
                 event.format = ChatColor.translateAlternateColorCodes('&', "&8[&7$prefix&8] &7$name &8: &7$message")
            } else {
                event.isCancelled = true

                chatPlayer.from(Prefixes.STAFF_CHAT,
                    "&8[&7$prefix&8] &7$name &8: &7$message")
            }
        }
    }
}
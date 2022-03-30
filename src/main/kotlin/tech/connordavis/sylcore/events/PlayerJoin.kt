package tech.connordavis.sylcore.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.permissions.PermissionAttachment
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.utils.Prefixes
import tech.connordavis.sylcore.utils.from
import tech.connordavis.sylcore.vault.permissions.Player

class PlayerJoin : Listener {
    private val plugin = SylCorePlugin.instance
    private val economyManager = plugin.getEconomyManager()
    private val permissionsManager = plugin.getPermissionsManager()

    @EventHandler
    fun playerJoin(event: PlayerJoinEvent) {
        plugin.server.consoleSender.from(
                Prefixes.NOTHING,
                "${event.player.displayName} joined the server${checkFirstJoin(event.player)}."
        )

        checkEconomy(event.player.name)
        checkGroup(event.player.name)

        // Set Player Permissions
        permissionsManager.getPlayers().forEach { (name, player) ->
            val attachment: PermissionAttachment =
                    plugin.server.getPlayer(name)!!.addAttachment(plugin)
            player.permissions.forEach { permission ->
                attachment.setPermission(permission, true)
                if (permission == "*") event.player.isOp = true
            }

            permissionsManager.getGroups().forEach { (groupName, group) ->
                if (player.groups.contains(groupName)) {
                    group.permissions.forEach { permission ->
                        attachment.setPermission(permission, true)
                        if (permission == "*") event.player.isOp = true
                    }
                }
            }
        }
    }

    private fun checkFirstJoin(player: org.bukkit.entity.Player): String {
        if (!player.hasPlayedBefore()) return " for the first time, welcome them." else return ""
    }

    private fun checkGroup(name: String) {
        if (!permissionsManager.playerExists(name) &&
                        permissionsManager.createPlayer(
                                Player(name, mutableListOf(), mutableListOf())
                        )
        ) {
            if (!permissionsManager.inGroup(name, "default")) {
                permissionsManager.addPlayerGroup(name, "default")
                plugin.server.consoleSender.from(Prefixes.RANKS, "Added $name to default rank.")
            }
        } else {
            if (!permissionsManager.inGroup(name, "default")) {
                permissionsManager.addPlayerGroup(name, "default")
                plugin.server.consoleSender.from(Prefixes.RANKS, "Added $name to default rank.")
            }
        }
    }

    private fun checkEconomy(name: String) {
        if (!economyManager.accountExists(name)) {
            economyManager.createAccount(name)
            plugin.server.consoleSender.from(Prefixes.ECONOMY, "Created a new account for $name.")
        }
    }
}

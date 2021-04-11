package tech.connordavis.sylcore.vault.permissions

import org.bukkit.permissions.Permission
import tech.connordavis.sylcore.SylCorePlugin

class PermissionsManager {
    private val plugin = SylCorePlugin.instance

    private val groups: MutableMap<String, Group> = mutableMapOf()
    private val players: MutableMap<String, Player> = mutableMapOf()
    private val permissions: MutableMap<String, MutableList<String>> = mutableMapOf()

    fun playerHas(name: String, permission: String): Boolean {
        return permissions[name]!!.contains(permission)
    }

    fun groupHas(name: String, permission: String): Boolean {
        return groups[name]!!.permissions.contains(permission)
    }

    fun playerGroups(player: String): Array<String> {
        return players[player]!!.groups.map { group -> group.name }.toTypedArray()
    }

    fun getGroups(): Array<String> {
        return groups.keys.toTypedArray()
    }

    fun addPlayerPermission(player: String, permission: String): Boolean {
        this.permissions[player]!!.add(permission)
        if (!plugin.server.pluginManager.permissions.contains(Permission(permission))) plugin.server.pluginManager.addPermission(
            Permission(permission))
        return this.permissions[player]!!.contains(permission)
    }

    fun removePlayerPermission(player: String, permission: String): Boolean {
        this.permissions[player]!!.remove(permission)
        plugin.server.pluginManager.removePermission(permission)
        return !this.permissions[player]!!.contains(permission)
    }

    fun addGroupPermission(group: String, permission: String): Boolean {
        this.groups[group]!!.permissions.add(permission)
        if (!plugin.server.pluginManager.permissions.contains(Permission(permission))) plugin.server.pluginManager.addPermission(
            Permission(permission))
        return this.groups[group]!!.permissions.contains(permission)
    }

    fun removeGroupPermission(group: String, permission: String): Boolean {
        this.groups[group]!!.permissions.remove(permission)
        plugin.server.pluginManager.removePermission(permission)
        return !this.groups[group]!!.permissions.contains(permission)
    }

    fun inGroup(player: String, group: String): Boolean {
        return this.groups[group]!!.players.contains(player)
    }

    fun addPlayerGroup(player: String, group: String): Boolean {
        this.players[player]!!.groups.add(this.groups[group]!!)
        return this.players[player]!!.groups.contains(this.groups[group]!!)
    }

    fun removePlayerGroup(player: String, group: String): Boolean {
        this.players[player]!!.groups.remove(this.groups[group]!!)
        return !this.players[player]!!.groups.contains(this.groups[group]!!)
    }
}
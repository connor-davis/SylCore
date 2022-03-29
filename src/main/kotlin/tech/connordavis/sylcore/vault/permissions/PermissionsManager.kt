package tech.connordavis.sylcore.vault.permissions

import org.bukkit.permissions.PermissionAttachment
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.utils.Prefixes
import tech.connordavis.sylcore.utils.from


class PermissionsManager {
    private val plugin = SylCorePlugin.instance
    private val fileManager = plugin.getFileManager()

    private val groups: MutableMap<String, Group> = mutableMapOf()
    private val players: MutableMap<String, Player> = mutableMapOf()

    fun createGroup(rank: Group): Boolean {
        return try {
            if (this.groups[rank.name] == null) {
                this.groups[rank.name] = rank
                saveGroup(rank)
                true
            } else false
        } catch (exception: Exception) {
            false
        }
    }

    fun removeGroup(rank: String): Boolean {
        return try {
            this.groups.remove(rank)
            true
        } catch (exception: Exception) {
            false
        }
    }

    fun createPlayer(player: Player): Boolean {
        return try {
            if (this.players[player.name] == null) {
                this.players[player.name] = player
                true
            } else false
        } catch (exception: Exception) {
            false
        }
    }

    fun removePlayer(name: String): Boolean {
        return try {
            this.groups.remove(name)
            true
        } catch (exception: Exception) {
            false
        }
    }

    fun playerHas(name: String, permission: String): Boolean {
        return this.players[name]!!.permissions.contains(permission)
    }

    fun groupHas(name: String, permission: String): Boolean {
        return groups[name]!!.permissions.contains(permission)
    }

    fun playerGroups(player: String): MutableList<String> {
        return players[player]!!.groups
    }

    fun getGroupsNames(): Array<String> {
        return groups.keys.toTypedArray()
    }

    fun getGroups(): MutableMap<String, Group> {
        return groups
    }

    fun getPlayers(): MutableMap<String, Player> {
        return players
    }

    fun addPlayerPermission(name: String, permission: String): Boolean {
        if (!this.players[name]!!.permissions.contains(permission)) {
            this.players[name]!!.permissions.add(permission)

            val attachment: PermissionAttachment = plugin.server.getPlayer(name)!!.addAttachment(plugin)

            attachment.setPermission(permission, true)
            this.savePlayer(this.players[name]!!)
        }
        return this.players[name]!!.permissions.contains(permission)
    }

    fun removePlayerPermission(name: String, permission: String): Boolean {
        this.players[name]!!.permissions.remove(permission)

        val attachment: PermissionAttachment = plugin.server.getPlayer(name)!!.addAttachment(plugin)

        attachment.setPermission(permission, false)
        this.savePlayer(this.players[name]!!)

        return !this.players[name]!!.permissions.contains(permission)
    }

    fun addGroupPermission(group: String, permission: String): Boolean {
        if (!this.groups[group]!!.permissions.contains(permission)) {
            this.groups[group]!!.permissions.add(permission)

            this.players.forEach { (name, player) ->
                if (player.groups.contains(group)) {
                    val attachment: PermissionAttachment = plugin.server.getPlayer(name)!!.addAttachment(plugin)
                    attachment.setPermission(permission, true)
                }
            }

            saveGroup(this.groups[group]!!)
        }

        return this.groups[group]!!.permissions.contains(permission)
    }

    fun removeGroupPermission(group: String, permission: String): Boolean {
        this.groups[group]!!.permissions.remove(permission)

        this.players.forEach { (name, player) ->
            if (player.groups.contains(group)) {
                val attachment: PermissionAttachment = plugin.server.getPlayer(name)!!.addAttachment(plugin)
                attachment.setPermission(permission, false)
            }
        }

        saveGroup(this.groups[group]!!)

        return !this.groups[group]!!.permissions.contains(permission)
    }

    fun inGroup(name: String, group: String): Boolean {
        return this.players[name]!!.groups.contains(group)
    }

    fun addPlayerGroup(name: String, group: String): Boolean {
        if (!this.players[name]!!.groups.contains(group)) {
            this.players[name]!!.groups.add(group)
            savePlayer(this.players[name]!!)
        }

        return this.players[name]!!.groups.contains(group)
    }

    fun removePlayerGroup(name: String, group: String): Boolean {
        this.players[name]!!.groups.remove(group)
        savePlayer(this.players[name]!!)

        return !this.players[name]!!.groups.contains(group)
    }

    fun playerExists(name: String): Boolean {
        return this.players.containsKey(name)
    }

    fun groupExists(name: String): Boolean {
        return this.groups.containsKey(name)
    }

    private fun savePlayer(player: Player) {
        val playersFile = fileManager.getFile("players")!!
        val playersConfig = playersFile.getConfig()

        playersConfig.set("${player.name}.name", player.name)
        playersConfig.set("${player.name}.permissions", player.permissions)
        playersConfig.set("${player.name}.groups", player.groups)

        playersFile.saveFile()
    }

    private fun saveGroup(group: Group) {
        val groupsFile = fileManager.getFile("groups")!!
        val groupsConfig = groupsFile.getConfig()

        groupsConfig.set("${group.name}.name", group.name)
        groupsConfig.set("${group.name}.permissions", group.permissions)

        groupsFile.saveFile()
    }

    fun loadPlayers() {
        val playersFile = fileManager.getFile("players")!!
        val playersConfig = playersFile.getConfig()

        for (key in playersConfig.getKeys(false)) {
            val playerName = playersConfig.getString("$key.name")!!
            val playerGroups: MutableList<String> = playersConfig.getStringList("$key.groups")
            val playerPermissions: MutableList<String> = playersConfig.getStringList("$key.permissions")

            val player = Player(playerName, playerGroups, playerPermissions)

            this.players.put(key, player)
        }

        plugin.server.consoleSender.from(Prefixes.RANKS, "Loaded ${this.players.size} players.")
    }

    fun loadGroups() {
        val groupsFile = fileManager.getFile("groups")!!
        val groupsConfig = groupsFile.getConfig()

        for (key in groupsConfig.getKeys(false)) {
            val groupName = groupsConfig.getString("$key.name")!!
            val groupPermissions = groupsConfig.getStringList("$key.permissions")

            val group = Group(groupName, groupPermissions)

            this.groups.put(key, group)
        }

        plugin.server.consoleSender.from(Prefixes.RANKS, "Loaded ${this.groups.size} ranks.")
    }
}
package tech.connordavis.sylcore.vault.permissions

import net.milkbowl.vault.permission.Permission

class SylPermissions : Permission() {
    private val permissionsManager: PermissionsManager = PermissionsManager()

    override fun getName(): String {
        return "SylPermissions"
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun hasSuperPermsCompat(): Boolean {
        return true
    }

    override fun playerHas(world: String?, player: String?, permission: String?): Boolean {
        return permissionsManager.playerHas(player!!, permission!!)
    }

    override fun playerAdd(world: String?, player: String?, permission: String?): Boolean {
        return permissionsManager.addPlayerPermission(player!!, permission!!)
    }

    override fun playerRemove(world: String?, player: String?, permission: String?): Boolean {
        return permissionsManager.removePlayerPermission(player!!, permission!!)
    }

    override fun groupHas(world: String?, group: String?, permission: String?): Boolean {
        return permissionsManager.groupHas(group!!, permission!!)
    }

    override fun groupAdd(world: String?, group: String?, permission: String?): Boolean {
        return permissionsManager.addGroupPermission(group!!, permission!!)
    }

    override fun groupRemove(world: String?, group: String?, permission: String?): Boolean {
        return permissionsManager.removeGroupPermission(group!!, permission!!)
    }

    override fun playerInGroup(world: String?, player: String?, group: String?): Boolean {
        return permissionsManager.inGroup(player!!, group!!)
    }

    override fun playerAddGroup(world: String?, player: String?, group: String?): Boolean {
        return permissionsManager.addPlayerGroup(player!!, group!!)
    }

    override fun playerRemoveGroup(world: String?, player: String?, group: String?): Boolean {
        return permissionsManager.removePlayerGroup(player!!, group!!)
    }

    override fun getPlayerGroups(world: String?, player: String?): Array<String> {
        return permissionsManager.playerGroups(player!!).toTypedArray()
    }

    override fun getPrimaryGroup(world: String?, player: String?): String {
        return permissionsManager.playerGroups(player!!)[0]
    }

    override fun getGroups(): Array<String> {
        return permissionsManager.getGroupsNames()
    }

    override fun hasGroupSupport(): Boolean {
        return true
    }
}
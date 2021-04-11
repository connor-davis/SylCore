package tech.connordavis.sylcore.vault.permissions

import org.bukkit.permissions.Permissible
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.plugin.Plugin

class Perm : Permissible {
    override fun isOp(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setOp(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isPermissionSet(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPermissionSet(p0: Permission): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasPermission(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasPermission(p0: Permission): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAttachment(p0: Plugin, p1: String, p2: Boolean): PermissionAttachment {
        TODO("Not yet implemented")
    }

    override fun addAttachment(p0: Plugin): PermissionAttachment {
        TODO("Not yet implemented")
    }

    override fun addAttachment(p0: Plugin, p1: String, p2: Boolean, p3: Int): PermissionAttachment? {
        TODO("Not yet implemented")
    }

    override fun addAttachment(p0: Plugin, p1: Int): PermissionAttachment? {
        TODO("Not yet implemented")
    }

    override fun removeAttachment(p0: PermissionAttachment) {
        TODO("Not yet implemented")
    }

    override fun recalculatePermissions() {
        TODO("Not yet implemented")
    }

    override fun getEffectivePermissions(): MutableSet<PermissionAttachmentInfo> {
        TODO("Not yet implemented")
    }
}
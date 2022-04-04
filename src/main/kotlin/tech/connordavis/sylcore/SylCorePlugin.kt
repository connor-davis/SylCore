package tech.connordavis.sylcore

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import tech.connordavis.sylcore.managers.CommandManager
import tech.connordavis.sylcore.managers.FileManager
import tech.connordavis.sylcore.managers.StaffChatManager
import tech.connordavis.sylcore.modules.SylModuleLoader
import tech.connordavis.sylcore.modules.SylModuleManager
import tech.connordavis.sylcore.network.Network
import tech.connordavis.sylcore.utils.Prefixes
import tech.connordavis.sylcore.utils.from
import tech.connordavis.sylcore.vault.economy.EconomyManager
import tech.connordavis.sylcore.vault.economy.SylEconomy
import tech.connordavis.sylcore.vault.permissions.PermissionsManager
import tech.connordavis.sylcore.vault.permissions.SylPermissions

class SylCorePlugin : JavaPlugin() {
    companion object {
        lateinit var instance: SylCorePlugin
            private set
        private lateinit var commandManager: CommandManager
        private lateinit var fileManager: FileManager
        private lateinit var economyManager: EconomyManager
        private lateinit var permissionsManager: PermissionsManager
        private lateinit var staffChatManager: StaffChatManager
        private lateinit var moduleManager: SylModuleManager
        private lateinit var network: Network
    }

    private lateinit var economy: SylEconomy
    lateinit var permissions: SylPermissions

    override fun onLoad() {
        instance = this
        commandManager = CommandManager(instance)
        fileManager = FileManager(instance)
        economy = SylEconomy()
        economyManager = EconomyManager()
        permissions = SylPermissions()
        permissionsManager = PermissionsManager()
        staffChatManager = StaffChatManager(instance)
        moduleManager = SylModuleManager()

        SylModuleLoader
    }

    override fun onEnable() {
        Registrations.registerFiles()

        network = Network.init()

        Registrations.registerCommands()

        economyManager.loadAccounts()
        economyManager.loadBanks()

        permissionsManager.loadPlayers()
        permissionsManager.loadGroups()

        if (!setupVault()) {
            server.consoleSender.from(Prefixes.CORE, "Plugin disabled because Vault was not found.")
            server.pluginManager.disablePlugin(this)
            return
        }

        Registrations.registerEvents()

        moduleManager.enableModules()
    }

    override fun onDisable() {
        moduleManager.disableModules()

        fileManager.getFiles().clear()
        commandManager.getCommands().clear()
        staffChatManager.getStaffChatPlayers().clear()

        network.close()
    }

    private fun setupVault(): Boolean {
        if (server.pluginManager.getPlugin("Vault") == null) {
            return false
        }

        server.servicesManager.register(Economy::class.java, economy, this, ServicePriority.Highest)
        server.consoleSender.from(Prefixes.CORE, "Economy has been registered with Vault.")

        server.servicesManager.register(Permission::class.java, permissions, this, ServicePriority.Highest)
        server.consoleSender.from(Prefixes.CORE, "Permissions has been registered with Vault.")

        return true
    }

    fun getPermissionsManager(): PermissionsManager {
        return permissionsManager
    }

    fun getEconomyManager(): EconomyManager {
        return economyManager
    }

    fun getFileManager(): FileManager {
        return fileManager
    }

    fun getCommandManager(): CommandManager {
        return commandManager
    }

    fun getStaffChatManager(): StaffChatManager {
        return staffChatManager
    }

    fun getModuleManager(): SylModuleManager {
        return moduleManager
    }

    fun getNetwork(): Network {
        return network
    }

    fun performReload() {
        server.pluginManager.disablePlugin(this)

        network = Network.init()

        SylModuleLoader

        server.pluginManager.enablePlugin(this)
    }
}

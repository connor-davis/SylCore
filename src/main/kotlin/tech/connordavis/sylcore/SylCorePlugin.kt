package tech.connordavis.sylcore

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import tech.connordavis.sylcore.commands.*
import tech.connordavis.sylcore.events.Chat
import tech.connordavis.sylcore.events.PlayerJoin
import tech.connordavis.sylcore.files.*
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
        lateinit var commandManager: CommandManager
            private set
        lateinit var fileManager: FileManager
            private set
        lateinit var economyManager: EconomyManager
            private set
        lateinit var permissionsManager: PermissionsManager
            private set
        lateinit var staffChatManager: StaffChatManager
            private set
        lateinit var moduleManager: SylModuleManager
            private set
        lateinit var network: Network
            private set
    }

    var economy: SylEconomy
    var permissions: SylPermissions

    init {
        instance = this
        commandManager = CommandManager(instance)
        fileManager = FileManager(instance)
        economy = SylEconomy()
        economyManager = EconomyManager()
        permissions = SylPermissions()
        permissionsManager = PermissionsManager()
        staffChatManager = StaffChatManager(instance)
        moduleManager = SylModuleManager()
    }

    override fun onLoad() {
        /**
         * Module Loader
         * Here we load SylModules
         */
        SylModuleLoader
    }

    override fun onEnable() {
        registerFiles();

        registerCommands();

        economyManager.loadAccounts()
        economyManager.loadBanks()

        permissionsManager.loadPlayers()
        permissionsManager.loadGroups()

        if (!setupVault()) {
            server.consoleSender.from(Prefixes.CORE, "Plugin disabled because Vault was not found.")
            server.pluginManager.disablePlugin(this)
            return
        }

        registerEvents()

        enableModules()

        network = Network.init()
    }

    override fun onDisable() {
        disableModules()

        fileManager.getFiles().clear()
        commandManager.getCommands().clear()
        staffChatManager.getStaffChatPlayers().clear()
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(PlayerJoin(), this)
        server.pluginManager.registerEvents(Chat, this)
    }

    private fun registerFiles() {
        fileManager.addFile("config", Config())
        fileManager.addFile("serverSpawn", ServerSpawn())
        fileManager.addFile("accounts", Accounts())
        fileManager.addFile("banks", Banks())
        fileManager.addFile("groups", Groups())
        fileManager.addFile("players", Players())
        fileManager.addFile("homes", Homes())
        fileManager.addFile("network", NetworkFile())

        fileManager.loadFiles()
    }

    private fun registerCommands() {
        commandManager.addCommand("gamemode", GameModeCommand())
        commandManager.addCommand("time", TimeCommand())
        commandManager.addCommand("spawn", SpawnCommand())
        commandManager.addCommand("ranks", RanksCommand())
        commandManager.addCommand("home", HomeCommand())
        commandManager.addCommand("staffchat", StaffChatCommand())
        commandManager.addCommand("sylreload", ReloadCommand())

        commandManager.registerCommands()
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

    private fun enableModules() {
        server.consoleSender.from(Prefixes.MODULE_MANAGER, "Enabling modules.")

        var enabledCount = 0

        moduleManager.moduleList.forEach { module ->
            run {
                if (module.description.dependencies.isNotEmpty()) {
                    module.description.dependencies.forEach { dependency ->
                        if (!moduleManager.exists(dependency)) {
                            server.consoleSender.from(Prefixes.MODULE_MANAGER,
                                "$9$dependency &7does not exist for module &9${module.description.name}&7, disabling plugin.")
                            moduleManager.remove(module)

                            return@run
                        } else {
                            module.onEnable()

                            enabledCount++
                        }
                    }
                } else {
                    module.onEnable()

                    enabledCount++
                }
            }
        }

        server.consoleSender.from(Prefixes.MODULE_MANAGER, "Enabled &9${enabledCount} &7modules.")
    }

    private fun disableModules() {
        server.consoleSender.from(Prefixes.MODULE_MANAGER, "Disabling modules.")

        var disabledCount = 0

        moduleManager.moduleList.forEach { module ->
            module.onDisable()

            disabledCount++
        }

        server.consoleSender.from(Prefixes.MODULE_MANAGER, "Disabled &9${disabledCount} &7modules.")
    }

    fun performReload() {
        server.pluginManager.disablePlugin(this)

        SylModuleLoader

        server.pluginManager.enablePlugin(this)
    }
}

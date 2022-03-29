package tech.connordavis.sylcore

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import tech.connordavis.sylcore.commands.*
import tech.connordavis.sylcore.events.PlayerJoin
import tech.connordavis.sylcore.files.*
import tech.connordavis.sylcore.managers.CommandManager
import tech.connordavis.sylcore.managers.FileManager
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
    }

    var economy: SylEconomy
    var permissions: SylPermissions

    init {
        instance = this
        commandManager = CommandManager(instance)
        fileManager = FileManager(this)
        economy = SylEconomy()
        economyManager = EconomyManager()
        permissions = SylPermissions()
        permissionsManager = PermissionsManager()
    }

    override fun onEnable() {
        // Files
        registerFiles();

        // Commands
        registerCommands();

        // Economy
        economyManager.loadAccounts()
        economyManager.loadBanks()

        // Permissions
        permissionsManager.loadPlayers()
        permissionsManager.loadGroups()

        if (!setupVault()) {
            logger.info { "Plugin disabled because Vault was not found." }
            server.pluginManager.disablePlugin(this)
            return
        }

        registerEvents()
    }

    override fun onDisable() {
        // Files
        fileManager.getFiles().clear()
        // Commands
        commandManager.getCommands().clear()
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(PlayerJoin(), this)
    }

    private fun registerFiles() {
        fileManager.addFile("config", Config())
        fileManager.addFile("serverSpawn", ServerSpawn())
        fileManager.addFile("accounts", Accounts())
        fileManager.addFile("banks", Banks())
        fileManager.addFile("groups", Groups())
        fileManager.addFile("players", Players())
        fileManager.addFile("homes", Homes())

        fileManager.loadFiles()
    }

    private fun registerCommands() {
        commandManager.addCommand("gamemode", GameModeCommand())
        commandManager.addCommand("time", TimeCommand())
        commandManager.addCommand("spawn", SpawnCommand())
        commandManager.addCommand("ranks", RanksCommand())
        commandManager.addCommand("home", HomeCommand())
        commandManager.addCommand("staffchat", StaffChatCommand())

        commandManager.registerCommands()
    }

    private fun setupVault(): Boolean {
        if (server.pluginManager.getPlugin("Vault") == null) {
            return false
        }

        server.servicesManager.register(Economy::class.java, economy, this, ServicePriority.Highest)
        logger.info { "Economy has been registered with Vault." }

        server.servicesManager.register(
                Permission::class.java,
                permissions,
                this,
                ServicePriority.Highest
        )
        logger.info { "Permissions has been registered with Vault." }

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
}

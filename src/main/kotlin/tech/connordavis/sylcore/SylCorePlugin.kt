package tech.connordavis.sylcore

import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import tech.connordavis.sylcore.commands.GameModeCommand
import tech.connordavis.sylcore.commands.SpawnCommand
import tech.connordavis.sylcore.commands.TimeCommand
import tech.connordavis.sylcore.events.PlayerJoin
import tech.connordavis.sylcore.files.Accounts
import tech.connordavis.sylcore.files.Banks
import tech.connordavis.sylcore.files.Config
import tech.connordavis.sylcore.files.ServerSpawn
import tech.connordavis.sylcore.managers.CommandManager
import tech.connordavis.sylcore.managers.FileManager
import tech.connordavis.sylcore.vault.SylEconomy
import tech.connordavis.sylcore.vault.economy.EconomyManager


class SylCorePlugin : JavaPlugin() {
    companion object {
        lateinit var instance: SylCorePlugin private set
        lateinit var commandManager: CommandManager private set
        lateinit var fileManager: FileManager private set
        lateinit var economyManager: EconomyManager private set
    }

    var economy: SylEconomy

    init {
        instance = this
        commandManager = CommandManager(instance)
        fileManager = FileManager(this)
        economyManager = EconomyManager()

        economy = SylEconomy()
    }

    override fun onEnable() {
        // Files
        fileManager.addFile("config", Config())
        fileManager.addFile("serverSpawn", ServerSpawn())
        fileManager.addFile("accounts", Accounts())
        fileManager.addFile("banks", Banks())

        fileManager.loadFiles()

        // Commands
        commandManager.addCommand("gamemode", GameModeCommand())
        commandManager.addCommand("time", TimeCommand())
        commandManager.addCommand("spawn", SpawnCommand())

        commandManager.registerCommands()

        if (!setupEconomy()) {
            logger.info { "Plugin disabled because Vault was not found." }
            server.pluginManager.disablePlugin(this);
            return;
        }

        registerEvents()
    }

    override fun onDisable() {
        // Files
        fileManager.getFiles().forEach { (name, file) ->
            file.saveFile()
            fileManager.removeFile(name)
        }

        // Commands
        commandManager.getCommands().forEach { (name, _) -> commandManager.removeCommand(name) }
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(PlayerJoin(), this)
    }

    private fun setupEconomy(): Boolean {
        if (server.pluginManager.getPlugin("Vault") == null) {
            return false
        }
        server.servicesManager.register(Economy::class.java, economy, this, ServicePriority.Highest)
        logger.info { "Economy has been registered with Vault." }
        return true
    }

    fun getFileManager(): FileManager {
        return fileManager
    }
}
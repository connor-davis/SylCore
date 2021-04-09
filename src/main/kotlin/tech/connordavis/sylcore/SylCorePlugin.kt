package tech.connordavis.sylcore

import org.bukkit.plugin.java.JavaPlugin
import tech.connordavis.sylcore.managers.CommandManager

class SylCorePlugin : JavaPlugin() {
    companion object {
        lateinit var instance: SylCorePlugin private set
        lateinit var commandManager: CommandManager private set
    }

    init {
        instance = this
        commandManager = CommandManager(instance)
    }

    override fun onEnable() {
        commandManager.registerCommands()
    }

    override fun onDisable() {
        commandManager.getCommands().forEach { (name, _) -> commandManager.removeCommand(name) }
    }
}
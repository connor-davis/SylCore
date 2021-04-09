package tech.connordavis.sylcore.managers

import org.bukkit.craftbukkit.v1_16_R3.CraftServer
import org.bukkit.plugin.java.JavaPlugin
import tech.connordavis.sylcore.commands.GameModeCommand
import tech.connordavis.sylcore.utils.Command

class CommandManager(private val plugin: JavaPlugin) {
    private var commands: MutableMap<String, Command> = mutableMapOf()

    init {
        "gamemode".addCommand(GameModeCommand())
    }

    private fun String.addCommand(command: Command) {
        this@CommandManager.commands.putIfAbsent(this, command)
    }

    fun removeCommand(name: String) {
        this.commands.remove(name)
    }

    fun registerCommands() {
        plugin.logger.info("Registering commands.")

        this.commands.forEach { (name, command) ->
            (plugin.server as CraftServer).commandMap.register(name, command)
        }

        plugin.logger.info("Registered ${this.commands.size} commands.")
    }

    fun getCommands(): MutableMap<String, Command> {
        return this.commands
    }
}

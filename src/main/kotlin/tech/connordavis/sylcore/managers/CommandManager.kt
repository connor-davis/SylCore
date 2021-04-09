package tech.connordavis.sylcore.managers

import org.bukkit.plugin.java.JavaPlugin
import tech.connordavis.sylcore.commands.TestCommand
import tech.connordavis.sylcore.utils.Command

class CommandManager(private val plugin: JavaPlugin) {
    private var commands: MutableMap<String, Command> = mutableMapOf()

    init {
        addCommand("test", TestCommand())
    }

    private fun addCommand(name: String, command: Command) {
        this.commands.putIfAbsent(name, command)
    }

    fun removeCommand(name: String) {
        this.commands.remove(name)
    }

    fun registerCommands() {
        this.commands.forEach { (name, command) ->
            plugin.getCommand(name)?.setExecutor { sender, _, label, args -> command.execute(sender, label, args) }
        }
    }

    fun getCommands(): MutableMap<String, Command> {
        return this.commands
    }
}

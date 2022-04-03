package tech.connordavis.sylcore.managers

import org.bukkit.craftbukkit.v1_18_R2.CraftServer
import org.bukkit.plugin.java.JavaPlugin
import tech.connordavis.sylcore.utils.Command

class CommandManager(private val plugin: JavaPlugin) {
    private var commands: MutableMap<String, Command> = mutableMapOf()

    fun addCommand(name: String, command: Command) {
        this.commands[name] = command
    }

    fun removeCommand(name: String) {
        this.commands.remove(name)
    }

    fun registerCommands() {
        plugin.logger.info("Registering commands.")

        this.commands.forEach { (name, command) ->
            (plugin.server as CraftServer).commandMap.register(name, command)

            plugin.getCommand(name)?.setExecutor { commandSender, _, commandLabel, args ->
                command.execute(commandSender,
                    commandLabel,
                    args)
            }

            plugin.getCommand(name)?.setTabCompleter { commandSender, cmd, commandLabel, args ->
                command.onTabComplete(commandSender, cmd, commandLabel, args)
            }
        }

        plugin.logger.info("Registered ${this.commands.size} commands.")
    }

    fun getCommands(): MutableMap<String, Command> {
        return this.commands
    }

    fun getPlugin(): JavaPlugin {
        return plugin
    }
}


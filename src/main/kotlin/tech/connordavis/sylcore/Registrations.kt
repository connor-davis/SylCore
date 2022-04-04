package tech.connordavis.sylcore

import org.bukkit.plugin.PluginManager
import tech.connordavis.sylcore.commands.*
import tech.connordavis.sylcore.events.Chat
import tech.connordavis.sylcore.events.PlayerJoin
import tech.connordavis.sylcore.files.*
import tech.connordavis.sylcore.managers.CommandManager
import tech.connordavis.sylcore.managers.FileManager

object Registrations {
    private val plugin: SylCorePlugin = SylCorePlugin.instance
    private val pluginManager: PluginManager = plugin.server.pluginManager
    private val commandManager: CommandManager = plugin.getCommandManager()
    private val fileManager: FileManager = plugin.getFileManager()

    fun registerCommands() {
        commandManager.addCommand("gamemode", GameModeCommand())
        commandManager.addCommand("time", TimeCommand())
        commandManager.addCommand("spawn", SpawnCommand())
        commandManager.addCommand("ranks", RanksCommand())
        commandManager.addCommand("home", HomeCommand())
        commandManager.addCommand("staffchat", StaffChatCommand())
        commandManager.addCommand("sylreload", ReloadCommand())

        commandManager.registerCommands()
    }

    fun registerFiles() {
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

    fun registerEvents() {
        pluginManager.registerEvents(PlayerJoin(), plugin)
        pluginManager.registerEvents(Chat, plugin)
    }
}
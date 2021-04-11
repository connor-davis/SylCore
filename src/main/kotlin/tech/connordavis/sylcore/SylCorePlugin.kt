package tech.connordavis.sylcore

import org.bukkit.plugin.java.JavaPlugin
import tech.connordavis.sylcore.commands.GameModeCommand
import tech.connordavis.sylcore.commands.SpawnCommand
import tech.connordavis.sylcore.commands.TimeCommand
import tech.connordavis.sylcore.files.Config
import tech.connordavis.sylcore.files.ServerSpawn
import tech.connordavis.sylcore.managers.CommandManager
import tech.connordavis.sylcore.managers.FileManager

class SylCorePlugin : JavaPlugin() {
    companion object {
        lateinit var instance: SylCorePlugin private set
        lateinit var commandManager: CommandManager private set
        lateinit var fileManager: FileManager private set
    }

    init {
        instance = this
        commandManager = CommandManager(instance)
        fileManager = FileManager(this)
    }

    override fun onEnable() {
        // Files
        fileManager.addFile("config", Config())
        fileManager.addFile("serverSpawn", ServerSpawn())

        fileManager.loadFiles()

        // Commands
        commandManager.addCommand("gamemode", GameModeCommand())
        commandManager.addCommand("time", TimeCommand())
        commandManager.addCommand("spawn", SpawnCommand())

        commandManager.registerCommands()
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

    fun getFileManager(): FileManager {
        return fileManager
    }
}
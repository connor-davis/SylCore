package tech.connordavis.sylcore.managers

import org.bukkit.plugin.java.JavaPlugin
import tech.connordavis.sylcore.utils.YamlFile

class FileManager(private val plugin: JavaPlugin) {
    private var files: MutableMap<String, YamlFile> = mutableMapOf()

    fun addFile(name: String, file: YamlFile) {
        this.files[name] = file
    }

    fun removeFile(name: String) {
        this.files.remove(name)
    }

    fun loadFiles() {
        plugin.logger.info("Loading files.")

        this.files.forEach { (_, file) ->
            file.loadFile()
        }

        plugin.logger.info("Loaded ${this.files.size} files.")
    }

    fun getFile(name: String): YamlFile? {
        return this.files[name]
    }

    fun getFiles(): MutableMap<String, YamlFile> {
        return this.files
    }

    fun getPlugin(): JavaPlugin {
        return plugin
    }
}
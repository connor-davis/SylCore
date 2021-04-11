package tech.connordavis.sylcore.utils

import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

abstract class YamlFile(private val plugin: JavaPlugin, private val fileName: String) {
    private val directory = plugin.dataFolder
    private val file = File(directory, fileName)
    private val yaml = YamlConfiguration()

    fun saveFile() {
        if (!directory.exists()) directory.mkdirs()
        if (!file.exists()) file.createNewFile()

        try {
            yaml.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getConfig(): YamlConfiguration {
        return yaml
    }

    fun loadFile() {
        if (!directory.exists()) directory.mkdirs()
        if (!file.exists()) file.createNewFile()

        try {
            yaml.load(file)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InvalidConfigurationException) {
            e.printStackTrace()
        }
    }

    fun getFileName(): String {
        return fileName
    }

    fun getPlugin(): JavaPlugin {
        return plugin
    }
}
package tech.connordavis.sylcore.utils

import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

abstract class YamlFile(plugin: JavaPlugin, fileName: String) {
    private val directory = plugin.dataFolder
    private val file = File(directory, fileName)
    private val yaml = YamlConfiguration()

    init {
        if (!directory.exists()) directory.mkdirs()
        if (!file.exists()) plugin.saveResource(fileName, false)

        try {
            yaml.load(file)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InvalidConfigurationException) {
            e.printStackTrace()
        }
    }

    fun saveFile() {
        try {
            yaml.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun get(): YamlConfiguration {
        return yaml
    }
}
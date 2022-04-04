@file:Suppress("UNCHECKED_CAST")

package tech.connordavis.sylcore.modules

import org.yaml.snakeyaml.Yaml
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.utils.Prefixes
import tech.connordavis.sylcore.utils.from
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URLClassLoader
import java.nio.file.Paths
import java.util.jar.JarFile

/**
 * This object is responsible for loading SylCore Modules into the server.
 * @author lupinmc
 */
object SylModuleLoader {
    private val plugin = SylCorePlugin.instance
    private val moduleManager = plugin.getModuleManager()

    /**
     * This init gets all .jar files from /modules
     * directory and loads each as if it were a plugin.
     *
     * This will only load the .jar as a valid module
     * if it contains a module.yml file inside of it.
     */
    init {
        val path = Paths.get("").toAbsolutePath().toString()
        val modulesPath = "$path/plugins/SylCore/modules";
        var moduleCount = 0

        if (!File(modulesPath).exists()) File(modulesPath).mkdir()

        plugin.server.consoleSender.from(Prefixes.MODULE_LOADER, "Loading modules.")

        File(modulesPath).walk().forEach { file ->
            if (file.isFile) {
                val jar = JarFile(file)
                val input = jar.getInputStream(jar.getJarEntry("module.yml"))

                loadModule(file, input)

                moduleCount++
            }
        }

        plugin.server.consoleSender.from(Prefixes.MODULE_LOADER, "Loaded &9$moduleCount &7modules.")
    }

    /**
     * This function will load the main class from the jar file
     * if there is a valid 'main: ' property in the plugin.yml
     *
     * It takes in the SylCorePlugin instance ClassLoader to load the
     * main class of the jar file and if the main class can be
     * casted to SylModule, it will add the plugin to the
     * plugin list.
     *
     * @param file
     * @param stream
     */
    private fun loadModule(file: File, stream: InputStream) {
        val streamReader = InputStreamReader(stream)
        val bufferedReader = BufferedReader(streamReader)

        val yaml = Yaml()
        val yamlProperties = yaml.load<HashMap<String, Any>>(bufferedReader)
        val pluginProperties: MutableMap<String, Any> = mutableMapOf()

        yamlProperties.forEach { property -> pluginProperties[property.key] = property.value }

        val name: String = pluginProperties["name"].toString()
        val main: String = pluginProperties["main"].toString()
        val version: String = pluginProperties["version"].toString()
        val dependencies: List<String> =
            if (pluginProperties.containsKey("depend")) pluginProperties["depend"] as List<String> else emptyList()
        val softDependencies: List<String> =
            if (pluginProperties.containsKey("softdepend")) pluginProperties["softdepend"] as List<String> else emptyList()

        val classLoader = URLClassLoader(arrayOf(file.toURL()), SylCorePlugin::class.java.classLoader)
        val pluginClass = classLoader.loadClass(main)
        val sylModule: SylModule = pluginClass.getConstructor().newInstance() as SylModule

        sylModule.description = SylModuleDescription(
            name,
            main,
            version,
            dependencies,
            softDependencies
        )

        sylModule.onLoad()

        moduleManager.add(sylModule)
    }
}
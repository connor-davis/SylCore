package tech.connordavis.sylcore.modules

import org.bukkit.command.ConsoleCommandSender
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.utils.Prefixes
import tech.connordavis.sylcore.utils.from

class SylModuleManager {
    private val plugin: SylCorePlugin = SylCorePlugin.instance
    private val consoleSender: ConsoleCommandSender = plugin.server.consoleSender
    private val moduleList: ArrayList<SylModule> = ArrayList()

    fun add(module: SylModule) {
        moduleList.add(module)
    }

    private fun remove(module: SylModule) {
        moduleList.remove(module)
    }

    private fun exists(name: String): Boolean {
        return moduleList.find { module ->
            return module.description.name == name
        } !== null
    }

    fun enableModules() {
        consoleSender.from(Prefixes.MODULE_MANAGER, "Enabling modules.")

        var enabledCount = 0

        moduleList.forEach { module ->
            run {
                if (module.description.dependencies.isNotEmpty()) {
                    module.description.dependencies.forEach { dependency ->
                        if (!exists(dependency)) {
                            consoleSender.from(Prefixes.MODULE_MANAGER,
                                "$9$dependency &7does not exist for module &9${module.description.name}&7, disabling plugin.")
                            remove(module)

                            return@run
                        } else {
                            module.onEnable()

                            enabledCount++
                        }
                    }
                } else {
                    module.onEnable()

                    enabledCount++
                }
            }
        }

        consoleSender.from(Prefixes.MODULE_MANAGER, "Enabled &9${enabledCount} &7modules.")
    }

    fun disableModules() {
        consoleSender.from(Prefixes.MODULE_MANAGER, "Disabling modules.")

        var disabledCount = 0

        moduleList.forEach { module ->
            module.onDisable()

            disabledCount++
        }

        consoleSender.from(Prefixes.MODULE_MANAGER, "Disabled &9${disabledCount} &7modules.")
    }
}
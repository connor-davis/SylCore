package tech.connordavis.sylcore.modules

import org.bukkit.command.ConsoleCommandSender
import tech.connordavis.sylcore.utils.from

abstract class SylModule : ISylModule {
    lateinit var logger: ConsoleCommandSender
    lateinit var description: SylModuleDescription

    override fun onLoad() {
        super.onLoad()

        logger.from(description.name, "module loaded.")
    }

    override fun onEnable() {
        super.onEnable()

        logger.from(description.name, "module enabled.")
    }

    override fun onDisable() {
        super.onDisable()

        logger.from(description.name, "module disabled.")
    }
}
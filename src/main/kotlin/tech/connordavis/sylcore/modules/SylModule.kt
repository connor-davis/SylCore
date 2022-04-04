package tech.connordavis.sylcore.modules

import org.bukkit.Server
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.managers.CommandManager
import tech.connordavis.sylcore.managers.FileManager
import tech.connordavis.sylcore.managers.StaffChatManager
import tech.connordavis.sylcore.network.Network
import tech.connordavis.sylcore.utils.Logger
import tech.connordavis.sylcore.vault.economy.EconomyManager
import tech.connordavis.sylcore.vault.permissions.PermissionsManager

abstract class SylModule : ISylModule {
    private val core = SylCorePlugin.instance
    lateinit var description: SylModuleDescription

    lateinit var network: Network
    private lateinit var logger: Logger

    val server: Server = core.server
    val fileManager: FileManager = core.getFileManager()
    val commandManager: CommandManager = core.getCommandManager()
    val economyManager: EconomyManager = core.getEconomyManager()
    val permissionsManager: PermissionsManager = core.getPermissionsManager()
    val staffChatManager: StaffChatManager = core.getStaffChatManager()

    override fun onLoad() {
        super.onLoad()

        logger = Logger(description.name)

        logger.info("${description.name} v${description.version} loaded.")
    }

    override fun onEnable() {
        super.onEnable()

        network = Network.init()

        logger.info("${description.name} v${description.version} enabled.")
    }

    override fun onDisable() {
        super.onDisable()

        network.close()

        logger.info("${description.name} v${description.version} disabled.")
    }

    fun getLogger(): Logger {
        return logger
    }
}
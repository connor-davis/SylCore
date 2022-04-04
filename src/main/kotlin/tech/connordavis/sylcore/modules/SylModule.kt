package tech.connordavis.sylcore.modules

import org.bukkit.Server
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.managers.CommandManager
import tech.connordavis.sylcore.managers.FileManager
import tech.connordavis.sylcore.managers.StaffChatManager
import tech.connordavis.sylcore.network.Network
import tech.connordavis.sylcore.utils.from
import tech.connordavis.sylcore.vault.economy.EconomyManager
import tech.connordavis.sylcore.vault.permissions.PermissionsManager
import java.util.logging.Logger

abstract class SylModule : ISylModule {
    private val core = SylCorePlugin.instance
    lateinit var description: SylModuleDescription

    val logger: Logger = core.logger
    val server: Server = core.server
    private val consoleSender = server.consoleSender

    val network: Network = core.getNetwork()
    val fileManager: FileManager = core.getFileManager()
    val commandManager: CommandManager = core.getCommandManager()
    val economyManager: EconomyManager = core.getEconomyManager()
    val permissionsManager: PermissionsManager = core.getPermissionsManager()
    val staffChatManager: StaffChatManager = core.getStaffChatManager()

    override fun onLoad() {
        super.onLoad()

        consoleSender.from(description.name, "module loaded.")
    }

    override fun onEnable() {
        super.onEnable()

        consoleSender.from(description.name, "module enabled.")
    }

    override fun onDisable() {
        super.onDisable()

        consoleSender.from(description.name, "module disabled.")
    }
}
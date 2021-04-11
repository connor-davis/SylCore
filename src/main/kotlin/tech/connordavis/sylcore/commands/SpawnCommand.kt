package tech.connordavis.sylcore.commands

import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.utils.Command
import tech.connordavis.sylcore.utils.CommandInfo
import tech.connordavis.sylcore.utils.Message.Companion.from
import tech.connordavis.sylcore.utils.Prefixes

class SpawnCommand : Command(
    CommandInfo(
        "spawn",
        "This command lets players get to spawn and for staff to set the spawn.",
        aliases = arrayOf("setspawn")
    )
) {
    private val plugin = SylCorePlugin.instance
    private val fileManager = plugin.getFileManager()

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (sender !is Player) sender.from(Prefixes.TEST, "Only players can access that command.")
        else {
            when (commandLabel) {
                "spawn" -> {
                    val serverSpawnFile = fileManager.getFile("serverSpawn")
                    val serverSpawnConfig = serverSpawnFile?.getConfig()

                    val spawnLocation = Location(plugin.server.getWorld(serverSpawnConfig?.getString("world")!!),
                        serverSpawnConfig.getString("x")!!.toDouble(), serverSpawnConfig.getString("y")!!.toDouble(),
                        serverSpawnConfig.getString("z")!!.toDouble(), serverSpawnConfig.getString("yaw")!!.toFloat(),
                        serverSpawnConfig.getString("pitch")!!.toFloat())

                    sender.teleport(spawnLocation)
                    sender.from(Prefixes.CORE, "You have arrived at spawn.")
                }
                "setspawn" -> {
                    val spawnLocation = Location(sender.world,
                        sender.location.x,
                        sender.location.y,
                        sender.location.z,
                        sender.location.yaw,
                        sender.location.pitch)

                    val serverSpawnFile = fileManager.getFile("serverSpawn")

                    serverSpawnFile?.getConfig()?.set("world", spawnLocation.world?.name)
                    serverSpawnFile?.getConfig()?.set("x", "${spawnLocation.x}")
                    serverSpawnFile?.getConfig()?.set("y", "${spawnLocation.y}")
                    serverSpawnFile?.getConfig()?.set("z", "${spawnLocation.z}")
                    serverSpawnFile?.getConfig()?.set("yaw", "${spawnLocation.yaw}")
                    serverSpawnFile?.getConfig()?.set("pitch", "${spawnLocation.pitch}")

                    serverSpawnFile?.saveFile()

                    sender.from(Prefixes.CORE, "Server spawn set at your position.")
                }
            }
        }
        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: org.bukkit.command.Command,
        commandLabel: String,
        args: Array<out String>,
    ): MutableList<String> {
        return mutableListOf("setspawn")
    }

}

package tech.connordavis.sylcore.commands

import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.utils.Command
import tech.connordavis.sylcore.utils.CommandInfo
import tech.connordavis.sylcore.utils.Prefixes
import tech.connordavis.sylcore.utils.from

class HomeCommand : Command(CommandInfo(
    "home",
    "This command lets staff work with ranks and permissions.",
    aliases = arrayOf("homes", "addhome", "delhome"))) {
    private val plugin = SylCorePlugin.instance
    private val fileManager = plugin.getFileManager()

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) sender.sendMessage("Only players can use this command.")
        else {
            val homesFile = fileManager.getFile("homes")!!
            val homesConfig = homesFile.getConfig()

            when (commandLabel) {
                "home" -> {
                    if (args.isEmpty()) {
                        for ((index, home) in homesConfig.getConfigurationSection(sender.name)!!.getKeys(false)
                            .withIndex()) {
                            if (index < 1) {
                                val homeLocation =
                                    Location(plugin.server.getWorld(homesConfig.getString("${sender.name}.$home.world")!!),
                                        homesConfig.getDouble("${sender.name}.$home.x"),
                                        homesConfig.getDouble("${sender.name}.$home.y"),
                                        homesConfig.getDouble("${sender.name}.$home.z"),
                                        homesConfig.getDouble("${sender.name}.$home.yaw").toFloat(),
                                        homesConfig.getDouble("${sender.name}.$home.pitch").toFloat())

                                sender.teleport(homeLocation)
                                sender.from(Prefixes.NOTHING, "Welcome home.")
                            } else {
                                return false
                            }
                        }
                        return false
                    } else {
                        val homeName = args[0]

                        val homeLocation =
                            Location(plugin.server.getWorld(homesConfig.getString("${sender.name}.$homeName.world")!!),
                                homesConfig.getDouble("${sender.name}.$homeName.x"),
                                homesConfig.getDouble("${sender.name}.$homeName.y"),
                                homesConfig.getDouble("${sender.name}.$homeName.z"),
                                homesConfig.getDouble("${sender.name}.$homeName.yaw").toFloat(),
                                homesConfig.getDouble("${sender.name}.$homeName.pitch").toFloat())

                        sender.teleport(homeLocation)
                        sender.from(Prefixes.NOTHING, "Welcome home.")

                        return false
                    }
                }
                "addhome" -> {
                    if (args.isNotEmpty()) {
                        val homeName = args[0]
                        val homes = homesConfig.getConfigurationSection(sender.name)!!.getKeys(false).size

                        if (homes > 4) {
                            when {
                                sender.hasPermission("sylcore.command.sethome.extra") -> {
                                    homesConfig.set("${sender.name}.${homeName}.world", sender.location.world!!.name)
                                    homesConfig.set("${sender.name}.${homeName}.x", sender.location.x)
                                    homesConfig.set("${sender.name}.${homeName}.y", sender.location.y)
                                    homesConfig.set("${sender.name}.${homeName}.z", sender.location.z)
                                    homesConfig.set("${sender.name}.${homeName}.yaw", sender.location.yaw)
                                    homesConfig.set("${sender.name}.${homeName}.pitch", sender.location.pitch)
                                    homesFile.saveFile()

                                    sender.from(Prefixes.NOTHING, "$homeName home set at your location.")
                                    return false
                                }

                                else -> {
                                    sender.from(Prefixes.CORE,
                                        "You need to upgrade your rank in order to set more homes.")
                                    return false
                                }
                            }
                        } else {
                            homesConfig.set("${sender.name}.${homeName}.world", sender.location.world!!.name)
                            homesConfig.set("${sender.name}.${homeName}.x", sender.location.x)
                            homesConfig.set("${sender.name}.${homeName}.y", sender.location.y)
                            homesConfig.set("${sender.name}.${homeName}.z", sender.location.z)
                            homesConfig.set("${sender.name}.${homeName}.yaw", sender.location.yaw)
                            homesConfig.set("${sender.name}.${homeName}.pitch", sender.location.pitch)
                            homesFile.saveFile()

                            sender.from(Prefixes.NOTHING, "$homeName home set at your location.")
                            return false
                        }
                    }
                }
                "delhome" -> {
                    if (args.isNotEmpty()) {
                        val homeName = args[0]

                        homesConfig.set("${sender.name}.${homeName}", null)
                        homesFile.saveFile()

                        sender.from(Prefixes.NOTHING, "$homeName home was removed.")
                        return false
                    }
                }
                "homes" -> {
                    var index = 1

                    sender.from(Prefixes.NOTHING, "List of your homes:")

                    for (home in homesConfig.getConfigurationSection(sender.name)!!.getKeys(false)) {
                        sender.from(Prefixes.NOTHING, "$index. $home")
                        index++
                    }
                }
                else -> {
                    if (args.isEmpty()) {
                        sender.from(Prefixes.RANKS, "Incorrect usage, please use:")
                        sender.from(Prefixes.NOTHING, "/sethome <name>")
                        sender.from(Prefixes.NOTHING, "/delhome <name>")
                    }
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
    ): MutableList<String>? {
        println(args[0])
        if (args.isEmpty()) {
            return mutableListOf("players")
        }
        return null
    }
}
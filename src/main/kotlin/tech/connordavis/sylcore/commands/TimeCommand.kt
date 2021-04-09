package tech.connordavis.sylcore.commands

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import tech.connordavis.sylcore.utils.Command
import tech.connordavis.sylcore.utils.CommandInfo
import tech.connordavis.sylcore.utils.NumberUtils.Companion.isNumber

class TimeCommand : Command(CommandInfo(
    "time",
    "This command lets you change the time.",
    aliases = arrayOf("day", "night", "midnight", "afternoon", "dawn")
)) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (sender is Player) when (commandLabel) {
            "time" -> {
                if (isNumber(args[0])) {
                    sender.world.time = args[0].toLong()
                } else when (args[0]) {
                    "day" -> {
                        sender.world.time = 6000
                    }
                    "night" -> {
                        sender.world.time = 12542
                    }
                    "midnight" -> {
                        sender.world.time = 17843
                    }
                    "afternoon" -> {
                        sender.world.time = 12000
                    }
                    "dawn" -> {
                        sender.world.time = 0
                    }
                }
            }
            "day" -> {
                sender.world.time = 6000
            }
            "night" -> {
                sender.world.time = 12542
            }
            "midnight" -> {
                sender.world.time = 17843
            }
            "afternoon" -> {
                sender.world.time = 12000
            }
            "dawn" -> {
                sender.world.time = 0
            }
        } else {
            sender.server.worlds.forEach { world ->
                when (commandLabel) {
                    "time" -> {
                        if (isNumber(args[0])) {
                            world.time = args[0].toLong()
                        } else when (args[0]) {
                            "day" -> {
                                world.time = 6000
                            }
                            "night" -> {
                                world.time = 12542
                            }
                            "midnight" -> {
                                world.time = 17843
                            }
                            "afternoon" -> {
                                world.time = 12000
                            }
                            "dawn" -> {
                                world.time = 0
                            }
                        }
                    }
                    "day" -> {
                        world.time = 6000
                    }
                    "night" -> {
                        world.time = 12542
                    }
                    "midnight" -> {
                        world.time = 17843
                    }
                    "afternoon" -> {
                        world.time = 12000
                    }
                    "dawn" -> {
                        world.time = 0
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
    ): MutableList<String> {
        return mutableListOf("day", "night", "midnight", "afternoon", "dawn")
    }


}

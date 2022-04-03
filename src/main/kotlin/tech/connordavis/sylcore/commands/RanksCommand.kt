package tech.connordavis.sylcore.commands

import org.bukkit.command.CommandSender
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.utils.Command
import tech.connordavis.sylcore.utils.CommandInfo
import tech.connordavis.sylcore.utils.Prefixes
import tech.connordavis.sylcore.utils.from
import tech.connordavis.sylcore.vault.permissions.Group

class RanksCommand : Command(CommandInfo(
    "ranks",
    "This command lets staff work with ranks and permissions.",
    aliases = arrayOf("players"))) {
    private val plugin = SylCorePlugin.instance
    private val permissionsManager = plugin.getPermissionsManager()

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (!checkPermissions(sender, commandLabel)) {
            sender.from(Prefixes.CORE, "You do not have permission to access that command.")
            return false
        } else when (commandLabel) {
            "ranks" -> {
                if (args.isNotEmpty()) {
                    when (args[0]) {
                        "addPermission" -> {
                            if (args.size > 2) {
                                val rankName = args[1]
                                val permission = args[2]

                                if (permissionsManager.addGroupPermission(rankName,
                                        permission)
                                ) sender.from(Prefixes.RANKS,
                                    "$permission permission added to rank $rankName.")
                                else sender.from(Prefixes.RANKS, "$permission permission not added to rank $rankName.")
                            } else {
                                sender.from(Prefixes.RANKS, "Please specify the rank name and the permission.")
                            }
                        }
                        "removePermission" -> {
                            if (args.size > 2) {
                                val rankName = args[1]
                                val permission = args[2]

                                if (permissionsManager.removeGroupPermission(rankName,
                                        permission)
                                ) sender.from(Prefixes.RANKS,
                                    "$permission permission removed from rank $rankName.")
                                else sender.from(Prefixes.RANKS,
                                    "$permission permission not removed from rank $rankName.")
                            } else {
                                sender.from(Prefixes.RANKS, "Please specify the rank name and the permission.")
                            }
                        }
                        "addRank" -> {
                            if (args.size > 1) {
                                val rankName = args[1]
                                val rank = Group(rankName, rankName, mutableListOf())

                                if (permissionsManager.createGroup(rank)) sender.from(Prefixes.RANKS,
                                    "$rankName rank created successfully.")
                                else sender.from(Prefixes.RANKS, "$rankName rank was not created.")

                            } else {
                                sender.from(Prefixes.RANKS, "Please specify the rank name.")
                            }
                        }
                        "removeRank" -> {
                            if (args.size > 1) {
                                val rankName = args[1]

                                if (permissionsManager.removeGroup(rankName)) sender.from(Prefixes.RANKS,
                                    "$rankName rank deleted successfully.")
                                else sender.from(Prefixes.RANKS, "$rankName rank was not deleted.")
                            } else {
                                sender.from(Prefixes.RANKS, "Please specify the rank name.")
                            }
                        }
                        "addPrefix" -> {
                            if (args.size > 2) {
                                val rank = args[1]
                                val prefix = args[2]

                                if (!permissionsManager.getGroups().containsKey(rank)) {
                                    sender.from(Prefixes.RANKS, "The rank does not exist.")

                                    return false
                                } else {
                                    permissionsManager.getGroups()[rank]!!.prefix = prefix

                                    sender.from(Prefixes.RANKS, "Added the prefix $prefix &7to rank &9$rank.")
                                }
                            } else {
                                sender.from(Prefixes.RANKS, "Please specify the rank name and prefix.")
                            }
                        }
                        "removePrefix" -> {
                            if (args.size > 1) {
                                val rank = args[1]

                                if (!permissionsManager.getGroups().containsKey(rank)) {
                                    sender.from(Prefixes.RANKS, "The rank does not exist.")

                                    return false
                                } else {
                                    val prefix = permissionsManager.getGroups()[rank]!!.prefix

                                    permissionsManager.getGroups()[rank]!!.prefix = rank

                                    sender.from(Prefixes.RANKS, "Removed the prefix $prefix &7from rank &9$rank.")
                                }
                            } else {
                                sender.from(Prefixes.RANKS, "Please specify the rank name and prefix.")
                            }
                        }
                    }
                } else {
                    sender.from(Prefixes.RANKS, "Incorrect usage, please use:")
                    sender.from(Prefixes.NOTHING,
                        "/ranks <addPermission/removePermission> <rankName> <permission>")
                    sender.from(Prefixes.NOTHING, "/ranks <addRank/removeRank> <rankName>")
                    sender.from(Prefixes.NOTHING, "/ranks <addPrefix> <rankName> <prefix>")
                    sender.from(Prefixes.NOTHING, "/ranks <removePrefix> <rankName>")
                }
            }
            "players" -> {
                if (args.isNotEmpty()) {
                    when (args[0]) {
                        "addPermission" -> {
                            if (args.size > 2) {
                                val playerName = args[1]
                                val permission = args[2]

                                if (permissionsManager.addPlayerPermission(playerName,
                                        permission)
                                ) sender.from(Prefixes.RANKS,
                                    "$permission permission added to player $playerName.")
                                else sender.from(Prefixes.RANKS,
                                    "$permission permission not added to player $playerName.")
                            } else {
                                sender.from(Prefixes.RANKS, "Please specify the player name and the permission.")
                            }
                        }
                        "removePermission" -> {
                            if (args.size > 2) {
                                val playerName = args[1]
                                val permission = args[2]

                                if (permissionsManager.removePlayerPermission(playerName,
                                        permission)
                                ) sender.from(Prefixes.RANKS,
                                    "$permission permission removed from player $playerName.")
                                else sender.from(Prefixes.RANKS,
                                    "$permission permission not removed from player $playerName.")
                            } else {
                                sender.from(Prefixes.RANKS, "Please specify the player name and the permission.")
                            }
                        }
                        "addRank" -> {
                            if (args.size > 2) {
                                val playerName = args[1]
                                val rankName = args[2]

                                if (permissionsManager.addPlayerGroup(playerName, rankName)) sender.from(Prefixes.RANKS,
                                    "$playerName added to rank $rankName.")
                                else sender.from(Prefixes.RANKS, "$playerName not added to rank $rankName.")
                            } else {
                                sender.from(Prefixes.RANKS, "Please specify the player name and the rank name.")
                            }
                        }
                        "removeRank" -> {
                            if (args.size > 2) {
                                val playerName = args[1]
                                val rankName = args[2]

                                if (permissionsManager.removePlayerGroup(playerName,
                                        rankName)
                                ) sender.from(Prefixes.RANKS,
                                    "$playerName removed from rank $rankName.")
                                else sender.from(Prefixes.RANKS, "$playerName not removed from rank $rankName.")
                            } else {
                                sender.from(Prefixes.RANKS, "Please specify the player name and the rank name.")
                            }
                        }
                    }
                } else {
                    sender.from(Prefixes.RANKS, "Incorrect usage, please use:")
                    sender.from(Prefixes.NOTHING,
                        "/players <addPermission/removePermission> <playerName> <permission>")
                    sender.from(Prefixes.NOTHING, "/players <addRank/removeRank> <playerName> <rankName>")
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
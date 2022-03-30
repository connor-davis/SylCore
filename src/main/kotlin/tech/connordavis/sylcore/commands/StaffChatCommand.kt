package tech.connordavis.sylcore.commands

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.utils.Command
import tech.connordavis.sylcore.utils.CommandInfo
import tech.connordavis.sylcore.utils.Prefixes
import tech.connordavis.sylcore.utils.from

class StaffChatCommand :
        Command(CommandInfo("staffchat", "Staff Chat Commands", aliases = arrayOf("sc"))) {
    private val plugin = SylCorePlugin.instance
    private val permissionsManager = plugin.getPermissionsManager()
    private val staffChatManager = plugin.getStaffChatManager()

    override fun execute(
            sender: CommandSender,
            commandLabel: String,
            args: Array<String>
    ): Boolean {
        if (!sender.hasPermission("sylcore.command.$commandLabel")) {
            sender.from(Prefixes.CORE, "You do not have permission to access that command.")
            return false
        } else {
            if (sender is Player)
                    when (commandLabel) {
                        // "createChannel" -> {
                        //     val channel = args[0]
                        //     val rank = permissionsManager.playerGroups(sender.name).get(0)

                        //     if (channel.isNullOrEmpty()) {
                        //         sender.from(Prefixes.STAFF_CHAT, "Created $rank chat channel.")

                        //         staffChatManager.addStaffChatPlayer(rank, sender)

                        //         sender.from(Prefixes.STAFF_CHAT, "Created $rank chat channel.")
                        //     } else {
                        //         sender.from(Prefixes.STAFF_CHAT, "Joining $rank chat channel.")

                        //         staffChatManager.addStaffChatPlayer(channel, sender)

                        //         sender.from(Prefixes.STAFF_CHAT, "Joined $rank chat channel")
                        //     }
                        // }
                        // "removeChannel" -> {}
                        "joinChannel" -> {
                            val channel = args[0]
                            val rank = permissionsManager.playerGroups(sender.name).get(0)

                            if (channel.isNullOrEmpty()) {
                                sender.from(Prefixes.STAFF_CHAT, "Joining $rank chat channel.")

                                staffChatManager.addStaffChatPlayer(rank, sender)

                                sender.from(Prefixes.STAFF_CHAT, "Joined $rank chat channel.")
                            } else {
                                sender.from(Prefixes.STAFF_CHAT, "Joining $rank chat channel.")

                                staffChatManager.addStaffChatPlayer(channel, sender)

                                sender.from(Prefixes.STAFF_CHAT, "Joined $rank chat channel")
                            }
                        }
                        "leaveChannel" -> {
                            val channel = args[0]
                            val rank = permissionsManager.playerGroups(sender.name).get(0)

                            if (channel.isNullOrEmpty()) {
                                sender.from(Prefixes.STAFF_CHAT, "Leaving $rank chat channel.")

                                staffChatManager.addStaffChatPlayer(rank, sender)

                                sender.from(Prefixes.STAFF_CHAT, "Left $rank chat channel.")
                            } else {
                                sender.from(Prefixes.STAFF_CHAT, "Leaving $rank chat channel.")

                                staffChatManager.addStaffChatPlayer(channel, sender)

                                sender.from(Prefixes.STAFF_CHAT, "Left $rank chat channel")
                            }
                        }
                    }
            else {
                sender.from(Prefixes.CORE, "Only players can use this command.")
                return false
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

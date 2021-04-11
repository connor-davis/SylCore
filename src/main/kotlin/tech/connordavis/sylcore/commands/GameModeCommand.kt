package tech.connordavis.sylcore.commands

import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import tech.connordavis.sylcore.utils.Command
import tech.connordavis.sylcore.utils.CommandInfo
import tech.connordavis.sylcore.utils.Prefixes
import tech.connordavis.sylcore.utils.from

class GameModeCommand : Command(
    CommandInfo(
        "gamemode",
        "This command lets you change your gamemode.",
        aliases = arrayOf("creative", "survival", "spectator", "adventure", "gmc", "gms", "gmspc", "gma"),
        permissions = arrayOf("sylcore.command.gamemode")
    )
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (sender !is Player) sender.sendMessage("Only players can use this command.")
        else {
            if (!sender.hasPermission("sylcore.command.$commandLabel")) {
                sender.from(Prefixes.CORE, "You do not have permission to access that command.")
                return false
            } else when (commandLabel) {
                "gamemode" -> {
                    when (args[0]) {
                        "creative" -> sender.gameMode = GameMode.CREATIVE
                        "survival" -> sender.gameMode = GameMode.SURVIVAL
                        "spectator" -> sender.gameMode = GameMode.SPECTATOR
                        "adventure" -> sender.gameMode = GameMode.ADVENTURE
                    }
                }

                "creative" -> sender.gameMode = GameMode.CREATIVE
                "gmc" -> sender.gameMode = GameMode.CREATIVE

                "survival" -> sender.gameMode = GameMode.SURVIVAL
                "gms" -> sender.gameMode = GameMode.SURVIVAL

                "spectator" -> sender.gameMode = GameMode.SPECTATOR
                "gmspc" -> sender.gameMode = GameMode.SPECTATOR

                "adventure" -> sender.gameMode = GameMode.ADVENTURE
                "gma" -> sender.gameMode = GameMode.ADVENTURE
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
        return mutableListOf("creative", "survival", "spectator", "adventure")
    }
}
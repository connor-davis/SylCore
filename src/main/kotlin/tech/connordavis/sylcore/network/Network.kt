package tech.connordavis.sylcore.network

import org.bukkit.configuration.file.YamlConfiguration
import tech.connordavis.sylcore.SylCorePlugin
import tech.connordavis.sylcore.managers.FileManager
import tech.connordavis.sylcore.utils.YamlFile
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

object Network {
    private val plugin: SylCorePlugin = SylCorePlugin.instance
    private val fileManager: FileManager = plugin.getFileManager()
    private val file: YamlFile = fileManager.getFile("network")!!
    private val config = file.getConfig()
    private lateinit var host: String
    private lateinit var port: Number
    private lateinit var socket: Socket
    private lateinit var dataOut: DataOutputStream
    private lateinit var dataIn: DataInputStream

    fun init(): Network {
        host = config.getString("host")!!
        port = config.getInt("port")
        socket = Socket(host, port as Int)
        dataOut = DataOutputStream(socket.getOutputStream())
        dataIn = DataInputStream(socket.getInputStream())

        return this
    }
}
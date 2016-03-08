package rskupnik.edgar

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Edgar {

    private fun getLogger() = LogManager.getLogger(this.javaClass)

    init {
        getLogger().info("Launching Edgar...")
        loadConfiguration()
        // Launch a server - decide on type (multithreaded, threadpooled, NIO)
    }

    private fun loadConfiguration() = null//Parrot.init()
}
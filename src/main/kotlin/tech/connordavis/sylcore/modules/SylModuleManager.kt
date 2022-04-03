package tech.connordavis.sylcore.modules

class SylModuleManager {
    val moduleList: ArrayList<SylModule> = ArrayList()

    fun add(module: SylModule) {
        moduleList.add(module)
    }

    fun remove(module: SylModule) {
        moduleList.remove(module)
    }

    fun exists(name: String): Boolean {
        return moduleList.find { module ->
            return module.description.name == name
        } !== null
    }
}
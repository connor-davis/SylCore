package tech.connordavis.sylcore.modules

data class SylModuleDescription(
    var name: String,
    var main: String,
    var version: String,
    var dependencies: List<String> = emptyList(),
    var softDependencies: List<String> = emptyList(),
)

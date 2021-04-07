// More about the setup here: https://github.com/DevSrSouza/KotlinBukkitAPI/wiki/Getting-Started
plugins {
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "tech.connordavis"
version = "0.0.1"

repositories {
    jcenter()
    // minecraft
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")

    //kotlinbukkitapi with backup repo
    maven("http://nexus.devsrsouza.com.br/repository/maven-public/")
    
    //plugins
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))

    //minecraft
    compileOnly("org.spigotmc:spigot-api:1.16.2-R0.1-SNAPSHOT")

    //kotlinbukkitapi
    val changing = Action<ExternalModuleDependency> { isChanging = true }
    compileOnly("br.com.devsrsouza.kotlinbukkitapi:core:0.2.0-SNAPSHOT", changing)
    compileOnly("br.com.devsrsouza.kotlinbukkitapi:serialization:0.2.0-SNAPSHOT", changing)
    compileOnly("br.com.devsrsouza.kotlinbukkitapi:plugins:0.2.0-SNAPSHOT", changing)
    compileOnly("br.com.devsrsouza.kotlinbukkitapi:exposed:0.2.0-SNAPSHOT", changing)

    //plugins
    val transitive = Action<ExternalModuleDependency> { isTransitive = false }
    compileOnly("com.github.MilkBowl:VaultAPI:1.7", transitive)
}

bukkit {
    main = "tech.connordavis.sylcore.SylCorePlugin"
    depend = listOf("KotlinBukkitAPI", "Vault")
    description = ""
    author = "darkio_"
    website = "https://connordavis.tech"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime,kotlin.ExperimentalStdlibApi"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime,kotlin.ExperimentalStdlibApi"
    }
    shadowJar {
        classifier = null
    }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(120, "seconds")
}
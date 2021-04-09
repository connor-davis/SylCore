// More about the setup here: https://github.com/DevSrSouza/KotlinBukkitAPI/wiki/Getting-Started
plugins {
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "tech.connordavis"
version = "0.0.3"

repositories {
    mavenCentral() // This is needed for dependencies.
    /*
    As Spigot-API depends on the Bungeecord ChatComponent-API,
   we need to add the Sonatype OSS repository, as Gradle,
   in comparison to maven, doesn't want to understand the ~/.m2
   directory unless added using mavenLocal(). Maven usually just gets
   it from there, as most people have run the BuildTools at least once.
   This is therefore not needed if you're using the full Spigot/CraftBukkit,
   or if you're using the Bukkit API.
   */
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    mavenLocal() // This is needed for CraftBukkit and Spigot.
    jcenter()

    //plugins
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))

//    minecraft
//    compileOnly("org.spigotmc:spigot-api:1.16.2-R0.1-SNAPSHOT")
//    compileOnly("org.bukkit:bukkit:1.16.2-R0.1-SNAPSHOT")
//    compileOnly("org.spigotmc:spigot:1.16.2-R0.1-SNAPSHOT")
//    compileOnly("org.bukkit:craftbukkit:1.16.2-R0.1-SNAPSHOT")

    //plugins
    val transitive = Action<ExternalModuleDependency> { isTransitive = false }
    compileOnly("com.github.MilkBowl:VaultAPI:1.7", transitive)
}

bukkit {
    main = "tech.connordavis.sylcore.SylCorePlugin"
    depend = listOf("Kotlin", "Vault")
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
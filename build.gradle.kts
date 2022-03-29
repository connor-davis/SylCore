plugins {
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "tech.connordavis"
version = "0.0.5"

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
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
    mavenLocal() // This is needed for CraftBukkit and Spigot.
    jcenter()

    //plugins
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT")

    val transitive = Action<ExternalModuleDependency> { isTransitive = false }

    // compileOnly("org.bukkit:craftbukkit:1.18.2-R0.1-SNAPSHOT", transitive)
    // compileOnly("org.bukkit:craftbukkit:1.18.2-R0.1-SNAPSHOT", transitive)
    compileOnly("com.github.MilkBowl:VaultAPI:1.7", transitive)
}

bukkit {
    main = "tech.connordavis.sylcore.SylCorePlugin"
    depend = listOf("Kotlin", "Vault")
    description = "This is a core plugin that adds functionality and features to a server."
    author = "lupinmc"
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
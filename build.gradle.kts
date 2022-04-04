plugins {
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("maven-publish")
}

group = "tech.connordavis"
version = "0.0.6"

publishing {
    repositories {
        maven {
            val releasesRepoUrl = layout.buildDirectory.dir("repos/releases")
            val snapshotsRepoUrl = layout.buildDirectory.dir("repos/snapshots")
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            pom {
                name.set("SylCore")
                description.set("SylCore is a Core plugin for Spigot servers. It handles many things and has an experimental module loader for itself.")
                url.set("https://github.com/connor-davis/SylCore")

                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("connor-davis")
                        name.set("Connor Davis")
                        email.set("cnnrproton@gmail.com")
                    }
                }

                scm {
                    url.set("https://github.com/connor-davis/SylCore")
                }
            }
        }
    }
}

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
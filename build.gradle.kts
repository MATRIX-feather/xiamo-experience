import groovy.lang.Closure
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    java
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("xyz.jpenilla.run-paper") version "2.0.1" // Adds runServer and runMojangMappedServer tasks for testing
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0" // Generates plugin.yml
    id("com.github.johnrengelman.shadow") version "7.1.2" // Shadow PluginBase
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://jitpack.io")
    }

    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }

    maven {
        url = uri("https://repo.md-5.net/content/groups/public/")
    }

    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }

    maven {
        url = uri("https://repo.dmulloy2.net/repository/public/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }

    maven {
        url = uri("https://maven.playpro.com")
    }
}

dependencies {
    paperweight.paperDevBundle("${project.property("minecraft_version")}")

    //compileOnly("com.comphenix.protocol:ProtocolLib:${project.property("protocollib_version")}")

    //compileOnly(files("libs/CMILib1.4.3.5.jar"))
    //compileOnly(files("libs/Residence5.1.4.0.jar"))

    implementation("org.bstats:bstats-bukkit:${project.property("bstats_version")}")
    {
        exclude("com.google.code.gson", "gson")
    }

    //implementation("org.apache.httpcomponents.client5:httpclient5:5.2.1")

    implementation("com.github.XiaMoZhiShi:PluginBase:${project.property("pluginbase_version")}")
    {
        exclude("com.google.code.gson", "gson")
    }

    //compileOnly("com.github.Gecolay:GSit:${project.property("gsit_version")}")
    compileOnly("me.clip:placeholderapi:${project.property("papi_version")}")
    compileOnly("net.coreprotect:coreprotect:22.2")
}

group = "xyz.nifeather.fexp"
version = "${project.property("project_version")}"
description = "Feather Experience!"
java.sourceCompatibility = JavaVersion.VERSION_17

bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
    main = "xyz.nifeather.fexp.FeatherExperience"
    apiVersion = "1.20"
    authors = listOf("MATRIX-feather")
    depend = listOf()
    softDepend = listOf()
    version = "${project.property("project_version")}"
    prefix = "FeatherExperience"
    name = "FeatherExperience"
    foliaSupported = true

    commands {
        var mainCommand = register("fexp").get();

        mainCommand.aliases = listOf("fe");
    }

    val permissionRoot = "fexp"

    permissions {
        register("${permissionRoot}.cmd").get().default = BukkitPluginDescription.Permission.Default.OP;
        register("${permissionRoot}.cmd.option").get().default = BukkitPluginDescription.Permission.Default.OP;
        register("${permissionRoot}.cmd.reload").get().default = BukkitPluginDescription.Permission.Default.OP;

        register("${permissionRoot}.mobegg.use").get().default = BukkitPluginDescription.Permission.Default.TRUE;

        register("${permissionRoot}.bonemeal.flower").get().default = BukkitPluginDescription.Permission.Default.TRUE;
        register("${permissionRoot}.bonemeal.sugarcane").get().default = BukkitPluginDescription.Permission.Default.TRUE;
        register("${permissionRoot}.bonemeal.coral").get().default = BukkitPluginDescription.Permission.Default.TRUE;

        register("${permissionRoot}.shulkerbox.use").get().default = BukkitPluginDescription.Permission.Default.TRUE;
    }
}

tasks.build {
    dependsOn(tasks.shadowJar, tasks.reobfJar)
}

tasks.shadowJar {
    minimize()
    relocate("xiamomc.pluginbase", "xyz.nifeather.fexp.shaded.pluginbase")
    relocate("org.bstats", "xyz.nifeather.fexp.shaded.bstats")
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

import java.io.BufferedReader
import java.io.InputStreamReader

plugins {
    `java-library`
    id("com.gradleup.shadow") version("9.3.1")
    id("xyz.jpenilla.run-paper") version("3.1.0")
}

group = "org.lushplugins"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.papermc.io/repository/maven-public/") // Paper
    maven("https://repo.lushplugins.org/snapshots/") // LushLib
}

dependencies {
    // Dependencies
    compileOnly("io.papermc.paper:paper-api:26.2.build.+")

    // Libraries
    implementation("org.lushplugins.lushlib:utils:1.0.2")
    implementation("io.github.revxrsal:lamp.common:4.0.0-rc.18")
    implementation("io.github.revxrsal:lamp.bukkit:4.0.0-rc.18")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))

    registerFeature("optional") {
        usingSourceSet(sourceSets["main"])
    }

    withSourcesJar()
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    shadowJar {
        enableAutoRelocation = true
        relocationPrefix = "org.lushplugins.regrowthvanish.libraries"

        minimize()

        archiveFileName.set("${project.name}-${project.version}.jar")
    }

    processResources{
        inputs.property("version", rootProject.version)
        inputs.property("commit", getCurrentCommitHash())

        filesMatching("plugin.yml") {
            expand(
                "version" to rootProject.version,
                "commit" to getCurrentCommitHash()
            )
        }
    }

    runServer {
        minecraftVersion("26.2")

        downloadPlugins {
            modrinth("viaversion", "5.12.1")
            modrinth("viabackwards", "5.12.1")
        }
    }
}

fun getCurrentCommitHash(): String {
    val process = ProcessBuilder("git", "rev-parse", "--short", "HEAD").start()
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    val commitHash = reader.readLine()
    reader.close()
    process.waitFor()
    if (process.exitValue() == 0) {
        return commitHash ?: ""
    } else {
        throw IllegalStateException("Failed to retrieve the commit hash.")
    }
}

import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    `java-library`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.qa)
    alias(libs.plugins.kotlin.dokka)
    alias(libs.plugins.git.semantic.versioning)
}

allprojects {
    group = "io.github.positionpal"

    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/position-pal/shared-kernel")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }

    fun Task.compose(vararg args: String) {
        doLast {
            exec {
                workingDir = project.rootDir
                commandLine("docker", "compose", *args)
            }
        }
    }

    tasks.create("composeDown") {
        compose("down")
    }

    tasks.create("composeUp") {
        compose("up", "-d")
    }
}

subprojects {

    with(rootProject.libs.plugins) {
        apply(plugin = "java-library")
        apply(plugin = kotlin.jvm.get().pluginId)
        apply(plugin = kotlin.qa.get().pluginId)
        apply(plugin = kotlin.dokka.get().pluginId)
    }

    with(rootProject.libs) {
        dependencies {
            implementation(kotlin.stdlib)
            implementation(kotlin.stdlib.jdk8)
            testImplementation(bundles.kotlin.testing)
            implementation(slf4j)
        }
    }

    kotlin {
        compilerOptions {
            allWarningsAsErrors = true
        }
    }

    tasks.withType<Test>().configureEach {
        testLogging {
            events(*TestLogEvent.values())
            exceptionFormat = TestExceptionFormat.FULL
        }
        useJUnitPlatform()
    }

    val generatedFilesFolder = "build${File.separator}generated"

    tasks.withType<SourceTask>()
        .matching { it is VerificationTask }
        .configureEach {
            exclude { generatedFilesFolder in it.file.absolutePath }
        }

    ktlint {
        filter {
            exclude { generatedFilesFolder in it.file.absolutePath }
        }
    }
}

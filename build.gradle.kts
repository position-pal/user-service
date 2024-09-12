import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.qa)
    alias(libs.plugins.kotlin.dokka)
    alias(libs.plugins.git.semantic.versioning)
    alias(libs.plugins.protobuf)
}

allprojects {
    group = "io.github.positionpal"

    repositories {
        mavenCentral()
    }
}

subprojects {

    with(rootProject.libs.plugins) {
        apply(plugin = kotlin.jvm.get().pluginId)
        apply(plugin = kotlin.qa.get().pluginId)
        apply(plugin = kotlin.dokka.get().pluginId)
        apply(plugin = protobuf.get().pluginId)
    }

    with(rootProject.libs) {
        dependencies {
            runtimeOnly(grpc.netty.shaded)
            implementation(grpc.protobuf)
            implementation(grpc.stub)
            implementation(protobuf.java)
            implementation(javax.annotation.api)
            implementation(kotlin.stdlib)
            implementation(kotlin.stdlib.jdk8)
            testImplementation(bundles.kotlin.testing)
            testImplementation(grpc.testing)
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

    protobuf {
        protoc {
            artifact = rootProject.libs.protobuf.protoc.get().toString()
        }
        plugins {
            create("grpc") {
                artifact = rootProject.libs.grpc.generator.java.get().toString()
            }
        }
        generateProtoTasks {
            all().forEach { task ->
                task.plugins {
                    create("grpc")
                }
            }
        }
    }

    sourceSets {
        main {
            java {
                srcDirs("build/generated/source/proto/main/grpc")
                srcDirs("build/generated/source/proto/main/java")
            }
        }
    }
}

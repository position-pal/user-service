plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

application {
    mainClass = "MainKt"
}

dependencies {
    api(project(":application"))
    implementation(libs.kotlin.stdlib)
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation("org.ktorm:ktorm-core:3.5.0")
    implementation("org.ktorm:ktorm-support-postgresql:3.5.0")
    implementation("org.postgresql:postgresql:42.7.2")
}

tasks.withType<Test> {
    dependsOn(":storage:composeUp")
    finalizedBy(":storage:composeDown")
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

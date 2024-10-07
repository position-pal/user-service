plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

application {
    mainClass = "MainKt"
}

dependencies {
    api(project(":application"))
    with(libs) {
        implementation(kotlin.stdlib)
        implementation(dotenv)
        implementation(ktorm.core)
        implementation(ktorm.postgres)
        implementation(postgresql)
    }
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

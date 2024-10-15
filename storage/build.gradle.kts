plugins {
    application
}

application {
    mainClass = "MainKt"
}

dependencies {
    api(project(":application"))
    with(libs) {
        implementation(dotenv)
        implementation(ktorm.core)
        implementation(ktorm.postgresql)
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

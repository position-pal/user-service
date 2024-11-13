repositories {
    maven {
        url = uri("https://maven.pkg.github.com/position-pal/shared-kernel")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }

    }
}

dependencies {
    api(project(":application"))
    implementation("com.rabbitmq:amqp-client:5.17.1")
    implementation("io.github.positionpal:kernel-presentation:0.5.0")
}
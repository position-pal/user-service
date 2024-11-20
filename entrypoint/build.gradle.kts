plugins {
    application
}

application {
    mainClass.set("MainKt") // Sostituisci con la tua classe principale
}

dependencies {
    with(libs) {
        implementation(grpc.stub)
        implementation(grpc.netty.shaded)
        implementation(ktorm.core)
        implementation(dotenv)
    }
    api(project(":storage"))
    api(project(":grpc"))
    api(project(":rabbitmq"))
}

tasks.named("run") {
    dependsOn(":composeUp")
    finalizedBy(":composeDown")
}

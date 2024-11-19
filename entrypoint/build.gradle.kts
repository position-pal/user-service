plugins {
    application
}

application {
    mainClass.set("MainKt") // Sostituisci con la tua classe principale
}

dependencies {
    implementation(libs.grpc.stub)
    implementation(libs.grpc.netty.shaded)
    implementation(libs.ktorm.core)
    api(project(":storage"))
    api(project(":grpc"))
    api(project(":rabbitmq"))
}

tasks.named("run") {
    dependsOn(":composeUp")
    finalizedBy(":composeDown")
}

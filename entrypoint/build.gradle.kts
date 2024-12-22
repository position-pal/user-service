plugins {
    application
    alias(libs.plugins.shadowJar)
}

application {
    mainClass.set("MainKt")
}

dependencies {
    with(libs) {
        implementation(grpc.stub)
        implementation(grpc.netty.shaded)
        implementation(ktorm.core)
        implementation(dotenv)
        implementation(rabbit.amqp)
    }
    api(project(":storage"))
    api(project(":grpc"))
    api(project(":rabbitmq"))
}

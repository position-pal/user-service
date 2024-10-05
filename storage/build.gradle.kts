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

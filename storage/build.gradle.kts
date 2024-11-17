plugins {
    application
}

dependencies {
    api(project(":application"))
    with(libs) {
        implementation(dotenv)
        implementation(ktorm.core)
        testImplementation(mockk)
        implementation(ktorm.postgresql)
        implementation(postgresql)
        implementation(kernel.domain)
        implementation(kernel.presentation)
    }
}

tasks.withType<Test> {
    dependsOn(":composeUp")
    finalizedBy(":composeDown")
}

dependencies {
    api(project(":application"))
    with(libs) {
        implementation(rabbit.amqp)
        implementation(kernel.presentation)
        implementation(kernel.domain)
        testImplementation(mockk)
    }
}

tasks.withType<Test> {
    dependsOn(":composeUp")
    finalizedBy(":composeDown")
}

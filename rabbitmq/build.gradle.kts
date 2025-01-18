import Utils.inCI
import Utils.normally
import Utils.onMac
import Utils.onWindows

dependencies {
    api(project(":application"))
    with(libs) {
        implementation(rabbit.amqp)
        implementation(kernel.presentation)
        implementation(kernel.domain)
        testImplementation(mockk)
    }
}

normally {
    dockerCompose {
        startedServices = listOf("rabbitmq")
        isRequiredBy(tasks.test)
    }
} except { inCI and (onMac or onWindows) } where {
    tasks.test { enabled = false }
} cause "GitHub Actions runner does not support Docker Compose"

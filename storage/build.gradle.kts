import Utils.inCI
import Utils.normally
import Utils.onMac
import Utils.onWindows


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
        implementation(jbcrypt)
    }
}

normally {
    dockerCompose {
        startedServices = listOf("postgres")
        isRequiredBy(tasks.test)
    }
} except { inCI and (onMac or onWindows) } where {
    tasks.test { enabled = false }
} cause "GitHub Actions runner does not support Docker Compose"

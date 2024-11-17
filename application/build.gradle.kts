dependencies {
    api(project(":domain"))
    with(libs) {
        implementation(ktor.auth)
        implementation(ktor.auth.jwt)
        implementation(avro)
        implementation(kernel.presentation)
        implementation(kernel.domain)
    }
}

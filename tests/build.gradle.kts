dependencies {
    api(project(":domain"))
    api(project(":application"))
    api(project(":presentation"))
    api(project(":storage"))
    api(project(":grpc"))
    with(libs) {
        implementation(grpc.stub)
        implementation(grpc.protobuf)
        implementation(protobuf.java)
        implementation(javax.annotation.api)
        implementation("org.ktorm:ktorm-core:3.5.0")
    }
    testImplementation("io.mockk:mockk:1.12.0")
}

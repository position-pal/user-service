repositories {
    mavenCentral()
}

dependencies {
    api(project(":presentation"))
    with(libs) {
        implementation(grpc.stub)
        implementation(javax.annotation.api)
        implementation(grpc.protobuf)
        implementation(protobuf.kotlin)
    }
}

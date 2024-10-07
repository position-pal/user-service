plugins {
    alias(libs.plugins.protobuf)
}

dependencies {
    api(project(":application"))
    with(libs) {
        implementation(grpc.stub)
        implementation(grpc.protobuf)
        implementation(protobuf.kotlin)
        implementation(javax.annotation.api)
    }
}

tasks.named("cpdKotlinCheck") {
    dependsOn(tasks.named("generateProto"))
}

protobuf {
    protoc {
        artifact = rootProject.libs.protobuf.protoc.get().toString()
    }
    plugins {
        create("grpc") {
            artifact = rootProject.libs.grpc.generator.java.get().toString()
        }
        create("grpckt") {
            artifact = rootProject.libs.grpc.generator.kotlin.get().toString()
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc")
                create("grpckt")
            }
            task.builtins {
                create("kotlin")
            }
        }
    }
}

import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    alias(libs.plugins.protobuf)
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":application"))
    with(libs) {
        implementation(grpc.stub)
        implementation(grpc.protobuf)
        implementation(protobuf.java)
        implementation(javax.annotation.api)
    }
}

tasks.withType<Test>().configureEach {
    testLogging {
        events(*TestLogEvent.values())
        exceptionFormat = TestExceptionFormat.FULL
    }
    useJUnitPlatform()
}

protobuf {
    protoc {
        artifact = rootProject.libs.protobuf.protoc.get().toString()
    }
    plugins {
        create("grpc") {
            artifact = rootProject.libs.grpc.generator.java.get().toString()
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc")
            }
        }
    }
}

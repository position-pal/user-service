import de.aaschmid.gradle.plugins.cpd.Cpd
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
        implementation(protobuf.kotlin)
        implementation(javax.annotation.api)
    }
}

ktlint {
    filter{
        exclude("**/build/generated/**")
    }
}

tasks.withType<Cpd>(){
    exclude("**/build/generated/**")
}

tasks.withType<Test>().configureEach {
    testLogging {
        events(*TestLogEvent.values())
        exceptionFormat = TestExceptionFormat.FULL
    }
    useJUnitPlatform()
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

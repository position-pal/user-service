FROM eclipse-temurin:25@sha256:63f965b560b905c9633218330ac3516304b526a74fdc323a504b05c06e712c30

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

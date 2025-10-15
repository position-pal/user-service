FROM eclipse-temurin:25@sha256:18755606ff20b3871244a75b026eaf5513b93739974100be9d86362f9bc4f98b

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

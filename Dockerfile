FROM eclipse-temurin:21@sha256:37d46d0b856ebad9cf56aa4244bec967fe0248862c75ec7d3d773a6d5918cf8c

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

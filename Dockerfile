FROM eclipse-temurin:21@sha256:40f9bb2bba5571ed0bb8d16f089afd2dbabb0d5fd727767c311330b8c604f4b0

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

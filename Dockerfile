FROM eclipse-temurin:25@sha256:12e44624adee6808a36d962717e1656e0afeeeff5a100f9cb00e0136513558f0

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

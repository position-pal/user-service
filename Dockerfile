FROM eclipse-temurin:21@sha256:4d8eddfd7ce6ce35c6a42115d73ef7e92a3e112fddbae21a6e273d2e05fc9fff

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

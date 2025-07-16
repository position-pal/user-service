FROM eclipse-temurin:21@sha256:1982388ee953f45c938e13cf9613bce9f1a3cb7d23cb4049ea60ac26176c8877

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

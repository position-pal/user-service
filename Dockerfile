FROM gradle:8-jdk17@sha256:e2129390b6f0a5c139e7c70164672fa5b4ee192c8da7dcff67c3e8d8f05acded AS builder

WORKDIR /app

COPY . .

RUN --mount=type=secret,id=github_username,env=USERNAME,required=true \
    --mount=type=secret,id=github_token,env=TOKEN,required=true \
    ./gradlew :entrypoint:shadowJar

FROM openjdk:21-slim@sha256:7072053847a8a05d7f3a14ebc778a90b38c50ce7e8f199382128a53385160688

WORKDIR /app

COPY --from=builder app/entrypoint/build/libs/entrypoint-all.jar app.jar

ENV POSTGRES_USER=admin \
    POSTGRES_DB=users_and_groups \
    RABBITMQ_HOST=localhost \
    RABBITMQ_PORT=5672 \
    RABBITMQ_USERNAME=guest

EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]
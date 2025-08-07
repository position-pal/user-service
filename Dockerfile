FROM eclipse-temurin:21@sha256:1f3171966c0ca6b7151c56059bc70c40d3b880b4333feb91d5b09a68f159af3b

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

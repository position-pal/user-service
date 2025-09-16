FROM eclipse-temurin:21@sha256:2b9cd724df3c0797032bfcfebd268ba89e80a77e93c791c4cecd4d5e1202e027

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

FROM eclipse-temurin:21@sha256:5e7983a70405b3e40f79592276e73c81215fd9822bf37a66e325fb87a6faa57d

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

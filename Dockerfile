FROM eclipse-temurin:21@sha256:4f53cf2ef36a0068a3acc0fa8ddf80548355f4ad880d7f7299fdb77118f8daed

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

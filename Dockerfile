FROM eclipse-temurin:21@sha256:784aa1a3cbbd6217307dded1749f85bccade79ffd539b771bbe6c4c94f60d593

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

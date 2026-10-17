FROM eclipse-temurin:25@sha256:40cefbf1de9d958594a1a38caafdd87e09bb8935b72ae3688ffe578f4f92767a

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

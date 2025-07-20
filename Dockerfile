FROM eclipse-temurin:21@sha256:03a98128909d4216057841a9e779af84b3e395a62f412b3073b369a53c02465b

WORKDIR /app

COPY ./entrypoint/build/libs/*-all.jar app.jar

# GRPC
EXPOSE 5052

ENTRYPOINT ["java", "-jar", "app.jar"]

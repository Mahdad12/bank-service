FROM gradle:8.0.2-jdk19-alpine as builder
COPY . /app
WORKDIR /app
RUN gradle build --no-daemon --stacktrace
FROM openjdk:19-jdk-alpine
COPY --from=builder /app/build/libs/*.jar transaction-service.jar
COPY --from=builder /app/src/main/resources/db/migration /app/db/migration
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "transaction-service.jar"]
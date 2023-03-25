FROM gradle:8.0.2-jdk19-alpine as builder
COPY . /app
WORKDIR /app
RUN gradle build --no-daemon --stacktrace
FROM openjdk:19-jdk-alpine
COPY --from=builder /app/build/libs/*.jar account-service.jar
COPY --from=builder /app/src/main/resources/db/migration /app/db/migration
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "account-service.jar"]
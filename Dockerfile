FROM gradle:8.11-jdk17 AS builder
ENV GRADLE_USER_HOME=/app/gradle-cache

WORKDIR /app/backend
COPY spoterly-backend .
RUN ./gradlew :spoterly-services:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app/backend
COPY --from=builder /app/backend/spoterly-services/build/libs/*.jar spoterly-backend.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "spoterly-backend.jar"]


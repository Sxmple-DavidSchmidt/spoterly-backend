FROM gradle:8.11-jdk17 AS builder
ENV GRADLE_USER_HOME=/app/gradle-cache

WORKDIR /app
COPY . .
RUN ./gradlew :spoterly-services:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/spoterly-services/build/libs/*.jar spoterly-backend.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "spoterly-backend.jar"]

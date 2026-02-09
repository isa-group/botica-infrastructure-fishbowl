# --- Stage 1: Build ---
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:resolve && mvn dependency:resolve-plugins

COPY . .
RUN mvn clean package -o -DskipTests


# --- Stage 2: Runtime ---
# Java JRE version (match with pom.xml Java target version compatibility)
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/bot.jar bot.jar

ENTRYPOINT ["java", "-jar", "bot.jar"]

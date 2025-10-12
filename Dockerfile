# ---- Stage 1: Build the app ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Stage 2: Run the app ----
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose API port
EXPOSE 8080

# Run Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]

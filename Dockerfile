# Use official Java 21 image
FROM eclipse-temurin:21-jdk

# Set working directory inside the container
WORKDIR /app

# Copy Maven wrapper & pom for dependency caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable
RUN chmod +x mvnw || true

# Download dependencies (to speed up later builds)
RUN ./mvnw dependency:go-offline -B || mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application (skip tests)
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# Expose app port
EXPOSE 8080

# Run the built JAR
CMD ["java", "-jar", "target/insurance-api-0.0.1-SNAPSHOT.jar"]

# Use maven to build the application
FROM maven:3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use a slim JDK base image for the final image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/registry-service.jar ./registry-service.jar
CMD ["java", "-jar", "registry-service.jar"]

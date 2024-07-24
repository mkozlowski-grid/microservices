# Use maven to build the application
FROM maven:3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use a slim JDK base image for the final image
FROM openjdk:17-jdk-slim
WORKDIR /app
# Install curl in the final image
RUN apt-get update && apt-get install -y curl
COPY --from=build /app/target/config-server.jar ./config-server.jar
CMD ["java", "-jar", "config-server.jar"]

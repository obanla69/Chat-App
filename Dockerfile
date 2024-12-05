# Build stage
# Build stage
FROM maven:3.9.0-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

# Run stage
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/target/Blues_Chat_App-0.0.1-SNAPSHOT.jar BluesChatApp.jar
EXPOSE 9005
ENTRYPOINT ["java", "-jar", "BluesChatApp.jar"]


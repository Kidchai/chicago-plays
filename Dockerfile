FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn install

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/chicago-plays-1.0-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
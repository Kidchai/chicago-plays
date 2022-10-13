FROM maven:3.8-eclipse-temurin-17-alpine as maven_builder
ENV HOME=/app
WORKDIR $HOME
COPY ./pom.xml $HOME/pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn clean install
ADD . $HOME

FROM tomcat:9-jdk17
ENV HOME=/app
COPY --from=maven_builder $HOME/target/dockertest-1.0-SNAPSHOT.jar /usr/local/tomcat/webapps
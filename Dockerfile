FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /home/app

COPY ./pom.xml /home/app/pom.xml
COPY ./src/main/java/com/ozius/internship/project/ProjectApplication.java /home/app/src/main/java/com/ozius/internship/project/ProjectApplication.java
COPY ./src/main/java/com/ozius/internship/project/SpringProfiles.java /home/app/src/main/java/com/ozius/internship/project/SpringProfiles.java

RUN mvn -f /home/app/pom.xml clean package

COPY ./src/main /home/app/src/main
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:21
EXPOSE 5000
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]
FROM maven:3.9-sapmachine-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-slim
COPY --from=build /target/pia-staj-project-2-0.0.1-SNAPSHOT.jar pia-staj-project-2.jar 
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pia-staj-project-2.jar"]


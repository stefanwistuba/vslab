FROM maven:3.6.3-jdk-11 as builder

WORKDIR /app
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/${JAR_FILE} /service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/service.jar"]

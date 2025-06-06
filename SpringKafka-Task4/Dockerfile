FROM eclipse-temurin:23-jdk-alpine AS build
RUN apk update && apk add maven

COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package -DskipTests

FROM openjdk:23-slim

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir /app
COPY --from=build /usr/src/app/target/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/spring-boot-application.jar"]

FROM openjdk:17-alpine
WORKDIR /tmp
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=build/libs/price-calculator-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /tmp/app.jar
ENTRYPOINT ["java","-jar","/tmp/app.jar"]

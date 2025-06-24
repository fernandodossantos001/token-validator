FROM eclipse-temurin:21-jdk-alpine

ENV JAVA_OPTS=" -Xms256m -Xmx512m -XX:+UseContainerSupport -XX:+AlwaysPreTouch -XX:InitialRAMPercentage=70.0 -XX:MaxRAMPercentage=70.0 -XX:+UseParallelGC "

EXPOSE 8080

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java $JAVA_OPTS","-jar ","/app.jar"]

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
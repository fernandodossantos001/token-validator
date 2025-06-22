FROM eclipse-temurin:21-jdk-jammy

ENV JAVA_OPTS="-Xmx512m -Xms256m"


ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java $JAVA_OPTS","-jar ","/app.jar"]

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
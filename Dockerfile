FROM openjdk:11.0.5-stretch

VOLUME /main-app
ADD /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]

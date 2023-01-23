FROM openjdk:18-jdk-alpine
ARG JAR_FILE=target/*.jar
WORKDIR /opt/bookshelf-service
COPY ${JAR_FILE} bookshelf-service.jar
EXPOSE 8080
CMD ["java", "-jar", "bookshelf-service.jar"]
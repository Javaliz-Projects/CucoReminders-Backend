FROM openjdk:11
WORKDIR /app
COPY ./target/cucoreminders-0.0.1-SNAPSHOT.jar /app/cucoreminders-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "cucoreminders-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
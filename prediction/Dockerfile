FROM openjdk:8
RUN mkdir prediction
COPY target/prediction-1.0-SNAPSHOT.jar /prediction
WORKDIR /prediction
CMD ["java", "-jar", "prediction-1.0-SNAPSHOT.jar"]
FROM java:8
RUN rm -rf /usr/app/TFM-ServiceREST-1.0-SNAPSHOT.jar
COPY ./target/TFM-ServiceREST-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "TFM-ServiceREST-1.0-SNAPSHOT.jar"]
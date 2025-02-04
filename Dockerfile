FROM bellsoft/liberica-openjre-alpine:17 AS layers
WORKDIR /T1_test
COPY target/app.jar /T1_test/app.jar
RUN java -Djarmode=layertools -jar app.jar extract

ENV CONTAINER_NAME=$T1_test

ENV KAFKA_SERVER host.docker.internal:29092
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
EXPOSE 8080
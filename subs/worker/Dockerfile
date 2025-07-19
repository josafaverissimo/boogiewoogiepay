FROM eclipse-temurin:24-alpine

COPY build/libs/worker-all.jar /worker.jar

EXPOSE 9999 

ENTRYPOINT ["java", "-jar", "/worker.jar"]

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ./build/libs/bows-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["helm repo add simpleApp helm repo add simpleApp https://bows-bo-web-service.github.io/BoWS-Infra/charts/simpleApp && java -jar bows-0.0.1-SNAPSHOT.jar"]
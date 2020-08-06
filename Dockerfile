FROM maven:3.5.4-jdk-8-alpine as builder
RUN mkdir dropwizard-test-task
COPY ./ /dropwizard-test-task
WORKDIR dropwizard-test-task
RUN mvn clean install

FROM openjdk:8-jdk-alpine
COPY --from=builder /dropwizard-test-task/target/dropwizard-test-task-1.0.0.jar ./
COPY --from=builder /dropwizard-test-task/config-docker.yml ./

CMD java -jar dropwizard-test-task-1.0.0 db migrate config-docker.yml && java -jar dropwizard-test-task-1.0.0 server config-docker.yml
EXPOSE 8080
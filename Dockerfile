# stage BUILD
FROM maven:3.6.0-jdk-8 AS builder
WORKDIR /opt/application/build
COPY . .

RUN ls
ENV LPH_ACTIVE_PROFILE=prod
RUN mvn clean package


# stage RUN
FROM openjdk:8-alpine
WORKDIR /opt/application
ENV LPH_ACTIVE_PROFILE=prod

COPY --from=builder /opt/application/build/target .
CMD ["java", "-jar", "backend.jar"]
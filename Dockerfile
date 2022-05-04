FROM maven:latest as maven
COPY ./pom.xml /build/
COPY ./src /build/src/
WORKDIR /build/
RUN mvn package

FROM openjdk:16
WORKDIR /app
COPY --from=maven /build/target/PseudoIdGenerator-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-jar", "PseudoIdGenerator-0.0.1-SNAPSHOT.jar"]
















# Etapa 1: Compilación
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /src
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jre-noble
WORKDIR /app
RUN sed -i 's|http://|https://|g' /etc/apt/sources.list.d/ubuntu.sources \
    && apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*
COPY --from=build /src/target/*.jar /app/app.jar
USER 10001:10001
EXPOSE 8862
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
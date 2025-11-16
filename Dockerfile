# Etapa 1: build do aplicativo
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia os arquivos do Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e empacota o projeto
COPY src ./src
RUN mvn package -DskipTests

# Etapa 2: imagem final leve
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/target/quarkus-app /app
CMD ["java", "-jar", "/app/quarkus-run.jar"]

# Define a porta padrão do Quarkus
EXPOSE 8080

# Comando para iniciar o app
ENTRYPOINT ["java", "-jar", "/app/quarkus-run.jar"]
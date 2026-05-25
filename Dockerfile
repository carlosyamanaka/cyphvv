# Estágio 1: Build da aplicação com Maven e JDK 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro para cachear as dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e compila o jar da aplicação (pulando testes)
COPY src ./src
RUN mvn package -DskipTests -B

# Estágio 2: Execução com JRE 21 leve
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o jar compilado do estágio de build de forma dinâmica
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot (8080)
EXPOSE 8080

# Define variáveis de ambiente padrão
ENV SPRING_PROFILES_ACTIVE=staging

# Executa o jar da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

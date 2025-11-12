# Estágio de Build (Nomeado 'builder')
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
# Copia todo o código-fonte
COPY . .
# Compila o projeto e gera o JAR, pulando os testes
RUN mvn clean package -DskipTests

# ----------------------------------------------------------------------

# Estágio de Produção (Imagem final e leve)
FROM openjdk:17-jre-slim
WORKDIR /app
# Copia apenas o JAR gerado no estágio 'builder' para a imagem final
COPY --from=builder /app/target/user-api-1.0.0.jar app.jar
# Expõe a porta que a aplicação Spring Boot irá usar
EXPOSE 8080
# Define o comando que será executado ao iniciar o container
CMD ["java", "-jar", "app.jar"]
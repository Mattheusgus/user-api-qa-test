# Estágio de Build (Nomeado 'builder')
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ----------------------------------------------------------------------

# Estágio de Produção (Imagem final e leve)
# CORREÇÃO AQUI: Trocar para eclipse-temurin:17-jre-alpine
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/user-api-1.0.0.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
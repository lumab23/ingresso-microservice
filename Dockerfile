# Use a versão mais recente do OpenJDK como base
FROM eclipse-temurin:22-jdk-alpine

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR da aplicação
COPY target/*.jar app.jar

# Expor a porta que a aplicação usa
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"] 
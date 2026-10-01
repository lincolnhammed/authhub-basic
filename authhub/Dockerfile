# Imagem base que já possui Java 21 (JDK)
FROM eclipse-temurin:21-jdk

# Define a pasta de trabalho dentro do container
# É como fazer: cd /app
WORKDIR /app

# Copia os arquivos do projeto para dentro do container
# O primeiro "." = diretório atual no computador
# O segundo "." = diretório atual dentro do container (/app)
COPY . .

# Executa o Maven durante a construção da imagem
# Compila o projeto e gera o arquivo .jar
# -DskipTests = não executa os testes durante o build
RUN ./mvnw clean package -DskipTests

# Documenta que a aplicação Spring Boot utiliza a porta 8080
# Esta é a porta INTERNA do container
EXPOSE 8080

# As configurações do banco são fornecidas pelo docker-compose.yml

# Comando executado quando o container for iniciado
# Inicia a aplicação Spring Boot através do arquivo .jar
CMD ["java", "-jar", "target/authhub-1.0.0.jar"]


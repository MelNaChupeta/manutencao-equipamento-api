# Use uma imagem base do JDK
FROM openjdk:21

# Defina o diretório de trabalho dentro do container
WORKDIR /app

# Copie o arquivo WAR da sua aplicação para o diretório de trabalho
COPY target/manutencao-equipamento-api-3.4.0-SNAPSHOT.war app.war

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.war"]
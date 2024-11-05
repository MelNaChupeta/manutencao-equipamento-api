FROM openjdk:21-jdk-slim

RUN apt-get update && \
    apt-get install -y wget unzip tzdata && \
    wget https://archive.apache.org/dist/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip && \
    unzip apache-maven-3.9.5-bin.zip -d /opt && \
    ln -s /opt/apache-maven-3.9.5/bin/mvn /usr/bin/mvn && \
    rm apache-maven-3.9.5-bin.zip

ENV TZ=America/Sao_Paulo

ENV JAVA_TOOL_OPTIONS="-Duser.timezone=America/Sao_Paulo"

WORKDIR /home/app/

COPY . /home/app/

USER root

CMD tail -f /dev/null

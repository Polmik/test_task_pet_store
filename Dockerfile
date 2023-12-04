FROM ubuntu:22.04
WORKDIR /app
RUN apt update && apt-get install -y wget && apt-get install zip unzip && apt install -y openjdk-19-jdk
RUN wget https://services.gradle.org/distributions/gradle-8.2-bin.zip -P /tmp
RUN unzip -d /opt/gradle /tmp/gradle-*.zip
RUN ln -s /opt/gradle/gradle-8.2/bin/gradle /usr/bin
COPY ./runTest.sh ./
CMD ["./runTest.sh"]
FROM maven:3-jdk-8
ENV LANG=C.UTF-8 LANGUAGE=C LC_ALL=C.UTF-8 TERM=linux
RUN \
 apt-get update &&\
 apt-get upgrade --yes &&\
 apt-get install --yes bash curl && \
 apt-get clean

USER root
RUN mkdir -p /opt/app
WORKDIR /opt/app

EXPOSE 8080
COPY . /opt/app

CMD ["mvn","jetty:run"] 

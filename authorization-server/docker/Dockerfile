FROM java:openjdk-8-jre
MAINTAINER Emerson Farrugia <emerson@openmhealth.org>

ENV SERVER_PREFIX /opt/omh-dsu-ri/authorization-server
 
RUN mkdir -p $SERVER_PREFIX
ADD authorization-server.jar $SERVER_PREFIX/
ADD keystore.jks $SERVER_PREFIX/
EXPOSE 443

CMD /usr/bin/java -jar $SERVER_PREFIX/authorization-server.jar --spring.config.location=file:$SERVER_PREFIX/

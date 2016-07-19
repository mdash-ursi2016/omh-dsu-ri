FROM java:openjdk-8-jre
MAINTAINER Jason Waterman <jawaterman@vassar.edu>

ENV SERVER_PREFIX /opt/omh-dsu-ri/dashboard

RUN mkdir -p $SERVER_PREFIX
ADD dashboard.jar $SERVER_PREFIX/
EXPOSE 8080

CMD /usr/bin/java -jar $SERVER_PREFIX/dashboard.jar --spring.config.location=file:$SERVER_PREFIX/
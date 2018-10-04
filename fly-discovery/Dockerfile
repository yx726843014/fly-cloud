FROM java:8
VOLUME /data
ADD fly-discovery-1.0.jar  fly-discovery.jar
RUN bash -c 'touch /fly-discovery.jar'
EXPOSE 8100
ENTRYPOINT  ["java", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=GMT+08", "-jar", "/fly-discovery.jar"]
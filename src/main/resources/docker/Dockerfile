FROM java
COPY miaosha-1.0-SNAPSHOT.jar app.jar
VOLUME /tmp
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
FROM java:8
VOLUME /tmp
 ./target/leaf-jobs-core-0.0.1-SNAPSHOT.jar leaf-jobs-core.jar
RUN bash -c 'touch /leaf-jobs-core.jar'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/leaf-jobs-core.jar"]

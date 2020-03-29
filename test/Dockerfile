FROM java:8
VOLUME /tmp
ADD ./target/leaf-jobs-test-0.0.1-SNAPSHOT.jar leaf-jobs-test.jar
RUN bash -c 'touch /leaf-jobs-test.jar'
EXPOSE 9000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/leaf-jobs-test.jar"]
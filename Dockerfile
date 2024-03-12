FROM openjdk:8-alpine
ADD target/apf-0.0.1-SNAPSHOT.jar /usr/share/apf.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/apf.jar"]

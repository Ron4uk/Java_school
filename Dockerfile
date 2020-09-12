FROM tomcat:8.5.51-jdk8-adoptopenjdk-openj9

MAINTAINER ron4uk
RUN mkdir -p /usr/local/tomcat/webapps/

COPY /target/eCare.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]
EXPOSE 8080

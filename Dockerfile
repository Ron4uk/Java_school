FROM tomcat:8.5.51

MAINTAINER ron4uk
#RUN mkdir -p /usr/local/tomcat/webapps/
COPY /target/ROOT.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]

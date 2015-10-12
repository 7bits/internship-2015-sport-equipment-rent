
#!/bin/bash
gulp prod
mvn clean -Dmaven.tomcat.url=http://192.168.200.110:8080/manager/text tomcat7:redeploy 
scp -r src/main/resources/public developer@192.168.200.110:/home/sport-equipment-rent/public
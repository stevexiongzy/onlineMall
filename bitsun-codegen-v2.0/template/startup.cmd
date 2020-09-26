call gradle bootJar
call java -jar ${projectName}-facade\${projectName}-provider\build\libs\${projectName}-provider-0.0.1-SNAPSHOT.jar
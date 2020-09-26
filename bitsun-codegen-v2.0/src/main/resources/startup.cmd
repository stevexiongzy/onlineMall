call java -jar code-generator-2.0.jar
call gradle bootJar
call java -jar new-center-facade\new-center-provider\build\libs\new-center-provider-0.0.1-SNAPSHOT.jar
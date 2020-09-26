call gradle clean zip
cd build\libs
call java -jar code-generator-2.0.jar
cd outRoot\new-center
call gradle bootJar
call java -jar new-center-facade\new-center-provider\build\libs\new-center-provider-0.0.1-SNAPSHOT.jar
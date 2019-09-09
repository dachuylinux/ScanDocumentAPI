@rem batch script run service for client

@echo off
SET java_path=C:\Program Files (x86)\Java\jdk1.8.0_221
SET currentPath=%cd%
cd %java_path%\bin\
start java -Dserver.port=9090 -jar "%currentPath%"\scan-document-api-0.0.1-SNAPSHOT.jar
EXIT

#!/bin/sh
mvn clean package && docker build -t co.pablob.csrf.example/java-ee-7 .
docker rm -f java-ee-7 || true && docker run -d -p 8080:8080 -p 4848:4848 --name java-ee-7 co.pablob.csrf.example/java-ee-7 

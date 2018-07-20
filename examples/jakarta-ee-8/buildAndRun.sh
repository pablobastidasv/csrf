#!/bin/sh
mvn clean package && docker build -t co.pablob.csrf.example/jakarta-ee-8 .
docker rm -f jakarta-ee-8 || true && docker run -d -p 8080:8080 -p 4848:4848 --name jakarta-ee-8 co.pablob.csrf.example/jakarta-ee-8 

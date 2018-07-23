#!/bin/sh
mvn clean package && docker build -t co.pablob.csrf.example/specific_methods .
docker rm -f specific_methods || true && docker run -d -p 8080:8080 -p 4848:4848 --name specific_methods co.pablob.csrf.example/specific_methods 

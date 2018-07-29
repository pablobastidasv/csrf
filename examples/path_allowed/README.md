# Build
mvn clean package && docker build -t co.pablob.csrf.example/path_allowed .

# RUN

docker rm -f path_allowed || true && docker run -d -p 8080:8080 -p 4848:4848 --name path_allowed co.pablob.csrf.example/path_allowed 
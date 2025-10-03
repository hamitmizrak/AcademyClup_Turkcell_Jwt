# Innova Spring Boot



## Redis Docker Öncelikle çalıştırmak
```sh 
docker run -d --name redis \
  -p 6379:6379 \
  redis:7-alpine
# şifreli isterseniz:
# docker run -d --name redis -p 6379:6379 redis:7-alpine \
#   redis-server --requirepass "MyStrongPass!"

```

## Redis Docker Ping ()
```sh
Ubuntu ==> 
$ apt install redis-tools 
$ redis-cli -h 127.0.0.1 -p 6379 ping
# PONG dönerse bağlantı ok.
# Şifreli kurduysanız: redis-cli -a MyStrongPass! ping
```


## Redis Daocker
```sh 
```


## Maven
mvn clean install

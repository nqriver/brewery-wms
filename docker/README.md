# Docker Compose dla aplikacji Brewery WMS

Ten plik README opisuje serwisy zdefiniowane w pliku Docker Compose dla aplikacji Brewery WMS.
## Uruchomienie
Najpierw należy zbudować mikroserwis backendowy poleceniem
```
../gradlew clean build
```
Następnie uruchomić usługi zdefiniowane w docker-compose.yml:
```
docker compose up
```

## Serwisy

### Postgres

Kontener PostgreSQL wykorzystuje najnowszy obraz i jest konfigurowany za pomocą zmiennych środowiskowych, aby utworzyć bazę danych o nazwie "brewery-wms" z użytkownikiem "BREWERY_ADMIN" i hasłem "BREWERY". Dane są przechowywane w wolumenie Docker o nazwie "./postgres/data".

### Redis

Kontener Redis wykorzystuje obraz w wersji 5.0.6. Redis jest konfigurowany do pracy w trybie "append only" dla zwiększonej trwałości danych. Dane są przechowywane w wolumenie Docker o nazwie "./redis/data".

### ScyllaDB

Kontener ScyllaDB wykorzystuje najnowszy obraz. Korzysta z dwóch rdzeni procesora dla poprawy wydajności. Dane są przechowywane w wolumenie Docker o nazwie "./scylla/data".

### Quarkus-app

Quarkus-app to główna aplikacja backendowa. Aplikacja łączy się z bazą danych Postgres i ScyllaDB oraz korzysta z Redis jako cache. Zmiennych środowiskowych używa do konfiguracji połączeń z bazami danych i Redis.

### Test-linux-client

Kontener alpine to dodatkowy kontener stworzony w celach wyłącznie testowych do testowania połączeń z mikroserwisem backendowym Quarkus-app. Po uruchomieniu Docker Compose, można uruchomić linię poleceń alpine'a za pomocą  i wykonać żądanie testowe do Rest API backendu, aby sprawdzić, czy aplikacja Quarkus-app poprawnie odpowiada:
Aby to zrobić należy wykonać kroki:

Uruchomić usługi poleceniem
```
docker compose up
```

Przejść do drugiego okna terminala i uruchomić polecenie;
```
docker exec -it linux-client /bin/sh
```

Kolejne polecenia realizowane będą już w terminalu alpine

Instalacja polecenia curl w kontenerze (przy pierwszym uruchomieniu):
```bash
apk add curl
```

Request do backendu na endpoint logowania z poprawnymi danymi (powinien zwrócić odpowiedź z Json Web Tokenem):
```bash
curl -X POST -H "Content-Type: application/json" -d '{"login":"steve","password":"steve"}' http://quarkus-app:8080/managers/login
```
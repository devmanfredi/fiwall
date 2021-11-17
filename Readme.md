# FiWall

## Getting Started

### Prerequisites

- Git
- Maven 3.0+
- JDK 11+
- Docker 1.13.0+
- Docker Compose 1.23.1+

### Clone

To get started you can simply clone this repository using git:

```
git clone https://github.com/devmanfredi/fiwall.git
cd fiwall
```

#### Develop

Run the application from the command line using:

```
mvn spring-boot:run
```

The H2 database will start running at http://localhost:8080/h2-console
The app will start running at <http://localhost:8080>

#### Test

Run the postgres database from the command line using:

```
docker run -d --network=host \
    --name fiwall-db \
    -e POSTGRES_DB=fiwall \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_PASSWORD=postgres \
   postgres:10.4
```

Run the application from the command line using:

```
mvn spring-boot:run -Dspring-boot.run.profiles=test
```

To Stop the postgres database from the command line using:

```
docker stop fiwall-db
```

To Start again the postgres database from the command line using:

```
docker start fiwall-db
```

The app will start running at <http://localhost:8080>

#### Production

Execute docker compose

```
docker-compose up
```

The app will start running at <http://localhost:8080>

## Explore Rest APIs

Namespace     |   URL                        | HTTP Verb        | Result
--------------|----------------------------- | ---------------- | -------------------------
OAuth         | /oauth/signup                | POST             | Add user
Wallet        | /wallet/user                 | POST             | Create Wallet of User
Wallet        | /wallet/:id                  | GET              | Return Wallet  By Id
Wallet        | /wallet/transfer             | POST             | Transfer between Users
Wallet        | /wallet/withdraw             | POST             | Withdraw money of Wallet
Wallet        | /wallet/deposit              | POST             | Deposit value in Wallet

You can test them using postman or any other rest client.

## Design Arch

![alt text](https://github.com/[devmanfredi]/[fiwall]/[develop]/fiwall-arch.jpeg?raw=true)

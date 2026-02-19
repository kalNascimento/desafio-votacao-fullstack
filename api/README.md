# Sistema de Votação — API (Spring Boot)

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen)
![Architecture](https://img.shields.io/badge/architecture-MVC-blueviolet)
![Coverage](https://img.shields.io/badge/tests-90%25%2B-brightgreen)
![Status](https://img.shields.io/badge/version-v1-informational)
![Docker](https://img.shields.io/badge/docker-ready-blue)

API REST para gerenciamento de pautas e sessões de votação, permitindo
registrar votos e finalizar votações.

Esta aplicação está em sua **primeira versão** e foi desenvolvida com
foco em boas práticas de arquitetura, organização em camadas e alta
cobertura de testes.

------------------------------------------------------------------------

## Visão Geral

O sistema permite:

- Criação de pautas
- Abertura de sessões de votação
- Registro de votos
- Finalização de votações
- Listagem de pautas p/ combo box
- Listagem de sessões para votação

Obs.: Nesta versão **não há autenticação**.

------------------------------------------------------------------------

## Arquitetura

A aplicação segue o padrão **MVC** com separação clara de
responsabilidades.

A aplicação segue o padrão **MVC** com separação clara de
responsabilidades.

```
controller -> domain -> infra
```

### Controller

Responsável pela exposição da API.

- Controllers REST
  - BaseController com respostas padrão:
    - 400 --- Bad Request
    - 404 --- Not Found
    - 500 --- Internal Server Error
- DTOs (Request / Response)

------------------------------------------------------------------------

### Domain

Contém as regras de negócio.

- Services -> lógica de negócio
- Entities -> entidades JPA
- Repositories -> interfaces JPA
- Enums -> tipos do domínio
- Validators -> validações com exceções de regra de negócio

------------------------------------------------------------------------

### Infra

Componentes compartilhados.

- Configurações
- Mappers (MapStruct)
- Utilitários

------------------------------------------------------------------------

## Testes

A aplicação possui:

- Testes unitários com Mockito (Services)
- Testes de integração com Controllers
- Cobertura mínima exigida: **90%**

------------------------------------------------------------------------

## Configuração de Ambiente

A aplicação possui um arquivo de propriedades padrão e três perfis:

- local
  - PostgreSQL
- test
  - H2
- prod
  - PostgresSql

------------------------------------------------------------------------

## Docker

O projeto utiliza container Docker para facilitar a visualização.

```
docker compose --profile api up -d

Obs.: Sobe o container da API e do banco e disponibiliza na posta 8080,
se o perfil não for informado, sobe apenas o banco
```

------------------------------------------------------------------------

## Variáveis de Ambiente

Utilizamos `.env` para configuração do ambiente Docker.

Existe um arquivo:

    .env.example

Com as variáveis necessárias para configuração.

------------------------------------------------------------------------

## Como Executar

### Rodando localmente

``` bash
./gradlew bootRun
```

Ou com perfil:

``` bash
./gradlew bootRun -Dspring.profiles.active=local
```
ou
``` bash
./gradlew bootRun --args='--spring.profiles.active=local'
```

------------------------------------------------------------------------

### Rodando com Docker

``` bash
docker compose up --build
```

Ou com API

``` bash
docker compose --profile api up -d
```

------------------------------------------------------------------------

## Documentação da API

Após iniciar a aplicação:

    http://localhost:8080/swagger-ui.html

------------------------------------------------------------------------

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- H2 Database
- MapStruct
- Swagger / OpenAPI
- Mockito
- JUnit
- Docker

------------------------------------------------------------------------

## Funcionalidades Implementadas (v1)

- Gestão de pautas
- Gestão de sessões de votação
- Registro de votos
- Finalização de votação

------------------------------------------------------------------------

## Melhorias Futuras

- Autenticação e autorização
- Controle de usuários
- Auditoria de votos
- Filas com Kafka
- Versionamento da API

------------------------------------------------------------------------

## Autor

Kalebe Nascimento

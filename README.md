# Big Chat Brasil

Big Chat Brasil é uma aplicação web full-stack composta por um backend Java Spring Boot e um frontend React, com banco de dados PostgreSQL.
## Pré-requisitos

Certifique-se de ter o Docker e o Docker Compose instalados na sua máquina.

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Instruções de Uso

### 1. Clone o repositório:

```bash
git clone https://github.com/devjleonardo/big-chat-brasil.git
cd big-chat-brasil
```

### 2. Iniciar os Serviços com Docker Compose e Construir as Imagens

```git
docker-compose up -d --build
```

### 3. Listar Containers em Execução

```git
docker ps
```

Este comando exibe uma lista dos containers em execução, com informações como ID, nomes, portas expostas, etc.

### Acesso

Sua aplicação estará disponível em http://localhost:3000.

O backend estará disponível em `localhost:8080`.

O banco de dados PostgreSQL estará disponível em `localhost:5432`.

Acessa a [Documentação da API](http://localhost:8080/swagger-ui/index.html).

### Notas Adicionais

- Este README assume que você está no mesmo diretório que o arquivo docker-compose.yml.

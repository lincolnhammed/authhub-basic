# AuthHub Basic

Sistema de autenticação **Full Stack** desenvolvido com **Spring Boot**, **Spring Security**, **JWT**, **React**, **MySQL** e **Docker Compose**.

Este projeto representa a versão básica do AuthHub. Posteriormente, será criada uma versão separada com autenticação através de redes sociais.

## Tecnologias utilizadas

### Backend

* Java 21
* Spring Boot
* Spring Security
* JWT
* JPA / Hibernate
* MySQL
* Maven

### Frontend

* React
* Vite
* Nginx

### Infraestrutura

* Docker
* Docker Compose

## Funcionalidades

* Cadastro de utilizadores
* Login
* Autenticação utilizando JWT
* Refresh Token
* Logout
* Endpoints protegidos
* Criptografia de palavras-passe com BCrypt
* Persistência de dados no MySQL
* Frontend desenvolvido em React
* Backend, frontend e banco de dados executados em containers Docker
* Rede Docker para comunicação entre os serviços
* Volume Docker para persistência do banco de dados

## Arquitetura

```text
                    Navegador
                        │
                        │ :8087
                        ▼
              ┌──────────────────┐
              │ React + Nginx    │
              │ authhub-frontend  │
              └────────┬─────────┘
                       │
                       │ :8088
                       ▼
              ┌──────────────────┐
              │ Spring Boot      │
              │ authhub-backend  │
              └────────┬─────────┘
                       │
                       │ MySQL :3306
                       ▼
              ┌──────────────────┐
              │ MySQL 8.0        │
              │ authhub-mysql    │
              └──────────────────┘
```

## Serviços Docker

| Serviço  | Container          | Porta do PC | Porta do container |
| -------- | ------------------ | ----------: | -----------------: |
| Frontend | `authhub-frontend` |      `8087` |               `80` |
| Backend  | `authhub-backend`  |      `8088` |             `8080` |
| MySQL    | `authhub-mysql`    |      `3308` |             `3306` |

O MySQL utiliza um volume Docker para manter os dados mesmo quando o container é recriado.

## Variáveis de ambiente

As configurações sensíveis são armazenadas no arquivo `.env`.

O arquivo `.env` está incluído no `.gitignore` e **não deve ser enviado para o Git**.

Para criar o arquivo de configuração:

```bash
cp .env.example .env
```

Depois, configure as variáveis:

```env
MYSQL_ROOT_PASSWORD=
MYSQL_USER=
MYSQL_PASSWORD=
JWT_SECRET=
```

**Nunca publique senhas ou chaves secretas no repositório.**

## Executando o projeto

Clone o repositório:

```bash
git clone <URL-DO-REPOSITORIO>
```

Entre na pasta:

```bash
cd authhub-basic
```

Crie o arquivo `.env`:

```bash
cp .env.example .env
```

Configure as variáveis do arquivo `.env`.

Depois, construa e execute os containers:

```bash
sudo docker compose up --build
```

Para executar em segundo plano:

```bash
sudo docker compose up -d --build
```

Para verificar os containers:

```bash
sudo docker compose ps
```

## Acesso

### Frontend

```text
http://localhost:8087
```

### Backend

```text
http://localhost:8088
```

### MySQL

```text
localhost:3308
```

O backend comunica-se com o MySQL através da rede interna do Docker.

## Estrutura do projeto

```text
authhub-basic/
│
├── authhub/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── authhub-frontend/
│   ├── src/
│   ├── package.json
│   ├── Dockerfile
│   └── nginx.conf
│
├── docker-compose.yml
├── .env.example
├── .gitignore
└── README.md
```

## Segurança

As palavras-passe dos utilizadores são armazenadas utilizando **BCrypt**.

As configurações do JWT e as credenciais do banco de dados são fornecidas através de variáveis de ambiente.

O arquivo `.env`, que contém os valores reais dessas configurações, não é versionado no Git.

## Próximos passos

Este projeto representa a versão básica do sistema de autenticação.

Uma segunda versão será criada separadamente a partir deste projeto para adicionar **Login Social utilizando OAuth2**.

Entre as possíveis integrações estão:

* Google
* GitHub

A versão atual mantém o foco na implementação da autenticação tradicional com JWT.

## Autor

**Lincoln Silva**

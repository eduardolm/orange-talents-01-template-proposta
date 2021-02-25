# Proposal  - API
![Version](src/main/resources/static/img/version.svg)
![License](src/main/resources/static/img/license.svg)
![Coverage](src/main/resources/static/img/coverage.svg)

## Objetivos
Desenvolver uma API para criação de proposta de cartão de crédito. Essa API deve permitir o cadastro do cliente, fazer verificações de crédito deste cliente comunicando-se com APIs externas. Em caso de crédito aprovado, deve ser criado um cartão de crédito para este cliente. A API deve ainda implementar diversas funcionalidades relacionadas ao cartão de crédito, como aviso de viagem, bloqueio de cartão, integração do cartão com carteiras digitais como Paypal, por exemplo. A API deve ainda permitir o envio de arquivos de biometria.

## Tecnologias utilizadas
O projeto foi desenvolvido utilizando as seguintes tecnologias:

 + Java 11
 + Spring Boot 2.4.2
 + Docker
 + Docker-compose
 + MySQL
 + Keycloak
 + Prometheus
 + Jaeger
 + Grafana
 + Github

## Obervações
Para a funcionalidade de upload de biometria, os dados são armazenados em base64. Não fizemos uso de serviços de cloud computing como o S3 para armazenamento dessas informações.
Como utilizamos Keycloak, não implementamos cadastros de usuários na API, deixando esta funcionalidade para o Keycloak. Devemos no entanto autenticar os usuários para que possamos interagir com os endpoints da API.
Todos os serviços utilizados estão sendo executados em containers docker.

## Endpoints & Payloads
### Endpoints
#### Autenticação
**Ação** | **Endpoint** | **Método**
---------- | ------------ | ----------
Autenticar | _auth/realms/nosso-cartao/protocol/openid-connect/token_ | POST

#### Propostas
**Ação** | **Endpoint** | **Método**
---------- | ------------ | ----------
Criar proposta | _/api/propostas_ | POST
Listar por id | _/api/propostas/{id}_ | GET

#### Cartões
**Ação** | **Endpoint** | **Método**
---------- | ------------ | ----------
Criar biometria | _/api/cartoes/{id}/images_ | POST
Bloquear cartão | _/api/cartoes/bloqueios?id={id}_ | POST
Listar por id | _/api/cartoes/{id}_ | GET
Criar aviso de viagem | _/api/cartoes/viagens?id={id}_ | POST
Criar carteira digital | _/api/cartoes/carteiras/id=?{id}_ | POST

### Payloads - (request & response)
#### Autenticação
##### POST - request (authenticate user)
    {
        "username": "johndoe",
        "password": "12345",
        "grant_type": "password",
        "client_id": "proposal", 
        "client_secret": "secret"
    }

##### POST - response
    {
        "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJTbDZjWmlrQV9JeVpkZGdiNW9idTZnVjVTZWlYWTBwTUZ6emxUQk4zRnE0In0.eyJleHAiOjE2MTQyOTE1MTMsImlhdCI6MTYxNDI1NTUxMywianRpIjoiODg1MTJkNjEtNWYyYy00NGMxLThkOWQtNjY4MGUxYTU2NGJjIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDoxODA4MC9hdXRoL3JlYWxtcy9ub3Nzby1jYXJ0YW8iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMWVhYjczMDItNGNlMS00MTBmLThmYWYtZjRhMzY1YmQyNTI1IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicHJvcG9zYWwiLCJzZXNzaW9uX3N0YXRlIjoiYjJiNjU2MGMtMGJjMy00ZTRkLWJjMGUtYTIyZDE3MmZjNjE3IiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJybNvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9wb3N0YXM6cmVhZCBwcm9wb3N0YXM6d3JpdGUgcHJvZmlsZSBjYXJ0b2VzOndyaXRlIGNhcnRvZXM6cmVhZCBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiSm9obiBEb2UiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJqb2huZG9lIiwiZ2l2ZW5fbmFtZSI6IkpvaG4iLCJmYW1pbHlfbmFtZSI6IkRvZSIsImVtYWlsIjoiam9obmRvZUBlbWFpbC5jb20ifQ.qIJEx9So-jfmUgWZjCUSUM3iOgVzJ0zqa5hKOXjuf6tVhdlDh0dwJszwXEpn-zL1xZvQkkPl6DVpkSdDqQcTfC2PEdwCg_sBqmhQjwYytznqxoDWzCh__3ZDXA6QW2-E1-iEPszKTle7zJA2Vi-tM6DCFO4qvSDs6jHr6Sl2MW3w75VeVGMR-2SnBuRVNIbWbO3w3FXSQEABSaHi6nkpRXxTQQbGK3qZY6HPwG4neM5eyudnecCnvjmNCrYK9lzll3JOBjk0omR1DMY4rBKgEf8ScaJVmC162-6eYA6uCFc-fen6p8pDuxrYHHVDXRpOq3jdN5M57bWuvGYyOduTAQ",
        "expires_in": 300,
        "refresh_expires_in": 1800,
        "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIwYjllOTdiNi0zYWJlLTQ0NWQtYTZiZC01ZTIyYjMzYmViODgifQ.eyJleHAiOjE2MTQyNTczMTQsImlhdCI6MTYxNDI1NTUxNCwianRpIjoiOTRhOWIxYTYtNTM3Ny00NTAzLWIzODEtMDY3YjBlNDQ3Yzg4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDoxODA4MC9hdXRoL3JlYWxtcy9ub3Nzby1jYXJ0YW8iLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjE4MDgwL2F1dGgvcmVhbG1zL25vc3NvLWNhcnRhbyIsInN1YiI6IjFlYWI3MzAyLTRjZTEtNDEwZi04ZmFmLWY0YTM2NWJkMjUyNSIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJwcm9wb3NhbCIsInNlc3Npb25fc3RhdGUiOiJiMmI2NTYwYy0wYmMzLTRlNGQtYmMwZS1hMjJkMTcyZmM2MTciLCJzY29wZSI6InByb3Bvc3RhczpyZWFkIHByb3Bvc3Rhczp3cml0ZSBwcm9maWxlIGNhcnRvZXM6d3JpdGUg3MZiSi9oD4LJ13Jee4K_ZHe5v7djg",
        "token_type": "Bearer",
        "not-before-policy": 0,
        "session_state": "b2b6560c-0bc3-4e4d-bc0e-a22d172fc617",
        "scope": "propostas:read propostas:write profile cartoes:write cartoes:read email"
    }

#### Propostas
##### POST - request (create proposal)
    {
        "document": "451.340.270-99",
        "name": "Samantha Santos",
        "email": "sammy@email.com",
        "income": "5000.00",
        "address": {
            "street": "Rua do Gasometro",
            "zip": "69303-495",
            "number": "35",
            "complement": "Casa"
        }
    }

##### POST - response
    {
        "id": 1,
        "document": "451.340.270-99",
        "name": "Samantha Santos",
        "email": "sammy@email.com",
        "income": 5000.00,
        "address": {
            "street": "Rua do Gasometro",
            "zip": "69303-495",
            "number": "35",
            "complement": "Casa"
        },
        "status": "ELEGIVEL"
    }

##### GET - (by id) response
    {
        "id": 1,
        "document": "451.340.270-99",
        "name": "Samantha Santos",
        "email": "sammy@email.com",
        "income": 5000.00,
        "address": {
            "street": "Rua do Gasometro",
            "zip": "69303-495",
            "number": "35",
            "complement": "Casa"
        },
        "status": "ELEGIVEL_CARTAO_ASSOCIADO"
    }

#### Cartões
##### POST - Criar biometria (response)
Header location: http://localhost:8080/api/cartoes/6880-8630-9412-2291/images/1

##### POST - Aviso de viagem (request)
    {
        "destino": "Estados Unidos",
        "validoAte": "2021-04-02"
    }

##### POST - response
    {
        "id": "6880-8630-9412-2291",
        "createdAt": "2021-02-25T12:28:23.343617",
        "name": "Samantha Santos",
        "blockedSet": [],
        "wallets": [],
        "installments": [],
        "creditLimit": 3223,
        "renegotiation": null,
        "dueDate": {
            "id": "c89d85ab-29bb-4b78-a4dd-7c326251ac16",
            "dia": 30,
            "dataDeCriacao": "2021-02-25T12:28:23.344519"
        },
        "proposal": {
            "id": 1,
            "document": "451.340.270-99",
            "name": "Samantha Santos",
            "email": "sammy@email.com",
            "income": 5000.00,
            "address": {
                "street": "Rua do Gasometro",
                "zip": "69303-495",
                "number": "35",
                "complement": "Casa"
            },
            "status": "ELEGIVEL_CARTAO_ASSOCIADO"
        },
        "images": [
            {
                "id": 1,
                "originalFileName": "CPF.jpg",
                "createdAt": "2021-02-25T09:31:27.350196"
            }
        ],
        "status": "ATIVO",
        "notes": [
            {
                "id": 1,
                "destination": "Estados Unidos",
                "validUntil": "2021-04-02",
                "createdAt": "2021-02-25T09:32:55.2819933",
                "clientIp": "0:0:0:0:0:0:0:1",
                "userAgent": "PostmanRuntime/7.26.10"
            }
        ]
    }

##### POST - Cadastrar carteira (request)
    {
        "email": "sammy@email.com",
        "carteira": "PAYPAL"
    }

##### POST - response
    {
        "id": "6880-8630-9412-2291",
        "createdAt": "2021-02-25T12:28:23.343617",
        "name": "Samantha Santos",
        "blockedSet": [],
        "wallets": [
            {
                "id": "5f4ed2b7-e5db-4d4d-9365-0428ae3328a8",
                "email": "maria@email.com",
                "associatedAt": "2021-02-25T09:34:04.7487774",
                "issuer": "PAYPAL"
            },
            {
                "id": "5f4ed2b7-e5db-4d4d-9365-0428ae3328a8",
                "email": "sammy@email.com",
                "associatedAt": "2021-02-25T09:34:04.7487774",
                "issuer": "PAYPAL"
            }
        ],
        "installments": [],
        "creditLimit": 3223,
        "renegotiation": null,
        "dueDate": {
            "id": "c89d85ab-29bb-4b78-a4dd-7c326251ac16",
            "dia": 30,
            "dataDeCriacao": "2021-02-25T12:28:23.344519"
        },
        "proposal": {
            "id": 1,
            "document": "451.340.270-99",
            "name": "Samantha Santos",
            "email": "sammy@email.com",
            "income": 5000.00,
            "address": {
                "street": "Rua do Gasometro",
                "zip": "69303-495",
                "number": "35",
                "complement": "Casa"
            },
            "status": "ELEGIVEL_CARTAO_ASSOCIADO"
        },
        "images": [
            {
                "id": 1,
                "originalFileName": "CPF.jpg",
                "createdAt": "2021-02-25T09:31:27.350196"
            }
        ],
        "status": "ATIVO",
        "notes": [
            {
                "id": 1,
                "destination": "Estados Unidos",
                "validUntil": "2021-04-02",
                "createdAt": "2021-02-25T09:32:55.281993",
                "clientIp": "0:0:0:0:0:0:0:1",
                "userAgent": "PostmanRuntime/7.26.10"
            }
        ]
    }

##### POST - Bloquear cartão (response)
    {
        "id": "6880-8630-9412-2291",
        "createdAt": "2021-02-25T12:28:23.343617",
        "name": "Samantha Santos",
        "blockedSet": [],
        "wallets": [
            {
                "id": "5f4ed2b7-e5db-4d4d-9365-0428ae3328a8",
                "email": "sammy@email.com",
                "associatedAt": "2021-02-25T09:34:04.748777",
                "issuer": "PAYPAL"
            }
        ],
        "installments": [],
        "creditLimit": 3223,
        "renegotiation": null,
        "dueDate": {
            "id": "c89d85ab-29bb-4b78-a4dd-7c326251ac16",
            "dia": 30,
            "dataDeCriacao": "2021-02-25T12:28:23.344519"
        },
        "proposal": {
            "id": 1,
            "document": "451.340.270-99",
            "name": "Samantha Santos",
            "email": "sammy@email.com",
            "income": 5000.00,
            "address": {
                "street": "Rua do Gasometro",
                "zip": "69303-495",
                "number": "35",
                "complement": "Casa"
            },
            "status": "ELEGIVEL_CARTAO_ASSOCIADO"
        },
        "images": [
            {
                "id": 1,
                "originalFileName": "CPF.jpg",
                "createdAt": "2021-02-25T09:31:27.350196"
            }
        ],
        "status": "BLOQUEADO",
        "notes": [
            {
                "id": 1,
                "destination": "Estados Unidos",
                "validUntil": "2021-04-02",
                "createdAt": "2021-02-25T09:32:55.281993",
                "clientIp": "0:0:0:0:0:0:0:1",
                "userAgent": "PostmanRuntime/7.26.10"
            }
        ]
    }

##### GET  - (by id) response
    {
        "id": "6880-8630-9412-2291",
        "createdAt": "2021-02-25T12:28:23.343617",
        "name": "Samantha Santos",
        "blockedSet": [
            {
                "id": "0edd9fe8-a468-4b99-8dc7-d5a234a4eb26",
                "blockedAt": "2021-02-25T09:36:32.122175",
                "responsibleSystem": "PostmanRuntime/7.26.10- 0:0:0:0:0:0:0:1",
                "active": true
            }
        ],
        "wallets": [
            {
                "id": "5f4ed2b7-e5db-4d4d-9365-0428ae3328a8",
                "email": "sammy@email.com",
                "associatedAt": "2021-02-25T09:34:04.748777",
                "issuer": "PAYPAL"
            }
        ],
        "installments": [],
        "creditLimit": 3223,
        "renegotiation": null,
        "dueDate": {
            "id": "c89d85ab-29bb-4b78-a4dd-7c326251ac16",
            "dia": 30,
            "dataDeCriacao": "2021-02-25T12:28:23.344519"
        },
        "proposal": {
            "id": 1,
            "document": "451.340.270-99",
            "name": "Samantha Santos",
            "email": "sammy@email.com",
            "income": 5000.00,
            "address": {
                "street": "Rua do Gasometro",
                "zip": "69303-495",
                "number": "35",
                "complement": "Casa"
            },
            "status": "ELEGIVEL_CARTAO_ASSOCIADO"
        },
        "images": [
            {
                "id": 1,
                "originalFileName": "CPF.jpg",
                "createdAt": "2021-02-25T09:31:27.350196"
            }
        ],
        "status": "BLOQUEADO",
        "notes": [
            {
                "id": 1,
                "destination": "Estados Unidos",
                "validUntil": "2021-04-02",
                "createdAt": "2021-02-25T09:32:55.281993",
                "clientIp": "0:0:0:0:0:0:0:1",
                "userAgent": "PostmanRuntime/7.26.10"
            }
        ]
    }
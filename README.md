# Auth API

> API REST de estudo para cadastro, login e validação de autenticação usando JWT.

**Stack:** Java 21 · Spring Boot 3 · Spring Security · JWT · Spring Data JPA · PostgreSQL/H2 · Maven  
**Status:** Projeto de estudo / portfólio

## Funcionalidades

- Cadastro de usuários e login com retorno de token JWT
- Proteção de rotas por autenticação
- Separação em controller, service, repository, DTO e camada de segurança
- Validação de entradas com Jakarta Validation

## Endpoints principais

| Método | Rota | Objetivo |
|---|---|---|
| `POST` | `/api/auth/register` | Cria conta e retorna token |
| `POST` | `/api/auth/login` | Autentica e retorna token |
| `GET` | `/api/auth/me` | Verifica acesso autenticado |

## Executar localmente

Requisitos: **JDK 21** e conexão com o banco configurada conforme `src/main/resources/application.properties`.

```bash
git clone https://github.com/fgscarvalho37-a11y/auth-api.git
cd auth-api
./mvnw spring-boot:run
```

No Windows, utilize `mvnw.cmd spring-boot:run`. A porta e as propriedades de banco dependem da configuração da aplicação.

## Organização

`controller` recebe as requisições; `service` centraliza regras; `repository` acessa dados; `model` e `dto` representam entidades e dados de entrada/saída.

## Notas

Projeto de portfólio e aprendizado. Configure o datasource e a chave de assinatura JWT conforme os arquivos de configuração antes de executar; nunca publique valores reais.

---

Desenvolvido por [Felipe Gomes](https://github.com/fgscarvalho37-a11y).
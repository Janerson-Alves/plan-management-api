# 🚀 Plan Management API

API RESTful desenvolvida em Java 20 e Spring Boot 3 para gerenciamento, consulta e provisionamento de planos de serviços. 

O projeto aplica os princípios de Clean Code e arquitetura em camadas desacoplada (Controller-Service), utilizando o container de Inversão de Controle (IoC) do Spring para injeção de dependências e gerenciamento de estado em memória.

---

## 🛠️ Tecnologias Utilizadas

- **Java 20**
- **Spring Boot 3** (Spring Web)
- **Spring Data JPA** (Hibernate ORM com `ddl-auto=update`)
- **Docker & Docker Compose** (Containerização do Banco de Dados)
- **PostgreSQL** (Banco de Dados Relacional em Container)
- **DBeaver** (Gerenciamento e Inspeção de Dados)
- **Spring Boot Validation** (Bean Validation & `@RestControllerAdvice`)
- **SpringDoc OpenAPI / Swagger 3** (Documentação Interativa)
- **Maven** (Gerenciador de Dependências)
- **JUnit 5 & Mockito** (Testes Unitários Automatizados)
- **Spring Security 6 & RBAC** (Autenticação HTTP Basic, BCrypt & Controle de Acesso por Roles: USER e ADMIN)
- **Auth0 Java-JWT** (Geração, Assinatura HMAC256 e Validação de Tokens Stateless)

---

## 🐳 Como Executar o Banco de Dados (Docker)

Para iniciar o banco de dados PostgreSQL conteinerizado, execute na raiz do projeto:

```bash
docker compose up -d
```

---

## 🏗️ Estrutura do Projeto (Layered Architecture)

A aplicação foi estruturada seguindo o padrão de arquitetura em camadas e desacoplamento de responsabilidades:

- 📁 `controllers`: Camada de exposição dos endpoints REST (`PlanoController`).
- 📁 `dto`: Objetos de transferência de dados com validações do Bean Validation (`PlanoDTO`).
- 📁 `entities`: Entidades de mapeamento objeto-relacional JPA/Hibernate (`PlanoTelefonia`).
- 📁 `exceptions`: Manipulador global de erros e padronização de respostas (`GlobalExceptionHandler`, `ErroResposta`).
- 📁 `repository`: Interfaces de persistência e consultas via Spring Data JPA (`PlanoRepository`).
- 📁 `services`: Regras de negócio, conversão de DTOs e orquestração (`PlanoService`).

---

## 🛡️ Tratamento Global de Exceções (`@RestControllerAdvice`)

A API conta com um manipulador global de exceções (`GlobalExceptionHandler`) que padroniza o formato dos erros retornados ao cliente em um DTO `ErroResposta`:

- **`400 Bad Request`**: Interceptado via `@ExceptionHandler(MethodArgumentNotValidException.class)`. Formata e consolida todas as violações do Bean Validation (`@NotBlank`, `@Positive`) em uma mensagem detalhada e legível, acompanhada de `status`, `erro` e `timestamp`.
- **`404 Not Found`**: Tratado no `RestController` com `ResponseEntity.notFound()` quando um recurso buscado, atualizado ou deletado por ID não existe na base de dados.

---

## 📌 Arquitetura & Conceitos Aplicados

- **Arquitetura em Camadas:** Divisão clara entre Web (`@RestController`) e Negócio (`@Service`).
- **Injeção de Dependência:** Uso de `@Autowired` para gerenciamento do ciclo de vida dos componentes.
- **Validação na Borda (Bean Validation):** Proteção dos endpoints com `@Valid`, `@NotBlank` e `@Positive`, retornando `400 Bad Request` para payloads inválidos.
- **RESTful Mappings:** Manipulação completa de verbos HTTP (`GET`, `POST`, `PUT`, `DELETE`).

---

## 🔌 Endpoints da API

Base URL: `http://localhost:8080/api/planos`

| Método | Endpoint | Descrição | Status HTTP |
| :--- | :--- | :--- | :--- |
| **GET** | `/status` | Health Check da aplicação | `200 OK` |
| **GET** | `/?page=0&size=10&sort=nome,asc` | Lista os planos com paginação e ordenação | `200 OK` |
| **GET** | `/{id}` | Busca um plano específico pelo ID | `200 OK` / `404 Not Found` |
| **GET** | `/buscar-por-nome?nome={texto}` | Filtra planos por nome (case-insensitive) | `200 OK` |
| **GET** | `/filtrar-preco?valorMaximo={valor}` | Filtra planos por valor máximo mensal | `200 OK` |
| **GET** | `/estatisticas` | Retorna total de planos e média de valores | `200 OK` |
| **POST** | `/` | Cadastra um novo plano de serviço | `200 OK` / `400 Bad Request` |
| **PUT** | `/{id}` | Atualiza os dados de um plano existente | `200 OK` / `400 Bad Request` / `404 Not Found` |
| **DELETE** | `/{id}` | Remove um plano de serviço | `204 No Content` / `404 Not Found` |

---

### 🛡️ Autenticação Stateless (JWT)
* Todas as rotas de negócios exigem o cabeçalho HTTP:
  `Authorization: Bearer <seu-token-jwt>`
* Obtenha o token via `POST /api/auth/login`.
* No Swagger UI, utilize o botão **Authorize 🔓** para persistir o token nas chamadas interativas.

---

## 📖 Documentação Interativa (Swagger / OpenAPI)

Com a aplicação rodando, acesse:
- **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

---

## 🛡️ Tratamento de Respostas e Exceções

- **`200 OK`**: Retornado em consultas e atualizações realizadas com sucesso.
- **`204 No Content`**: Retornado na exclusão bem-sucedida de um registro.
- **`400 Bad Request`**: Retornado pelo Bean Validation quando os dados enviados no payload ferem as regras de negócio (`@NotBlank`, `@Positive`).
- **`404 Not Found`**: Retornado quando a operação busca, atualiza ou deleta um recurso com ID inexistente no banco de dados.

---

## 💻 Exemplo de Payload (POST)

**Endpoint:** `POST /api/planos`  
**Header:** `Content-Type: application/json`

```json
{
  "id": 1,
  "nome": "Plano Premium 100GB",
  "franquiaGb": 100,
  "valorMensal": 149.90
}

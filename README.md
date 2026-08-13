# 🚀 Plan Management API

API RESTful desenvolvida em Java 20 e Spring Boot 3 para gerenciamento, consulta e provisionamento de planos de serviços. 

O projeto aplica os princípios de Clean Code e arquitetura em camadas desacoplada (Controller-Service), utilizando o container de Inversão de Controle (IoC) do Spring para injeção de dependências e gerenciamento de estado em memória.

---

## 🛠️ Tecnologias Utilizadas

- **Java 20**
- **Spring Boot 3** (Spring Web)
- **Spring Data JPA** (Persistência Relacional & ORM/Hibernate)
- **H2 Database** (Banco de Dados Relacional em Memória)
- **Spring Boot Validation** (Bean Validation)
- **Maven** (Gerenciador de Dependências)
- **Postman** (Testes e Validação de Endpoints)

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
| **GET** | `/` | Lista todos os planos cadastrados | `200 OK` |
| **GET** | `/{id}` | Busca um plano específico pelo ID | `200 OK` / `404 Not Found` |
| **GET** | `/buscar-por-nome?nome={texto}` | Filtra planos por nome (case-insensitive) | `200 OK` |
| **GET** | `/filtrar-preco?valorMaximo={valor}` | Filtra planos por valor máximo mensal | `200 OK` |
| **POST** | `/` | Cadastra um novo plano de serviço | `200 OK` / `400 Bad Request` |
| **PUT** | `/{id}` | Atualiza os dados de um plano existente | `200 OK` / `400 Bad Request` / `404 Not Found` |
| **DELETE** | `/{id}` | Remove um plano de serviço | `204 No Content` / `404 Not Found` |

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

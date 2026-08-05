# 🚀 Plan Management API

API RESTful desenvolvida em Java 20 e Spring Boot 3 para gerenciamento, consulta e provisionamento de planos de serviços. 

O projeto aplica os princípios de Clean Code e arquitetura em camadas desacoplada (Controller-Service), utilizando o container de Inversão de Controle (IoC) do Spring para injeção de dependências e gerenciamento de estado em memória.

---

## 🛠️ Tecnologias Utilizadas

- **Java 20**
- **Spring Boot 3** (Spring Web)
- **Maven** (Gerenciador de Dependências)
- **Postman** (Testes e Validação de Endpoints)

---

## 📌 Arquitetura & Conceitos Aplicados

- **Arquitetura em Camadas:** Divisão clara de responsabilidades entre a camada de exposição Web (`@RestController`) e a camada de regras de negócio (`@Service`).
- **Injeção de Dependência:** Uso de `@Autowired` para desacoplamento e gerenciamento do ciclo de vida dos componentes pelo Spring IoC.
- **Java Streams API:** Processamento declarativo e filtragem de dados em memória.
- **RESTful Mappings:** Manipulação correta de verbos HTTP (`GET`, `POST`), corpo de requisição (`@RequestBody`) e parâmetros de rota (`@PathVariable`).

---

## 🔌 Endpoints da API

Base URL: `http://localhost:8080/api/planos`

| Método | Endpoint | Descrição | Status HTTP |
| :--- | :--- | :--- | :--- |
| **GET** | `/status` | Health Check da aplicação | `200 OK` |
| **GET** | `/` | Lista todos os planos cadastrados | `200 OK` |
| **GET** | `/{id}` | Busca um plano específico pelo ID | `200 OK` |
| **POST** | `/` | Cadastra um novo plano de serviço | `200 OK` |

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

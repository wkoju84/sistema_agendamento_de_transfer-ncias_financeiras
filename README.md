# Sistema de Agendamento de Transferências Financeiras

Sistema web para agendamento de transferências financeiras com cálculo automático de taxas baseado na data da transferência.

---

## 🚀 Tecnologias utilizadas

### Back-end
- Java 11
- Spring Boot 2.7.18
- Spring Web
- Spring Data JPA
- Spring Validation
- H2 Database (em memória)
- JUnit 5
- Mockito

### Front-end
- Vue.js 3
- Axios

---

## 🏗️ Decisões arquiteturais

### Arquitetura em camadas
O back-end foi estruturado em três camadas bem definidas:

- **Controller** — responsável por receber e responder requisições HTTP. Não contém regra de negócio.
- **Service** — contém toda a lógica de negócio, incluindo o cálculo de taxas e validações.
- **Repository** — responsável pela comunicação com o banco de dados via Spring Data JPA.

### DTOs (Data Transfer Objects)
Foram criados DTOs separados da entidade para controlar o que entra e o que sai da API:

- `TransferenciaRequest` — contém apenas os campos que o usuário pode enviar. Campos calculados pelo sistema (`taxa`, `dataAgendamento`) não são expostos na entrada.
- `TransferenciaResponse` — contém todos os campos retornados pela API, incluindo os calculados.

### Tratamento de erros
Implementado com `@RestControllerAdvice` centralizando o tratamento de duas exceções:

- `TaxaNaoAplicavelException` — lançada quando a data de transferência ultrapassa 50 dias. Retorna HTTP 422.
- `MethodArgumentNotValidException` — lançada quando o Bean Validation rejeita o DTO. Retorna HTTP 400 com os campos inválidos.

### Banco de dados
Utilizado H2 em memória conforme requisito do enunciado. A escolha elimina necessidade de configuração externa e o banco sobe junto com a aplicação. Em produção seria substituído por PostgreSQL ou MySQL — a troca seria transparente para o código graças à abstração do Spring Data JPA.

### Cálculo de taxa
A taxa é calculada no Service com base na diferença em dias entre a data de agendamento (hoje) e a data de transferência:

| Dias | Taxa fixa | Taxa % |
|------|-----------|--------|
| 0 | R$ 3,00 | 2,5% |
| 1 a 10 | R$ 12,00 | 0% |
| 11 a 20 | R$ 0,00 | 8,2% |
| 21 a 30 | R$ 0,00 | 6,9% |
| 31 a 40 | R$ 0,00 | 4,7% |
| 41 a 50 | R$ 0,00 | 1,7% |
| Acima de 50 | ❌ Inválido | — |

### Nota sobre versão do Java
O enunciado solicita Java 11. O projeto foi desenvolvido com Java 11 e Spring Boot 2.7.18, última versão com suporte a Java 11. Em Java 17 seriam utilizados Records para os DTOs, eliminando boilerplate, e Switch expressions na lógica de cálculo de taxa.

---

## ▶️ Como executar o projeto

### Pré-requisitos
- Java 11 instalado
- Maven instalado
- Node.js instalado (para o front-end)

### Back-end

```bash
# Na raiz do projeto
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

O console do H2 estará disponível em `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:transferencias`
- Usuário: `sa`
- Senha: (deixar em branco)

### Front-end

```bash
# Na pasta frontend
cd frontend
npm install
npm run serve
```

O front-end estará disponível em `http://localhost:8081` (Vue.js CLI) ou `http://localhost:5173` (Vite)

---

## 📡 Endpoints da API

### Agendar transferência
```http
POST /transferencias
Content-Type: application/json

{
  "contaOrigem": "1234567890",
  "contaDestino": "0987654321",
  "valor": 1000.00,
  "dataTransferencia": "2025-07-10"
}
```

### Listar agendamentos
```http
GET /transferencias
```
---

## ✅ Testes

```bash
./mvnw test
```

Os testes unitários cobrem todos os casos da tabela de taxas, incluindo os limites de cada faixa e o caso de data inválida.
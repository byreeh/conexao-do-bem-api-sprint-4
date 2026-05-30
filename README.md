# Conexão do Bem API - Sprint 04

## Descrição
Sistema desenvolvido para gerenciamento de pacientes em uma clínica odontológica. A aplicação permite realizar operações de cadastro, listagem, atualização e exclusão de pacientes através de uma API REST desenvolvida com Quarkus.

---

## Tecnologias Utilizadas
- Java
- Quarkus
- Maven
- Oracle Database
- HTML, CSS e JavaScript 

---

## Como Executar o Projeto

### Backend (API)

1. Clone o repositório
2. Execute o comando:
mvn quarkus:dev


3. Acesse no navegador:
- Swagger: http://localhost:8081/q/swagger-ui
- API: http://localhost:8081/paciente

---

### Frontend

1. Abra o arquivo:
index.html


2. Utilize as funcionalidades:
- Cadastrar paciente
- Listar pacientes
- Excluir paciente

---

## Funcionalidades

- Cadastro de pacientes
- Listagem de pacientes ativos
- Atualização de dados
- Exclusão lógica de pacientes

---

## Estrutura do Projeto

- `resource` → Endpoints da API
- `bo` → Regras de negócio
- `dao` → Acesso ao banco de dados
- `entities` → Classes modelo

---

## Observações

- O sistema utiliza banco de dados Oracle
- É necessário configurar a conexão no arquivo `application.properties`
- A aplicação depende da existência da tabela `TB_PACIENTE` e da sequence `SEQ_PACIENTE`

---

## Integrantes

- Ilda Adosfo - RM: 568233 | Turma: 1TDSPA
- Renata Lessa - RM: 568510 | Turma: 1TDSPR 

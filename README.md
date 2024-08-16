# API REST Smart Park

Bem-vindo à API REST Smart Park! Esta API é responsável por gerenciar os recursos relacionados a um estacionamento, como clientes, pagamentos, registros, etc.

## Tecnologias Utilizadas

- **Docker**: Containerização da aplicação e de seus serviços.
- **Docker Compose**: Orquestração de múltiplos containers Docker.
- **PostgreSQL**: Banco de dados relacional utilizado para armazenar dados.
- **Java**: Linguagem de programação utilizada para desenvolver a API.
- **Spring Boot**: Framework para criar aplicações Java baseadas em Spring de forma rápida.
- **Spring Security**: Framework para gerenciar a segurança e autenticação da aplicação.

## Instruções para Execução
Para executar a aplicação, siga os passos abaixo:

### 1. Clone o repositório:
Primeiro, clone o repositório do projeto:
```bash
git clone https://github.com/VictorVFM/smart-park.git
```

### 2. Navegue para o diretório do projeto
Depois de clonar o repositório, entre no diretório do projeto:
```bash
cd smart-park
```


### 3. Configuração de ambiente
Certifique-se de ter o Docker e o Docker Compose instalados em sua máquina. Em seguida, execute o comando abaixo para iniciar os serviços necessários:
```bash
./init-system.sh
```
Este comando irá iniciar os serviços necessários, como o banco de dados PostGres, e deixá-los rodando em segundo plano.


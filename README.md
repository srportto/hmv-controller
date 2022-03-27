
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/srportto/portinvestimentos-pi/blob/master/LICENSE)


HMV administrativa diz respeito as principais endpoints para uma gestão "basica" de hospital


## Requisitos de negócios
- [x] Possibilitar o cadastro de funcionarios
- [x] Possibilitar o cadastro(SIMPLES) Pacientes
- [x] Possibilitar o cadastro(COMPLETO) Pacientes
- [x] Possibilitar o cadastro de unidades do hospital HMV
- [x] Possibilitar o cadastro de convenios associados ao HMV
- [x] Possibilitar login dos Pacientes
- [x] Possibilitar login dos funcionários
- [x] Efetuar os controles de autenticacao e autorização 
- [x] Possibilitar consulta detalhada de pacientes 
- [x] Possibilitar que o paciente solicite o check-in digital
- [x] Calcular o "Score" de prioridade de atendimento
- [x] Possibilitar que o paciente cancele o check-in digital
- [x] Possibilitar consulta de pedidos de atendimentos em aberto
- [x] Possibilitar alteração do status de um pedido de atendimento
- [x] Possibilitar a consulta(lista/detalhe) funcionarios
- [x] Possibilitar a consulta(lista/detalhe) especialidades
- [x] Possibilitar a consulta(lista/detalhe) convenios
- [x] Possibilitar a alteração/atualização de funcionarios
- [x] Possibilitar a alteração/atualização de especialidades
- [x] Possibilitar a alteração/atualização de convenios


## Requisitos de técnicos
- [x] Prover endpoints REST para interação com as partes mobile e web da aplicação
- [x] Usar banco de dados relacional com "engine" Open Souurce
- [x] Usar uma liguagem de programação JAVA, para aplicação e para a infra
- [x] Usar suite de tecnologias de cloud publica
- [x] Usar docker para execução e testes da aplicação
- [x] Usar autenticação e autorização via token (Spring security + Oauth2)
- [x] Usar um ORM para interagir com o Banco de dados


## Protótipo da aplicação
 - [] Entregar um Nesse MVP, do fluxo de check-in virtual para os pacientes do hospital HMV


## Modelo conceitual
 ⚠ Em desenvolvimento ⚠

# Tecnologias utilizadas
## Back end
- Java 11 via OpenJDK
  - Herança
  - Polimorfismo
  - Interface
  - Generics
  - Override
  - Sobrecargas 
  - Manipulação de datas
  - Manipulação de Strings  
  - Manipulação de arrays
  - Manipulação de collections    
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA  
- JPA / Hibernate
- Maven 3.8.4
- Docker (criação de imagem(dockerfile), images, containers )

- JSON
- MySql  
- Intellij IDE
- Lombok

## Front end
- [X] Mobile - App para Pacientes
- [X] Web - Aplicação administrativa

## Implantação em produção
- [x] Imagem da aplicação no dockerHub (hmv-api: 4.0)
- [x] Deploy na AWS (ECS, Fargate, ALB, RDS + MariaDB)


# Como executar o projeto

## Back end
Pré-requisitos: 
* Java 11
* MySql server 8.0
* MySql Workbench 8.0 CE
* Docker
* Postman ou equivalente


```bash
# clonar repositório
git clone git@github.com:srportto/hmv-controller.git

# entrar na pasta do projeto back end
cd hmv-controller/app

# executar o projeto
./mvnw spring-boot:run
```



# Autores (grupo 17 - Challenge FIAP 2022)
- Alan
- Caique Porto
- Carlos
- Gabriel
- Orlando


# Sistema Sócio Torcedor <br>
Autor: Yan Victor dos Santos<br>
Contato: yanvictor_ds@hotmail.com
<br><br>
Sistema responsável por cadastrar sócio torcedores e gerenciar suas campanhas por meio de seu time.
<br><br>

## Microserviço de Cadastro de Sócio - responsável por:<br>
- cadastrar sócio torcedor 
- orquestrar associação de sócios com campanhas
<br><br>

## Microserviço de Manutenção de Campanhas - responsável por:<br>
- criar, deletar, consultar e atualizar campanhas 
- associar campanhas a um sócio torcedor pelo time
<br><br>

## Documentação Swagger<br>
- servidor=localhost<br><br>
- Serviço de Campanhas
```
http://${servidor}:8140/campaign-java/swagger-ui.html
```
- Serviço de Sócio Torcedor
```
http://${servidor}:8130/partner-java/swagger-ui.html
```
<br>

## Este projeto foi desenvolvido utilizando as seguintes tecnologias:

- Java 11
- Maven 3.6
- Spring Boot
- Docker 19.03.8
- docker-compose 1.25.0
- MySQL latest
- Jdbc template
- Flayway para migrations
- Testes unitários com JUnit
- Documentação de API's com Swagger
- Feign Client para comunicação com serviços externos

<br>

## Execução:
Na pasta inicial do projeto, execute:

- Build dos serviços
```
1) find . -name "pom.xml" -exec mvn clean install -f '{}' \;
```
<br>

- Subida dos serviços

```
2) sudo docker-compose up
```

<br>

- Migração de tabelas (executar na pasta inicial, em um novo terminal)

```
3) cd campaign-java && mvn clean flyway:migrate -Dflyway.configFile=src/main/resources/application-dev.properties
```


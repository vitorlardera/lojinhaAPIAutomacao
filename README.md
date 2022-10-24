# Lojinha API Automação
Esse é um repositório que contém a automação de alguns testes de um software denomidado lojinha. Os sub tópicos abaixo descrevem algumas decisões tomadas na estruturação do projeto.
## Tecnologias Utilizadas

- Java
  https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html

- Junit
  https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api/5.9.1

- RestAssured
- https://mvnrepository.com/artifact/io.rest-assured/rest-assured/4.4.0

- Maven
  https://maven.apache.org

## Testes Automatizados
Testes para validar as partições de equivalência relacionadas ao valor de produto na Lojinha, que estão diretamente ligadas as regras de negócio que diz que o produto deve possuir um valor entre R$ 0,01 e R$ 7000,00.

## Notas Gerais
- Sempre utilizamos o @BeforeEach antes dos testes para capturar o token utilizado no teste
- Utilizamos o @DisplayName para podermos dar nomes em português para os testes.
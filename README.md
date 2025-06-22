# 🔐 Token Validator

API responsável pela validação de tokens JWT, desenvolvida em Java 21 com Spring Boot. O projeto segue a abordagem **Contrato Primeiro** (Contract-First), utilizando OpenAPI para definição e documentação dos endpoints.

## 📚 Sumário

- [Descrição](#descrição)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Executar](#como-executar)
- [Testes](#testes)
- [Contrato Primeiro (Contract-First)](#contrato-primeiro-contract-first)
- [Como Ajustar o Contrato ou Adicionar Novos Endpoints](#como-ajustar-o-contrato-ou-adicionar-novos-endpoints)
- [Como Criar Novas Validações do Token](#como-criar-novas-validações-do-token)
- [Como Subir Ambiente com Monitoramento](#como-subir-ambiente-com-monitoramento)
- [Como Gerar Imagem Docker da Aplicação](#como-gerar-imagem-docker-da-aplicação)
- [Contribuição](#contribuição)

## 📝 Descrição

Esta API realiza a validação de tokens JWT, permitindo diferentes estratégias de validação (nome, papel, seed, etc). O contrato da API está definido em OpenAPI (YAML), garantindo padronização e fácil integração.

## 🧰 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.5.2
- Maven
- OpenAPI/Swagger
- JUnit 5
- Mockito

## 🚀 Como Executar

1. Clone o repositório:
   ```bash
   git clone git@github.com:fernandodossantos001/token-validator.git
   ```

2. Acesse a pasta do projeto:
   ```bash
   cd token-validator
   ```

3. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Acesse a API localmente em:
   ```
   http://localhost:8080/swagger-ui/index.html#/
   ```

## 🧪 Testes

Para rodar os testes automatizados:
```bash
./mvnw test
```

## 📄 Contrato Primeiro (Contract-First)

A API segue a abordagem de **Contrato Primeiro**, onde o contrato OpenAPI (YAML) é a fonte de verdade para os endpoints. O contrato está localizado em:

```
src/main/resources/contratos/token-validator-contrato.yaml
```

## ✍️ Como Ajustar o Contrato ou Adicionar Novos Endpoints

1. **Edite o arquivo de contrato**: Modifique ou adicione endpoints no arquivo YAML.
2. **Gere as classes a partir do contrato**: Utilize ferramentas como Swagger Codegen ou OpenAPI Generator para gerar stubs ou modelos.
3. **Implemente a lógica**: Implemente os novos endpoints conforme o contrato.
4. **Atualize os testes**: Garanta cobertura de testes para os novos comportamentos.

> 💡 **Dica:** Sempre mantenha o contrato atualizado e versionado para garantir compatibilidade entre consumidores e provedores da API.

### 🛠 Exemplo de plugin Maven para geração a partir do contrato:

```xml
<plugin>
  <groupId>org.openapitools</groupId>
  <artifactId>openapi-generator-maven-plugin</artifactId>
  <version>7.13.0</version>
  <executions>
    <execution>
      <goals>
        <goal>generate</goal>
      </goals>
      <configuration>
        <inputSpec>${project.basedir}/src/main/resources/contratos/token-validator-contrato.yaml</inputSpec>
        <generatorName>spring</generatorName>
        <apiPackage>com.itau.token.validator.api</apiPackage>
        <modelPackage>com.itau.token.validator.model</modelPackage>
        <supportingFilesToGenerate>ApiUtil.java</supportingFilesToGenerate>
        <configOptions>
          <interfaceOnly>true</interfaceOnly>
          <delegatePattern>true</delegatePattern>
          <useJakartaEe>true</useJakartaEe>
          <openApiNullable>false</openApiNullable>
        </configOptions>
      </configuration>
    </execution>
  </executions>
</plugin>
```

## 🧩 Como Criar Novas Validações do Token

Basta implementar a interface `IValidadorStrategy` e definir as regras de negócio necessárias. Atualmente, as validações se baseiam nos **claims** presentes no payload do token JWT.

## 📈 Como Subir Ambiente com Monitoramento

Para subir o ambiente com Prometheus e Grafana:

1. Acesse o diretório `monitoring`
2. Execute o comando:
   ```bash
   docker compose up
   ```

> Isso provisionará o Prometheus e Grafana com base no `docker-compose.yml`.  
> A aplicação já estará expondo as métricas via Actuator automaticamente.<br>
> A aplicação precisará estar rodando na porta 8080.

## 🐳 Como Gerar Imagem Docker da Aplicação

Execute o comando abaixo na raiz do projeto:
```bash
docker build -t token-validator .
```

## 🤝 Contribuição

Contribuições são bem-vindas!  
Siga as boas práticas de desenvolvimento, mantenha o contrato atualizado e escreva testes automatizados para novas funcionalidades.
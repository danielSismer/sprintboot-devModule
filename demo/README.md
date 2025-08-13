# Sistema de Login Spring Boot + Java Swing

Este projeto implementa um sistema de login com interface gráfica Java Swing que se conecta a uma aplicação Spring Boot.

## Funcionalidades

- **Interface gráfica Java Swing** para login e cadastro
- **Endpoints REST** na aplicação Spring Boot
- **Comunicação HTTP** entre a interface e o servidor
- **Validação de dados** no frontend e backend

## Como executar

### 1. Iniciar a aplicação Spring Boot

```bash
cd demo
.\mvnw.cmd spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8081`

### 2. Executar a interface gráfica

```bash
cd demo/src/main/java/weg/demo/gui
javac *.java
java MainGUI
```

Ou execute diretamente a classe `LoginFrame`:

```bash
java -cp . weg.demo.gui.LoginFrame
```

## Endpoints disponíveis

### GET
- `GET /` - Página inicial
- `GET /login` - Informações sobre o endpoint de login
- `GET /cadastro` - Informações sobre o endpoint de cadastro

### POST
- `POST /login` - Realizar login (parâmetros: username, password)
- `POST /cadastro` - Realizar cadastro (parâmetros: username, password)

## Interface gráfica

A interface gráfica inclui:

- Campo para usuário
- Campo para senha (mascarado)
- Botão de Login
- Botão de Cadastro
- Label de status para feedback

## Estrutura do projeto

```
demo/
├── src/main/java/weg/demo/
│   ├── controller/
│   │   └── HelloController.java          # Endpoints REST
│   ├── gui/
│   │   ├── LoginFrame.java              # Interface gráfica principal
│   │   └── MainGUI.java                 # Classe principal para executar GUI
│   └── DemoApplication.java             # Aplicação Spring Boot
├── src/main/resources/
│   └── application.properties           # Configurações (porta 8081)
└── pom.xml                              # Dependências Maven
```

## Tecnologias utilizadas

- **Spring Boot 3.5.4** - Framework backend
- **Java Swing** - Interface gráfica
- **Maven** - Gerenciamento de dependências
- **HTTP/JSON** - Comunicação cliente-servidor

## Notas importantes

1. A aplicação Spring Boot deve estar rodando antes de executar a interface gráfica
2. A interface se conecta automaticamente ao `localhost:8081`
3. Os dados são enviados via POST com Content-Type `application/x-www-form-urlencoded`
4. A interface fornece feedback visual sobre o status das operações 
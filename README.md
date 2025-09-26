# Automação de Testes de API REST com Cucumber e Java

## Visão Geral do Projeto

Este projeto demonstra a criação de uma automação de testes de API REST utilizando Java, Maven, Cucumber para BDD (Behavior-Driven Development), RestAssured para as chamadas de API, e um conjunto robusto de ferramentas para relatórios e evidências. A automação foi projetada com foco em boas práticas de desenvolvimento de software, como reuso de código, separação de responsabilidades e fácil manutenção.

Utilizamos a API pública gratuita [JSONPlaceholder](https://jsonplaceholder.typicode.com/) para os exemplos de teste de posts.

## Tecnologias Utilizadas

*   **Java 21:** Linguagem de programação.
*   **Maven:** Ferramenta de automação de build e gerenciamento de dependências.
*   **Cucumber:** Framework BDD para escrever testes em linguagem natural (Gherkin).
*   **RestAssured:** Biblioteca para facilitar o teste de APIs REST.
*   **JUnit 4:** Framework de teste utilizado pelo Cucumber Runner.
*   **Selenium WebDriver & WebDriverManager:** (Base para futuros testes de UI, não diretamente usado nos testes de API atuais) Para automação de navegadores e gerenciamento automático de drivers.
*   **Apache POI:** Para manipulação e salvamento de resultados em planilhas Excel.
*   **Allure Framework:** Para geração de relatórios HTML interativos e detalhados com evidências.
*   **Commons IO:** Biblioteca de utilitários de I/O (usada para, por exemplo, manipulação de arquivos).

## Configuração do Ambiente

Para executar este projeto, você precisará ter o seguinte instalado:

*   **Java Development Kit (JDK) 21** (ou compatível, como JDK 17).
*   **Apache Maven** (versão 3.x ou superior).
*   **(Opcional) Allure Command-line Interface (CLI):** Para gerar e visualizar o relatório Allure. [Download aqui](https://docs.qameta.io/allure/#_install_and_generate_report).

### Passos de Configuração:

1.  **Clone o Repositório:**
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    cd Automacao-de-Testes
    ```
2.  **Instale as Dependências do Maven:**
    Navegue até o diretório raiz do projeto e execute:
    ```bash
    mvn clean install
    ```
    Este comando baixará todas as dependências especificadas no `pom.xml` e compilará o projeto.

## Estrutura do Projeto

A estrutura do projeto segue as convenções Maven e Cucumber:

```
Automacao-de-Testes/
├── pom.xml                                  # Configurações do Maven e dependências
├── README.md                                # Este arquivo de documentação
├── src/
│   ├── main/
│   │   ├── java/                            # Código fonte principal da aplicação (se houver)
│   │   └── resources/                       # Recursos da aplicação
│   └── test/
│       ├── java/                            # Classes de teste Java
│       │   └── br/
│       │       └── com/
│       │           └── sandra/
│       │               └── automation/
│       │                   ├── pages/       # (Opcional) Page Objects para testes de UI
│       │                   ├── runner/      # Classes Runner do Cucumber (TestRunner.java)
│       │                   ├── steps/       # Step Definitions (PostSteps.java)
│       │                   └── support/     # Classes de suporte (Hooks.java, ExcelUtils.java)
│       └── resources/                       # Recursos de teste
│           └── features/                    # Arquivos .feature do Cucumber (posts.api.feature)
└── target/                                  # Diretório gerado pelo Maven (artefatos de build, relatórios)
```

## Como Executar os Testes

Todos os testes são executados através do Maven. Certifique-se de estar no diretório raiz do projeto (`Automacao-de-Testes`).

### Executar todos os cenários com a tag configurada no Runner (Ex: `@regressivo`):

```bash
mvn test
```

### Executar cenários com tags específicas (sobrescrevendo o Runner):

Você pode especificar tags via linha de comando para executar um subconjunto de testes. Por exemplo, para executar apenas testes de API:

```bash
mvn test -Dcucumber.filter.tags="@api"
```

Para executar apenas os testes GET da API:

```bash
mvn test -Dcucumber.filter.tags="@api and @GET"
```

## Como Visualizar os Relatórios

Após a execução dos testes (`mvn test`), os resultados serão gerados na pasta `target/`.

### 1. Relatório em Planilha Excel

*   **Local:** `target/relatorio_testes.xlsx`
*   **Conteúdo:** Contém os resultados dos testes, incluindo tipo de teste, ID do cenário, valores atual/esperado e o resultado final (Passou/Falhou).

### 2. Relatório HTML Básico do Cucumber

*   **Local:** `target/cucumber-report.html`
*   **Conteúdo:** Um relatório HTML simples que resume a execução dos cenários do Cucumber.

### 3. Relatório Allure (HTML Interativo)

O Allure gera um relatório HTML rico em detalhes, com dashboards, gráficos e a possibilidade de anexar evidências.

1.  **Gerar o relatório (requer Allure CLI instalada):**
    ```bash
    allure generate target/allure-results --clean
    ```
2.  **Abrir o relatório no navegador:**
    ```bash
    allure open
    ```
    Isso abrirá o relatório interativo no seu navegador padrão.

## Boas Práticas de Desenvolvimento

Este projeto foi construído com as seguintes boas práticas em mente:

*   **BDD (Behavior-Driven Development):** Utilização de Gherkin para escrever testes em linguagem natural, promovendo a colaboração e a documentação viva.
*   **Reuso de Código:** Emprego de `Esquema do Cenário` com `Exemplos` no Cucumber, e classes de suporte (`Hooks`, `ExcelUtils`) para centralizar lógicas comuns.
*   **Separação de Responsabilidades:** Cada classe tem uma responsabilidade clara (e.g., `PostSteps` para lógica de API, `Hooks` para setup/teardown, `ExcelUtils` para Excel).
*   **Gerenciamento de Dependências:** Uso do Maven para gerenciar todas as bibliotecas e garantir a consistência do ambiente de desenvolvimento.
*   **Relatórios e Evidências:** Geração de múltiplos formatos de relatório (Excel, HTML Cucumber, Allure) com capacidade de anexar evidências para facilitar a análise de falhas e a comunicação dos resultados.

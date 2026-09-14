# Desafio Prático Iniflex

Solução em Java para o **Teste Prático de Programação** do processo seletivo Iniflex, conduzido via plataforma Gupy.

O desafio consiste em um sistema de console que cadastra os funcionários de uma indústria e realiza uma série de operações sobre essa lista: remoção, reajuste salarial, agrupamento por função, filtros por data de nascimento, ordenação, cálculo de idade e totalizações financeiras.

## Sobre o desenvolvedor

**César Rodrigues Ribeiro**

Java sempre foi a linguagem com a qual mais me identifiquei, e é também onde mais me dedico a evoluir tecnicamente. Este projeto foi desenvolvido com atenção à organização do código, às boas práticas de orientação a objetos e à clareza da solução — características que busco aplicar em qualquer desafio que eu assumo. Estou em busca de uma oportunidade para colocar esse conhecimento em prática em um time de desenvolvimento e contribuir de forma consistente.

- LinkedIn: [linkedin.com/in/césar-rodrigues-ribeiro](https://www.linkedin.com/in/c%C3%A9sar-rodrigues-ribeiro-229b65282/)
- GitHub: [github.com/Cluwt](https://github.com/Cluwt)

## Requisitos atendidos

| # | Requisito | Status |
|---|---|---|
| 1 | Classe `Pessoa` (nome, data de nascimento) | ✅ |
| 2 | Classe `Funcionario` estende `Pessoa` (salário, função) | ✅ |
| 3.1 | Inserir os 10 funcionários na ordem da tabela | ✅ |
| 3.2 | Remover o funcionário "João" | ✅ |
| 3.3 | Imprimir todos com data `dd/MM/aaaa` e valores em formato monetário `R$ 0.000,00` | ✅ |
| 3.4 | Aplicar aumento de 10% e atualizar a lista | ✅ |
| 3.5 | Agrupar funcionários por função em um `Map` | ✅ |
| 3.6 | Imprimir os funcionários agrupados por função | ✅ |
| 3.8 | Imprimir aniversariantes dos meses 10 e 12 | ✅ |
| 3.9 | Imprimir nome e idade do funcionário mais velho | ✅ |
| 3.10 | Imprimir a lista em ordem alfabética | ✅ |
| 3.11 | Imprimir o total dos salários | ✅ |
| 3.12 | Imprimir quantos salários mínimos (R$ 1.212,00) cada funcionário ganha | ✅ |

> O item 3.7 não consta no enunciado original — a numeração pula de 3.6 para 3.8 propositalmente.

## Estrutura do projeto

```
src/main/java/com/cluwt/iniflex/
├── Principal.java              # main — orquestra cada item do enunciado, em ordem
├── model/
│   ├── Pessoa.java              # nome + data de nascimento
│   └── Funcionario.java         # Pessoa + salário + função, com aumentarSalario()
├── service/
│   └── FuncionarioService.java  # regras de negócio: filtrar, agrupar, ordenar, somar...
└── util/
    ├── Formatador.java          # formatação de data e moeda no padrão pt-BR
    └── ConsolePrinter.java      # impressão de tabelas/títulos no console
```

A separação de pacotes segue o princípio de responsabilidade única: `model` armazena apenas dados, `service` concentra as regras de negócio, `util` cuida exclusivamente de formatação e apresentação, e `Principal` apenas orquestra a execução — sem lógica de negócio ou formatação espalhada pelo método `main`.

## Detalhes de implementação

- **Valores monetários sempre em `BigDecimal`**, nunca em `double`/`float`, evitando erros de arredondamento no cálculo do aumento salarial e na soma total.
- **Formatação pt-BR** via `java.text.NumberFormat`/`DecimalFormat` (biblioteca padrão do Java, sem dependências externas) — separador de milhar `.` e decimal `,`.
- **Ordenação alfabética** utiliza `java.text.Collator` no locale `pt-BR`, garantindo o tratamento correto de nomes acentuados (ex.: "Heloísa").
- **Cálculo de idade** via `java.time.Period`; o funcionário mais velho é obtido comparando diretamente a data de nascimento (menor data = mais velho).
- **Tabelas do console** calculam a largura de cada coluna dinamicamente a partir dos dados recebidos, sem tamanho fixo — a saída não quebra com listas vazias nem com nomes curtos ou longos.

## Tecnologias

- Java 21 (compilado com JDK 25 via `maven.compiler.release=21`)
- Maven 3.9+
- JUnit 5 (escopo de teste)

## Como compilar e executar

```bash
# Compilar
mvn compile

# Executar diretamente
mvn exec:java

# Ou gerar o .jar e rodar
mvn package
java -jar target/desafio-pratico-iniflex.jar
```

## Testes e validações

O projeto valida os dados de entrada nas classes de domínio: nome, data de nascimento, salário e função não podem ser nulos/vazios/negativos, e cada violação lança uma exceção (`IllegalArgumentException` ou `FuncionarioNaoEncontradoException`) em vez de aplicar um valor padrão silenciosamente.

Essas regras — junto com o cálculo de aumento, agrupamento, ordenação, filtro por aniversário e formatação — são cobertas por testes unitários com JUnit 5:

```bash
mvn test
```

## Exemplo de saída

```
╔═════════════════════════════════════╗
║ 3.9 - Funcionário com maior idade ║
╚═════════════════════════════════════╝
• Nome: Caio
• Idade: 65 anos

╔════════════════════════════════╗
║ 3.11 - Soma total dos salários ║
╚════════════════════════════════╝
• Total: R$ 50.906,82
```

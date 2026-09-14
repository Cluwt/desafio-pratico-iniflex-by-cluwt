# Desafio Prático Iniflex

Solução em Java para o **Teste Prático de Programação** da seleção Iniflex (processo via Gupy).

O desafio pede um pequeno sistema de console que cadastra funcionários de uma indústria e executa uma série de operações sobre essa lista: remoção, reajuste salarial, agrupamento, filtros, ordenação e cálculos estatísticos.

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

> O item 3.7 não existe no enunciado original — a numeração pula de 3.6 para 3.8 de propósito.

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

A separação existe para que cada camada tenha uma única responsabilidade: `model` só guarda dados, `service` só tem regra de negócio, `util` só formata e imprime, e `Principal` apenas orquestra — sem lógica de negócio nem formatação espalhada pelo `main`.

## Detalhes de implementação

- **Dinheiro sempre em `BigDecimal`**, nunca `double`/`float`, para não haver erro de arredondamento no aumento de salário nem na soma total.
- **Formatação pt-BR** via `java.text.NumberFormat`/`DecimalFormat` (biblioteca padrão do Java, sem dependências externas) — separador de milhar `.` e decimal `,`.
- **Ordenação alfabética** usa `java.text.Collator` no locale `pt-BR`, para tratar corretamente nomes acentuados (ex.: "Heloísa").
- **Cálculo de idade** via `java.time.Period`, e o funcionário mais velho é obtido comparando a data de nascimento diretamente (menor data = mais velho).
- **Tabelas de console** calculam a largura de cada coluna dinamicamente a partir dos dados recebidos — não há tamanho fixo, então a saída não quebra com listas vazias, nomes curtos ou longos.

## Tecnologias

- Java 21 (compilado com o JDK 25 instalado localmente via `maven.compiler.release=21`)
- Maven 3.9+
- Sem dependências externas

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

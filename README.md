# Sistema de Controle de Estoque

Aplicação desktop desenvolvida em Java 17 e JavaFX para gerenciamento de produtos, com armazenamento de dados em arquivos CSV.

## Funcionalidades

- Cadastro de produtos.
- Consulta por código.
- Atualização de produtos.
- Exclusão de produtos.
- Listagem de produtos em tabela.
- Persistência dos dados em arquivo CSV.
- Relatório de produtos com estoque baixo.
- Relatório do valor total do estoque.
- Validação dos dados cadastrados.

## Tecnologias utilizadas

- Java 17
- JavaFX 17
- FXML
- Maven
- CSV
- Stream API
- `BigDecimal` para valores monetários
- `LocalDate` para datas

## Organização do projeto

- `app`: inicialização da aplicação.
- `controller`: controladores da interface gráfica.
- `model`: classes `Produto` e `Categoria`.
- `service`: regras de negócio e gerenciamento do estoque.
- `repository`: leitura e gravação dos arquivos CSV.

## Regras de validação

- Código com oito caracteres alfanuméricos.
- Nome com pelo menos três caracteres.
- Quantidade em estoque não negativa.
- Preço de compra maior que zero.
- Preço de venda superior ao preço de compra.
- Data de fabricação não posterior à data atual.
- Data de validade não anterior à data de fabricação.
- Prevenção de códigos duplicados.

## Relatórios

**Estoque baixo:** lista produtos com menos de 10 unidades.

**Valor total do estoque:** calcula a soma do preço de compra multiplicado pela quantidade de cada produto.

## Como executar

1. Instale o JDK 17.
2. Abra o projeto pelo arquivo `pom.xml` no IntelliJ IDEA.
3. Aguarde o Maven carregar as dependências.
4. Execute a classe `HelloApplication`, localizada no pacote `app`.

## Persistência

Os dados são armazenados em arquivo CSV, utilizando ponto e vírgula (`;`) como separador. Confira a configuração de `ArquivoCSV.java` para identificar o caminho utilizado pelo arquivo.

## Autor

Weberth Silva de Souza

## Observação

Projeto acadêmico desenvolvido para praticar Java, Programação Orientada a Objetos, interfaces gráficas e manipulação de arquivos.

Autor
Weberth Silva de Souza

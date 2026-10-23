Sistema de Controle de Estoque
Aplicação desktop desenvolvida em Java 17, JavaFX e Maven
para gerenciar produtos de um estoque, com persistência em arquivo CSV.
Funcionalidades
- Cadastro de produtos.
- Consulta de produto pelo código.
- Atualização de dados de produtos existentes.
- Exclusão de produtos com confirmação.
- Listagem dos produtos em uma tabela.
- Relatório de produtos com estoque baixo (quantidade inferior a 10).
- Relatório do valor de compra total do estoque.
- Persistência dos registros em produtos.csv, usando ponto e vírgula
  (;) como separador.
Tecnologias
- Java 17
- JavaFX 17
- Maven
- FXML para a construção das telas
- Stream API para filtragem e agregação de dados
- BigDecimal para cálculos monetários
- LocalDate para datas
Organização principal
- Produto.java: dados e validações do produto.
- Categoria.java: dados da categoria.
- Estoque.java: operações de cadastro, consulta, atualização e
  exclusão.
- ArquivoCSV.java: leitura e gravação dos produtos.
- RelatoriosEstoque.java: operações de relatório.
- HelloApplication.java: inicialização da aplicação.
- HelloController.java: tela principal, tabela e ações.
- CadastroProdutoController.java: formulário de cadastro.
- EditarProdutoController.java: formulário de edição.
- hello-view.fxml: interface principal.
- cadastro-produto.fxml: formulário de cadastro.
- editar-produto.fxml: formulário de edição.
A estrutura exata pode variar de acordo com a versão final do projeto.
Confirme os nomes dos arquivos antes da entrega.

Como executar
1. Instale o JDK 17.
2. Instale o IntelliJ IDEA ou outra IDE com suporte a Maven.
3. Abra o projeto pela pasta que contém pom.xml.
4. Aguarde o Maven baixar as dependências.
5. Execute org.example.projetop1.app.HelloApplication pela IDE.
Também é possível executar pelo terminal, na pasta do projeto, se o
pom.xml estiver configurado com o plugin JavaFX:
mvn clean javafx:run
Arquivo de dados
O sistema utiliza produtos.csv. O arquivo deve estar no diretório de
trabalho configurado para a execução. Os campos são separados por ;. A
estrutura usada pelo projeto inclui código, nome, descrição, datas de
fabricação e validade, preços, quantidade e dados da categoria.
Faça uma cópia de segurança do CSV antes de editar o arquivo
manualmente.
Regras de validação
O formulário verifica, entre outros pontos:
- Código com exatamente oito caracteres alfanuméricos.
- Nome com pelo menos três caracteres.
- Data de fabricação não posterior à data atual.
- Data de validade não anterior à fabricação.
- Preços positivos e preço de venda maior que o preço de compra.
- Quantidade em estoque igual ou superior a zero.
- Categoria selecionada.
Relatórios
- Estoque baixo: lista produtos com menos de 10 unidades.
- Valor total do estoque: soma o preço de compra multiplicado pela
  quantidade de cada produto.
Testes recomendados antes da entrega
- Cadastrar um produto válido e confirmar sua presença na tabela e no
  CSV.
- Tentar cadastrar um código já existente.
- Consultar código existente e inexistente.
- Atualizar um produto e reiniciar a aplicação para conferir a
  persistência.
- Excluir um produto de teste e reiniciar a aplicação.
- Conferir os resultados dos dois relatórios.
- Verificar que produtos.csv está incluído no pacote final, se for
  necessário entregar os dados de exemplo.
Limitações / observações
Este README descreve a implementação relatada durante o desenvolvimento.
Antes da entrega, execute todos os testes e confirme que a aplicação
informa corretamente falhas de leitura e gravação do CSV, especialmente
na atualização. O comportamento de persistência deve ser validado no
código final.
Autor
Weberth Silva de Souza

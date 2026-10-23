
package org.example.projetop1;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import java.util.Optional;

public class HelloController {

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableColumn<Produto, String> colunaCodigo;

    @FXML
    private TableColumn<Produto, String> colunaNome;

    @FXML
    private TableColumn<Produto, String> colunaPreco;

    @FXML
    private TableColumn<Produto, String> colunaQuantidade;

    @FXML
    private TableColumn<Produto, String> colunaCategoria;

    @FXML
    private TableColumn<Produto, String> colunaDescricao;

    @FXML
    private TableColumn<Produto, String> colunaFabricacao;

    @FXML
    private TableColumn<Produto, String> colunaValidade;

    @FXML
    private TableColumn<Produto, String> colunaPrecoCompra;

    private Estoque estoque;

    @FXML
    public void initialize() {
        estoque = new Estoque();

        colunaCodigo.setCellValueFactory(dados ->
                new SimpleStringProperty(dados.getValue().getCodigo()));

        colunaNome.setCellValueFactory(dados ->
                new SimpleStringProperty(dados.getValue().getNome()));

        colunaPreco.setCellValueFactory(dados ->
                new SimpleStringProperty(
                        dados.getValue().getPrecoVenda().toString()));

        colunaQuantidade.setCellValueFactory(dados ->
                new SimpleStringProperty(
                        String.valueOf(dados.getValue().getQuantidadeEstoque())));

        colunaCategoria.setCellValueFactory(dados ->
                new SimpleStringProperty(
                        dados.getValue().getCategoria().getNome()));

        colunaDescricao.setCellValueFactory(dados ->
                new SimpleStringProperty(dados.getValue().getDescricao()));

        colunaFabricacao.setCellValueFactory(dados ->
                new SimpleStringProperty(
                        dados.getValue().getDataFabricacao().toString()));

        colunaValidade.setCellValueFactory(dados ->
                new SimpleStringProperty(
                        dados.getValue().getDataValidade().toString()));

        colunaPrecoCompra.setCellValueFactory(dados ->
                new SimpleStringProperty(
                        dados.getValue().getPrecoCompra().toString()));

        carregarProdutos();
    }

    private void carregarProdutos() {
        try {
            new ArquivoCSV().carregarProdutos(estoque);
            tabelaProdutos.getItems().setAll(estoque.getProdutos());
        } catch (IOException e) {
            System.out.println("Erro ao carregar produtos: " + e.getMessage());
        }
    }

    @FXML
    public void onCadastrarClick() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("cadastro-produto.fxml")
            );

            Parent tela = loader.load();
            CadastroProdutoController controller = loader.getController();

            controller.configurarCadastro(
                    estoque,
                    produto -> tabelaProdutos.getItems().setAll(estoque.getProdutos())
            );

            Stage janela = new Stage();
            janela.setTitle("Cadastrar Produto");
            janela.setScene(new Scene(tela));
            janela.show();

        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Não foi possível abrir o cadastro");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    public void onConsultarClick() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Consultar produto");
        dialog.setHeaderText("Pesquisa de produto");
        dialog.setContentText("Digite o código do produto:");

        Optional<String> resultado = dialog.showAndWait();

        resultado.ifPresent(codigo -> {
            Produto produto = estoque.buscarProduto(codigo.trim());

            if (produto != null) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Produto encontrado");
                alerta.setHeaderText(produto.getNome());
                alerta.setContentText(
                        "Código: " + produto.getCodigo()
                                + "\nDescrição: " + produto.getDescricao()
                                + "\nPreço de compra: " + produto.getPrecoCompra()
                                + "\nPreço de venda: " + produto.getPrecoVenda()
                                + "\nQuantidade: " + produto.getQuantidadeEstoque()
                                + "\nCategoria: " + produto.getCategoria().getNome()
                );
                alerta.showAndWait();
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Produto não encontrado");
                alerta.setHeaderText(null);
                alerta.setContentText("Não existe produto com esse código.");
                alerta.showAndWait();
            }
        });
    }


    @FXML
    public void onAtualizarClick() {
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Atualizar produto");
        dialog.setHeaderText("Localizar produto para atualização");
        dialog.setContentText("Digite o código do produto:");

        dialog.showAndWait().ifPresent(codigo -> {
            Produto produto = estoque.buscarProduto(codigo.trim());

            if (produto == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Produto não encontrado");
                alerta.setHeaderText(null);
                alerta.setContentText("Não existe produto com esse código.");
                alerta.showAndWait();
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("editar-produto.fxml")
                );

                Parent tela = loader.load();

                EditarProdutoController controller = loader.getController();
                controller.configurar(produto, estoque);

                Stage janela = new Stage();
                janela.setTitle("Atualizar produto");
                janela.setScene(new Scene(tela));
                janela.showAndWait();

                tabelaProdutos.getItems().setAll(estoque.getProdutos());

            } catch (Exception e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Erro");
                alerta.setHeaderText("Não foi possível abrir a edição");
                alerta.setContentText(e.getMessage());
                alerta.showAndWait();
            }
        });
    }


    @FXML
    public void onExcluirClick() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Excluir produto");
        dialog.setHeaderText("Excluir produto do estoque");
        dialog.setContentText("Digite o código do produto:");

        dialog.showAndWait().ifPresent(codigo -> {
            Produto produto = estoque.buscarProduto(codigo.trim());

            if (produto == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Produto não encontrado");
                alerta.setHeaderText(null);
                alerta.setContentText("Não existe produto com esse código.");
                alerta.showAndWait();
                return;
            }

            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmar exclusão");
            confirmacao.setHeaderText("Excluir " + produto.getNome() + "?");
            confirmacao.setContentText("Essa operação removerá o produto do estoque.");

            Optional<javafx.scene.control.ButtonType> resposta =
                    confirmacao.showAndWait();

            if (resposta.isPresent()
                    && resposta.get() == javafx.scene.control.ButtonType.OK) {

                estoque.removerProduto(codigo.trim());
                tabelaProdutos.getItems().setAll(estoque.getProdutos());

                Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
                sucesso.setTitle("Exclusão");
                sucesso.setHeaderText(null);
                sucesso.setContentText("Verifique se o produto foi removido e salvo.");
                sucesso.showAndWait();
            }
        });
    }


    @FXML
    public void onEstoqueBaixoClick() {
        var produtosBaixo = estoque.getProdutos()
                .stream()
                .filter(produto -> produto.getQuantidadeEstoque() < 10)
                .toList();

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Relatório de estoque baixo");
        alerta.setHeaderText("Produtos com menos de 10 unidades");

        if (produtosBaixo.isEmpty()) {
            alerta.setContentText("Nenhum produto com estoque baixo.");
        } else {
            StringBuilder texto = new StringBuilder();

            produtosBaixo.forEach(produto ->
                    texto.append(produto.getNome())
                            .append(" | Código: ")
                            .append(produto.getCodigo())
                            .append(" | Quantidade: ")
                            .append(produto.getQuantidadeEstoque())
                            .append("\n")
            );

            alerta.setContentText(texto.toString());
        }

        alerta.showAndWait();
    }



    @FXML
    public void onValorTotalClick() {
        java.math.BigDecimal valorTotal = estoque.getProdutos()
                .stream()
                .map(produto -> produto.getPrecoCompra()
                        .multiply(
                                java.math.BigDecimal.valueOf(
                                        produto.getQuantidadeEstoque()
                                )
                        )
                )
                .reduce(java.math.BigDecimal.ZERO,
                        java.math.BigDecimal::add);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Valor total do estoque");
        alerta.setHeaderText("Valor investido nos produtos disponíveis");
        alerta.setContentText(
                "Valor total: R$ "
                        + valorTotal.setScale(
                        2, java.math.RoundingMode.HALF_UP
                ).toPlainString()
        );

        alerta.showAndWait();
    }

}

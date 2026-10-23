
package org.example.projetop1;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarProdutoController {

    @FXML private TextField campoCodigo;
    @FXML private TextField campoNome;
    @FXML private TextField campoDescricao;
    @FXML private TextField campoFabricacao;
    @FXML private TextField campoValidade;
    @FXML private TextField campoPrecoCompra;
    @FXML private TextField campoPrecoVenda;
    @FXML private TextField campoQuantidade;
    @FXML private ComboBox<Categoria> campoCategoria;
    @FXML private Label mensagem;

    private Produto produtoOriginal;
    private Estoque estoque;

    public void configurar(Produto produto, Estoque estoque) {
        this.produtoOriginal = produto;
        this.estoque = estoque;

        campoCategoria.getItems().setAll(
                new Categoria(1, "Alimentos",
                        "Produtos alimentícios", "Mercearia"),
                new Categoria(2, "Limpeza",
                        "Produtos de limpeza", "Higiene")
        );

        campoCodigo.setText(produto.getCodigo());
        campoNome.setText(produto.getNome());
        campoDescricao.setText(produto.getDescricao());
        campoFabricacao.setText(produto.getDataFabricacao().toString());
        campoValidade.setText(produto.getDataValidade().toString());
        campoPrecoCompra.setText(produto.getPrecoCompra().toString());
        campoPrecoVenda.setText(produto.getPrecoVenda().toString());
        campoQuantidade.setText(
                String.valueOf(produto.getQuantidadeEstoque())
        );

        campoCategoria.getItems().stream()
                .filter(c -> c.getId() == produto.getCategoria().getId())
                .findFirst()
                .ifPresent(campoCategoria::setValue);
    }


    @FXML
    public void onSalvarClick() {
        try {
            String nome = campoNome.getText().trim();
            String descricao = campoDescricao.getText().trim();

            if (nome.length() < 3 || descricao.isBlank()
                    || campoFabricacao.getText().isBlank()
                    || campoValidade.getText().isBlank()
                    || campoPrecoCompra.getText().isBlank()
                    || campoPrecoVenda.getText().isBlank()
                    || campoQuantidade.getText().isBlank()
                    || campoCategoria.getValue() == null) {

                mensagem.setText("Preencha todos os campos corretamente.");
                return;
            }

            java.time.LocalDate fabricacao =
                    java.time.LocalDate.parse(campoFabricacao.getText().trim());

            java.time.LocalDate validade =
                    java.time.LocalDate.parse(campoValidade.getText().trim());

            java.math.BigDecimal compra =
                    new java.math.BigDecimal(
                            campoPrecoCompra.getText().trim().replace(",", "."));

            java.math.BigDecimal venda =
                    new java.math.BigDecimal(
                            campoPrecoVenda.getText().trim().replace(",", "."));

            int quantidade =
                    Integer.parseInt(campoQuantidade.getText().trim());

            if (fabricacao.isAfter(java.time.LocalDate.now())) {
                mensagem.setText("A fabricação não pode ser futura.");
                return;
            }

            if (validade.isBefore(fabricacao)) {
                mensagem.setText("A validade não pode ser anterior à fabricação.");
                return;
            }

            if (compra.signum() <= 0 || venda.compareTo(compra) <= 0) {
                mensagem.setText("A venda deve ser maior que a compra, e ambas positivas.");
                return;
            }

            if (quantidade < 0) {
                mensagem.setText("A quantidade não pode ser negativa.");
                return;
            }

            Produto atualizado = new Produto(
                    produtoOriginal.getCodigo(),
                    nome,
                    descricao,
                    fabricacao,
                    validade,
                    compra,
                    venda,
                    quantidade,
                    campoCategoria.getValue()
            );

            estoque.atualizarProduto(atualizado);

            Produto salvo = estoque.buscarProduto(produtoOriginal.getCodigo());

            if (salvo == atualizado) {
                mensagem.setText("Alterações processadas.");
                ((Stage) campoCodigo.getScene().getWindow()).close();
            } else {
                mensagem.setText("Não foi possível confirmar a atualização.");
            }

        } catch (java.time.format.DateTimeParseException e) {
            mensagem.setText("Use o formato de data AAAA-MM-DD.");
        } catch (NumberFormatException e) {
            mensagem.setText("Preços e quantidade devem ser numéricos.");
        } catch (Exception e) {
            mensagem.setText("Erro ao atualizar: " + e.getMessage());
        }
    }


    @FXML
    public void onCancelarClick() {
        ((Stage) campoCodigo.getScene().getWindow()).close();
    }
}

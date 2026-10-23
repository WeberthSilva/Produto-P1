
package org.example.projetop1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.projetop1.model.Categoria;
import org.example.projetop1.model.Produto;
import org.example.projetop1.service.Estoque;

import java.util.function.Consumer;

public class CadastroProdutoController {

    private Estoque estoque;
    private Consumer<Produto> aoCadastrar;

    public void configurarCadastro(
            Estoque estoque,
            Consumer<Produto> aoCadastrar) {
        this.estoque = estoque;
        this.aoCadastrar = aoCadastrar;
    }

    @FXML
    private TextField campoCodigo;

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoDescricao;

    @FXML
    private TextField campoFabricacao;

    @FXML
    private TextField campoValidade;

    @FXML
    private TextField campoPrecoCompra;

    @FXML
    private TextField campoPrecoVenda;

    @FXML
    private TextField campoQuantidade;

    @FXML
    private ComboBox<Categoria> campoCategoria;

    @FXML
    private Label mensagem;

    @FXML
    public void initialize() {
        campoCategoria.getItems().addAll(
                new Categoria(1, "Alimentos",
                        "Produtos alimentícios", "Mercearia"),

                new Categoria(2, "Limpeza",
                        "Produtos de limpeza", "Higiene")
        );
    }


    @FXML
    public void onSalvarClick() {
        try {
            String codigo = campoCodigo.getText().trim();
            String nome = campoNome.getText().trim();
            String descricao = campoDescricao.getText().trim();

            if (codigo.isEmpty() || nome.isEmpty()
                    || descricao.isEmpty()
                    || campoFabricacao.getText().isBlank()
                    || campoValidade.getText().isBlank()
                    || campoPrecoCompra.getText().isBlank()
                    || campoPrecoVenda.getText().isBlank()
                    || campoQuantidade.getText().isBlank()
                    || campoCategoria.getValue() == null) {

                mensagem.setText("Preencha todos os campos.");
                return;
            }

            if (!codigo.matches("[A-Za-z0-9]{8}")) {
                mensagem.setText(
                        "O código deve ter exatamente 8 letras ou números."
                );
                return;
            }

            if (estoque.buscarProduto(codigo) != null) {
                mensagem.setText("Esse código já está cadastrado.");
                return;
            }

            java.time.LocalDate fabricacao =
                    java.time.LocalDate.parse(campoFabricacao.getText().trim());

            java.time.LocalDate validade =
                    java.time.LocalDate.parse(campoValidade.getText().trim());

            java.math.BigDecimal precoCompra =
                    new java.math.BigDecimal(
                            campoPrecoCompra.getText().trim().replace(",", ".")
                    );

            java.math.BigDecimal precoVenda =
                    new java.math.BigDecimal(
                            campoPrecoVenda.getText().trim().replace(",", ".")
                    );

            int quantidade =
                    Integer.parseInt(campoQuantidade.getText().trim());

            if (nome.length() < 3) {
                mensagem.setText("O nome deve ter pelo menos 3 caracteres.");
                return;
            }

            if (fabricacao.isAfter(java.time.LocalDate.now())) {
                mensagem.setText("A fabricação não pode ser futura.");
                return;
            }

            if (validade.isBefore(fabricacao)) {
                mensagem.setText(
                        "A validade não pode ser anterior à fabricação."
                );
                return;
            }

            if (precoCompra.signum() <= 0
                    || precoVenda.compareTo(precoCompra) <= 0) {
                mensagem.setText(
                        "Os preços devem ser positivos e a venda maior que a compra."
                );
                return;
            }

            if (quantidade < 0) {
                mensagem.setText("A quantidade não pode ser negativa.");
                return;
            }

            Produto produto = new Produto(
                    codigo, nome, descricao,
                    fabricacao, validade,
                    precoCompra, precoVenda,
                    quantidade, campoCategoria.getValue()
            );

            int quantidadeAntes = estoque.getProdutos().size();

            estoque.adicionarProduto(produto);

            if (estoque.getProdutos().size() == quantidadeAntes + 1) {
                aoCadastrar.accept(produto);
                mensagem.setText("Produto cadastrado com sucesso!");

                campoCodigo.clear();
                campoNome.clear();
                campoDescricao.clear();
                campoFabricacao.clear();
                campoValidade.clear();
                campoPrecoCompra.clear();
                campoPrecoVenda.clear();
                campoQuantidade.clear();
                campoCategoria.setValue(null);
            } else {
                mensagem.setText(
                        "Não foi possível cadastrar. Verifique o CSV."
                );
            }

        } catch (java.time.format.DateTimeParseException e) {
            mensagem.setText("Informe as datas no formato AAAA-MM-DD.");
        } catch (NumberFormatException e) {
            mensagem.setText(
                    "Preços e quantidade devem conter números válidos."
            );
        } catch (Exception e) {
            mensagem.setText("Erro ao cadastrar: " + e.getMessage());
        }
    }


    @FXML
    public void onCancelarClick() {
        Stage janela = (Stage) campoCodigo.getScene().getWindow();
        janela.close();
    }
}

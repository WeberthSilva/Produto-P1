package org.example.projetop1.repository;


import org.example.projetop1.model.Categoria;
import org.example.projetop1.model.Produto;
import org.example.projetop1.service.Estoque;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class ArquivoCSV {

    private final String nomeArquivo = "produtos.csv";

    public void carregarProdutos(Estoque estoque) throws IOException {

        // Se o arquivo ainda não existir, não há produtos para carregar.
        if (!Files.exists(Path.of(nomeArquivo))) {
            return;
        }

        try (BufferedReader leitor =
                     new BufferedReader(new FileReader(nomeArquivo))) {

            String linha;
            int numeroLinha = 0;

            while ((linha = leitor.readLine()) != null) {
                numeroLinha++;

                if (linha.isBlank()) {
                    continue;
                }

                String[] campos = linha.split(";", -1);

                if (campos.length != 12) {
                    throw new IOException(
                            "Linha " + numeroLinha +
                                    " do CSV possui quantidade incorreta de campos."
                    );
                }

                try {
                    String codigo = campos[0];
                    String nome = campos[1];
                    String descricao = campos[2];

                    LocalDate dataFabricacao =
                            LocalDate.parse(campos[3]);

                    LocalDate dataValidade =
                            LocalDate.parse(campos[4]);

                    BigDecimal precoCompra =
                            new BigDecimal(campos[5]);

                    BigDecimal precoVenda =
                            new BigDecimal(campos[6]);

                    int quantidadeEstoque =
                            Integer.parseInt(campos[7]);

                    int idCategoria =
                            Integer.parseInt(campos[8]);

                    Categoria categoria = new Categoria(
                            idCategoria,
                            campos[9],
                            campos[10],
                            campos[11]
                    );

                    Produto produto = new Produto(
                            codigo,
                            nome,
                            descricao,
                            dataFabricacao,
                            dataValidade,
                            precoCompra,
                            precoVenda,
                            quantidadeEstoque,
                            categoria
                    );

                    estoque.adicionarProdutoCarregado(produto);

                } catch (RuntimeException e) {
                    throw new IOException(
                            "Erro na linha " + numeroLinha + ": " + e.getMessage(), e
                    );
                }
            }
        }
    }

    public void salvarProdutos(List<Produto> produtos)
            throws IOException {

        try (FileWriter arquivo = new FileWriter(nomeArquivo)) {

            for (Produto produto : produtos) {
                Categoria categoria = produto.getCategoria();

                arquivo.write(
                        produto.getCodigo() + ";" +
                                produto.getNome() + ";" +
                                produto.getDescricao() + ";" +
                                produto.getDataFabricacao() + ";" +
                                produto.getDataValidade() + ";" +
                                produto.getPrecoCompra() + ";" +
                                produto.getPrecoVenda() + ";" +
                                produto.getQuantidadeEstoque() + ";" +
                                categoria.getId() + ";" +
                                categoria.getNome() + ";" +
                                categoria.getDescricao() + ";" +
                                categoria.getSetor() +
                                System.lineSeparator()
                );
            }
        }
    }
}

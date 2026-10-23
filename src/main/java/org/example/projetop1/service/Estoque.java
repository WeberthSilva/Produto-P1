package org.example.projetop1.service;


import org.example.projetop1.repository.ArquivoCSV;
import org.example.projetop1.model.Produto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Estoque {

    private final List<Produto> produtos;
    private final ArquivoCSV arquivoCSV = new ArquivoCSV();

    public Estoque(List<Produto> produtos) {
        this.produtos = new ArrayList<>(produtos);
    }

    public Estoque() {
        this.produtos = new ArrayList<>();
    }

    // Adiciona um produto carregado do CSV, sem salvar novamente.
    public void adicionarProdutoCarregado(Produto produto) {
        if (buscarProduto(produto.getCodigo()) == null) {
            produtos.add(produto);
        }
    }

    // Cadastra um produto novo e salva a lista atualizada.
    public void adicionarProduto(Produto produto) {
        if (buscarProduto(produto.getCodigo()) != null) {
            System.out.println("Código já existe.");
            return;
        }

        produtos.add(produto);

        if (salvarAlteracoes()) {
            System.out.println("Produto cadastrado e salvo no CSV.");
        } else {
            produtos.remove(produto);
            System.out.println(
                    "Não foi possível salvar. Cadastro cancelado."
            );
        }
    }

    // Busca um produto pelo código.
    public Produto buscarProduto(String codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigo() != null &&
                    produto.getCodigo().equals(codigo)) {
                return produto;
            }
        }
        return null;
    }
    // Lista os produtos cadastrados.
    public void listarProdutosCadastrados() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }

    // Remove um produto e salva a lista atualizada.
    public void removerProduto(String codigo) {
        Produto produto = buscarProduto(codigo);

        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        produtos.remove(produto);

        if (salvarAlteracoes()) {
            System.out.println("Produto removido e CSV atualizado.");
        } else {
            produtos.add(produto);
            System.out.println(
                    "Erro ao salvar. Remoção cancelada na lista."
            );
        }
    }

    // Atualiza os dados de um produto existente.
    // O código do produto é usado para identificá-lo.
    public void atualizarProduto(Produto produtoAtualizado) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCodigo()
                    .equals(produtoAtualizado.getCodigo())) {

                Produto produtoAnterior = produtos.set(
                        i, produtoAtualizado
                );

                if (salvarAlteracoes()) {
                    System.out.println(
                            "Produto atualizado e CSV salvo."
                    );
                } else {
                    produtos.set(i, produtoAnterior);
                    System.out.println(
                            "Erro ao salvar. Alteração cancelada na lista."
                    );
                }

                return;
            }
        }

        System.out.println("Produto não encontrado para atualização.");
    }

    // Salva a lista completa no CSV.
    private boolean salvarAlteracoes() {
        try {
            arquivoCSV.salvarProdutos(produtos);
            return true;
        } catch (IOException e) {
            System.out.println(
                    "Erro ao salvar CSV: " + e.getMessage()
            );
            return false;
        }
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}

package org.example.projetop1.service;

import org.example.projetop1.model.Produto;

import java.math.BigDecimal;
import java.util.List;

public class RelatoriosEstoque {

    // Relatório 1: produtos com menos de 15 unidades
    public void listarEstoqueBaixo(List<Produto> produtos) {
        System.out.println("=== PRODUTOS COM ESTOQUE BAIXO ===");

        produtos.stream()
                .filter(produto -> produto.getQuantidadeEstoque() < 10)
                .forEach(System.out::println);
    }

    // Relatório 2: valor total de venda do estoque
    public void calcularValorTotalEstoque(List<Produto> produtos) {
        BigDecimal valorTotal = produtos.stream()
                .map(produto -> produto.getPrecoVenda()
                        .multiply(BigDecimal.valueOf(
                                produto.getQuantidadeEstoque())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("=== VALOR TOTAL DE VENDA DO ESTOQUE ===");
        System.out.println("R$ " + valorTotal);
    }
}





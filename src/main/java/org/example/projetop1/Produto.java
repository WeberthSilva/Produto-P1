package org.example.projetop1;

import java.time.LocalDate;
import java.math.BigDecimal;

public class Produto {

    private String codigo;
    private String nome;
    private String descricao;
    private LocalDate dataFabricacao;
    private LocalDate dataValidade;
    private BigDecimal precoCompra;
    private BigDecimal precoVenda;
    private int quantidadeEstoque;
    private Categoria categoria;


    public Produto(String codigo,String nome, String descricao, LocalDate dataFabricacao, LocalDate dataValidade, BigDecimal precoCompra, BigDecimal precoVenda, int quantidadeEstoque, Categoria categoria) {
        setCodigo(codigo);
        setNome(nome);
        setDescricao(descricao);
        setDataFabricacao(dataFabricacao);
        setDataValidade(dataValidade);
        setPrecoCompra(precoCompra);
        setPrecoVenda(precoVenda);
        setQuantidadeEstoque(quantidadeEstoque);
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && nome.trim().length() >= 3){
            this.nome = nome.trim();
        }else {
            System.out.println("ERRO: O nome deve ter pelo menos 3 caracteres");
        }

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo != null && codigo.matches("[A-Za-z0-9]{8}")) {
            System.out.println("Valido");
            this.codigo = codigo;
        } else {
            System.out.println("ERRO");
        }

    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        if (quantidadeEstoque < 0) {
            System.out.println("ERRO : Valor invalido ");
        } else
            this.quantidadeEstoque = quantidadeEstoque;

    }

    public BigDecimal getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(BigDecimal precoCompra) {
        if (precoCompra != null) {
            int resultado = precoCompra.compareTo(BigDecimal.ZERO);
            if (resultado > 0) {
                this.precoCompra = precoCompra;
            } else {
                System.out.println("ERRO: valor de compra invalido");
            }
        } else {
            System.out.println("ERRO: valor invalido");
        }
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        if (precoVenda != null && precoCompra != null) {
            int resultado = precoVenda.compareTo(BigDecimal.ZERO);
            if (resultado > 0) {
                int resultado1 = precoVenda.compareTo(precoCompra);
                if (resultado1 > 0) {
                    this.precoVenda = precoVenda;
                } else {
                    System.out.println("ERRO: venda deve ser maior que a compra");
                }
            } else {
                System.out.println("ERRO: valor de venda invalido");
            }
        } else {
            System.out.println("ERRO: valor invalido");
        }
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao != null && !descricao.isBlank()) {
            this.descricao = descricao;
        } else {
            System.out.println("ERRO: Descrição inválida");
        }
    }

    public LocalDate getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(LocalDate dataFabricacao) {
        if (dataFabricacao != null) {
            if (dataFabricacao.isAfter(LocalDate.now())) {
                System.out.println("ERRO: Data invalida");
            } else {
                this.dataFabricacao = dataFabricacao;
            }
        } else {
            System.out.println("ERRO: Data de fabricação invalida");
        }
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        if (dataValidade != null && dataFabricacao != null) {
            if (dataValidade.isBefore(dataFabricacao)) {
                System.out.println("ERRO: Data de validade invalida");
            } else {
                this.dataValidade = dataValidade;
            }
        } else {
            System.out.println("ERRO: valor nao pode ser NULL");
        }
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            System.out.println("A categoria não pode ser nula.");
            return;
        }
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataFabricacao=" + dataFabricacao +
                ", dataValidade=" + dataValidade +
                ", precoCompra=" + precoCompra +
                ", precoVenda=" + precoVenda +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", categoria=" + categoria +
                '}';
    }
}





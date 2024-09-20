package br.uel.produtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;


@Entity
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    private int quantidade;

    @Transient
    private  int quantidade_carrinho;
    @NotNull(message = "O preço é obrigatório.")
    @Positive(message = "O preço deve ser um número positivo.")
    private Double preco;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidade_carrinho() {
        return quantidade_carrinho;
    }

    public void setQuantidade_carrinho(int quantidade_carrinho) {
        this.quantidade_carrinho = quantidade_carrinho;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }


    @Override
    public boolean equals(Object o){
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return ((Produto)o).id == (this.id);
    }

    @Override
    public int hashCode() {
        return id * 12345;
    }


}

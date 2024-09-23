package book.com.entities;

import java.time.LocalDate;

public class Carrinho {
    private LocalDate dataCriacao;
    private int sessao;
    private String clienteCpf;
    private Integer numPedido; // Usar Integer para permitir nulo

    public Carrinho(LocalDate dataCriacao, int sessao, String clienteCpf) {
        this.dataCriacao = dataCriacao;
        this.sessao = sessao;
        this.clienteCpf = clienteCpf;
    }

    // Getters e Setters
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getSessao() {
        return sessao;
    }

    public void setSessao(int sessao) {
        this.sessao = sessao;
    }

    public  void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public String getClienteCpf() {
        return clienteCpf;
    }

    public void setClienteCpf(String clienteCpf) {
        this.clienteCpf = clienteCpf;
    }

    public Integer getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(Integer numPedido) {
        this.numPedido = numPedido;
    }
}

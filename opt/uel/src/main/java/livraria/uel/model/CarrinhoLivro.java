package livraria.uel.model;

public class CarrinhoLivro {
    private String cliente_cpf;
    private int isbn;
    private int quantidade;

    // Construtor
    public CarrinhoLivro(String clienteCpf, int codLivro, int quantidade) {
        this.cliente_cpf = clienteCpf;
        this.isbn = codLivro;
        this.quantidade = quantidade;
    }

    // Getters e Setters




    public String getClienteCpf() {
        return cliente_cpf;
    }

    public void setClienteCpf(String clienteCpf) {
        this.cliente_cpf = clienteCpf;
    }

    public int getCodLivro() {
        return isbn;
    }

    public void setCodLivro(int codLivro) {
        this.isbn = codLivro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

}

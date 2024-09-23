package book.com.entities;

public class CarrinhoLivro {
    private int carrinho_sessao;
    private String cliente_cpf;
    private int cod_livro;
    private int quantidade;

    // Construtor
    public CarrinhoLivro(int carrinho_sessao, String clienteCpf, int codLivro, int quantidade) {
        this.carrinho_sessao = carrinho_sessao;
        this.cliente_cpf = clienteCpf;
        this.cod_livro = codLivro;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public int getCarrinhoSessao() {
        return carrinho_sessao;
    }

    public void setCarrinhoSessao(int carrinhoSessao) {
        this.carrinho_sessao = carrinhoSessao;
    }

    public String getClienteCpf() {
        return cliente_cpf;
    }

    public void setClienteCpf(String clienteCpf) {
        this.cliente_cpf = clienteCpf;
    }

    public int getCodLivro() {
        return cod_livro;
    }

    public void setCodLivro(int codLivro) {
        this.cod_livro = codLivro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}

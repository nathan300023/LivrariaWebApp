package book.com.entities;

public class Book {
    private Integer cod_livro;      // Código do livro
    private String titulo;         // Título do livro
    private String autor;          // Autor do livro
    private String editora;        // Editora do livro
    private double preco;          // Preço do livro
    private int estoque;           // Estoque do livro

    public Book(String titulo, String autor, String editora, double preco, int estoque) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.preco = preco;
        this.estoque = estoque;
    }

    // Getters e Setters
    public Integer getCodLivro() {
        return cod_livro;
    }

    public void setCodLivro(Integer cod_livro) {
        this.cod_livro = cod_livro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditora() {
        return editora;
    }

    public double getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }
}

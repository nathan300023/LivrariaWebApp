package livraria.uel.model;

public class Book {
    private String isbn;      // Código do livro
    private String titulo;         // Título do livro
    private String autor;          // Autor do livro
    private String editora;        // Editora do livro
    private double preco;          // Preço do livro
    private int estoque;           // Estoque do livro

    public Book(String isbn,String titulo, String autor, String editora, double preco, int estoque) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.preco = preco;
        this.estoque = estoque;
        this.isbn = isbn;
    }

    // Getters e Setters
    public String getIsbn() {
        return isbn;
    }

    public void setCodLivro(String isbn) {
        this.isbn = isbn;
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

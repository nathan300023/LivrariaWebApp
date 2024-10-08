package livraria.uel.controller;


import livraria.uel.model.Book;
import livraria.uel.records.DadosCadastroBook;
import livraria.uel.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @PostMapping
    public ResponseEntity<Book> cadastrar(@RequestBody DadosCadastroBook dados) {
        BookService bookService = new BookService(); // Injetando BookService
        Book book = new Book(dados.isbn(), dados.titulo(), dados.autor(), dados.editora(), dados.preco(), dados.estoque());
        bookService.inserirBook(book); // Usando o BookService para cadastrar o livro
        Book createdBook = bookService.inserirBook(book); // Chama o método para cadastrar e buscar o livro
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED); // Retorna o livro criado
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deletar(@PathVariable String isbn) {
        BookService bookService = new BookService(); // Injetando BookService
        boolean deletado = bookService.deletarLivro(isbn);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Book>> listar() {
        BookService bookService = new BookService(); // Injetando BookService
        List<Book> livros = bookService.listarLivros();
        return new ResponseEntity<>(livros, HttpStatus.OK);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Book> alterar(@PathVariable String isbn, @RequestBody DadosCadastroBook dados) {
        BookService bookService = new BookService(); // Injetando BookService
        Book livroAlterado = bookService.alterarLivro(isbn, dados);
        if (livroAlterado != null) {
            return new ResponseEntity<>(livroAlterado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> buscar(@PathVariable String isbn) {
        BookService bookService = new BookService(); // Injetando BookService
        Book livro = bookService.buscarBook(isbn);
        if (livro != null) {
            return new ResponseEntity<>(livro, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

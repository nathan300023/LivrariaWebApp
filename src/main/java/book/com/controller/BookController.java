package book.com.controller;

import book.com.entities.Book;
import book.com.records.DadosCadastroBook;
import book.com.services.BookService;
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
        Book book = new Book(dados.titulo(), dados.autor(), dados.editora(), dados.preco(), dados.estoque());
        bookService.inserirBook(book); // Usando o BookService para cadastrar o livro
        Book createdBook = bookService.inserirBook(book); // Chama o método para cadastrar e buscar o livro
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED); // Retorna o livro criado
    }

    @DeleteMapping("/{cod_livro}")
    public ResponseEntity<Void> deletar(@PathVariable int cod_livro) {
        BookService bookService = new BookService(); // Injetando BookService
        boolean deletado = bookService.deletarLivro(cod_livro   );
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

    @PutMapping("/{cod_livro}")
    public ResponseEntity<Book> alterar(@PathVariable int cod_livro, @RequestBody DadosCadastroBook dados) {
        BookService bookService = new BookService(); // Injetando BookService
        Book livroAlterado = bookService.alterarLivro(cod_livro, dados);
        if (livroAlterado != null) {
            return new ResponseEntity<>(livroAlterado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{cod_livro}")
    public ResponseEntity<Book> buscar(@PathVariable int cod_livro) {
        BookService bookService = new BookService(); // Injetando BookService
        Book livro = bookService.buscarBook(cod_livro);
        if (livro != null) {
            return new ResponseEntity<>(livro, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}


package book.com.controller;

import book.com.entities.Book;
import book.com.entities.CarrinhoLivro;
import book.com.records.DadosCadastroBook;
import book.com.records.DadosCarrinhoLivro;
import book.com.services.BookService;
import book.com.services.CarrinhoLivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho-livro")
public class CarrinhoLivroController
{
    @PostMapping
    public ResponseEntity<CarrinhoLivro> adicionar(@RequestBody DadosCarrinhoLivro dados) {
        CarrinhoLivroService carrinhoLivroService = new CarrinhoLivroService(); // Injetando BookService
        CarrinhoLivro carrinhoLivro = new CarrinhoLivro(dados.carrinhoSessao(), dados.clienteCpf(), dados.codLivro(), dados.quantidade());
        carrinhoLivroService.inserirCarrinhoLivro(carrinhoLivro);
        CarrinhoLivro createdCarrinhoLivro= carrinhoLivroService.inserirCarrinhoLivro(carrinhoLivro); // Chama o método para cadastrar e buscar o livro
        return new ResponseEntity<>(createdCarrinhoLivro, HttpStatus.CREATED); // Retorna o livro criado
    }

    @DeleteMapping("/{cod_livro}/{cliente_cpf}/{carrinho_sessao}")
    public ResponseEntity<Void> deletar(@PathVariable int cod_livro, @PathVariable String cliente_cpf, @PathVariable int carrinho_sessao) {
        CarrinhoLivroService carrinhoLivroService = new CarrinhoLivroService(); // Injetando BookService
        boolean deletado = carrinhoLivroService.deletarCarrinhoLivro(cod_livro, cliente_cpf, carrinho_sessao);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
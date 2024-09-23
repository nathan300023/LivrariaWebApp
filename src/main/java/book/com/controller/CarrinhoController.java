package book.com.controller;

import book.com.entities.Book;
import book.com.entities.Carrinho;
import book.com.entities.Client;
import book.com.records.DadosCriacaoCarrinho;
import book.com.services.BookService;
import book.com.services.CarrinhoService;
import book.com.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @PostMapping
    public ResponseEntity<Carrinho> criarCarrinho(@RequestBody DadosCriacaoCarrinho dados) {
        Carrinho carrinho = new Carrinho(dados.dataCriacao(), dados.sessao(), dados.clienteCpf());
        CarrinhoService carrinhoService = new CarrinhoService();
        Carrinho carrinhoCriado = carrinhoService.criarCarrinho(carrinho);
        if (carrinho != null) {
            return new ResponseEntity<>(carrinhoCriado, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{clienteCpf}/{sessao}")
    public ResponseEntity<Carrinho> buscar(@PathVariable String clienteCpf, @PathVariable int sessao) {
        CarrinhoService carrinhoService = new CarrinhoService(); // Injetando BookService
        Carrinho carrinho = carrinhoService.buscarCarrinho(sessao, clienteCpf);
        if (carrinho != null) {
            return new ResponseEntity<>(carrinho, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
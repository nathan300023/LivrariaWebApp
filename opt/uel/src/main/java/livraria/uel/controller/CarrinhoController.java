package livraria.uel.controller;

import livraria.uel.model.Carrinho;
import livraria.uel.records.DadosCriacaoCarrinho;
import livraria.uel.services.CarrinhoService;

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
        Carrinho carrinho = new Carrinho(dados.dataCriacao(), dados.clienteCpf());
        CarrinhoService carrinhoService = new CarrinhoService();
        Carrinho carrinhoCriado = carrinhoService.criarCarrinho(carrinho);
        if (carrinho != null) {
            return new ResponseEntity<>(carrinhoCriado, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{clienteCpf}")
    public ResponseEntity<Carrinho> buscar(@PathVariable String clienteCpf) {
        CarrinhoService carrinhoService = new CarrinhoService(); // Injetando BookService
        Carrinho carrinho = carrinhoService.buscarCarrinho(clienteCpf);
        if (carrinho != null) {
            return new ResponseEntity<>(carrinho, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

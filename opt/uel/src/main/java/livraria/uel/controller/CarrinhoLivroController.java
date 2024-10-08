package livraria.uel.controller;
import livraria.uel.model.CarrinhoLivro;

import livraria.uel.records.DadosCarrinhoLivro;
import livraria.uel.services.CarrinhoLivroService;
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
        CarrinhoLivroService carrinhoLivroService = new CarrinhoLivroService();
        CarrinhoLivro carrinhoLivro = new CarrinhoLivro(dados.clienteCpf(), dados.codLivro(), dados.quantidade());
        CarrinhoLivro createdCarrinhoLivro = carrinhoLivroService.inserirCarrinhoLivro(carrinhoLivro);
        return new ResponseEntity<>(createdCarrinhoLivro, HttpStatus.CREATED);
    }




    @DeleteMapping("/{isbn}/{cliente_cpf}")
    public ResponseEntity<Void> deletar(@PathVariable int isbn, @PathVariable String cliente_cpf) {
        CarrinhoLivroService carrinhoLivroService = new CarrinhoLivroService(); // Injetando BookService
        boolean deletado = carrinhoLivroService.deletarCarrinhoLivro(isbn, cliente_cpf);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

package book.com.controller;

import book.com.entities.Pedido;
import book.com.records.DadosAssociacaoLivro;
import book.com.records.DadosCriacaoPedido;
import book.com.services.ClientService;
import book.com.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody DadosCriacaoPedido dados) {
        PedidoService pedidoService = new PedidoService();
        Pedido pedido = pedidoService.criarPedido(dados);
        if (pedido != null) {
            return new ResponseEntity<>(pedido, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }


    @GetMapping("/{num_pedido}")
    public ResponseEntity<Pedido> consultarPedido(@PathVariable int num_pedido) {
        PedidoService pedidoService = new PedidoService();
        Pedido pedido = pedidoService.buscarPedido(num_pedido);
        if (pedido != null) {
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/associar-livro")
    public ResponseEntity<String> associarLivroAoPedido(@RequestBody DadosAssociacaoLivro dados) {
        PedidoService pedidoService = new PedidoService();

        try {
            pedidoService.associaLivroAoPedido(dados.numPedido(), dados.codLivro(), dados.quantidade());
            return new ResponseEntity<>("Livro associado ao pedido com sucesso.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao associar o livro ao pedido.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
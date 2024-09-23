package book.com.controller;

import book.com.entities.Client;
import book.com.records.DadosCadastroClient;
import book.com.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @PostMapping
    public ResponseEntity<Client> cadastrar(@RequestBody DadosCadastroClient dados) {
        ClientService clientService = new ClientService();
        Client client = new Client(dados.cpf(), dados.pnome(), dados.snome(), dados.email(), dados.endereco());
        clientService.inserirClient(client);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    // Listar todos os clientes
    @GetMapping
    public ResponseEntity<List<Client>> listar() {
        ClientService clientService = new ClientService();
        List<Client> clientes = clientService.listarClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // Buscar cliente por CPF
    @GetMapping("/{cpf}")
    public ResponseEntity<Client> buscar(@PathVariable String cpf) {
        ClientService clientService = new ClientService();
        Client cliente = clientService.buscarClient(cpf);
        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // Atualizar cliente
    @PutMapping("/{cpf}")
    public ResponseEntity<Client> atualizar(@PathVariable String cpf, @RequestBody DadosCadastroClient dados) {
        ClientService clientService = new ClientService();
        Client clienteAtualizado = clientService.atualizarClient(cpf, dados);
        if (clienteAtualizado != null) {
            return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Deletar cliente
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletar(@PathVariable String cpf) {
        ClientService clientService = new ClientService();
        boolean deletado = clientService.deletarClient(cpf);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}


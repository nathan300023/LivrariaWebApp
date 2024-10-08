package livraria.uel.controller;

import livraria.uel.model.Carrinho;
import livraria.uel.model.Cliente;
import livraria.uel.services.CarrinhoService;
import livraria.uel.services.ClienteService;
import livraria.uel.records.DadosCadastroCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {



    // Função de Cadastro
    @PostMapping("/cadastro")
    public ResponseEntity<Cliente> cadastrar(@RequestBody DadosCadastroCliente dados) {
        ClienteService clienteService = new ClienteService();
        Cliente cliente = new Cliente(dados.cpf(), dados.pnome(), dados.snome(), dados.email(), dados.senha(), dados.endereco());

        clienteService.inserirCliente(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    // Função de Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginData loginData) {
        ClienteService clienteService = new ClienteService();
        Cliente cliente = clienteService.buscarPorEmailESenha(loginData.email, loginData.senha);

        if (cliente != null) {
           CarrinhoService carrinhoService = new CarrinhoService();
           Carrinho carrinho = new Carrinho(loginData.data, cliente.getCpf());
           carrinhoService.criarCarrinho(carrinho);

            return ResponseEntity.ok().body(new LoginResponse(true, cliente.getCpf()));
        } else {
            // Retorna falha no login
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(false, null));
        }
    }

    static class LoginData {
        public String email;
        public String senha;
        public LocalDate data; // Adiciona a data como LocalDate

        public LoginData(String email, String senha, LocalDate data) {
            this.email = email;
            this.senha = senha;
            this.data = data;
        }
    }
    // Classe interna para estruturar a resposta do login
    static class LoginResponse {
        public boolean success;
        public String clienteCpf;

        public LoginResponse(boolean success, String clienteCpf) {
            this.success = success;
            this.clienteCpf = clienteCpf;
        }
    }



    // Listar todos os clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        ClienteService clienteService = new ClienteService();
        List<Cliente> clientes = clienteService.listarClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // Buscar cliente por CPF
    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> buscar(@PathVariable String cpf) {
        ClienteService clienteService = new ClienteService();
        Cliente cliente = clienteService.buscarCliente(cpf);
        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Atualizar cliente
    @PutMapping("/{cpf}")
    public ResponseEntity<Cliente> atualizar(@PathVariable String cpf, @RequestBody DadosCadastroCliente dados) {
        ClienteService clienteService = new ClienteService();
        Cliente clienteAtualizado = clienteService.atualizarCliente(cpf, dados);
        if (clienteAtualizado != null) {
            return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Deletar cliente
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletar(@PathVariable String cpf) {
        ClienteService clienteService = new ClienteService();
        boolean deletado = clienteService.deletarCliente(cpf);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

package livraria.uel.controller;
import livraria.uel.model.Book;
import livraria.uel.model.Cliente;
import livraria.uel.services.BookService;
import livraria.uel.services.ClienteService;
import livraria.uel.records.DadosCadastroCliente;
import livraria.uel.model.Cliente;
import livraria.uel.services.ClienteService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/cliente")
    public String areaCliente(){
        return "cliente";
    }

    @GetMapping("/admin")
    public String areaAdmin(){
        return "admin";
    }


    @GetMapping("/homeusuario/{cpf}")
    public String paginaOperacoes(@PathVariable String cpf, Model model) {
        ClienteService clienteService = new ClienteService();
        BookService bookService = new BookService();
        Cliente cliente = clienteService.buscarCliente(cpf);
        List<Book> livros = bookService.listarLivros();


        model.addAttribute("cliente", cliente);
        model.addAttribute("livros", livros);
        return "homeCliente";
    }
}

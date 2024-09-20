package br.uel.produtos;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ProdutoController {

    private static final String SESSION_CARRINHO = "sessionCarrinho";

    @Autowired // indica para o framework que o controle deste atributo está sob responsabilidade dele
    ProdutoRepository produtoRepository;

    @GetMapping("/novo-produto")
    public String mostrarFormNovoProduto(Produto produto)
    {
        return "novo-produto";
    }

    @GetMapping(value={"/index", "/"})
    public String mostrarListaProdutos(Model model)
    {
        model.addAttribute("produtos", produtoRepository.findAll());
        return "index";
    }

    @PostMapping("/adicionar-produto")
    public String adicionarProduto(@Valid Produto produto, BindingResult result)
    {

        if (result.hasErrors())
        {
            return "/novo-produto";
        }

        produtoRepository.save(produto);
        return "redirect:/index"; // usando o "redirect", o método mapeado à URL "/novo-produto" (mostrarFormNovoProduto) será invocado.
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormAtualizar(@PathVariable("id") int id, Model model)
    {
        Produto produto = produtoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("O id do produto é inválido:" + id));

        model.addAttribute("produto", produto);
        return "atualizar-produto";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarProduto(@PathVariable("id") int id, @Valid Produto produto,BindingResult result, Model model)
    {
        if (result.hasErrors()) {
            produto.setId(id);
            return "atualizar-produto";
        }

        produtoRepository.save(produto);
        return "redirect:/index";
    }

    @GetMapping("/remover/{id}")
    public String removerProduto(@PathVariable("id") int id, HttpServletRequest request)
    {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("O id do produto é inválido:" + id));
        produtoRepository.delete(produto);
        List<Produto> listCarrinho = (List<Produto>) request.getSession().getAttribute(SESSION_CARRINHO);
        listCarrinho.remove(produto);
        request.getSession().setAttribute(SESSION_CARRINHO, listCarrinho);
        return "redirect:/index";
    }

    @GetMapping("/adicionar-carrinho/{id}")
    public String adicionarCarrinho(@PathVariable("id") int id, HttpServletRequest request)
    {
        Produto produto = produtoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("O id do produto é inválido: " + id));

        List<Produto> listCarrinho = (List<Produto>) request.getSession().getAttribute(SESSION_CARRINHO);

        if (CollectionUtils.isEmpty(listCarrinho))
        {
            listCarrinho = new ArrayList<>();
        }

        if (!listCarrinho.contains(produto)) //Se não existe ainda, crie e add +1 no carrinho
        {
            int qtidade_final = produto.getQuantidade_carrinho() + 1;
            produto.setQuantidade_carrinho(qtidade_final);
            listCarrinho.add(produto);
        }else
        {
            Produto produtoExistente = listCarrinho.get(listCarrinho.indexOf(produto));
            int atualizar_qtidade = produtoExistente.getQuantidade_carrinho() + 1;

            if(atualizar_qtidade <= produtoExistente.getQuantidade())
            {
                produto.setQuantidade_carrinho(atualizar_qtidade);
                listCarrinho.remove(produto);
                listCarrinho.add(produto);
            }
        }

        request.getSession().setAttribute(SESSION_CARRINHO, listCarrinho);
        return "redirect:/carrinho";
    }


    @GetMapping("/remover-carrinho/{id}")
    public String removerCarrinho(@PathVariable("id") int id, HttpServletRequest request)
    {
        Produto produto = produtoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("O id do produto é inválido: " + id));

        List<Produto> listCarrinho = (List<Produto>) request.getSession().getAttribute(SESSION_CARRINHO);

        if (CollectionUtils.isEmpty(listCarrinho))
        {
            listCarrinho = new ArrayList<>();
        }

        Produto produtoExistente = listCarrinho.get(listCarrinho.indexOf(produto));
        int atualizar_qtidade = produtoExistente.getQuantidade_carrinho() - 1;

        if(atualizar_qtidade > 0)
        {
            produto.setQuantidade_carrinho(atualizar_qtidade);
            listCarrinho.remove(produto);
            listCarrinho.add(produto);
        }else
        {
            listCarrinho.remove(produto);
        }

        request.getSession().setAttribute(SESSION_CARRINHO, listCarrinho);
        return "redirect:/carrinho";
    }

    @GetMapping("/carrinho")
    public String mostrarCarrinho(Model model, HttpServletRequest request){
        List<Produto> listCarrinho = (List<Produto>) request.getSession().getAttribute(SESSION_CARRINHO);
        model.addAttribute("sessionCarrinho", !CollectionUtils.isEmpty(listCarrinho) ? listCarrinho : new ArrayList<>());

        return "carrinho";
    }

}

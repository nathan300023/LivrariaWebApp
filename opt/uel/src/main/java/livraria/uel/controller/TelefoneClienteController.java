package livraria.uel.controller;
import livraria.uel.model.TelefoneCliente;
import livraria.uel.records.DadosTelefoneCliente;
import livraria.uel.services.TelefoneClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("telefones-cliente")
public class TelefoneClienteController {

    @PostMapping public ResponseEntity <TelefoneCliente> adicionar(@RequestBody DadosTelefoneCliente dados) {
        TelefoneClienteService telefoneClientService = new TelefoneClienteService();
        TelefoneCliente telefoneClient = new TelefoneCliente(dados.numTelefone(), dados.clienteCpf());
        telefoneClientService.inserirTelefoneClient(telefoneClient);
        return new ResponseEntity<>(telefoneClient, HttpStatus.OK);
    }

    @GetMapping("/listar-{cpf}")
    public ResponseEntity<List<TelefoneCliente>> listar(@PathVariable String cliente_cpf){
        TelefoneClienteService telefoneClientService = new TelefoneClienteService();
        List<TelefoneCliente> telefones = telefoneClientService.listarTelefones(cliente_cpf);
        return new ResponseEntity<>(telefones,HttpStatus.OK);
    }

    @GetMapping("/buscar-{numTelefone}-{cpf}")
    public ResponseEntity<TelefoneCliente> buscar(@PathVariable String numTelefone,String cliente_cpf){

        TelefoneClienteService telefoneClientService = new TelefoneClienteService();
        TelefoneCliente telefone = telefoneClientService.buscarTelefone(numTelefone,cliente_cpf);
        if(telefone != null){
            return new ResponseEntity<>(telefone,HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{num_telefone}-{cpf}")
    public ResponseEntity<TelefoneCliente> atualizar(@PathVariable String num_telefone,@PathVariable String cpf, @RequestBody DadosTelefoneCliente dados) {
        TelefoneClienteService telefoneClientService = new TelefoneClienteService();
        TelefoneCliente telefoneAtualizado = telefoneClientService.atualizarTelefone(num_telefone, cpf, dados);
        if (telefoneAtualizado != null) {
            return new ResponseEntity<>(telefoneAtualizado, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{num-telefone}-{cpf}")
    public ResponseEntity<Void> deletar(@PathVariable String num_telefone,@PathVariable String cpf) {
        TelefoneClienteService telefoneClientService = new TelefoneClienteService();
        boolean deletado = telefoneClientService.deletarTelefoneClient(num_telefone,cpf);
        if(deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

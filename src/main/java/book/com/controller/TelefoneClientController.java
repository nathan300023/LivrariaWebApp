package book.com.controller;

import book.com.entities.TelefoneClient;
import book.com.records.DadosTelefonesClient;
import book.com.services.TelefoneClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("telefones-cliente")
public class TelefoneClientController {

    @PostMapping public ResponseEntity <TelefoneClient> adicionar(@RequestBody DadosTelefonesClient dados) {
        TelefoneClientService telefoneClientService = new TelefoneClientService();
        TelefoneClient telefoneClient = new TelefoneClient(dados.numTelefone(), dados.clienteCpf());
        telefoneClientService.inserirTelefoneClient(telefoneClient);
        return new ResponseEntity<>(telefoneClient, HttpStatus.OK);
    }

    @GetMapping("/listar-{cpf}")
    public ResponseEntity<List<TelefoneClient>> listar(@PathVariable String cliente_cpf){
        TelefoneClientService telefoneClientService = new TelefoneClientService();
        List<TelefoneClient> telefones = telefoneClientService.listarTelefones(cliente_cpf);
        return new ResponseEntity<>(telefones,HttpStatus.OK);
    }

    @GetMapping("/buscar-{numTelefone}-{cpf}")
    public ResponseEntity<TelefoneClient> buscar(@PathVariable String numTelefone,String cliente_cpf){

        TelefoneClientService telefoneClientService = new TelefoneClientService();
        TelefoneClient telefone = telefoneClientService.buscarTelefone(numTelefone,cliente_cpf);
        if(telefone != null){
            return new ResponseEntity<>(telefone,HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{num_telefone}-{cpf}")
    public ResponseEntity<TelefoneClient> atualizar(@PathVariable String num_telefone,@PathVariable String cpf, @RequestBody DadosTelefonesClient dados) {
        TelefoneClientService telefoneClientService = new TelefoneClientService();
        TelefoneClient telefoneAtualizado = telefoneClientService.atualizarTelefone(num_telefone, cpf, dados);
        if (telefoneAtualizado != null) {
            return new ResponseEntity<>(telefoneAtualizado, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{num-telefone}-{cpf}")
    public ResponseEntity<Void> deletar(@PathVariable String num_telefone,@PathVariable String cpf) {
        TelefoneClientService telefoneClientService = new TelefoneClientService();
        boolean deletado = telefoneClientService.deletarTelefoneClient(num_telefone,cpf);
        if(deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

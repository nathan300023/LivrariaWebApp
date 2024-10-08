package livraria.uel.controller;
import livraria.uel.services.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/gerar/mensal/{mes}")
    public ResponseEntity<List<Map<String, String>>> gerarRelatorioMensal(@PathVariable String mes, @RequestParam(required = false) String filtro) {
        List<Map<String, String>> relatorio = relatorioService.gerarRelatorioMensal(mes);
        return ResponseEntity.ok(relatorio);
    }


    @GetMapping("/gerar/semanal")
    public ResponseEntity<List<Map<String, String>>> gerarRelatorioSemanal(@RequestParam(required = false) String filtro) {
        List<Map<String, String>> relatorio = relatorioService.gerarRelatorioSemanal();
        return ResponseEntity.ok(relatorio);
    }


    @GetMapping("/gerar/diario/{ano}/{mes}/{dia}")
    public ResponseEntity<List<Map<String, String>>> gerarRelatorioDiario(@PathVariable String dia, @PathVariable String mes, @PathVariable String ano, @RequestParam(required = false) String filtro) {
        List<Map<String, String>> relatorio = relatorioService.gerarRelatorioDiario(dia, mes, ano);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/gerar/estoque")
    public ResponseEntity<List<Map<String, String>>> gerarRelatorioEstoque(@RequestParam(required = false) String filtro) {
        List<Map<String, String>> relatorio = relatorioService.gerarRelatorioEstoque();
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/gerar/clientes-ativos")
    public ResponseEntity<List<Map<String, String>>> gerarRelatorioClientesAtivos(@RequestParam(required = false) String filtro) {
        List<Map<String, String>> relatorio = relatorioService.gerarRelatorioClientesAtivos();
        return ResponseEntity.ok(relatorio);
    }



}
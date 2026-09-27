package br.utpfr.td.tsi.apicompra.controller;

import br.utpfr.td.tsi.apicompra.Compra;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/ecommerce/compra")
@CrossOrigin(origins = "*")
public class CompraController {
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public ResponseEntity<Map<String, String>> efetuaCompra(@RequestBody Compra compra) {
        System.out.println("Compra recebida" + compra.getNome());
        String emailApiUrl = "http://localhost:8083/enviaemail/confirmacao";

        Map<String, String> emailPayload = Map.of(
                "email", compra.getEmail(),
                "nome", compra.getNome()
        );
        try {
            restTemplate.postForObject(emailApiUrl, emailPayload, String.class);
        }catch (Exception e){
            System.err.println("Erro no serviço de email" + e.getMessage());
        }

        return ResponseEntity.ok(Map.of("mensagem", "Compra efetuada, email enviado, " + compra.getNome() + " aguarde a confirmação da transação"));
    }

    @PostMapping("/transacao")
    public ResponseEntity<Map<String, String>> transacaoConcluida(@RequestBody Compra compra) {
        System.out.println("Compra recebida" + compra.getNome());
        String emailApiUrl = "http://localhost:8083/enviaemail/transacao";

        Map<String, String> emailPayload = Map.of(
                "email", compra.getEmail(),
                "nome", compra.getNome()
        );
        try {
            restTemplate.postForObject(emailApiUrl, emailPayload, String.class);
        }catch (Exception e){
            System.err.println("Erro no serviço de email" + e.getMessage());
        }

        return ResponseEntity.ok(Map.of("mensagem", "Transacao efetuada, email enviado"));
    }
}
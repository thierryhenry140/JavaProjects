package com.example.lesson49hibernatepart2.controller;

import com.example.lesson49hibernatepart2.model.Card;
import com.example.lesson49hibernatepart2.model.Client;
import com.example.lesson49hibernatepart2.service.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
@Tag(name = "Bank API", description = "Управление клиентами, картами и переводами")
public class BankController {

    private static final Logger log = LoggerFactory.getLogger(BankController.class);
    private final BankService service;

    public BankController(BankService service) {
        this.service = service;
    }

    @Operation(summary = "Сохранить клиента")
    @PostMapping("/client")
    public ResponseEntity<String> saveClient(@RequestBody @Valid Client client) {
        service.saveClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body("Client saved");
    }

    @Operation(summary = "Сохранить карту")
    @PostMapping("/card")
    public ResponseEntity<String> saveCard(@RequestBody @Valid Card card) {
        service.saveCard(card);
        return ResponseEntity.status(HttpStatus.CREATED).body("Card saved");
    }

    @Operation(summary = "Получить клиента по ID")
    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Integer id) {
        Client client = service.getClient(id);
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "Получить карту по номеру")
    @GetMapping("/card/{number}")
    public ResponseEntity<Card> getCard(@PathVariable String number) {
        Card card = service.getCard(number);
        return ResponseEntity.ok(card);
    }

    @Operation(summary = "Перевод между картами")
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam String from,
                                           @RequestParam String to,
                                           @RequestParam String amount) {
        service.transfer(from, to, new BigDecimal(amount));
        return ResponseEntity.ok("Transfer successful");
    }
}
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
import java.util.List;

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

    @Operation(summary = "Получить клиента по ID")
    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getClient(id));
    }

    @Operation(summary = "Получить всех клиентов")
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(service.getAllClients());
    }

    @Operation(summary = "Обновить клиента")
    @PutMapping("/client")
    public ResponseEntity<String> updateClient(@RequestBody @Valid Client client) {
        service.updateClient(client);
        return ResponseEntity.ok("Client updated");
    }

    @Operation(summary = "Удалить клиента по ID")
    @DeleteMapping("/client/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Integer id) {
        service.deleteClient(id);
        return ResponseEntity.ok("Client deleted");
    }


    @Operation(summary = "Сохранить карту")
    @PostMapping("/card")
    public ResponseEntity<String> saveCard(@RequestBody @Valid Card card) {
        service.saveCard(card);
        return ResponseEntity.status(HttpStatus.CREATED).body("Card saved");
    }

    @Operation(summary = "Получить карту по номеру")
    @GetMapping("/card/{number}")
    public ResponseEntity<Card> getCard(@PathVariable String number) {
        return ResponseEntity.ok(service.getCard(number));
    }

    @Operation(summary = "Получить все карты")
    @GetMapping("/cards")
    public ResponseEntity<List<Card>> getAllCards() {
        return ResponseEntity.ok(service.getAllCards());
    }

    @Operation(summary = "Обновить карту")
    @PutMapping("/card")
    public ResponseEntity<String> updateCard(@RequestBody @Valid Card card) {
        service.updateCard(card);
        return ResponseEntity.ok("Card updated");
    }

    @Operation(summary = "Удалить карту по номеру")
    @DeleteMapping("/card/{number}")
    public ResponseEntity<String> deleteCard(@PathVariable String number) {
        service.deleteCard(number);
        return ResponseEntity.ok("Card deleted");
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
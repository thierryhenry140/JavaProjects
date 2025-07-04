package com.example.lesson48hibernate.controller;

import com.example.lesson48hibernate.model.Card;
import com.example.lesson48hibernate.model.Client;
import com.example.lesson48hibernate.service.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
@Tag(name = "Bank API", description = "Управление клиентами и картами")
public class BankController {

    @Autowired
    private BankService service;

    @Operation(summary = "Создать клиента")
    @PostMapping("/client")
    public ResponseEntity<Client> saveClient(@Valid @RequestBody Client client) {
        return new ResponseEntity<>(service.saveClient(client), HttpStatus.CREATED);
    }

    @Operation(summary = "Создать карту")
    @PostMapping("/card")
    public ResponseEntity<Card> saveCard(@Valid @RequestBody Card card) {
        return new ResponseEntity<>(service.saveCard(card), HttpStatus.CREATED);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getClient(id));
    }

    @GetMapping("/card/{number}")
    public ResponseEntity<Card> getCard(@PathVariable String number) {
        return ResponseEntity.ok(service.getCard(number));
    }

    @PostMapping("/transfer")
    @Operation(summary = "Перевести деньги с одной карты на другую")
    public ResponseEntity<String> transfer(
            @Parameter(description = "Номер карты отправителя", example = "1234567890123456")
            @RequestParam String from,
            @Parameter(description = "Номер карты получателя", example = "6543210987654321")
            @RequestParam String to,
            @Parameter(description = "Сумма перевода", example = "100.00")
            @RequestParam String amount) {

        service.transfer(from, to, new BigDecimal(amount));
        return ResponseEntity.ok("Transfer successful");
    }
}
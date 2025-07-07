package com.example.lesson43_springbootapi.controller;

import com.example.lesson43_springbootapi.model.Card;
import com.example.lesson43_springbootapi.model.Client;
import com.example.lesson43_springbootapi.repository.BankRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;

@RestController
@RequestMapping("/api")
public class BankController {

    @Autowired
    private BankRepository repo;

    @PostMapping("/client")
    public ResponseEntity<String> saveClient(@RequestBody @Valid Client client) {
        repo.saveClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body("Client saved");
    }

    @PostMapping("/card")
    public ResponseEntity<String> saveCard(@RequestBody @Valid Card card) {
        repo.saveCard(card);
        return ResponseEntity.status(HttpStatus.CREATED).body("Card saved");
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Integer id) {
        Client client = repo.getClient(id);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }
        return ResponseEntity.ok(client);
    }

    @GetMapping("/card/{number}")
    public ResponseEntity<Card> getCard(@PathVariable String number) {
        Card card = repo.getCard(number);
        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }
        return ResponseEntity.ok(card);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam String from,
                                           @RequestParam String to,
                                           @RequestParam String amount) {
        BigDecimal amt = new BigDecimal(amount);
        repo.transfer(from, to, amt);
        return ResponseEntity.ok("Transfer successful");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        if (filename.contains("..")) {
            throw new IllegalArgumentException("Invalid file name: " + filename);
        }

        Path uploadDir = Paths.get("uploads");
        Files.createDirectories(uploadDir);

        Path path = uploadDir.resolve(filename);

        if (Files.exists(path)) {
            throw new IllegalArgumentException("File already exists: " + filename);
        }

        Files.write(path, file.getBytes());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("File uploaded successfully: " + filename);
    }
}
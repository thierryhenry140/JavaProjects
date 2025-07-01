package com.example.lesson46loggingandtests.controller;


import com.example.lesson46loggingandtests.model.Card;
import com.example.lesson46loggingandtests.repository.BankRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankControllerTest {

    @Test
    void testSaveCard() {
        BankRepository repo = new BankRepository();
        BankController controller = new BankController(repo);


        Card card = new Card("999", new BigDecimal("200"));
        ResponseEntity<String> response = controller.saveCard(card);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Card saved", response.getBody());
    }
}
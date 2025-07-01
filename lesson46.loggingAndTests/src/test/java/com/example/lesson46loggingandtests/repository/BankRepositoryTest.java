package com.example.lesson46loggingandtests.repository;

import com.example.lesson46loggingandtests.model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankRepositoryTest {

    private BankRepository repo;

    @BeforeEach
    void setUp() {
        repo = new BankRepository();
    }

    @Test
    void testSaveAndGetCard() {
        Card card = new Card("123", new BigDecimal("100"));
        repo.saveCard(card);

        Card found = repo.getCard("123");
        assertNotNull(found);
        assertEquals("123", found.getNumber());
    }

    @Test
    void testTransferSuccess() {
        repo.saveCard(new Card("1", new BigDecimal("100")));
        repo.saveCard(new Card("2", new BigDecimal("50")));

        repo.transfer("1", "2", new BigDecimal("40"));

        assertEquals(new BigDecimal("60"), repo.getCard("1").getBalance());
        assertEquals(new BigDecimal("90"), repo.getCard("2").getBalance());
    }

    @Test
    void testTransferInsufficientFunds() {
        repo.saveCard(new Card("1", new BigDecimal("10")));
        repo.saveCard(new Card("2", new BigDecimal("50")));

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                repo.transfer("1", "2", new BigDecimal("20"))
        );

        assertEquals("Insufficient funds", ex.getMessage());
    }
}
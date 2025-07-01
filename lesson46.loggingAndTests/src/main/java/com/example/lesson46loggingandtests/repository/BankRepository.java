package com.example.lesson46loggingandtests.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.lesson46loggingandtests.model.Card;
import com.example.lesson46loggingandtests.model.Client;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BankRepository {

    private static final Logger log = LoggerFactory.getLogger(BankRepository.class);

    private final ConcurrentHashMap<Integer, Client> clients = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Card> cards = new ConcurrentHashMap<>();

    public Client getClient(Integer id) {
        return clients.get(id);
    }

    public Card getCard(String number) {
        return cards.get(number);
    }

    public void saveClient(Client client) {

        log.info("Saving client: {}", client);
        clients.put(client.getId(), client);
    }

    public void saveCard(Card card) {

        log.info("Saving card: {}", card);
        cards.put(card.getNumber(), card);
    }

    public void transfer(String from, String to, BigDecimal amount) {
        log.debug("Start transfer: {} -> {} (amount: {})", from, to, amount);
        Card cardFrom = cards.get(from);
        Card cardTo = cards.get(to);
        if (cardFrom == null || cardTo == null) {
            log.warn("Card not found: from={} to={}", from, to);
            throw new IllegalArgumentException("Card not found");
        }
        synchronized (this) {
            if (cardFrom.getBalance().compareTo(amount) < 0) {
                log.warn("Insufficient funds: {} has {}", from, cardFrom.getBalance());
                throw new IllegalArgumentException("Insufficient funds");
            }
            cardFrom.setBalance(cardFrom.getBalance().subtract(amount));
            cardTo.setBalance(cardTo.getBalance().add(amount));

            log.info("Transfer successful: {} -> {} amount: {}", from, to, amount);
        }
    }
}
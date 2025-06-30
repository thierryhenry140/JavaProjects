package com.example.lesson44rest2.repository;


import com.example.lesson44rest2.model.Card;
import com.example.lesson44rest2.model.Client;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BankRepository {
    private final ConcurrentHashMap<Integer, Client> clients = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Card> cards = new ConcurrentHashMap<>();

    public Client getClient(Integer id) {
        return clients.get(id);
    }

    public Card getCard(String number) {
        return cards.get(number);
    }

    public void saveClient(Client client) {
        clients.put(client.getId(), client);
    }

    public void saveCard(Card card) {
        cards.put(card.getNumber(), card);
    }

    public void transfer(String from, String to, BigDecimal amount) {
        Card cardFrom = cards.get(from);
        Card cardTo = cards.get(to);
        if (cardFrom == null || cardTo == null) {
            throw new IllegalArgumentException("Card not found");
        }
        synchronized (this) {
            if (cardFrom.getBalance().compareTo(amount) < 0) {
                throw new IllegalArgumentException("Insufficient funds");
            }
            cardFrom.setBalance(cardFrom.getBalance().subtract(amount));
            cardTo.setBalance(cardTo.getBalance().add(amount));
        }
    }
}
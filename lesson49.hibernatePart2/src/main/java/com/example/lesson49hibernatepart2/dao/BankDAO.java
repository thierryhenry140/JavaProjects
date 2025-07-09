package com.example.lesson49hibernatepart2.dao;

import com.example.lesson49hibernatepart2.model.Card;
import com.example.lesson49hibernatepart2.model.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class BankDAO {

    @PersistenceContext
    private EntityManager em;

    public void saveClient(Client client) {
        em.persist(client);
    }

    public void saveCard(Card card) {
        em.persist(card);
    }

    public Client getClientById(Integer id) {
        return em.find(Client.class, id);
    }

    public Card getCardByNumber(String number) {
        return em.find(Card.class, number);
    }

    public void transfer(String fromNumber, String toNumber, BigDecimal amount) {
        Card from = em.find(Card.class, fromNumber);
        Card to = em.find(Card.class, toNumber);

        if (from == null || to == null) throw new IllegalArgumentException("Card not found");

        if (from.getBalance().compareTo(amount) < 0)
            throw new IllegalArgumentException("Insufficient funds");

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        em.merge(from);
        em.merge(to);
    }
}
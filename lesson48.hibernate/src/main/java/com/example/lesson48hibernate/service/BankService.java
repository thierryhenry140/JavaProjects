package com.example.lesson48hibernate.service;

import com.example.lesson48hibernate.model.Card;
import com.example.lesson48hibernate.model.Client;
import com.example.lesson48hibernate.repository.CardRepository;
import com.example.lesson48hibernate.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BankService {
    private static final Logger log = LoggerFactory.getLogger(BankService.class);

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private CardRepository cardRepo;

    public Client saveClient(Client client) {
        log.info("Saving client: {}", client);
        return clientRepo.save(client);
    }

    public Card saveCard(Card card) {
        log.info("Saving card: {}", card);
        return cardRepo.save(card);
    }

    public Client getClient(Integer id) {
        return clientRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
    }

    public Card getCard(String number) {
        return cardRepo.findById(number)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));
    }

    @Transactional
    public void transfer(String from, String to, BigDecimal amount) {
        Card fromCard = getCard(from);
        Card toCard = getCard(to);

        if (fromCard.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromCard.setBalance(fromCard.getBalance().subtract(amount));
        toCard.setBalance(toCard.getBalance().add(amount));

        cardRepo.save(fromCard);
        cardRepo.save(toCard);

        log.info("Transfer completed: {} -> {}, amount: {}", from, to, amount);
    }
}
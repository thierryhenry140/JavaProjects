package com.example.lesson49hibernatepart2.service;

import com.example.lesson49hibernatepart2.dao.BankDAO;
import com.example.lesson49hibernatepart2.model.Card;
import com.example.lesson49hibernatepart2.model.Client;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankService {

    private final BankDAO dao;

    public BankService(BankDAO dao) {
        this.dao = dao;
    }

    public void saveClient(Client client) {
        dao.saveClient(client);
    }

    public void saveCard(Card card) {
        dao.saveCard(card);
    }

    public Client getClient(Integer id) {
        return dao.getClientById(id);
    }

    public Card getCard(String number) {
        return dao.getCardByNumber(number);
    }

    public void transfer(String from, String to, BigDecimal amount) {
        dao.transfer(from, to, amount);
    }
}

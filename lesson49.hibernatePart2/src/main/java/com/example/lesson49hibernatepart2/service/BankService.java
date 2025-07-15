package com.example.lesson49hibernatepart2.service;

import com.example.lesson49hibernatepart2.model.Card;
import com.example.lesson49hibernatepart2.model.Client;
import com.example.lesson49hibernatepart2.dao.BankDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankService {

    private static final Logger log = LoggerFactory.getLogger(BankService.class);
    private final BankDAO bankDAO;

    public BankService(BankDAO bankDAO) {
        this.bankDAO = bankDAO;
    }


    public void saveClient(Client client) {
        log.debug("Saving client in service: {}", client);
        bankDAO.saveClient(client);
    }

    public Client getClient(Integer id) {
        log.debug("Fetching client by id: {}", id);
        return bankDAO.getClient(id);
    }

    public List<Client> getAllClients() {
        log.debug("Fetching all clients");
        return bankDAO.getAllClients();
    }

    public void updateClient(Client client) {
        log.debug("Updating client: {}", client);
        bankDAO.updateClient(client);
    }

    public void deleteClient(Integer id) {
        log.debug("Deleting client by id: {}", id);
        bankDAO.deleteClient(id);
    }

    // Card CRUD

    public void saveCard(Card card) {
        log.debug("Saving card in service: {}", card);
        bankDAO.saveCard(card);
    }

    public Card getCard(String number) {
        log.debug("Fetching card by number: {}", number);
        return bankDAO.getCard(number);
    }

    public List<Card> getAllCards() {
        log.debug("Fetching all cards");
        return bankDAO.getAllCards();
    }

    public void updateCard(Card card) {
        log.debug("Updating card: {}", card);
        bankDAO.updateCard(card);
    }

    public void deleteCard(String number) {
        log.debug("Deleting card by number: {}", number);
        bankDAO.deleteCard(number);
    }


    public void transfer(String from, String to, BigDecimal amount) {
        log.debug("Initiating transfer: from={} to={} amount={}", from, to, amount);
        bankDAO.transfer(from, to, amount);
    }
}
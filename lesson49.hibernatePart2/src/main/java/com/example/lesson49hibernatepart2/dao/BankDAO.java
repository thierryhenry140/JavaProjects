package com.example.lesson49hibernatepart2.dao;

import com.example.lesson49hibernatepart2.model.Card;
import com.example.lesson49hibernatepart2.model.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

@Repository
@Transactional
public class BankDAO {

    private static final Logger log = LoggerFactory.getLogger(BankDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void saveClient(Client client) {
        log.info("Saving client: {}", client);
        entityManager.persist(client);
    }

    public void saveCard(Card card) {
        log.info("Saving card: {}", card);
        entityManager.persist(card);
    }


    public Client getClient(Integer id) {
        TypedQuery<Client> query = entityManager.createQuery(
                "FROM Client c WHERE c.id = :id", Client.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }


    public Card getCard(String number) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Card> cq = cb.createQuery(Card.class);
        Root<Card> root = cq.from(Card.class);
        Predicate predicate = cb.equal(root.get("number"), number);
        cq.select(root).where(predicate);
        return entityManager.createQuery(cq).getSingleResult();
    }


    public List<Client> getAllClients() {
        return entityManager.createQuery("FROM Client", Client.class).getResultList();
    }


    public List<Card> getAllCards() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Card> cq = cb.createQuery(Card.class);
        cq.from(Card.class);
        return entityManager.createQuery(cq).getResultList();
    }


    public void updateClient(Client client) {
        log.info("Updating client: {}", client);
        entityManager.merge(client);
    }


    public void updateCard(Card card) {
        log.info("Updating card: {}", card);
        entityManager.merge(card);
    }


    public void deleteClient(Integer id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Client> cd = cb.createCriteriaDelete(Client.class);
        Root<Client> root = cd.from(Client.class);
        cd.where(cb.equal(root.get("id"), id));
        entityManager.createQuery(cd).executeUpdate();
    }


    public void deleteCard(String number) {
        TypedQuery<Card> query = entityManager.createQuery(
                "FROM Card c WHERE c.number = :number", Card.class);
        query.setParameter("number", number);
        Card card = query.getSingleResult();
        if (card != null) {
            entityManager.remove(card);
        }
    }


    public void transfer(String from, String to, BigDecimal amount) {
        log.debug("Start transfer: {} -> {} (amount: {})", from, to, amount);
        Card cardFrom = getCard(from);
        Card cardTo = getCard(to);

        if (cardFrom == null || cardTo == null) {
            log.warn("Card not found: from={} to={}", from, to);
            throw new IllegalArgumentException("Card not found");
        }

        if (cardFrom.getBalance().compareTo(amount) < 0) {
            log.warn("Insufficient funds: {} has {}", from, cardFrom.getBalance());
            throw new IllegalArgumentException("Insufficient funds");
        }

        cardFrom.setBalance(cardFrom.getBalance().subtract(amount));
        cardTo.setBalance(cardTo.getBalance().add(amount));

        entityManager.merge(cardFrom);
        entityManager.merge(cardTo);

        log.info("Transfer successful: {} -> {} amount: {}", from, to, amount);
    }
}

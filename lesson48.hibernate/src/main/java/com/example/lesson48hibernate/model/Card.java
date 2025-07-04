package com.example.lesson48hibernate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Entity
@Table(name = "cards")
@Schema(description = "Банковская карта", example = "{\"number\":\"1234567890123456\", \"balance\":1000.00, " +
        "\"client\":{\"id\":1, \"name\":\"Ivan Ivanov\"}}")
public class Card {
    @Id
    @Schema(description = "Номер карты", example = "1234567890123456")
    @NotBlank
    private String number;

    @NotNull
    @Schema(description = "Баланс карты", example = "1000.00")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
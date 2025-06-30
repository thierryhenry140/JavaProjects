package com.example.lesson44rest2.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class Card {
    @NotBlank
    private String number;

    @NotNull
    private BigDecimal balance;

    public Card() {}

    public Card(String number, BigDecimal balance) {
        this.number = number;
        this.balance = balance;
    }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
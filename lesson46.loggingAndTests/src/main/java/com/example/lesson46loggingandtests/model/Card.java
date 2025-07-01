package com.example.lesson46loggingandtests.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Банковская карта")
public class Card {
    @NotBlank
    @Schema(description = "Номер карты", example = "1234567890123456")
    private String number;

    @NotNull
    @Schema(description = "Баланс карты", example = "1000.00")
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
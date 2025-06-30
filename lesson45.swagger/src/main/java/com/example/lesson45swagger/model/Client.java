package com.example.lesson45swagger.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Клиент банка")
public class Client {
    @NotNull
    @Schema(description = "ID клиента", example = "1")
    private Integer id;

    @NotBlank
    @Schema(description = "Имя клиента", example = "Ivan Ivanov")
    private String name;

    public Client() {}

    public Client(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
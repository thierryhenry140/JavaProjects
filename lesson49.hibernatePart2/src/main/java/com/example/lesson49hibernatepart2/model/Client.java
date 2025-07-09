package com.example.lesson49hibernatepart2.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "clients")
@Schema(description = "Клиент банка")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

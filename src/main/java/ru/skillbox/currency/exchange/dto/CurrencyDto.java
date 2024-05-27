package ru.skillbox.currency.exchange.dto;

import lombok.*;

@Data
public class CurrencyDto {
    private Long id;

    private String name;

    private Long nominal;

    private Double value;

    private Long isoNumCode;

    private String isoCharCode;
}
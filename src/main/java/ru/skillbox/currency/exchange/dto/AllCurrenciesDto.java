package ru.skillbox.currency.exchange.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AllCurrenciesDto {

    List<ItemCurrency> currencies = new ArrayList<>();

    @Data
    @Builder
    public static class ItemCurrency {
        private String name;
        private Double value;
    }
}

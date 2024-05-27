package ru.skillbox.currency.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.currency.exchange.dto.AllCurrenciesDto;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.service.CurrencyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/currency")
public class CurrencyController {
    private final CurrencyService service;

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    AllCurrenciesDto getAll() {
        return service.getAllCurrency();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    CurrencyDto getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @GetMapping(value = "/convert")
    @ResponseStatus(HttpStatus.OK)
    Double convertValue(@RequestParam("value") Long value, @RequestParam("numCode") Long numCode) {
        return service.convertValue(value, numCode);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    CurrencyDto create(@RequestBody CurrencyDto dto) {
        return service.create(dto);
    }
}

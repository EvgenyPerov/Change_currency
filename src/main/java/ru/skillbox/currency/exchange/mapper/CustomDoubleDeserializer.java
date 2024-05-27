package ru.skillbox.currency.exchange.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class CustomDoubleDeserializer extends JsonDeserializer<Double> {
    @Override
    public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        if (value != null && !value.isEmpty()) {
            value = value.replace(",", "."); // Замена запятых на точки
            return Double.parseDouble(value);
        }
        return null;
    }
}
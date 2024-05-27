package ru.skillbox.currency.exchange.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import ru.skillbox.currency.exchange.mapper.CustomDoubleDeserializer;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "ValCurs")
public class ApiValutesDto {

    @JacksonXmlProperty(isAttribute = true, localName = "Date")
    private String date;

    @JacksonXmlProperty(isAttribute = true, localName = "name")
    private String name;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Valute")
    public List<Item> valutes;

    @Data
    public static class Item {
        @JacksonXmlProperty(isAttribute = true, localName = "ID")
        private String id;
        @JacksonXmlProperty(localName = "NumCode")
        private Long isoNumCode;
        @JacksonXmlProperty(localName = "CharCode")
        private String isoCharCode;
        @JacksonXmlProperty(localName = "Nominal")
        private Long nominal;
        @JacksonXmlProperty(localName = "Name")
        private String name;
        @JacksonXmlProperty(localName = "Value")
        @JsonDeserialize(using = CustomDoubleDeserializer.class)
        private Double value;
        @JacksonXmlProperty(localName = "VunitRate")
        @JsonDeserialize(using = CustomDoubleDeserializer.class)
        private Double vunitRate;
    }
}
package ru.skillbox.currency.exchange.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.currency.exchange.dto.ApiValutesDto;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.mapper.CurrencyMapper;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@EnableAsync
@Service
@RequiredArgsConstructor
public class UpdateCurrencyService {

    private final CurrencyMapper mapper;
    private final CurrencyRepository repository;

    @Value("${api.source.path}")
    private String path;

    @Async
    @Scheduled(fixedRateString = "${api.update.time}", timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void updateCurrencyApi() {
        Request request = new Request.Builder().url(path)
            .method("GET", null)
            .build();

        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

        ApiValutesDto valCurs = null;
        try {
            String body = client.newCall(request).execute().body().string();

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.findAndRegisterModules();
            valCurs = xmlMapper.readValue(body, ApiValutesDto.class);

        } catch (IOException e) {
            log.info("Ошибка с подключением к API и поиску валют. " + e);
        }
        List<Currency> currencyListFomDB = repository.findAllByIsoCharCodeNotNull();
        Set<String> codeSetFromDB = currencyListFomDB.stream()
            .map(Currency::getIsoCharCode).collect(Collectors.toSet());

        if (valCurs != null && !valCurs.getValutes().isEmpty()) {
            valCurs.getValutes().forEach(valuteFromApi -> {
                if (codeSetFromDB.contains(valuteFromApi.getIsoCharCode())) {
                    currencyListFomDB.stream().filter(v -> v.getIsoCharCode()
                            .equalsIgnoreCase(valuteFromApi.getIsoCharCode())).findFirst().get()
                        .setValue(valuteFromApi.getValue());
                } else {
                    repository.save(mapper.convertApiValuteToEntity(valuteFromApi));
                }
            });
        }
        log.info("По расписанию выполнилось обновление валют с внешнего API");
    }
}

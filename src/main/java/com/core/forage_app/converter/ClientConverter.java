package com.core.forage_app.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.forage_app.entity.Client;
import com.core.forage_app.repository.ClientRepository;

@Component
public class ClientConverter implements Converter<String, Client> {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client convert(String source) {
        if (source == null || source.isEmpty())
            return null;
        return clientRepository.findById(Integer.parseInt(source)).orElse(null);
    }
}
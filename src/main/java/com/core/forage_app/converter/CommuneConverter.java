package com.core.forage_app.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.forage_app.entity.Commune;
import com.core.forage_app.repository.CommuneRepository;

@Component
public class CommuneConverter implements Converter<String, Commune> {

    @Autowired
    private CommuneRepository communeRepository;

    @Override
    public Commune convert(String source) {
        if (source == null || source.isEmpty())
            return null;
        return communeRepository.findById(Integer.parseInt(source)).orElse(null);
    }
}

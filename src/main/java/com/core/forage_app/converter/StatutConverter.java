package com.core.forage_app.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.forage_app.entity.Statut;
import com.core.forage_app.repository.StatutRepository;

@Component
public class StatutConverter implements Converter<String, Statut> {

    @Autowired
    private StatutRepository statutRepository;

    @Override
    public Statut convert(String source) {
        return statutRepository.findById(Integer.parseInt(source))
                .orElse(null);
    }
}

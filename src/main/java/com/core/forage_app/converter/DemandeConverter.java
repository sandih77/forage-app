package com.core.forage_app.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.forage_app.entity.Demande;
import com.core.forage_app.repository.DemandeRepository;

@Component
public class DemandeConverter implements Converter<String, Demande> {

    @Autowired
    private DemandeRepository demandeRepository;

    @Override
    public Demande convert(String source) {
        return demandeRepository.findById(Integer.parseInt(source))
                .orElse(null);
    }
}

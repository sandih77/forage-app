package com.core.forage_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.forage_app.entity.TypeDevis;
import com.core.forage_app.repository.TypeDevisRepository;

@Service
public class TypeDevisService {
    @Autowired
    TypeDevisRepository typeDevisRepository;

    public TypeDevis findByType(String type) {
        return this.typeDevisRepository.findByType(type);
    }

    public List<TypeDevis> findAll() {
        return this.typeDevisRepository.findAll();
    }
}

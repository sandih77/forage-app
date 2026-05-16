package com.core.forage_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.forage_app.entity.Client;
import com.core.forage_app.repository.ClientRepository;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }

    public void save(Client client) {
        this.clientRepository.save(client);
    }

    public void delete(Client client) {
        this.clientRepository.delete(client);
    }

    public Client findById(int id) {
        return this.clientRepository.findById(id).orElse(null);
    }
}

package com.core.forage_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.forage_app.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}

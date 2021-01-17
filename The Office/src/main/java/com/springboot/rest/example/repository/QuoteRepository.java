package com.springboot.rest.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rest.example.model.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long>{

}
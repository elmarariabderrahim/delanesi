package com.kata.delanesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kata.delanesi.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
}

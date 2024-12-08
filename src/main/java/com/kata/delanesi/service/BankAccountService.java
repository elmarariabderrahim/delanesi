package com.kata.delanesi.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kata.delanesi.repository.TransactionRepository;
import com.kata.delanesi.entity.Transaction;

@Service
public class BankAccountService {

    private final TransactionRepository transactionRepository;
    private int currentBalance = 0;

    public BankAccountService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        currentBalance += amount;
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setBalance(currentBalance);
        transaction.setType("DEPOSIT");
        transaction.setDate(LocalDate.now());
        return transactionRepository.save(transaction);
    }

    public Transaction withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > currentBalance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        currentBalance -= amount;
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setBalance(currentBalance);
        transaction.setType("WITHDRAWAL");
        transaction.setDate(LocalDate.now());
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getStatement() {
        return transactionRepository.findAll();
    }
}

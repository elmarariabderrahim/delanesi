package com.kata.delanesi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kata.delanesi.entity.Transaction;
import com.kata.delanesi.service.BankAccountService;

import java.util.List;

@RestController
@RequestMapping("/api/bank-account")
public class BankAccountController {

    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestParam int amount) {
        Transaction transaction = bankAccountService.deposit(amount);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestParam int amount) {
        Transaction transaction = bankAccountService.withdraw(amount);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/statement")
    public ResponseEntity<List<Transaction>> getStatement() {
        List<Transaction> transactions = bankAccountService.getStatement();
        return ResponseEntity.ok(transactions);
    }
}

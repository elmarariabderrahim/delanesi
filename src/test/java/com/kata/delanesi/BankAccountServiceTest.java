package com.kata.delanesi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.kata.delanesi.entity.Transaction;
import com.kata.delanesi.repository.TransactionRepository;
import com.kata.delanesi.service.BankAccountService;

public class BankAccountServiceTest {
     @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeposit() {
        int amount = 100;
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setBalance(100);
        transaction.setType("DEPOSIT");
        transaction.setDate(LocalDate.now());
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

       
        Transaction result = bankAccountService.deposit(amount);

        // Assert
        assertEquals("DEPOSIT", result.getType());
        assertEquals(100, result.getAmount());
        assertEquals(100, result.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }


    @Test
    void testWithdraw() {
        bankAccountService.deposit(200);
        int withdrawAmount = 50;

        Transaction transaction = new Transaction();
        transaction.setAmount(withdrawAmount);
        transaction.setBalance(150);
        transaction.setType("WITHDRAWAL");
        transaction.setDate(LocalDate.now());
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = bankAccountService.withdraw(withdrawAmount);

        // Assert
        assertEquals("WITHDRAWAL", result.getType());
        assertEquals(50, result.getAmount());
        assertEquals(150, result.getBalance());
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }

    @Test
    void testWithdrawInsufficientFunds() {
        bankAccountService.deposit(50);
        int withdrawAmount = 100;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> bankAccountService.withdraw(withdrawAmount));
        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    void testStatement() {
        List<Transaction> transactions = new ArrayList<>();


        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100);
        transaction1.setBalance(100);
        transaction1.setType("DEPOSIT");
        transaction1.setDate(LocalDate.now());

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(50);
        transaction2.setBalance(50);
        transaction2.setType("WITHDRAWAL");
        transaction2.setDate(LocalDate.now());

        transactions.add(transaction1);
        transactions.add(transaction2);
        
        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> result = bankAccountService.getStatement();

        // Assert
        assertEquals(2, result.size());
        assertEquals("DEPOSIT", result.get(0).getType());
        assertEquals("WITHDRAWAL", result.get(1).getType());
        verify(transactionRepository, times(1)).findAll();
    }
}

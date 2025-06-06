package ru.t1.lint.springaoptask4.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.t1.lint.springaoptask4.model.Transaction;
import ru.t1.lint.springaoptask4.service.TransactionService;
import ru.t1.lint.springaoptask4.web.dto.TransactionDTO;
import ru.t1.lint.springaoptask4.web.dto.UpdatedAmountDTO;
import ru.t1.lint.springaoptask4.web.mapper.TransactionMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    @GetMapping("/{id}")
    public ResponseEntity<List<TransactionDTO>> getAccountTransactions(@PathVariable UUID id) {
        List<Transaction> transactions = transactionService.getAccountTransactions(id);
        List<TransactionDTO> transactionsDTO = transactionMapper.toDtoList(transactions);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionsDTO);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        List<TransactionDTO> transactionsDTO = transactionMapper.toDtoList(transactions);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionsDTO);
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> create(@Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction = transactionService.createTransaction(transaction);
        TransactionDTO createdTransactionDTO = transactionMapper.toDto(transaction);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTransactionDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateAmount(@Valid @RequestBody UpdatedAmountDTO amountDTO, @PathVariable Long id) {
        Transaction transaction = transactionService.updateAmount(amountDTO.getAmount(), id);
        TransactionDTO updatedTransactionDTO = transactionMapper.toDto(transaction);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedTransactionDTO);
    }
}

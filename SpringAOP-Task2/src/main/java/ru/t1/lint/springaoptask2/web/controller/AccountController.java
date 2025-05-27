package ru.t1.lint.springaoptask2.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.t1.lint.springaoptask2.model.Account;
import ru.t1.lint.springaoptask2.service.AccountService;
import ru.t1.lint.springaoptask2.web.dto.AccountDTO;
import ru.t1.lint.springaoptask2.web.dto.UpdatedBalanceDTO;
import ru.t1.lint.springaoptask2.web.mapper.AccountMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@Validated
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable UUID id) {
        Account account = accountService.getAccountByClientId(id);
        AccountDTO dto = accountMapper.toDto(account);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        List<AccountDTO> dtos = accountMapper.toDtoList(accounts);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dtos);
    }

    @PostMapping
    public ResponseEntity<AccountDTO> create(@Valid @RequestBody AccountDTO accountDTO) {
        Account account = accountMapper.toEntity(accountDTO);
        account = accountService.createAccount(account);
        AccountDTO dto = accountMapper.toDto(account);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AccountDTO> updateBalance(@Valid @RequestBody UpdatedBalanceDTO balance, @PathVariable UUID id) {
        Account account = accountService.updateBalance(balance.getBalance(), id);
        AccountDTO dto = accountMapper.toDto(account);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }
}

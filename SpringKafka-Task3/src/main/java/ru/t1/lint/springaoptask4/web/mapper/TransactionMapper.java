package ru.t1.lint.springaoptask4.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.t1.lint.springaoptask4.model.Account;
import ru.t1.lint.springaoptask4.model.Client;
import ru.t1.lint.springaoptask4.model.Transaction;
import ru.t1.lint.springaoptask4.web.dto.TransactionDTO;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TransactionMapper extends Mappable<Transaction, TransactionDTO> {

    @Override
    @Mapping(source = "account.client.clientId", target = "clientId")
    TransactionDTO toDto(Transaction transaction);

    @Override
    @Mapping(target = "account.client.clientId", source = "clientId")
    Transaction toEntity(TransactionDTO transactionDTO);

    @Override
    List<TransactionDTO> toDtoList(List<Transaction> transactions);

    @Override
    List<Transaction> toEntityList(List<TransactionDTO> transactionDTOs);

    default Account map(UUID clientId) {
        if (clientId == null) return null;
        Client client = new Client();
        client.setClientId(clientId);
        Account account = new Account();
        account.setClient(client);
        return account;
    }
}

package ru.t1.lint.springaoptask4.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.t1.lint.springaoptask4.model.Account;
import ru.t1.lint.springaoptask4.model.Client;
import ru.t1.lint.springaoptask4.web.dto.AccountDTO;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AccountMapper extends Mappable<Account, AccountDTO> {

    @Override
    @Mapping(source = "client.clientId", target = "clientId")
    AccountDTO toDto(Account account);

    @Override
    @Mapping(target = "client", source = "clientId")
    Account toEntity(AccountDTO accountDTO);

    @Override
    List<AccountDTO> toDtoList(List<Account> accounts);

    @Override
    List<Account> toEntityList(List<AccountDTO> accountDTOs);

    default Client map(UUID clientId) {
        if (clientId == null) return null;
        Client client = new Client();
        client.setClientId(clientId);
        return client;
    }
}

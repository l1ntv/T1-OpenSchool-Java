package ru.t1.lint.springaoptask3.web.mapper;

import org.mapstruct.Mapper;
import ru.t1.lint.springaoptask3.model.Client;
import ru.t1.lint.springaoptask3.web.dto.ClientDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper extends Mappable<Client, ClientDTO> {

    @Override
    ClientDTO toDto(Client client);

    @Override
    Client toEntity(ClientDTO clientDTO);

    @Override
    List<ClientDTO> toDtoList(List<Client> clients);

    @Override
    List<Client> toEntityList(List<ClientDTO> clientsDTO);
}

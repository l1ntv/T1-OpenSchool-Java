package ru.t1.lint.springaoptask4.service;

import ru.t1.lint.springaoptask4.model.Client;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    List<Client> getAllClients();

    Client getClientById(UUID id);

    Client createClient(Client client);

    Client updateClient(UUID id, Client client);

    void deleteClient(UUID id);
}

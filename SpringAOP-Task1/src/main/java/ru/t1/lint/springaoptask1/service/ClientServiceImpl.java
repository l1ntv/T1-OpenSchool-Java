package ru.t1.lint.springaoptask1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.lint.springaoptask1.aop.DataSourceErrorLoggable;
import ru.t1.lint.springaoptask1.model.Client;
import ru.t1.lint.springaoptask1.model.exception.ClientNotFoundException;
import ru.t1.lint.springaoptask1.repository.ClientRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    @DataSourceErrorLoggable
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    @DataSourceErrorLoggable
    public Client getClientById(UUID id) {
        return clientRepository.findByClientId(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found."));
    }

    @Override
    @Transactional
    @DataSourceErrorLoggable
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    @DataSourceErrorLoggable
    public Client updateClient(UUID id, Client client) {
        Client existingClient = clientRepository.findByClientId(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found."));
        existingClient.setFirstName(client.getFirstName());
        existingClient.setLastName(client.getLastName());
        existingClient.setPatronymic(client.getPatronymic());
        return clientRepository.save(existingClient);
    }

    @Override
    @Transactional
    @DataSourceErrorLoggable
    public void deleteClient(UUID id) {
        if (!clientRepository.existsByClientId(id)) {
            throw new ClientNotFoundException("Client not found.");
        }
        clientRepository.deleteByClientId(id);
    }
}

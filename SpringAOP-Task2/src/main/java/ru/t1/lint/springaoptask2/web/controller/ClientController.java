package ru.t1.lint.springaoptask2.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.t1.lint.springaoptask2.model.Client;
import ru.t1.lint.springaoptask2.service.ClientService;
import ru.t1.lint.springaoptask2.web.dto.ClientDTO;
import ru.t1.lint.springaoptask2.web.mapper.ClientMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        List<ClientDTO> dto = clientMapper.toDtoList(clients);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable UUID clientId) {
        Client client = clientService.getClientById(clientId);
        ClientDTO dto = clientMapper.toDto(client);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        Client createdClient = clientService.createClient(client);
        ClientDTO responseDTO = clientMapper.toDto(createdClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable UUID clientId, @RequestBody ClientDTO clientDTO) {
        Client updatedClient = clientService.updateClient(clientId, clientMapper.toEntity(clientDTO));
        ClientDTO responseDTO = clientMapper.toDto(updatedClient);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }
}

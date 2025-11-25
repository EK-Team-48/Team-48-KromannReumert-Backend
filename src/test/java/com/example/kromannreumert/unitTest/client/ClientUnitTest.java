package com.example.kromannreumert.unitTest.client;

import com.example.kromannreumert.client.DTO.CreateClientDTO;
import com.example.kromannreumert.client.entity.Client;
import com.example.kromannreumert.client.repository.ClientRepository;
import com.example.kromannreumert.client.service.ClientService;
import com.example.kromannreumert.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ClientUnitTest {


    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    @Test
    void getAllClients() {

        // ARRANGE
        Set<User> addUsers = Set.of(
                new User
                        (0L, "test", "test", "test","test", null, null));
        List<Client> addClients = List.of(
                new Client
                        (1L,"ClientTestName", addUsers,1000L));

        when(clientRepository.findAll()).thenReturn(addClients);

        // ACT
        List<Client> result = clientService.getAllClients();

        // ASSERT
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(clientRepository).findAll();
    }

    @Test
    void getClientByIdPrefix() {

        // ARRANGE
        Long idPrefix = 1000L;
        String clientName = "ClientTestName";
        Set<User> addUsers = Set.of(
                new User
                        (0L, "test", "test", "test","test", null, null));
        Client addClients = new Client(1L,clientName, addUsers,idPrefix);

        // ACT
        when(clientRepository.getClientByIDPrefix(1000L)).thenReturn(Optional.of(addClients));
        Client result = clientService.getClientByIdPrefix(1000L);

        // ASSERT
        assertNotNull(result);
        assertEquals(idPrefix, result.getIDPrefix());
        assertEquals(clientName, result.getName());
        verify(clientRepository).getClientByIDPrefix(idPrefix);
    }

    @Test
    void getClientByName() {

        // ARRANGE
        Long idPrefix = 1000L;
        String clientName = "ClientTestName";
        Set<User> addUsers = Set.of(
                new User
                        (0L, "test", "test", "test","test", null, null));
        Client addClients = new Client(1L,clientName, addUsers,idPrefix);

        // ACT
        when(clientRepository.findClientByName("ClientTestName")).thenReturn(Optional.of(addClients));
        Client result = clientService.getClientByName("ClientTestName");

        // ASSERT
        assertNotNull(result);
        assertEquals(idPrefix, result.getIDPrefix());
        assertEquals(clientName, result.getName());
        verify(clientRepository).findClientByName("ClientTestName");

    }

    @Test
    void addClient() {


        // ARRANGE
        String clientName = "ClientTest";
        Long idPrefix = 99000L;
        Set<User> addUsers = Set.of(new User(0L, "test", "test", "test","test", null, null));
        CreateClientDTO addClient = new CreateClientDTO(clientName, addUsers, idPrefix);
        Client client = new Client(null, addClient.clientName(), addClient.users(), addClient.idPrefix());

        // ACT
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(clientRepository.findClientByName(client.getName())).thenReturn(Optional.of(client));


        String returnResult = clientService.addClient(addClient);
        Client result = clientService.getClientByName(clientName);
        String expectedResult = "Client successfully created: " + clientName;

        // ASSERT
        assertNotNull(returnResult);
        assertNotNull(result);
        assertEquals(clientName, result.getName());
        assertEquals(expectedResult, returnResult);
        verify(clientRepository).save(any(Client.class));
        verify(clientRepository).findClientByName(clientName);
    }

}

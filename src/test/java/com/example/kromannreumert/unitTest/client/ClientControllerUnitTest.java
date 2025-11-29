package com.example.kromannreumert.unitTest.client;

import com.example.kromannreumert.client.DTO.ClientResponeDTO;
import com.example.kromannreumert.client.controller.ClientController;
import com.example.kromannreumert.client.service.ClientService;
import com.example.kromannreumert.security.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClientController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")

public class ClientControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ClientService clientService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_returnAllClients_isOK_for_Admin() throws Exception {
        ClientResponeDTO dto = new ClientResponeDTO(1L, "TestClient", null, 1000L);

        when(clientService.getAllClients()).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/v1/client/"))
                .andExpect(status().isOk());

        verify(clientService).getAllClients();
    }


    @Test
    @WithMockUser(roles = "SAGSBEHANDLER")
    void should_returnAllClients_isOK_for_sagsbehandler() throws Exception {
        ClientResponeDTO dto = new ClientResponeDTO(1L, "TestClient", null, 1000L);

        when(clientService.getAllClients()).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/v1/client/"))
                .andExpect(status().isOk());

        verify(clientService).getAllClients();
    }

    @Test
    @WithMockUser(roles = "PARTNER")
    void should_returnAllClients_isOK_for_partner() throws Exception {
        ClientResponeDTO dto = new ClientResponeDTO(1L, "TestClient", null, 1000L);

        when(clientService.getAllClients()).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/v1/client/"))
                .andExpect(status().isOk());

        verify(clientService).getAllClients();
    }

    @Test
    @WithMockUser(roles = "JURIST")
    void should_returnAllClients_isForbidden_for_jurist() throws Exception {
        mockMvc.perform(get("/api/v1/client/"))
                .andExpect(status().isForbidden());
    }

    @Test
    void should_returnAllClients_isUnAuthorized_for_noOne() throws Exception {
        mockMvc.perform(get("/api/v1/client/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_returnOneClientWithId_isOK_for_Admin() throws Exception {
        ClientResponeDTO dto = new ClientResponeDTO(1L, "TestClient", null, 1000L);

        when(clientService.getClientByIdPrefix(1000L))
                .thenReturn(dto);

        mockMvc.perform(get("/api/v1/client/1000"))
                .andExpect(status().isOk());

        verify(clientService).getClientByIdPrefix(1000L);
    }


    @Test
    @WithMockUser(roles = "SAGSBEHANDLER")
    void should_returnOneClientWithId_isOK_for_sagsbehandler() throws Exception {
        ClientResponeDTO dto = new ClientResponeDTO(1L, "TestClient", null, 1000L);

        when(clientService.getClientByIdPrefix(1000L))
                .thenReturn(dto);

        mockMvc.perform(get("/api/v1/client/1000"))
                .andExpect(status().isOk());

        verify(clientService).getClientByIdPrefix(1000L);
    }

    @Test
    @WithMockUser(roles = "PARTNER")
    void should_returnOneClientWithId_isOK_for_partner() throws Exception {
        ClientResponeDTO dto = new ClientResponeDTO(1L, "TestClient", null, 1000L);

        when(clientService.getClientByIdPrefix(1000L))
                .thenReturn(dto);

        mockMvc.perform(get("/api/v1/client/1000"))
                .andExpect(status().isOk());

        verify(clientService).getClientByIdPrefix(1000L);
    }

    @Test
    @WithMockUser(roles = "JURIST")
    void should_returnOneClientWithId_isForbidden_for_jurist() throws Exception {
        mockMvc.perform(get("/api/v1/client/1" ))
                .andExpect(status().isForbidden());
    }

    @Test
    void should_returnOneClientWithId_isUnAuthorized_for_noOne() throws Exception {
        mockMvc.perform(get("/api/v1/client/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_returnOneClientWithName_isOK_for_Admin() throws Exception {
        ClientResponeDTO dto = new ClientResponeDTO(1L, "TestClient", null, 1000L);

        when(clientService.getClientByName("TestClient"))
                .thenReturn(dto);

        mockMvc.perform(get("/api/v1/client/getclientbyname/TestClient"))
                .andExpect(status().isOk());

        verify(clientService).getClientByName("TestClient");
    }


    @Test
    @WithMockUser(roles = "SAGSBEHANDLER")
    void should_returnOneClientWithName_isOK_for_sagsbehandler() throws Exception {
        ClientResponeDTO dto = new ClientResponeDTO(1L, "TestClient", null, 1000L);

        when(clientService.getClientByName("TestClient"))
                .thenReturn(dto);

        mockMvc.perform(get("/api/v1/client/getclientbyname/TestClient"))
                .andExpect(status().isOk());

        verify(clientService).getClientByName("TestClient");
    }

    @Test
    @WithMockUser(roles = "PARTNER")
    void should_returnOneClientWithName_isOK_for_partner() throws Exception {
        ClientResponeDTO dto = new ClientResponeDTO(1L, "TestClient", null, 1000L);

        when(clientService.getClientByName("TestClient"))
                .thenReturn(dto);

        mockMvc.perform(get("/api/v1/client/getclientbyname/TestClient"))
                .andExpect(status().isOk());

        verify(clientService).getClientByName("TestClient");
    }

    @Test
    @WithMockUser(roles = "JURIST")
    void should_returnOneClientWithName_isForbidden_for_jurist() throws Exception {
        mockMvc.perform(get("/api/v1/client/getclientbyname/test" ))
                .andExpect(status().isForbidden());
    }

    @Test
    void should_returnOneClientWithName_isUnAuthorized_for_noOne() throws Exception {
        mockMvc.perform(get("/api/v1/client/getclientbyname/test"))
                .andExpect(status().isUnauthorized());
    }

}


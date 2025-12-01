package com.example.kromannreumert.integrationTest.client;

import com.example.kromannreumert.client.DTO.ClientResponeDTO;
import com.example.kromannreumert.client.entity.Client;
import com.example.kromannreumert.client.repository.ClientRepository;
import com.example.kromannreumert.client.service.ClientService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

    import static org.mockito.Mockito.*;
    import static org.junit.jupiter.api.Assertions.*;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
    import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ClientIntegrationTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    ApplicationContext context;

    private final String BASEURL = "/api/v1/client/";

    @Test
    void context() {
        assertNotNull(context);
    }


    @Test
    void should_return_allClients_for_AuthorizedUsers() throws Exception{
        String [] authorizedRoles = {"ADMIN", "SAGSBEHANDLER", "PARTNER"};
        for(String roles : authorizedRoles)
        {
            mockMvc.perform(get(BASEURL).with(user("ADMIN").roles(roles)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].name").value("Kromann Reumert"))
                    .andExpect(jsonPath("$[0].idPrefix").value(1000))
                    .andExpect(jsonPath("$[1].id").value(2))
                    .andExpect(jsonPath("$[1].name").value("AlphaSolution"))
                    .andExpect(jsonPath("$[1].idPrefix").value(2000));
        }
    }

    @Test
    void should_return_unAuthorized_for_unAuthorizedUsers() throws Exception {
        mockMvc.perform(get(BASEURL).with(user("JURIST").roles("JURIST")))
                .andExpect(status().isForbidden());
    }
}

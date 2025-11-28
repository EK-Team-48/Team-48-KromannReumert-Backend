package com.example.kromannreumert.unitTest.user;

import com.example.kromannreumert.security.config.SecurityConfig;
import com.example.kromannreumert.user.controller.UserController;
import com.example.kromannreumert.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = UserController.class)
@Import(SecurityConfig.class)
public class UserUnitTestController {


    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserService userService;


    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsersWhileLoggedIn() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void getAllUsersNotLoggedIn() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }





}

package com.example.kromannreumert.unitTest.role;

import com.example.kromannreumert.security.config.SecurityConfig;
import com.example.kromannreumert.user.controller.RoleController;
import com.example.kromannreumert.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RoleController.class)
@Import(SecurityConfig.class)
public class RoleUnitTestController {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    RoleService roleService;



}

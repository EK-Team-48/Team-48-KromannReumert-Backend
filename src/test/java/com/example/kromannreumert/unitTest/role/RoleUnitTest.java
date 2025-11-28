package com.example.kromannreumert.unitTest.role;

import com.example.kromannreumert.logging.service.LoggingService;
import com.example.kromannreumert.user.repository.RoleRepository;
import com.example.kromannreumert.user.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoleUnitTest {

    @InjectMocks
    RoleService roleService;

    @Mock
    RoleRepository roleRepository;



    @Test
    void getAllRoles(){

    }

    @Test
    void getRolebyRoleId(){

    }

    @Test
    void getRoleById(){

    }


    @Test
    void createRole(){

    }

    @Test
    void updateRole(){

    }

    @Test
    void deleteRole(){

    }





}

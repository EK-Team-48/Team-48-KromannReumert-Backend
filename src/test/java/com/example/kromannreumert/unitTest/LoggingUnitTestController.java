package com.example.kromannreumert.unitTest;


import com.example.kromannreumert.securityFeature.controller.LogController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = LogController.class)
public class LoggingUnitTestController {


    @Autowired
    MockMvc mockMvc;


}

package com.example.kromannreumert.securityFeature.service;

import com.example.kromannreumert.securityFeature.controller.AuthorizeController;
import com.example.kromannreumert.securityFeature.entity.LogAction;
import com.example.kromannreumert.securityFeature.entity.Logging;
import com.example.kromannreumert.securityFeature.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoggingService {

    private final static Logger log = LoggerFactory.getLogger(AuthorizeController.class);
    private final LogRepository logRepository;

    public LoggingService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void log(LogAction action, String actor, String details) {
        Logging entry = new Logging(actor, action, details);
        logRepository.save(entry);
    }

    public List<Logging> getAllLogs() {
        return logRepository.findAll();
    }

    public List<Logging> getAllLogsByAction(LogAction action) {
        return logRepository.findAllByAction(action);
    }
}


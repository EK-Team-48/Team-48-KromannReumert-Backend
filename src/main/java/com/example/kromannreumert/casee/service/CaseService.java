package com.example.kromannreumert.casee.service;

import com.example.kromannreumert.casee.entity.Casee;
import com.example.kromannreumert.casee.repository.CaseRepository;
import com.example.kromannreumert.client.DTO.CreateClientDTO;
import com.example.kromannreumert.client.DTO.UpdateClientIdPrefixDTO;
import com.example.kromannreumert.client.DTO.UpdateClientNameDTO;
import com.example.kromannreumert.client.entity.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseService {

    private final CaseRepository caseRepository;

    public CaseService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    public List<Casee> getAllClients() {
        return caseRepository.findAll();
    }


    public Casee getClientByName(String caseName) {
        return caseRepository.findAllByName(caseName).orElseThrow(() -> new RuntimeException("Case not found"));
    }


    public String addCase() {
        return "TODO";
    }


    private Casee updateCase() {
        return null; //TODO
    }

    public String deleteCase(Long id) {
        caseRepository.deleteById(id);
        return "Case with id: " + id + " has been deleted";
    }
}

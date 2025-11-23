package com.example.kromannreumert.securityFeature.repository;

import com.example.kromannreumert.securityFeature.entity.LogAction;
import com.example.kromannreumert.securityFeature.entity.Logging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Logging, Integer> {

    List<Logging> findAllByAction(LogAction logAction);
}

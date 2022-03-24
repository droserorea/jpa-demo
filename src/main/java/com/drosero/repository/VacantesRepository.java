package com.drosero.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drosero.model.Vacante;

public interface VacantesRepository extends JpaRepository<Vacante, Integer> {

}

package com.drosero.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drosero.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario,Integer> {

}

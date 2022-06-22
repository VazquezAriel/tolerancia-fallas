package com.mitocode.repo.postgresql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitocode.model.Vehiculo;

public interface VehiculoRepoPostgresql extends JpaRepository<Vehiculo, String>{

}

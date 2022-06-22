package com.mitocode.repo.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitocode.model.Vehiculo;

public interface VehiculoRepoMysql extends JpaRepository<Vehiculo, String>{

}

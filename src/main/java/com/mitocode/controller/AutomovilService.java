package com.mitocode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.model.Vehiculo;
import com.mitocode.service.VehiculosService;

@RestController
@RequestMapping("/vehiculos")
public class AutomovilService {

	@Autowired
	private VehiculosService servicios; 
	
	@GetMapping("/cargarDatos")
	public List<Vehiculo> cargarDatos() {
		return servicios.getVehiculos();
	}
	
	@PostMapping("/registrar")
	public Respuesta registrarVehiculo(@RequestBody Vehiculo vehiculo) {
		return servicios.registrar(vehiculo);
	}
	
}

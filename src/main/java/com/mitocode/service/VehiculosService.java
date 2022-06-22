package com.mitocode.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.controller.Respuesta;
import com.mitocode.model.Vehiculo;
import com.mitocode.repo.mysql.VehiculoRepoMysql;
import com.mitocode.repo.postgresql.VehiculoRepoPostgresql;


@Service
public class VehiculosService{

	@Autowired
	private VehiculoRepoMysql repoPrincipal;
	
	@Autowired
	private VehiculoRepoPostgresql repoRespaldo;
	
	public Respuesta registrar(Vehiculo v) {
		boolean estadoBP = true;
		boolean estadoBR = true;
		Respuesta res = new Respuesta();
		String resImagen = "";
		
		try {
			guardarImagen(v);
			resImagen = ", Imagen guardada";
		} catch (Exception e1) {
			resImagen = ", No se guardo la imagen: " + e1.getMessage();
		}
		
		try {
			repoPrincipal.save(v);
		} catch (Exception e) {
			estadoBP = false;
		}
		try {
			repoRespaldo.save(v);
		} catch (Exception e) {
			estadoBR = false;
		}
		if (estadoBP && estadoBR) {
			res =  new Respuesta(0, "Guardado correctamente" + resImagen);
		} else if (!estadoBP && estadoBR) {
			res =  new Respuesta(1, "Guardado unicamente en DB de respaldo" + resImagen);
		} else if (estadoBP && !estadoBR) {
			res =  new Respuesta(2, "Guardado unicamente en DB principal" + resImagen);
		} else if (!estadoBP && !estadoBR) {
			res =  new Respuesta(3, "Los datos no se guardaron" + resImagen);
		}
		
		return res;
	}
	
	public List<Vehiculo> getVehiculos() {
		
		List<Vehiculo> lista = new ArrayList<>();
		try {
			lista =  repoPrincipal.findAll();
			for (Vehiculo vehiculo : lista) {
				cargarImagen(vehiculo);
			}
		} catch (Exception e) {
			lista =  repoPrincipal.findAll();
			for (Vehiculo vehiculo : lista) {
				cargarImagen(vehiculo);
			}
		}
		return lista;
	}
	
	public void guardarImagen(Vehiculo auto) throws Exception  {
		
		String imgBase64 = "", ruta = "";
		int aux = 1, aux2 = 1;
		for (int i=1; i <auto.getImagen().length (); i++) {
			char c = auto.getImagen().charAt (i);
			if (c == '/'){aux = i+1;}
			if (c == ';'){aux2=i;}
			if (c == ',') {
				imgBase64 = auto.getImagen().substring(i+1, auto.getImagen().length());
				auto.setImagen(auto.getImagen().substring(aux, aux2));
				ruta = "/home/xavier37nano/" + auto.getPlaca() +  "." + auto.getImagen();
				break;
			}
		}
		
		byte[] data = Base64.getDecoder().decode(imgBase64);
		FileOutputStream fileOutputStream = new FileOutputStream(ruta);
		fileOutputStream.write(data);
		fileOutputStream.close();
		
	}
	
	public void cargarImagen(Vehiculo vehiculo) {
		
		try {
			FileInputStream imageStream = new FileInputStream("/home/xavier37nano/" + vehiculo.getPlaca() +  "." + vehiculo.getImagen());
			byte[] data = imageStream.readAllBytes();
			String imgBase64 = Base64.getEncoder().encodeToString(data);
			vehiculo.setImagen(imgBase64);
		} catch (Exception e) {
			
		}
		
		
	}
}











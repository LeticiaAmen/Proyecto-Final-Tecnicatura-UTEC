package com.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.GeneracionDAO;
import com.entidades.Generacion;

@Stateless
public class GeneracionService {
	
	@EJB
	private GeneracionDAO generacionDAO;
	
	public void crearGeneracion (Generacion generacion) {
		generacionDAO.crearGeneracion(generacion);
	}
	
	public Generacion obtenerGeneracion(Long id) {
		return generacionDAO.obtenerGeneracion(id);
	}
	
	public List<Generacion> obtenerGeneracionesTodas(){
		return generacionDAO.obtenerGeneracionesTodas();
	}
	
	public void actualizarGeneracion(Generacion generacion) {
		generacionDAO.actualizarGeneracion(generacion);
	}
	
	public Generacion obtenerGeneracionPorNombre (String nombre) {
		return generacionDAO.obtenerGeneracionPorNombre(nombre);
	}

}

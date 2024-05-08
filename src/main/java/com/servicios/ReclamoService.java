package com.servicios;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.ReclamoDAO;
import com.entidades.Estado;
import com.entidades.Reclamo;

@Stateless
public class ReclamoService {
	
	@EJB
	private ReclamoDAO reclamoDAO;

	public void crearReclamo(Reclamo reclamo) {
		reclamoDAO.crearReclamo(reclamo);
	}
	
	public Reclamo obtenerReclamo(long id) {
		return reclamoDAO.obtenerReclamo(id);
	}
	
	public List<Reclamo> obtenerReclamosTodos(){
		return reclamoDAO.obtenerReclamosTodos();
	}
	
	public void actualizarReclamo(Reclamo reclamo) {
		reclamoDAO.actualizarReclamo(reclamo);
	}
	
	public List<Reclamo> obtenerReclamosPorEstado(Estado estado){
		return reclamoDAO.obtenerReclamosPorEstado(estado);
	}
	
	public Reclamo obtenerReclamoDesdeBaseDeDatosNombre(String nombre) {
		return reclamoDAO.obtenerReclamoDesdeBaseDeDatosNombre(nombre);
	}


}

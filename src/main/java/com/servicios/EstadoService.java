package com.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.EstadoDAO;
import com.entidades.Estado;

@Stateless
public class EstadoService {
	
	public EstadoService() {
		
	}
	
	@EJB
	private EstadoDAO estadoDAO;
	
	public Estado obtenerEstadoId (long id) {
		return estadoDAO.obtenerEstadoId(id);
	}
	
	public List<Estado> obtenerEstados(){
		return estadoDAO.obtnerEstados();
	}

}

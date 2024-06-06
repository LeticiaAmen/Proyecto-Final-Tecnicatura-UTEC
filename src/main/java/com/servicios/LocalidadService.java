package com.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.LocalidadDAO;
import com.entidades.Localidad;

@Stateless
public class LocalidadService {
	
	@EJB
	private LocalidadDAO localidadDAO;
	
	public List<Localidad> obtenerTodasLocalidades() {
		return localidadDAO.obtenerTodasLocalidades();
	}
	
	public Localidad obtenerLocalidadPorId(long Idlocalidad) {
		return localidadDAO.obtenerLocalidadPorId(Idlocalidad);
	}
	
	public Localidad actualizarLocalidad(Localidad localidad) {
	    return localidadDAO.actualizarLocalidad(localidad);
	}

	 public List<Localidad> obtenerLocalidadesPorDepartamento(Long departamentoId) {
	        return localidadDAO.obtenerLocalidadesPorDepartamento(departamentoId);
	    }

}

package com.servicios;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.RegistroAccionDAO;
import com.entidades.RegistroAccione;

@Stateless
public class RegistroAccionService {

	@EJB
	private RegistroAccionDAO registroAccionDAO;
	
	public RegistroAccione obtenerRegistroAccion(long id ) {
		return registroAccionDAO.obtenerRegistroAccion(id);
	}

}

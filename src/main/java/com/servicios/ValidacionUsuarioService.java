package com.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.ValidacionUsuarioDAO;
import com.entidades.ValidacionUsuario;

@Stateless
public class ValidacionUsuarioService {
	
	public ValidacionUsuarioService() {
		
	}
	
	@EJB
	private ValidacionUsuarioDAO validacionDAO;
	
	public ValidacionUsuario obtenerValidacionUsuario(long id) {
		return validacionDAO.obtenerValidacionUsuario(id);
	}
	
	public List<ValidacionUsuario> obtenerValidaciones(){
		return validacionDAO.obtenerValidaciones(); 
	}

}

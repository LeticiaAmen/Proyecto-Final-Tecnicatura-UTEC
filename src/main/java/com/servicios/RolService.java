package com.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.RolDAO;
import com.entidades.Rol;

@Stateless
public class RolService {
	
	@EJB
	private RolDAO rolDAO; 
	
	
	public void crearRol(Rol rol) {
		rolDAO.crearRol(rol);
	}
	
	public Rol obtenerRol(long id) {
		return rolDAO.obtenerRol(id);
	}
	
	public List<Rol> obtenerRolTodos(){
		return rolDAO.obtenerRolTodos();
	}
	
	public void actualizarRol(Rol rol) {
		rolDAO.actualizarRol(rol);
	}
	
	public Rol obtenerRolPorNombre(String nombre) {
		return rolDAO.obtenerRolPorNombre(nombre);
	}

}

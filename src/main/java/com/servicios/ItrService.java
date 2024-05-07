package com.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.EstadoDAO;
import com.dao.ItrDAO;
import com.entidades.Estado;
import com.entidades.Itr;


@Stateless
public class ItrService {

	@EJB
	private ItrDAO itrDAO; 

	public void crearItr(Itr itr) {
		itrDAO.crearItr(itr);
	}

	public Itr obtenerItr(long id) {
		return itrDAO.obtenerItr(id);
	}

	public List<Itr> obtenerItrTodos() {
		return itrDAO.obtenerItrTodos();
	}

	public void actualizarItr(Itr itr) {
		itrDAO.actualizarItr(itr);
	}

	public List<Itr> obtenerItrsPorEstado (Estado estado){
		return itrDAO.obtenerItrsPorEstado(estado);
	}

	public Itr obtenerItrDesdeBaseDeDatosNombre(String nombre) {
		return itrDAO.obtenerItrDesdeBaseDeDatosNombre(nombre);
	}
	
}

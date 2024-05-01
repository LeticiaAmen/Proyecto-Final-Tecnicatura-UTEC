package com.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.DepartamentoDAO;
import com.entidades.Departamento;

@Stateless
public class DepartamentoService {
	
	@EJB
	private DepartamentoDAO departamentoDAO; 
	
	
	public List<Departamento> obtenerTodosDepartamento(){
		return departamentoDAO.obtenerTodosDepartamento();
	}
	
	public Departamento obtenerPorId(long idDepartamento) {
		return departamentoDAO.obtenerPorId(idDepartamento);
	}

}

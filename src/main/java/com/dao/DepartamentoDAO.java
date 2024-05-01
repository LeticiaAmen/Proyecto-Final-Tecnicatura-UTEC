package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.entidades.Departamento;

@Stateless
public class DepartamentoDAO {

	public DepartamentoDAO() {

	}

	@PersistenceContext (unitName= "PFT2024")
	private EntityManager entityManager;

	//--------------------------------Listar todos los Departamentos------------
	public List<Departamento> obtenerTodosDepartamento() {		
		TypedQuery<Departamento> query = entityManager.createQuery("SELECT d FROM Departamento d",Departamento.class); 
		return query.getResultList();
	}

	//--------------------------------Listar Departamentos por nombre ------------------------
	public List<Departamento> listarDepartamentoPorFiltro(String nombre) {
		TypedQuery<Departamento> query = entityManager.createNamedQuery("Departamento.obtenerPorNombre", Departamento.class);
		query.setParameter("nombre", "%" + nombre + "%");
		return query.getResultList();
	}

	public Departamento obtenerDepartamentoPorNombre(String nombre) {
		TypedQuery<Departamento> query = entityManager.createNamedQuery("Departamento.obtenerPorNombre", Departamento.class);
		query.setParameter("nombre", nombre);
		List<Departamento> departamentos = query.getResultList();
		if (!departamentos.isEmpty()) {
			return departamentos.get(0);
		}
		return null;
	}

	//obtener departamento id
	public Departamento obtenerPorId(long idDepartamento) {
		return entityManager.find(Departamento.class, idDepartamento);
	}



}

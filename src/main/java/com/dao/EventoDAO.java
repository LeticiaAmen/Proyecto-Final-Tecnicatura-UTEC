package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entidades.Evento;

@Stateless
public class EventoDAO {

	@PersistenceContext(unitName = "PFT2024")
	private EntityManager entityManager;


	//obtener Evento
	public Evento obtenerEvento(long id) {
		return entityManager.find(Evento.class, id);
	}

	//listar todo
	public List<Evento> obtenerEventosTodos(){
		return entityManager.createQuery("SELECT e FROM Evento e", Evento.class).getResultList();
	}



}

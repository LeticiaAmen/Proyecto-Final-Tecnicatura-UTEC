package com.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.EventoDAO;
import com.dto.EventoDTO;
import com.entidades.Evento;

@Stateless
public class EventoService {

	@EJB
	private EventoDAO eventoDAO;

	public Evento obtenerEvento(long id) {
		return eventoDAO.obtenerEvento(id);
	}

	public List<Evento> obtenerEventosTodos(){
		return eventoDAO.obtenerEventosTodos();
	}
	public List<EventoDTO> obtenerEventos() {
		return eventoDAO.obtenerEventos();
	}

	
	
}

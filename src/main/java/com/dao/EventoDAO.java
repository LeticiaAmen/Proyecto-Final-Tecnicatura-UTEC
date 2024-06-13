package com.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dto.EventoDTO;
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
	
	 public List<EventoDTO> obtenerEventos() {
	        List<Evento> eventos = entityManager.createQuery("SELECT e FROM Evento e", Evento.class).getResultList();
	        return eventos.stream().map(this::convertToDTO).collect(Collectors.toList());
	    }
	
	
	private EventoDTO convertToDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setIdEvento(evento.getIdEvento());
        dto.setCreditos(evento.getCreditos());
        dto.setFechaHoraFinal(evento.getFechaHoraFinal());
        dto.setFechaHoraInicio(evento.getFechaHoraInicio());
        dto.setInformacion(evento.getInformacion());
        dto.setModalidad(evento.getModalidad());
        dto.setTituloEvento(evento.getTituloEvento());
        dto.setSemestre(evento.getSemestre());
        dto.setUbicacion(evento.getUbicacion());
        return dto;
    }



}

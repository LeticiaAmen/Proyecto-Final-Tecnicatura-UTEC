package com.servicios.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.dto.EventoDTO;
import com.servicios.EventoService;

@Path("/eventos")
@Stateless
public class EventosResource {
	
	@EJB
	private EventoService eventoService;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventoDTO> obtenerEventos() {
        return eventoService.obtenerEventos();
    }
	
}

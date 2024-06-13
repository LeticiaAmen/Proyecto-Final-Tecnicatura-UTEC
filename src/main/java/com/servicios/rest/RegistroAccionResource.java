package com.servicios.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.entidades.RegistroAccione;
import com.servicios.RegistroAccionService;

@Path("/registroacciones")
@Stateless
public class RegistroAccionResource {
	
	@EJB
	private RegistroAccionService registroAccionService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RegistroAccione> obtenerRegistroAcciones(){
		return registroAccionService.obtenerRegistrosAcciones();
	}

}

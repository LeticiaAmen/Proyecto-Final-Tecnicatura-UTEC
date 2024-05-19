package com.servicios.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dto.ReclamoDTO;
import com.entidades.Estudiante;
import com.entidades.Evento;
import com.entidades.Reclamo;
import com.entidades.RegistroAccione;
import com.servicios.EventoService;
import com.servicios.ReclamoService;
import com.servicios.RegistroAccionService;
import com.servicios.UsuarioService;

@Path("/crear")
public class ReclamosCrearResource {
    @EJB
    private ReclamoService reclamoService;

    @EJB
    private EventoService eventoService;

    @EJB
    private UsuarioService usuarioService;

    @EJB
    private RegistroAccionService registroAccionService;

    @POST
    @Path("/reclamo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearReclamo(ReclamoDTO input) {
        try {
            Estudiante estudiante = usuarioService.obtenerEstudiante(input.getIdEstudiante());
            if (estudiante == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Estudiante no encontrado").build();
            }

            Evento evento = eventoService.obtenerEvento(input.getIdEvento());
            if (evento == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Evento no encontrado").build();
            }

            // Establecer la fecha actual en el servidor como la fecha del reclamo
            Date fechaReclamo = new Date();

            RegistroAccione registroAccion = registroAccionService.obtenerRegistroAccion(1); // Estado 'Ingresado'

            Reclamo reclamo = new Reclamo();
            reclamo.setEstudiante(estudiante);
            reclamo.setEvento(evento);
            reclamo.setFechaHoraReclamo(fechaReclamo);
            reclamo.setTituloReclamo(input.getTitulo());
            reclamo.setDetalle(input.getDetalle());
            reclamo.setRegistroAccione(registroAccion);

            reclamoService.crearReclamo(reclamo);

            return Response.ok().entity("Reclamo creado con éxito").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al crear el reclamo").build();
        }
    }
}

package com.servicios.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.entidades.Reclamo;
import com.servicios.ReclamoService;

@Path("/modificar/reclamo")
public class ReclamosModificarResource {

    @EJB
    private ReclamoService reclamoService;

    @PUT
    @Path("/{idReclamo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarReclamo(@PathParam("idReclamo") Long idReclamo, Reclamo reclamo) {
        try {
            Reclamo reclamoExistente = reclamoService.obtenerReclamo(idReclamo);
            if (reclamoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Reclamo no encontrado").build();
            }
            // Actualizar los atributos del reclamo existente
            reclamoExistente.setTituloReclamo(reclamo.getTituloReclamo());
            reclamoExistente.setDetalle(reclamo.getDetalle());
            reclamoExistente.setFechaHoraReclamo(reclamo.getFechaHoraReclamo());
            reclamoExistente.setEvento(reclamo.getEvento());

            reclamoService.actualizarReclamo(reclamoExistente);
            return Response.ok().entity(reclamoExistente).build();
        } catch (Exception e) {
            return Response.serverError().entity("Error al actualizar el reclamo: " + e.getMessage()).build();
        }
    }
}

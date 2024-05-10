package com.servicios.rest;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.entidades.Reclamo;
import com.servicios.ReclamosService;

@Path("/borrar/reclamo")
public class ReclamosBorrarResource {

    @EJB
    private ReclamosService reclamosService;

    @DELETE
    @Path("/{idReclamo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarReclamo(@PathParam("idReclamo") Long idReclamo) {
        try {
            Reclamo reclamo = reclamosService.obtenerReclamo(idReclamo);
            if (reclamo == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Reclamo no encontrado").build();
            }
            
            if ("Ingresado".equals(reclamo.getRegistroAccione().getNombre())) {
                reclamosService.eliminarReclamo(idReclamo);
                return Response.ok().entity("Reclamo eliminado correctamente").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("No se puede eliminar el reclamo a menos que esté en estado 'Ingresado'").build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("Error al eliminar el reclamo: " + e.getMessage()).build();
        }
    }
}
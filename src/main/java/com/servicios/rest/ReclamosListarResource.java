package com.servicios.rest;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dto.ReclamoDTO;
import com.entidades.Accion;
import com.entidades.Reclamo;
import com.servicios.ReclamosService;
import com.util.JwtUtil;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Path("/listar/reclamos")
@Stateless
public class ReclamosListarResource {
    @EJB
    private ReclamosService reclamosService;

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarReclamos(@Context HttpHeaders headers, @QueryParam("filtroUsuario") String filtroUsuario, @QueryParam("estadoReclamo") String estadoReclamo) {
        List<String> authHeaders = headers.getRequestHeader("Authorization");
        if (authHeaders == null || authHeaders.isEmpty() || !authHeaders.get(0).startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("No authorization token provided").build();
        }

        String token = authHeaders.get(0).substring(7); 
		/* System.out.println("Received Token: " + token); */

        try {
            DecodedJWT jwt = JwtUtil.verifyToken(token);
			/*
			 * System.out.println("Decoded JWT: " + jwt); System.out.println("Claims: " +
			 * jwt.getClaims());
			 */

            String role = jwt.getClaim("rol").asString();
            Long userId = Long.parseLong(jwt.getClaim("usuarioId").asString()); 

			/*
			 * System.out.println("Rol: " + role); System.out.println("UserID: " + userId);
			 */

            List<Reclamo> reclamos;
            if ("ANALISTA".equals(role)) {
                reclamos = reclamosService.obtenerReclamosConFiltros(filtroUsuario, estadoReclamo);
            } else if ("ESTUDIANTE".equals(role)) {
                reclamos = reclamosService.obtenerReclamosPorUsuario(userId);
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity("Access denied").build();
            }

            List<ReclamoDTO> reclamosDTO = reclamos.stream().map(this::convertToDTO).collect(Collectors.toList());
            return Response.ok(reclamosDTO).build();
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid or expired token").build();
        }
    }

    private ReclamoDTO convertToDTO(Reclamo reclamo) {
        ReclamoDTO dto = new ReclamoDTO();
        dto.setId(reclamo.getIdReclamo());
        dto.setDetalle(reclamo.getDetalle());
        dto.setTitulo(reclamo.getTituloReclamo());

        if (reclamo.getFechaHoraReclamo() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            dto.setFechaReclamo(sdf.format(reclamo.getFechaHoraReclamo()));
        } else {
            dto.setFechaReclamo(null);
        }

        if (reclamo.getEvento() != null) {
            dto.setIdEvento(reclamo.getEvento().getIdEvento());
        }
     /* Utiliza la API de Streams de Java para transformar la lista de entidades `Accion` asociadas al reclamo en una lista de detalles (String).
      	Convierte la lista en un stream, aplica la función `getDetalle` a cada elemento del stream y recoge los resultados en una nueva lista.*/
        dto.setAcciones(reclamo.getAcciones().stream().map(Accion::getDetalle).collect(Collectors.toList()));
        if (reclamo.getEstudiante() != null) {
            dto.setIdEstudiante(reclamo.getEstudiante().getIdUsuario());
        }
        return dto;
    }
}

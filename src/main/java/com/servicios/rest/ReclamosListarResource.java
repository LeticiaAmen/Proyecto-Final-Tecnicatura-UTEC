package com.servicios.rest;

import java.util.ArrayList;
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
import org.hibernate.Hibernate;

@Path("/listar")
@Stateless
public class ReclamosListarResource {
    @EJB
    private ReclamosService reclamosService;

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reclamos")
    public Response listarReclamos(@Context HttpHeaders headers, @QueryParam("filtroUsuario") String filtroUsuario, @QueryParam("estadoReclamo") String estadoReclamo) {
        List<String> authHeaders = headers.getRequestHeader("Authorization");
        if (authHeaders == null || authHeaders.isEmpty() || !authHeaders.get(0).startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("No authorization token provided").build();
        }

        String token = authHeaders.get(0).substring(7); // extraer el token JWT
        try {
            DecodedJWT jwt = JwtUtil.verifyToken(token);
            String role = jwt.getClaim("rol").asString();
            Long userId = jwt.getClaim("userId").asLong();

            List<Reclamo> reclamos;
            if ("ANALISTA".equals(role)) {
                reclamos = reclamosService.obtenerReclamosConFiltros(filtroUsuario, estadoReclamo);
            } else if ("ESTUDIANTE".equals(role)) {
                reclamos = reclamosService.obtenerReclamosPorUsuario(userId);
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity("Access denied").build();
            }

            List<ReclamoDTO> reclamosDTO = new ArrayList<>();
            for (Reclamo reclamo : reclamos) {
                entityManager.refresh(reclamo);
                ReclamoDTO dto = new ReclamoDTO();
                dto.setId(reclamo.getIdReclamo());
                dto.setDetalle(reclamo.getDetalle());
                dto.setTitulo(reclamo.getTituloReclamo());
                //dto.setFechaReclamo(reclamo.getFechaHoraReclamo());
                //dto.setIdEvento(reclamo.getEvento());
                Hibernate.initialize(reclamo.getAcciones());
                dto.setAcciones(reclamo.getAcciones().stream().map(Accion::getDetalle).collect(Collectors.toList()));
                reclamosDTO.add(dto);
            }
            return Response.ok(reclamosDTO).build();
        } catch (JWTVerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid or expired token").build();
        }
    }
}

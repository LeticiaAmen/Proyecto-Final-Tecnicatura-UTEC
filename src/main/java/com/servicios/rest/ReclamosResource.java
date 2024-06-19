package com.servicios.rest;
import java.util.Date;
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
import com.dto.ReclamoDTO;
import com.dto.RegistroAccionDTO;
import com.entidades.Accion;
import com.entidades.Estudiante;
import com.entidades.Evento;
import com.entidades.Reclamo;
import com.entidades.RegistroAccione;
import com.servicios.EventoService;
import com.servicios.ReclamoService;
import com.servicios.RegistroAccionService;
import com.servicios.ReclamosService;
import com.servicios.UsuarioService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.util.JwtUtil;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
@Path("/reclamo")
@Stateless
public class ReclamosResource {
   @EJB
   private ReclamoService reclamoService;
   @EJB
   private EventoService eventoService;
   @EJB
   private UsuarioService usuarioService;
   @EJB
   private RegistroAccionService registroAccionService;
   @EJB
   private ReclamosService reclamosService;
   @PersistenceContext
   private EntityManager entityManager;
   @POST
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
   @PUT
   @Path("/{idReclamo}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response modificarReclamo(@PathParam("idReclamo") Long idReclamo, ReclamoDTO reclamoData) {
       try {
           Reclamo reclamoExistente = reclamoService.obtenerReclamo(idReclamo);
           if (reclamoExistente == null) {
               return Response.status(Response.Status.NOT_FOUND).entity("Reclamo no encontrado").build();
           }
           reclamoExistente.setTituloReclamo(reclamoData.getTitulo());
           reclamoExistente.setDetalle(reclamoData.getDetalle());
           reclamoExistente.setFechaHoraReclamo(new Date());
//           reclamoExistente.setEvento(reclamoData.getIdEvento());
           reclamoService.actualizarReclamo(reclamoExistente);
           return Response.ok().entity("Reclamo actualizado con éxito").build();
       } catch (Exception e) {
           return Response.serverError().entity("Error al actualizar el reclamo: " + e.getMessage()).build();
       }
   }
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Response listarReclamos(@Context HttpHeaders headers, @QueryParam("filtroUsuario") String filtroUsuario, @QueryParam("estadoReclamo") String estadoReclamo) {
       List<String> authHeaders = headers.getRequestHeader("Authorization");
       if (authHeaders == null || authHeaders.isEmpty() || !authHeaders.get(0).startsWith("Bearer ")) {
           return Response.status(Response.Status.UNAUTHORIZED).entity("No authorization token provided").build();
       }
       String token = authHeaders.get(0).substring(7);
       try {
           DecodedJWT jwt = JwtUtil.verifyToken(token);
           String role = jwt.getClaim("role").asString();//acá decía "rol"          
           Long userId = Long.parseLong(jwt.getClaim("usuarioId").asString());
           
        // Agregar logs para depuración
           System.out.println("Token verificado: " + token);
           System.out.println("Role: " + role);
           System.out.println("User ID: " + userId);
           
           List<Reclamo> reclamos;
           if ("ANALISTA".equals(role)) {
               reclamos = reclamosService.obtenerReclamosConFiltros(filtroUsuario, estadoReclamo);
           } else if ("ESTUDIANTE".equals(role)) {
               reclamos = reclamosService.obtenerReclamosPorUsuario(userId);
           } else {
        	   System.out.println("Access denied: Role not allowed");
               return Response.status(Response.Status.FORBIDDEN).entity("Access denied").build();
           }
           List<ReclamoDTO> reclamosDTO = reclamos.stream().map(this::convertToDTO).collect(Collectors.toList());
           return Response.ok(reclamosDTO).build();
       } catch (JWTVerificationException e) {
           e.printStackTrace();
           return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid or expired token").build();
       }
   }
   private ReclamoDTO convertToDTO(Reclamo reclamo) {
       ReclamoDTO dto = new ReclamoDTO();
       dto.setId(reclamo.getIdReclamo());
       dto.setDetalle(reclamo.getDetalle());
       dto.setTitulo(reclamo.getTituloReclamo());
       dto.setNombreEstudiante(reclamo.getEstudiante().getNombres());
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
       dto.setAcciones(reclamo.getAcciones().stream().map(Accion::getDetalle).collect(Collectors.toList()));
       if (reclamo.getEstudiante() != null) {
           dto.setIdEstudiante(reclamo.getEstudiante().getIdUsuario());
       }
       if (reclamo.getRegistroAccione() != null) {
           RegistroAccionDTO registroAccionDTO = new RegistroAccionDTO();
           registroAccionDTO.setId(reclamo.getRegistroAccione().getIdRegistroAccion());
           registroAccionDTO.setNombre(reclamo.getRegistroAccione().getNombre());
           dto.setRegistroAccion(registroAccionDTO);
       }
       return dto;
   }
}


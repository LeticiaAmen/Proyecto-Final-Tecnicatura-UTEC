package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.entidades.Estado;
import com.entidades.Justificacion;
import com.entidades.Reclamo;

@Stateless
public class JustificacionDAO {
	
	 @PersistenceContext(unitName = "PFT2024")
	    private EntityManager entityManager;
	 
	 public Justificacion crearJustificacion(Justificacion justificacion) {
		 try {
			 entityManager.persist(justificacion);
			 entityManager.flush();
			 return justificacion;
		 }catch (Exception e) {
			 System.out.println(e.getMessage());
			 return null; 
		 }
	 }
	 
	 public void actualizarJustificacion(Justificacion justificacion) {
		 entityManager.merge(justificacion);
	 }
	 
	 public Justificacion obtenerJustificacion(long id) {
		 return entityManager.find(Justificacion.class, id);
	 }
	 
	 public List<Justificacion> obtenerJustificacionesTodas(){
		 return entityManager.createQuery("SELECT j FROM Justificacion j", Justificacion.class).getResultList();
	 }
	
	 public Justificacion obtenerJustificacionDesdeBaseDeDatosNombre(String nombre) {
	        try {
	            Query query = entityManager.createQuery("SELECT j FROM Justificacion j WHERE j.nombre = :nombre", Justificacion.class);
	            query.setParameter("nombre", nombre);
	            List<Justificacion> justificaciones = query.getResultList();
	            if (!justificaciones.isEmpty()) {
	                return justificaciones.get(0);
	            } else {
	                System.out.println("No user found with nombre: " + nombre);
	            }
	        } catch (Exception e) {
	            System.err.println("Error retrieving user from database with username: " + nombre);
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	 public List<Justificacion> obtenerJustificacionesPorEstado(Estado estado){
	        try {
	            Query query = entityManager.createQuery("SELECT j FROM Justificacion j WHERE j.estado = :estado", Justificacion.class);
	            query.setParameter("estado", estado);
	            return query.getResultList();
	        } catch (Exception e) {
	            System.err.println("Error retrieving Reclamos with estado: " + estado);
	            e.printStackTrace();
	            return null;
	        }
	    }
	 
	 public List<Justificacion> obtenerJustificacionesPorEstudianteId(long estudianteId) {
	        TypedQuery<Justificacion> query = entityManager.createQuery(
	            "SELECT j FROM Justificacion j WHERE j.estudiante.id = :estudianteId", Justificacion.class);
	        query.setParameter("estudianteId", estudianteId);
	        return query.getResultList();
	    }
	 
	 public List<Justificacion> obtenerJustificacionesPorEstudiante(long idUsuarioEstudiante) {
	        return entityManager.createQuery("SELECT j FROM Justificacion j WHERE j.estudiante.idUsuario = :idUsuarioEstudiante", Justificacion.class)
	                 .setParameter("idUsuarioEstudiante", idUsuarioEstudiante)
	                 .getResultList();
	    }
	 
	 public List<Justificacion> obtenerJustificacionesConFiltros(String filtroUsuario, String estadoJustificacion) {
		 String jpql = "SELECT j FROM Justificacion j LEFT JOIN j.registroAccione ra WHERE " +
                 "(:filtroUsuario IS NULL OR LOWER(j.estudiante.nombreUsuario) LIKE :filtroUsuario) AND " +
                 "(:estadoJustificacion IS NULL OR ra.idRegistroAccion = :estadoJustificacion)";
		 
		 TypedQuery<Justificacion> query = entityManager.createQuery(jpql, Justificacion.class);
	        query.setParameter("filtroUsuario", (filtroUsuario == null || filtroUsuario.trim().isEmpty()) ? null : '%' + filtroUsuario.trim().toLowerCase() + '%');
	        query.setParameter("estadoJustificacion", (estadoJustificacion == null || estadoJustificacion.isEmpty()) ? null : Long.parseLong(estadoJustificacion)); 

	        return query.getResultList();
	
	 }
	
	 public List<Justificacion> obtenerJustificacionesPorUsuarioConFiltros(long idUsuario, String filtroUsuario, String estadoJustificacion) {
		 String jpql = "SELECT j FROM Justificacion j LEFT JOIN j.registroAccione ra WHERE " +
                 "j.estudiante.idUsuario = :idUsuario AND " +
                 "(:filtroUsuario IS NULL OR LOWER(j.estudiante.nombreUsuario) LIKE :filtroUsuario) AND " +
                 "(:estadoJustificacion IS NULL OR ra.idRegistroAccion = :estadoJustificacion)";
   TypedQuery<Justificacion> query = entityManager.createQuery(jpql, Justificacion.class);
   query.setParameter("idUsuario", idUsuario);
   query.setParameter("filtroUsuario", (filtroUsuario == null || filtroUsuario.trim().isEmpty()) ? null : '%' + filtroUsuario.trim().toLowerCase() + '%');
   query.setParameter("estadoJustificacion", (estadoJustificacion == null || estadoJustificacion.isEmpty()) ? null : Long.parseLong(estadoJustificacion)); 

   return query.getResultList();
	 }
	 

}

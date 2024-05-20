package com.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import com.entidades.Estado;
import com.entidades.Reclamo;

@Stateless
public class ReclamoDAO {
    
    @PersistenceContext(unitName = "PFT2024")
    private EntityManager entityManager;

    public Reclamo crearReclamo(Reclamo reclamo) {
        try {
            entityManager.persist(reclamo);
            entityManager.flush();
            return reclamo;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void actualizarReclamo(Reclamo reclamo) {
        entityManager.merge(reclamo);
    }
    
    public Reclamo obtenerReclamo(long id) {
        return entityManager.find(Reclamo.class, id);
    }
    
    public Reclamo obtenerReclamoConAcciones(long idReclamo) {
        try {
            return entityManager.createQuery(
                "SELECT r FROM Reclamo r LEFT JOIN FETCH r.acciones WHERE r.id = :idReclamo", Reclamo.class)
                .setParameter("idReclamo", idReclamo)
                .getSingleResult();
        } catch (Exception e) {
            System.out.println("Error during fetching Reclamo with actions: " + e.getMessage());
            return null;
        }
    }

    public List<Reclamo> obtenerReclamosTodos(){
        return entityManager.createQuery("SELECT r FROM Reclamo r", Reclamo.class).getResultList();
    }

    public Reclamo obtenerReclamoDesdeBaseDeDatosNombre(String nombre) {
        try {
            Query query = entityManager.createQuery("SELECT r FROM Reclamo r WHERE r.nombre = :nombre", Reclamo.class);
            query.setParameter("nombre", nombre);
            List<Reclamo> reclamos = query.getResultList();
            if (!reclamos.isEmpty()) {
                return reclamos.get(0);
            } else {
                System.out.println("No user found with nombre: " + nombre);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving user from database with username: " + nombre);
            e.printStackTrace();
        }
        return null;
    }

    public List<Reclamo> obtenerReclamosPorEstado(Estado estado){
        try {
            Query query = entityManager.createQuery("SELECT r FROM Reclamo r WHERE r.estado = :estado", Reclamo.class);
            query.setParameter("estado", estado);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error retrieving Reclamos with estado: " + estado);
            e.printStackTrace();
            return null;
        }
    }

    public void eliminarReclamo(long id) {
        Reclamo reclamo = entityManager.find(Reclamo.class, id);
        if (reclamo != null) {
            entityManager.remove(reclamo);
        }
    }

    public List<Reclamo> obtenerReclamosPorEstudianteId(Long estudianteId) {
        TypedQuery<Reclamo> query = entityManager.createQuery(
            "SELECT r FROM Reclamo r WHERE r.estudiante.id = :estudianteId", Reclamo.class);
        query.setParameter("estudianteId", estudianteId);
        return query.getResultList();
    }
    
    public List<Reclamo> obtenerReclamosPorEstudiante(Long idUsuarioEstudiante) {
        return entityManager.createQuery("SELECT r FROM Reclamo r WHERE r.estudiante.idUsuario = :idUsuarioEstudiante", Reclamo.class)
                 .setParameter("idUsuarioEstudiante", idUsuarioEstudiante)
                 .getResultList();
    }
    
    /* Consulta genérica en SQL:
	SELECT *
	FROM reclamos r
	WHERE r.id_usuario_estudiante = ?
	AND EXTRACT(YEAR FROM r.fecha_hora_reclamo) = ?
	AND EXTRACT(MONTH FROM r.fecha_hora_reclamo) = ?
	ORDER BY fecha_hora_reclamo DESC;
	
	Ejemplo de consulta en SQL con un estudiante con id 21 para el mes 5 y el año 2024
	SELECT *
	FROM reclamos r
	WHERE r.id_usuario_estudiante = 21
	AND EXTRACT(YEAR FROM r.fecha_hora_reclamo) = 2024
	AND EXTRACT(MONTH FROM r.fecha_hora_reclamo) = 5
	ORDER BY fecha_hora_reclamo DESC;
    */
 // Método para obtener reclamos de un estudiante filtrado por año y mes, ordenados por fecha
    public List<Reclamo> obtenerReclamosPorEstudianteYFecha(Long estudianteId, int anio, int mes) {
        TypedQuery<Reclamo> query = entityManager.createQuery(
            "SELECT r FROM Reclamo r WHERE r.estudiante.id = :estudianteId AND FUNCTION('YEAR', r.fechaHoraReclamo) = :anio AND FUNCTION('MONTH', r.fechaHoraReclamo) = :mes ORDER BY r.fechaHoraReclamo DESC", 
            Reclamo.class);
        query.setParameter("estudianteId", estudianteId);
        query.setParameter("anio", anio);
        query.setParameter("mes", mes);
        return query.getResultList();
    }

}

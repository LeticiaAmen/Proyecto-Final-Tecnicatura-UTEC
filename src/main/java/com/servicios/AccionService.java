package com.servicios;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.dao.AccionDAO;
import com.entidades.Accion;

@Stateless
public class AccionService {

    @EJB
    private AccionDAO accionDAO;

    @PersistenceContext
    private EntityManager entityManager;

    public void guardarAccion(Accion accion) {
        entityManager.persist(accion);
    }

    public Accion obtenerAccionExistente(long idReclamo, long idRegistroAccion) {
        TypedQuery<Accion> query = entityManager.createQuery(
            "SELECT a FROM Accion a WHERE a.reclamo.id = :idReclamo AND a.registroAccion.id = :idRegistroAccion", 
            Accion.class
        );
        query.setParameter("idReclamo", idReclamo);
        query.setParameter("idRegistroAccion", idRegistroAccion);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // Si no se encuentra ninguna acción, devolver null
        }
    }
}

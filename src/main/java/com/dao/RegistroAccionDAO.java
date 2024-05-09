package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entidades.RegistroAccione;

@Stateless
public class RegistroAccionDAO {

    @PersistenceContext(unitName = "PFT2024")
    private EntityManager entityManager;

    // Obtener RegistroAccion por ID
    public RegistroAccione obtenerRegistroAccion(long id) {
        return entityManager.find(RegistroAccione.class, id);
    }

    // Obtener todos los Registros de Acciones
    public List<RegistroAccione> obtenerRegistrosAcciones() {
        Query query = entityManager.createQuery("SELECT r FROM RegistroAccione r", RegistroAccione.class);
        return query.getResultList();
    }
}

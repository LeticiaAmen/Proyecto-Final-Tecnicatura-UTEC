package com.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entidades.Accion;

@Stateless
public class AccionDAO {

    @PersistenceContext(unitName = "PFT2024")
    private EntityManager entityManager;

    public void guardarAccion(Accion accion) {
        entityManager.persist(accion);
    }
}

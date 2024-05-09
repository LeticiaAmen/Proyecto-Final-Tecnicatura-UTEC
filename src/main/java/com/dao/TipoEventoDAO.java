package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.entidades.TipoEvento;

@Stateless
public class TipoEventoDAO {

    @PersistenceContext(unitName = "PFT2024")
    private EntityManager entityManager;

    // Obtener TipoEvento por ID
    public TipoEvento obtenerTipoEvento(long id) {
        return entityManager.find(TipoEvento.class, id);
    }

    // Listar todos los TiposEvento
    public List<TipoEvento> obtenerTiposEventoTodos() {
        return entityManager.createQuery("SELECT t FROM TipoEvento t", TipoEvento.class).getResultList();
    }
}

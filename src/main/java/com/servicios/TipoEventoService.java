package com.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.TipoEventoDAO;
import com.entidades.TipoEvento;

@Stateless
public class TipoEventoService {

    @EJB
    private TipoEventoDAO tipoEventoDAO;

    public TipoEvento obtenerTipoEvento(long id) {
        return tipoEventoDAO.obtenerTipoEvento(id);
    }

    public List<TipoEvento> obtenerTiposEventoTodos() {
        return tipoEventoDAO.obtenerTiposEventoTodos();
    }
}

package com.servicios;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.AccionDAO;
import com.entidades.Accion;

@Stateless
public class AccionService {

    @EJB
    private AccionDAO accionDAO;

    public void guardarAccion(Accion accion) {
        accionDAO.guardarAccion(accion);
    }
}

package com.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.RegistroAccionDAO;
import com.entidades.RegistroAccione;

@Stateless
public class RegistroAccionService {

    @EJB
    private RegistroAccionDAO registroAccionDAO;
    
    // Obtener RegistroAccion por ID
    public RegistroAccione obtenerRegistroAccion(long id ) {
        return registroAccionDAO.obtenerRegistroAccion(id);
    }

    // Obtener todos los Registros de Acciones
    public List<RegistroAccione> obtenerRegistrosAcciones() {
        return registroAccionDAO.obtenerRegistrosAcciones();
 
    }
    
}

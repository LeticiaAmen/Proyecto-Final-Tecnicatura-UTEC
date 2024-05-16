package com.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.RegistroAccionDAO;
import com.entidades.Itr;
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
    
    //Obtener por nombre
    public RegistroAccione obtenerNombre(String nombre) {
		return registroAccionDAO.obtenerNombre(nombre);
    }
    
    //Crear
    public void crear(RegistroAccione nuevo) {
		registroAccionDAO.crear(nuevo);
	}
    
    //Actualizar
    public void actualizar(RegistroAccione registro) {
        registroAccionDAO.actualizar(registro);
    }

    
    public boolean existeNombre(String nombre) {
        return registroAccionDAO.obtenerNombre(nombre) != null;
    }
    
  //Obtener estados activos
  	public List<RegistroAccione> obtenerEstadosActivos() {
  	    return registroAccionDAO.obtenerEstadosActivos();
  	}
}

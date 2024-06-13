package com.servicios;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.RegistroAccionDAO;
import com.dto.RegistroAccionDTO;
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
  	
  	private RegistroAccionDTO convertToDTO(RegistroAccione registroAccione) {
        RegistroAccionDTO dto = new RegistroAccionDTO();
        dto.setId(registroAccione.getIdRegistroAccion());
        dto.setNombre(registroAccione.getNombre());
        return dto;
    }
  	
    public List<RegistroAccionDTO> obtenerRegistroAcciones() {
        List<RegistroAccione> registroAcciones = registroAccionDAO.obtenerRegistrosAcciones();
        return registroAcciones.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
  	
}

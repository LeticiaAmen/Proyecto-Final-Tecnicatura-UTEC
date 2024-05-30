package com.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.JustificacionDAO;
import com.entidades.Estado;
import com.entidades.Justificacion;

@Stateless
public class JustificacionService {
	
	@EJB
	private JustificacionDAO justificacionDAO;
	
	public void crearJustificacion(Justificacion justificacion) {
		justificacionDAO.crearJustificacion(justificacion);
	}
	
	public void actualizarJustificacion(Justificacion justificacion) {
		justificacionDAO.actualizarJustificacion(justificacion);
	}
	
	public Justificacion obtenerJustificacion(long id) {
		return justificacionDAO.obtenerJustificacion(id);
	}
	
	public List<Justificacion> obtenerJustificacionesTodas(){
		return justificacionDAO.obtenerJustificacionesTodas();
	}
	
	public Justificacion obtenerJustificacionDesdeBaseDeDatosNombre(String nombre) {
		return justificacionDAO.obtenerJustificacionDesdeBaseDeDatosNombre(nombre);
	}
	
	public List<Justificacion> obtenerJustificacionesPorEstado(Estado estado){
		return justificacionDAO.obtenerJustificacionesPorEstado(estado);
	}
	
	public List<Justificacion> obtenerJustificacionesPorEstudianteId(long estudianteId) {
		return justificacionDAO.obtenerJustificacionesPorEstudianteId(estudianteId);
	}
	
	public List<Justificacion> obtenerJustificacionesPorEstudiante(long idUsuarioEstudiante) {
		return justificacionDAO.obtenerJustificacionesPorEstudiante(idUsuarioEstudiante);
	}
	
	

}

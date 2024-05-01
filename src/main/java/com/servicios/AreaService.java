package com.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.AreaDAO;
import com.entidades.Area;

@Stateless
public class AreaService {
	
	@EJB
	private AreaDAO areaDAO;
	
	public void crearArea(Area area) {
		areaDAO.crearArea(area);
	}
	
	public Area obtenerArea(long id) {
		return areaDAO.obtenerArea(id);
	}
	
	public List<Area> obtenerAreaTodas(){
		return areaDAO.obtenerAreasTodas();
	}
	
	public void actualizarArea(Area area) {
		areaDAO.actualizarArea(area);
	}
	
	public Area obtenerAreaPorNombre(String nombre){
		return areaDAO.obtenerAreaPorNombre(nombre);
	}

}

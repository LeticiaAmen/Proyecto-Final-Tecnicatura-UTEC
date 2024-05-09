package com.dto;

import java.util.List;

public class ReclamoDTO {
    private Long id;
    private String detalle;  
    private List<String> acciones; 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public List<String> getAcciones() {
		return acciones;
	}
	public void setAcciones(List<String> acciones) {
		this.acciones = acciones;
	}
    
}

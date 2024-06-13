package com.dto;

import java.util.List;

public class ReclamoDTO {
	private Long id;
	private String detalle;  
	private List<String> acciones; 
	private Long idEstudiante;
	private String titulo;
	private String fechaReclamo;
	private Long idEvento;
	private String registroAccion; // Nueva propiedad para el estado del reclamo

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
	public Long getIdEstudiante() {
		return idEstudiante;
	}
	public void setIdEstudiante(Long idEstudiante) {
		this.idEstudiante = idEstudiante;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getFechaReclamo() {
		return fechaReclamo;
	}
	public void setFechaReclamo(String fechaReclamo) {
		this.fechaReclamo = fechaReclamo;
	}
	public Long getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}
	public String getRegistroAccion() {
		return registroAccion;
	}
	public void setRegistroAccion(String registroAccion) {
		this.registroAccion = registroAccion;
	}
}

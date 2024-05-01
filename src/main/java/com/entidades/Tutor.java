package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TUTORES database table.
 * 
 */
@Entity
@Table(name="TUTORES")
@NamedQuery(name="Tutor.findAll", query="SELECT t FROM Tutor t")
public class Tutor extends Usuario {

	@Column(name="MAIL_INSTITUCIONAL")
	private String mailInstitucional;

	//bi-directional many-to-one association to Area
	@ManyToOne
	@JoinColumn(name="ID_AREA")
	private Area area;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="ID_ROL")
	private Rol rol;

	//bi-directional many-to-many association to Evento
	@ManyToMany
	@JoinTable(
		name="TUTORES_EVENTOS"
		, joinColumns={
			@JoinColumn(name="ID_USUARIO_TUTOR")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ID_EVENTO")
			}
		)
	private List<Evento> eventos;

	public Tutor() {
	}

	public String getMailInstitucional() {
		return this.mailInstitucional;
	}

	public void setMailInstitucional(String mailInstitucional) {
		this.mailInstitucional = mailInstitucional;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

}
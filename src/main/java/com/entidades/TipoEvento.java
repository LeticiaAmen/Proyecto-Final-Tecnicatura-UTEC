package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TIPO_EVENTOS database table.
 * 
 */
@Entity
@Table(name="TIPO_EVENTOS")
@NamedQuery(name="TipoEvento.findAll", query="SELECT t FROM TipoEvento t")
public class TipoEvento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_TIPO_EVENTO")
	private long idTipoEvento;

	private String nombre;

	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="tipoEvento")
	private List<Evento> eventos;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;

	public TipoEvento() {
	}

	public long getIdTipoEvento() {
		return this.idTipoEvento;
	}

	public void setIdTipoEvento(long idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento addEvento(Evento evento) {
		getEventos().add(evento);
		evento.setTipoEvento(this);

		return evento;
	}

	public Evento removeEvento(Evento evento) {
		getEventos().remove(evento);
		evento.setTipoEvento(null);

		return evento;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
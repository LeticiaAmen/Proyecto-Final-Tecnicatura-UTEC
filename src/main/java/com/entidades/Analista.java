package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ANALISTAS database table.
 * 
 */
@Entity
@Table(name="ANALISTAS")
@NamedQuery(name="Analista.findAll", query="SELECT a FROM Analista a")
public class Analista extends Usuario {


	@Column(name="MAIL_INSTITUCIONAL")
	private String mailInstitucional;

	//bi-directional many-to-one association to Accion
	@OneToMany(mappedBy="analista")
	private List<Accion> acciones;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;



	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="analista")
	private List<Evento> eventos;

	public Analista() {
	}


	@Override
    public String getMailInstitucional() {
        return this.mailInstitucional;
    }

    @Override
    public void setMailInstitucional(String mailInstitucional) {
        this.mailInstitucional = mailInstitucional;
    }
    

	public List<Accion> getAcciones() {
		return this.acciones;
	}

	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}

	public Accion addAccione(Accion accione) {
		getAcciones().add(accione);
		accione.setAnalista(this);

		return accione;
	}

	public Accion removeAccione(Accion accione) {
		getAcciones().remove(accione);
		accione.setAnalista(null);

		return accione;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}


	public List<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento addEvento(Evento evento) {
		getEventos().add(evento);
		evento.setAnalista(this);

		return evento;
	}

	public Evento removeEvento(Evento evento) {
		getEventos().remove(evento);
		evento.setAnalista(null);

		return evento;
	}

}
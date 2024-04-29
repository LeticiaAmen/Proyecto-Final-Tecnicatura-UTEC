package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CONSTANCIAS database table.
 * 
 */
@Entity
@Table(name="CONSTANCIAS")
@NamedQuery(name="Constancia.findAll", query="SELECT c FROM Constancia c")
public class Constancia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_CONSTANCIA")
	private long idConstancia;

	private String detalle;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_HORA")
	private Date fechaHora;

	//bi-directional many-to-one association to Accion
	@OneToMany(mappedBy="constancia")
	private List<Accion> acciones;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;

	//bi-directional many-to-one association to Estudiante
	@ManyToOne
	@JoinColumn(name="ID_USUARIO_ESTUDIANTE")
	private Estudiante estudiante;

	//bi-directional many-to-one association to Evento
	@ManyToOne
	@JoinColumn(name="ID_EVENTO")
	private Evento evento;

	//bi-directional many-to-one association to TipoConstancia
	@ManyToOne
	@JoinColumn(name="ID_TIPO_CONSTANCIA")
	private TipoConstancia tipoConstancia;

	public Constancia() {
	}

	public long getIdConstancia() {
		return this.idConstancia;
	}

	public void setIdConstancia(long idConstancia) {
		this.idConstancia = idConstancia;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Date getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public List<Accion> getAcciones() {
		return this.acciones;
	}

	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}

	public Accion addAccione(Accion accione) {
		getAcciones().add(accione);
		accione.setConstancia(this);

		return accione;
	}

	public Accion removeAccione(Accion accione) {
		getAcciones().remove(accione);
		accione.setConstancia(null);

		return accione;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Estudiante getEstudiante() {
		return this.estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public TipoConstancia getTipoConstancia() {
		return this.tipoConstancia;
	}

	public void setTipoConstancia(TipoConstancia tipoConstancia) {
		this.tipoConstancia = tipoConstancia;
	}

}
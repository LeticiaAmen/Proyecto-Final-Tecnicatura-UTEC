package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the RECLAMOS database table.
 * 
 */
@Entity
@Table(name="RECLAMOS")
@NamedQuery(name="Reclamo.findAll", query="SELECT r FROM Reclamo r")
public class Reclamo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_reclamo", sequenceName = "seq_reclamo", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_reclamo")
	@Column(name="ID_RECLAMO")
	private long idReclamo;

	@Column(name="DETALLE")
	private String detalle;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_HORA_RECLAMO")
	private Date fechaHoraReclamo;

	@Column(name="TITULO_RECLAMO")
	private String tituloReclamo;
	
	@ManyToOne
	@JoinColumn(name="ID_REGISTRO_ACCION")
	private RegistroAccione registroAccione;

	//bi-directional many-to-one association to Accion
	@OneToMany(mappedBy="reclamo")
	private List<Accion> acciones;

	//bi-directional many-to-one association to Estudiante
	@ManyToOne
	@JoinColumn(name="ID_USUARIO_ESTUDIANTE")
	private Estudiante estudiante;

	//bi-directional many-to-one association to Evento
	@ManyToOne
	@JoinColumn(name="ID_EVENTO")
	private Evento evento;

	public Reclamo() {
	}

	public long getIdReclamo() {
		return this.idReclamo;
	}

	public void setIdReclamo(long idReclamo) {
		this.idReclamo = idReclamo;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Date getFechaHoraReclamo() {
		return this.fechaHoraReclamo;
	}

	public void setFechaHoraReclamo(Date fechaHoraReclamo) {
		this.fechaHoraReclamo = fechaHoraReclamo;
	}

	public String getTituloReclamo() {
		return this.tituloReclamo;
	}

	public void setTituloReclamo(String tituloReclamo) {
		this.tituloReclamo = tituloReclamo;
	}

	public RegistroAccione getRegistroAccione() {
		return registroAccione;
	}

	public void setRegistroAccione(RegistroAccione registroAccione) {
		this.registroAccione = registroAccione;
	}

	public List<Accion> getAcciones() {
		return this.acciones;
	}

	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}

	public Accion addAccione(Accion accione) {
		getAcciones().add(accione);
		accione.setReclamo(this);

		return accione;
	}

	public Accion removeAccione(Accion accione) {
		getAcciones().remove(accione);
		accione.setReclamo(null);

		return accione;
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

}
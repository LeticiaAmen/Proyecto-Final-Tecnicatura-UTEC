package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ACCIONES database table.
 * 
 */
@Entity
@Table(name="ACCIONES")
@NamedQuery(name="Accion.findAll", query="SELECT a FROM Accion a")
public class Accion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ACCION")
	private long idAccion;

	private String detalle;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_HORA")
	private Date fechaHora;

	//bi-directional many-to-one association to Analista
	@ManyToOne
	@JoinColumn(name="ID_USUARIO_ANALISTA")
	private Analista analista;

	//bi-directional many-to-one association to Constancia
	@ManyToOne
	@JoinColumn(name="ID_CONSTANCIA")
	private Constancia constancia;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;

	//bi-directional many-to-one association to Justificacion
	@ManyToOne
	@JoinColumn(name="ID_JUSTIFICACION")
	private Justificacion justificacion;

	//bi-directional many-to-one association to Reclamo
	@ManyToOne
	@JoinColumn(name="ID_RECLAMO")
	private Reclamo reclamo;

	//bi-directional many-to-one association to RegistroAccione
	@ManyToOne
	@JoinColumn(name="ID_REGISTRO_ACCION")
	private RegistroAccione registroAccion;

	public Accion() {
	}

	public long getIdAccion() {
		return this.idAccion;
	}

	public void setIdAccion(long idAccion) {
		this.idAccion = idAccion;
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

	public Analista getAnalista() {
		return this.analista;
	}

	public void setAnalista(Analista analista) {
		this.analista = analista;
	}

	public Constancia getConstancia() {
		return this.constancia;
	}

	public void setConstancia(Constancia constancia) {
		this.constancia = constancia;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Justificacion getJustificacion() {
		return this.justificacion;
	}

	public void setJustificacion(Justificacion justificacion) {
		this.justificacion = justificacion;
	}

	public Reclamo getReclamo() {
		return this.reclamo;
	}

	public void setReclamo(Reclamo reclamo) {
		this.reclamo = reclamo;
	}

	public RegistroAccione getRegistroAccion() {
		return this.registroAccion;
	}

	public void setRegistroAccion(RegistroAccione registroAccion) {
		this.registroAccion = registroAccion;
	}

}
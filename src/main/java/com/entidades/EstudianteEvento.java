package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name="ESTUDIANTES_EVENTOS")
@NamedQuery(name="EstudianteEvento.findAll", query="SELECT e FROM EstudianteEvento e")
public class EstudianteEvento implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal calificacion;

	private BigDecimal inasistencia;

	//bi-directional many-to-one association to Estudiante
	@Id
	@ManyToOne
	@JoinColumn(name="ID_USUARIO_ESTUDIANTE")
	private Estudiante estudiante;

	//bi-directional many-to-one association to Evento
	@ManyToOne
	@JoinColumn(name="ID_EVENTO")
	private Evento evento;

	public EstudianteEvento() {
	}

	public BigDecimal getCalificacion() {
		return this.calificacion;
	}

	public void setCalificacion(BigDecimal calificacion) {
		this.calificacion = calificacion;
	}

	public BigDecimal getInasistencia() {
		return this.inasistencia;
	}

	public void setInasistencia(BigDecimal inasistencia) {
		this.inasistencia = inasistencia;
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
package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the ESTUDIANTES database table.
 * 
 */
@Entity
@Table(name="ESTUDIANTES")
@NamedQuery(name="Estudiante.findAll", query="SELECT e FROM Estudiante e")
public class Estudiante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_USUARIO")
	private long idUsuario;

	@Column(name="MAIL_INSTITUCIONAL")
	private String mailInstitucional;

	private BigDecimal semestre;

	//bi-directional many-to-one association to Constancia
	@OneToMany(mappedBy="estudiante")
	private List<Constancia> constancias;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;

	//bi-directional many-to-one association to Generacion
	@ManyToOne
	@JoinColumn(name="ID_GENERACION")
	private Generacion generacion;

	//bi-directional one-to-one association to Usuario
	@OneToOne
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;

	//bi-directional many-to-one association to EstudianteEvento
	@OneToMany(mappedBy="estudiante")
	private List<EstudianteEvento> estudiantesEventos;

	//bi-directional many-to-one association to Justificacion
	@OneToMany(mappedBy="estudiante")
	private List<Justificacion> justificaciones;

	//bi-directional many-to-one association to Reclamo
	@OneToMany(mappedBy="estudiante")
	private List<Reclamo> reclamos;

	public Estudiante() {
	}

	public long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getMailInstitucional() {
		return this.mailInstitucional;
	}

	public void setMailInstitucional(String mailInstitucional) {
		this.mailInstitucional = mailInstitucional;
	}

	public BigDecimal getSemestre() {
		return this.semestre;
	}

	public void setSemestre(BigDecimal semestre) {
		this.semestre = semestre;
	}

	public List<Constancia> getConstancias() {
		return this.constancias;
	}

	public void setConstancias(List<Constancia> constancias) {
		this.constancias = constancias;
	}

	public Constancia addConstancia(Constancia constancia) {
		getConstancias().add(constancia);
		constancia.setEstudiante(this);

		return constancia;
	}

	public Constancia removeConstancia(Constancia constancia) {
		getConstancias().remove(constancia);
		constancia.setEstudiante(null);

		return constancia;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Generacion getGeneracion() {
		return this.generacion;
	}

	public void setGeneracion(Generacion generacion) {
		this.generacion = generacion;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<EstudianteEvento> getEstudiantesEventos() {
		return this.estudiantesEventos;
	}

	public void setEstudiantesEventos(List<EstudianteEvento> estudiantesEventos) {
		this.estudiantesEventos = estudiantesEventos;
	}

	public EstudianteEvento addEstudiantesEvento(EstudianteEvento estudiantesEvento) {
		getEstudiantesEventos().add(estudiantesEvento);
		estudiantesEvento.setEstudiante(this);

		return estudiantesEvento;
	}

	public EstudianteEvento removeEstudiantesEvento(EstudianteEvento estudiantesEvento) {
		getEstudiantesEventos().remove(estudiantesEvento);
		estudiantesEvento.setEstudiante(null);

		return estudiantesEvento;
	}

	public List<Justificacion> getJustificaciones() {
		return this.justificaciones;
	}

	public void setJustificaciones(List<Justificacion> justificaciones) {
		this.justificaciones = justificaciones;
	}

	public Justificacion addJustificacione(Justificacion justificacione) {
		getJustificaciones().add(justificacione);
		justificacione.setEstudiante(this);

		return justificacione;
	}

	public Justificacion removeJustificacione(Justificacion justificacione) {
		getJustificaciones().remove(justificacione);
		justificacione.setEstudiante(null);

		return justificacione;
	}

	public List<Reclamo> getReclamos() {
		return this.reclamos;
	}

	public void setReclamos(List<Reclamo> reclamos) {
		this.reclamos = reclamos;
	}

	public Reclamo addReclamo(Reclamo reclamo) {
		getReclamos().add(reclamo);
		reclamo.setEstudiante(this);

		return reclamo;
	}

	public Reclamo removeReclamo(Reclamo reclamo) {
		getReclamos().remove(reclamo);
		reclamo.setEstudiante(null);

		return reclamo;
	}

}
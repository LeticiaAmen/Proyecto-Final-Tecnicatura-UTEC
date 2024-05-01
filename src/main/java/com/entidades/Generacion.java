package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the GENERACIONES database table.
 * 
 */
@Entity
@Table(name="GENERACIONES")
@NamedQuery(name="Generacion.findAll", query="SELECT g FROM Generacion g")
public class Generacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_GENERACION")
	private long idGeneracion;

	@Column
	private String nombre;

	//bi-directional many-to-one association to Estudiante
	@OneToMany(mappedBy="generacion")
	private List<Estudiante> estudiantes;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;

	public Generacion() {
	}

	public long getIdGeneracion() {
		return this.idGeneracion;
	}

	public void setIdGeneracion(long idGeneracion) {
		this.idGeneracion = idGeneracion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Estudiante> getEstudiantes() {
		return this.estudiantes;
	}

	public void setEstudiantes(List<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public Estudiante addEstudiante(Estudiante estudiante) {
		getEstudiantes().add(estudiante);
		estudiante.setGeneracion(this);

		return estudiante;
	}

	public Estudiante removeEstudiante(Estudiante estudiante) {
		getEstudiantes().remove(estudiante);
		estudiante.setGeneracion(null);

		return estudiante;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
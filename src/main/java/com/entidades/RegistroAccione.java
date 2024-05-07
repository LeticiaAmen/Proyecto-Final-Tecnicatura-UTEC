package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the REGISTRO_ACCIONES database table.
 * 
 */
@Entity
@Table(name="REGISTRO_ACCIONES")
@NamedQuery(name="RegistroAccione.findAll", query="SELECT r FROM RegistroAccione r")
public class RegistroAccione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_REGISTRO_ACCION")
	private long idRegistroAccion;

	@Column(name="REGISTRO_ACCIONES")
	private String nombre;

	//bi-directional many-to-one association to Accion
	@OneToMany(mappedBy="registroAccion")
	private List<Accion> acciones;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;

	public RegistroAccione() {
	}

	public long getIdRegistroAccion() {
		return this.idRegistroAccion;
	}

	public void setIdRegistroAccion(long idRegistroAccion) {
		this.idRegistroAccion = idRegistroAccion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Accion> getAcciones() {
		return this.acciones;
	}

	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}

	public Accion addAccione(Accion accione) {
		getAcciones().add(accione);
		accione.setRegistroAccion(this);

		return accione;
	}

	public Accion removeAccione(Accion accione) {
		getAcciones().remove(accione);
		accione.setRegistroAccion(null);

		return accione;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
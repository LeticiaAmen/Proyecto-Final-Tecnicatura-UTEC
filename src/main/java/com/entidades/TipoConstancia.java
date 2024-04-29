package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TIPO_CONSTANCIAS database table.
 * 
 */
@Entity
@Table(name="TIPO_CONSTANCIAS")
@NamedQuery(name="TipoConstancia.findAll", query="SELECT t FROM TipoConstancia t")
public class TipoConstancia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_TIPO_CONSTANCIA")
	private long idTipoConstancia;

	private String nombre;

	//bi-directional many-to-one association to Constancia
	@OneToMany(mappedBy="tipoConstancia")
	private List<Constancia> constancias;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;

	public TipoConstancia() {
	}

	public long getIdTipoConstancia() {
		return this.idTipoConstancia;
	}

	public void setIdTipoConstancia(long idTipoConstancia) {
		this.idTipoConstancia = idTipoConstancia;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Constancia> getConstancias() {
		return this.constancias;
	}

	public void setConstancias(List<Constancia> constancias) {
		this.constancias = constancias;
	}

	public Constancia addConstancia(Constancia constancia) {
		getConstancias().add(constancia);
		constancia.setTipoConstancia(this);

		return constancia;
	}

	public Constancia removeConstancia(Constancia constancia) {
		getConstancias().remove(constancia);
		constancia.setTipoConstancia(null);

		return constancia;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the VALIDACION_USUARIOS database table.
 * 
 */
@Entity
@Table(name="VALIDACION_USUARIOS")
@NamedQuery(name="ValidacionUsuario.findAll", query="SELECT v FROM ValidacionUsuario v")
@NamedQuery(name = "ValidacionUsuario.obtenerPorNombre", query = "SELECT v FROM ValidacionUsuario v WHERE v.nombre = :nombre")
public class ValidacionUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_VALIDACION")
	private long idValidacion;

	@Column(name ="NOMBRE")
	private String nombre;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="validacionUsuario")
	private List<Usuario> usuarios;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;

	public ValidacionUsuario() {
	}

	public long getIdValidacion() {
		return this.idValidacion;
	}

	public void setIdValidacion(long idValidacion) {
		this.idValidacion = idValidacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setValidacionUsuario(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setValidacionUsuario(null);

		return usuario;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
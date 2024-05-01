package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ITRS database table.
 * 
 */
@Entity
@Table(name="ITRS")
@NamedQuery(name = "Itr.obtenerItrTodos", query = "SELECT i FROM Itr i")
@NamedQuery(name = "Itr.obtenerPorNombre", query = "SELECT i FROM Itr i WHERE i.nombre = :nombre")
public class Itr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ITR")
	private long idItr;

	@Column
	private String nombre;

	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="itr")
	private List<Evento> eventos;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="itr")
	private List<Usuario> usuarios;

	public Itr() {
	}

	public long getIdItr() {
		return this.idItr;
	}

	public void setIdItr(long idItr) {
		this.idItr = idItr;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento addEvento(Evento evento) {
		getEventos().add(evento);
		evento.setItr(this);

		return evento;
	}

	public Evento removeEvento(Evento evento) {
		getEventos().remove(evento);
		evento.setItr(null);

		return evento;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setItr(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setItr(null);

		return usuario;
	}

}
package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ESTADOS database table.
 * 
 */
@Entity
@Table(name="ESTADOS")
@NamedQuery(name="Estado.findAll", query="SELECT e FROM Estado e")
@NamedQuery(name="Estado.obtenerPorNombre", query = "SELECT e FROM Estado e WHERE e.nombre = :nombre")
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ESTADO")
	private long idEstado;

	private String nombre;

	//bi-directional many-to-one association to Accion
	@OneToMany(mappedBy="estado")
	private List<Accion> acciones;

	//bi-directional many-to-one association to Analista
	@OneToMany(mappedBy="estado")
	private List<Analista> analistas;

	//bi-directional many-to-one association to Area
	@OneToMany(mappedBy="estado")
	private List<Area> areas;

	//bi-directional many-to-one association to Constancia
	@OneToMany(mappedBy="estado")
	private List<Constancia> constancias;

	//bi-directional many-to-one association to Departamento
	@OneToMany(mappedBy="estado")
	private List<Departamento> departamentos;

	//bi-directional many-to-one association to Estudiante
	@OneToMany(mappedBy="estado")
	private List<Estudiante> estudiantes;

	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="estado")
	private List<Evento> eventos;

	//bi-directional many-to-one association to Generacion
	@OneToMany(mappedBy="estado")
	private List<Generacion> generaciones;

	//bi-directional many-to-one association to Itr
	@OneToMany(mappedBy="estado")
	private List<Itr> itrs;

	//bi-directional many-to-one association to Justificacion
	@OneToMany(mappedBy="estado")
	private List<Justificacion> justificaciones;

	//bi-directional many-to-one association to Localidad
	@OneToMany(mappedBy="estado")
	private List<Localidad> localidades;

	//bi-directional many-to-one association to RegistroAccione
	@OneToMany(mappedBy="estado")
	private List<RegistroAccione> registroAcciones;

	//bi-directional many-to-one association to Rol
	@OneToMany(mappedBy="estado")
	private List<Rol> roles;

	//bi-directional many-to-one association to TipoConstancia
	@OneToMany(mappedBy="estado")
	private List<TipoConstancia> tipoConstancias;

	//bi-directional many-to-one association to TipoEvento
	@OneToMany(mappedBy="estado")
	private List<TipoEvento> tipoEventos;

	//bi-directional many-to-one association to Tutor
	@OneToMany(mappedBy="estado")
	private List<Tutor> tutores;

	//bi-directional many-to-one association to ValidacionUsuario
	@OneToMany(mappedBy="estado")
	private List<ValidacionUsuario> validacionUsuarios;

	public Estado() {
	}

	public long getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado(long idEstado) {
		this.idEstado = idEstado;
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
		accione.setEstado(this);

		return accione;
	}

	public Accion removeAccione(Accion accione) {
		getAcciones().remove(accione);
		accione.setEstado(null);

		return accione;
	}

	public List<Analista> getAnalistas() {
		return this.analistas;
	}

	public void setAnalistas(List<Analista> analistas) {
		this.analistas = analistas;
	}

	public Analista addAnalista(Analista analista) {
		getAnalistas().add(analista);
		analista.setEstado(this);

		return analista;
	}

	public Analista removeAnalista(Analista analista) {
		getAnalistas().remove(analista);
		analista.setEstado(null);

		return analista;
	}

	public List<Area> getAreas() {
		return this.areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public Area addArea(Area area) {
		getAreas().add(area);
		area.setEstado(this);

		return area;
	}

	public Area removeArea(Area area) {
		getAreas().remove(area);
		area.setEstado(null);

		return area;
	}

	public List<Constancia> getConstancias() {
		return this.constancias;
	}

	public void setConstancias(List<Constancia> constancias) {
		this.constancias = constancias;
	}

	public Constancia addConstancia(Constancia constancia) {
		getConstancias().add(constancia);
		constancia.setEstado(this);

		return constancia;
	}

	public Constancia removeConstancia(Constancia constancia) {
		getConstancias().remove(constancia);
		constancia.setEstado(null);

		return constancia;
	}

	public List<Departamento> getDepartamentos() {
		return this.departamentos;
	}

	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}

	public Departamento addDepartamento(Departamento departamento) {
		getDepartamentos().add(departamento);
		departamento.setEstado(this);

		return departamento;
	}

	public Departamento removeDepartamento(Departamento departamento) {
		getDepartamentos().remove(departamento);
		departamento.setEstado(null);

		return departamento;
	}

	public List<Estudiante> getEstudiantes() {
		return this.estudiantes;
	}

	public void setEstudiantes(List<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public Estudiante addEstudiante(Estudiante estudiante) {
		getEstudiantes().add(estudiante);
		estudiante.setEstado(this);

		return estudiante;
	}

	public Estudiante removeEstudiante(Estudiante estudiante) {
		getEstudiantes().remove(estudiante);
		estudiante.setEstado(null);

		return estudiante;
	}

	public List<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Evento addEvento(Evento evento) {
		getEventos().add(evento);
		evento.setEstado(this);

		return evento;
	}

	public Evento removeEvento(Evento evento) {
		getEventos().remove(evento);
		evento.setEstado(null);

		return evento;
	}

	public List<Generacion> getGeneraciones() {
		return this.generaciones;
	}

	public void setGeneraciones(List<Generacion> generaciones) {
		this.generaciones = generaciones;
	}

	public Generacion addGeneracione(Generacion generacione) {
		getGeneraciones().add(generacione);
		generacione.setEstado(this);

		return generacione;
	}

	public Generacion removeGeneracione(Generacion generacione) {
		getGeneraciones().remove(generacione);
		generacione.setEstado(null);

		return generacione;
	}

	public List<Itr> getItrs() {
		return this.itrs;
	}

	public void setItrs(List<Itr> itrs) {
		this.itrs = itrs;
	}

	public Itr addItr(Itr itr) {
		getItrs().add(itr);
		itr.setEstado(this);

		return itr;
	}

	public Itr removeItr(Itr itr) {
		getItrs().remove(itr);
		itr.setEstado(null);

		return itr;
	}

	public List<Justificacion> getJustificaciones() {
		return this.justificaciones;
	}

	public void setJustificaciones(List<Justificacion> justificaciones) {
		this.justificaciones = justificaciones;
	}

	public Justificacion addJustificacione(Justificacion justificacione) {
		getJustificaciones().add(justificacione);
		justificacione.setEstado(this);

		return justificacione;
	}

	public Justificacion removeJustificacione(Justificacion justificacione) {
		getJustificaciones().remove(justificacione);
		justificacione.setEstado(null);

		return justificacione;
	}

	public List<Localidad> getLocalidades() {
		return this.localidades;
	}

	public void setLocalidades(List<Localidad> localidades) {
		this.localidades = localidades;
	}

	public Localidad addLocalidade(Localidad localidade) {
		getLocalidades().add(localidade);
		localidade.setEstado(this);

		return localidade;
	}

	public Localidad removeLocalidade(Localidad localidade) {
		getLocalidades().remove(localidade);
		localidade.setEstado(null);

		return localidade;
	}

	public List<RegistroAccione> getRegistroAcciones() {
		return this.registroAcciones;
	}

	public void setRegistroAcciones(List<RegistroAccione> registroAcciones) {
		this.registroAcciones = registroAcciones;
	}

	public RegistroAccione addRegistroAccione(RegistroAccione registroAccione) {
		getRegistroAcciones().add(registroAccione);
		registroAccione.setEstado(this);

		return registroAccione;
	}

	public RegistroAccione removeRegistroAccione(RegistroAccione registroAccione) {
		getRegistroAcciones().remove(registroAccione);
		registroAccione.setEstado(null);

		return registroAccione;
	}

	public List<Rol> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public Rol addRole(Rol role) {
		getRoles().add(role);
		role.setEstado(this);

		return role;
	}

	public Rol removeRole(Rol role) {
		getRoles().remove(role);
		role.setEstado(null);

		return role;
	}

	public List<TipoConstancia> getTipoConstancias() {
		return this.tipoConstancias;
	}

	public void setTipoConstancias(List<TipoConstancia> tipoConstancias) {
		this.tipoConstancias = tipoConstancias;
	}

	public TipoConstancia addTipoConstancia(TipoConstancia tipoConstancia) {
		getTipoConstancias().add(tipoConstancia);
		tipoConstancia.setEstado(this);

		return tipoConstancia;
	}

	public TipoConstancia removeTipoConstancia(TipoConstancia tipoConstancia) {
		getTipoConstancias().remove(tipoConstancia);
		tipoConstancia.setEstado(null);

		return tipoConstancia;
	}

	public List<TipoEvento> getTipoEventos() {
		return this.tipoEventos;
	}

	public void setTipoEventos(List<TipoEvento> tipoEventos) {
		this.tipoEventos = tipoEventos;
	}

	public TipoEvento addTipoEvento(TipoEvento tipoEvento) {
		getTipoEventos().add(tipoEvento);
		tipoEvento.setEstado(this);

		return tipoEvento;
	}

	public TipoEvento removeTipoEvento(TipoEvento tipoEvento) {
		getTipoEventos().remove(tipoEvento);
		tipoEvento.setEstado(null);

		return tipoEvento;
	}

	public List<Tutor> getTutores() {
		return this.tutores;
	}

	public void setTutores(List<Tutor> tutores) {
		this.tutores = tutores;
	}

	public Tutor addTutore(Tutor tutore) {
		getTutores().add(tutore);
		tutore.setEstado(this);

		return tutore;
	}

	public Tutor removeTutore(Tutor tutore) {
		getTutores().remove(tutore);
		tutore.setEstado(null);

		return tutore;
	}

	public List<ValidacionUsuario> getValidacionUsuarios() {
		return this.validacionUsuarios;
	}

	public void setValidacionUsuarios(List<ValidacionUsuario> validacionUsuarios) {
		this.validacionUsuarios = validacionUsuarios;
	}

	public ValidacionUsuario addValidacionUsuario(ValidacionUsuario validacionUsuario) {
		getValidacionUsuarios().add(validacionUsuario);
		validacionUsuario.setEstado(this);

		return validacionUsuario;
	}

	public ValidacionUsuario removeValidacionUsuario(ValidacionUsuario validacionUsuario) {
		getValidacionUsuarios().remove(validacionUsuario);
		validacionUsuario.setEstado(null);

		return validacionUsuario;
	}

	public static Estado valueOf(String parameter) {
		
		return null;
	}

}
package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the EVENTOS database table.
 * 
 */
@Entity
@Table(name="EVENTOS")
@NamedQuery(name="Evento.findAll", query="SELECT e FROM Evento e")
public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_EVENTO")
	private long idEvento;

	private BigDecimal creditos;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_HORA_FINAL")
	private Date fechaHoraFinal;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_HORA_INICIO")
	private Date fechaHoraInicio;

	private String informacion;

	@Column(columnDefinition="CHAR(1)")
	private String modalidad;

	@Column(name="TITULO_EVENTO")
	private String tituloEvento;

	private String ubicacion;

	//bi-directional many-to-one association to Constancia
	@OneToMany(mappedBy="evento")
	private List<Constancia> constancias;

	//bi-directional many-to-one association to EstudianteEvento
	@OneToMany(mappedBy="evento")
	private List<EstudianteEvento> estudiantesEventos;

	//bi-directional many-to-one association to Analista
	@ManyToOne
	@JoinColumn(name="ID_USUARIO_ANALISTA")
	private Analista analista;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="ID_ESTADO")
	private Estado estado;

	//bi-directional many-to-one association to Itr
	@ManyToOne
	@JoinColumn(name="ID_ITR")
	private Itr itr;

	//bi-directional many-to-one association to TipoEvento
	@ManyToOne
	@JoinColumn(name="ID_TIPO_EVENTO")
	private TipoEvento tipoEvento;

	//bi-directional many-to-one association to Justificacion
	@OneToMany(mappedBy="evento")
	private List<Justificacion> justificaciones;

	//bi-directional many-to-one association to Reclamo
	@OneToMany(mappedBy="evento")
	private List<Reclamo> reclamos;

	//bi-directional many-to-many association to Tutor
	@ManyToMany(mappedBy="eventos")
	private List<Tutor> tutores;

	public Evento() {
	}

	public long getIdEvento() {
		return this.idEvento;
	}

	public void setIdEvento(long idEvento) {
		this.idEvento = idEvento;
	}

	public BigDecimal getCreditos() {
		return this.creditos;
	}

	public void setCreditos(BigDecimal creditos) {
		this.creditos = creditos;
	}

	public Date getFechaHoraFinal() {
		return this.fechaHoraFinal;
	}

	public void setFechaHoraFinal(Date fechaHoraFinal) {
		this.fechaHoraFinal = fechaHoraFinal;
	}

	public Date getFechaHoraInicio() {
		return this.fechaHoraInicio;
	}

	public void setFechaHoraInicio(Date fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}

	public String getInformacion() {
		return this.informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	public String getModalidad() {
		return this.modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getTituloEvento() {
		return this.tituloEvento;
	}

	public void setTituloEvento(String tituloEvento) {
		this.tituloEvento = tituloEvento;
	}

	public String getUbicacion() {
		return this.ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<Constancia> getConstancias() {
		return this.constancias;
	}

	public void setConstancias(List<Constancia> constancias) {
		this.constancias = constancias;
	}

	public Constancia addConstancia(Constancia constancia) {
		getConstancias().add(constancia);
		constancia.setEvento(this);

		return constancia;
	}

	public Constancia removeConstancia(Constancia constancia) {
		getConstancias().remove(constancia);
		constancia.setEvento(null);

		return constancia;
	}

	public List<EstudianteEvento> getEstudiantesEventos() {
		return this.estudiantesEventos;
	}

	public void setEstudiantesEventos(List<EstudianteEvento> estudiantesEventos) {
		this.estudiantesEventos = estudiantesEventos;
	}

	public EstudianteEvento addEstudiantesEvento(EstudianteEvento estudiantesEvento) {
		getEstudiantesEventos().add(estudiantesEvento);
		estudiantesEvento.setEvento(this);

		return estudiantesEvento;
	}

	public EstudianteEvento removeEstudiantesEvento(EstudianteEvento estudiantesEvento) {
		getEstudiantesEventos().remove(estudiantesEvento);
		estudiantesEvento.setEvento(null);

		return estudiantesEvento;
	}

	public Analista getAnalista() {
		return this.analista;
	}

	public void setAnalista(Analista analista) {
		this.analista = analista;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Itr getItr() {
		return this.itr;
	}

	public void setItr(Itr itr) {
		this.itr = itr;
	}

	public TipoEvento getTipoEvento() {
		return this.tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public List<Justificacion> getJustificaciones() {
		return this.justificaciones;
	}

	public void setJustificaciones(List<Justificacion> justificaciones) {
		this.justificaciones = justificaciones;
	}

	public Justificacion addJustificacione(Justificacion justificacione) {
		getJustificaciones().add(justificacione);
		justificacione.setEvento(this);

		return justificacione;
	}

	public Justificacion removeJustificacione(Justificacion justificacione) {
		getJustificaciones().remove(justificacione);
		justificacione.setEvento(null);

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
		reclamo.setEvento(this);

		return reclamo;
	}

	public Reclamo removeReclamo(Reclamo reclamo) {
		getReclamos().remove(reclamo);
		reclamo.setEvento(null);

		return reclamo;
	}

	public List<Tutor> getTutores() {
		return this.tutores;
	}

	public void setTutores(List<Tutor> tutores) {
		this.tutores = tutores;
	}

}
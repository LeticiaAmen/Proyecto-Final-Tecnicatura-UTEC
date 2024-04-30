package com.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the USUARIOS database table.
 * 
 */
@Entity
@Table(name="USUARIOS")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_USUARIO")
	private long idUsuario;

	private String apellidos;

	private BigDecimal documento;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_NACIMIENTO")
	private Date fechaNacimiento;

	@Column(columnDefinition="CHAR(1)")
	private String genero;

	@Column(name="HASH_CONTRASEÑA")
	private String hashContraseña;

	private String mail;

	@Column(name="NOMBRE_USUARIO")
	private String nombreUsuario;

	private String nombres;

	@Column(name="SALT_CONTRASEÑA")
	private String saltContraseña;

	private String telefono;

	//bi-directional one-to-one association to Analista
	@OneToOne(mappedBy="usuario")
	private Analista analista;

	//bi-directional one-to-one association to Estudiante
	@OneToOne(mappedBy="usuario")
	private Estudiante estudiante;

	//bi-directional one-to-one association to Tutor
	@OneToOne(mappedBy="usuario")
	private Tutor tutore;

	//bi-directional many-to-one association to Itr
	@ManyToOne
	@JoinColumn(name="ID_ITR")
	private Itr itr;

	//bi-directional many-to-one association to Localidad
	@ManyToOne
	@JoinColumn(name="ID_LOCALIDAD")
	private Localidad localidade;

	//bi-directional many-to-one association to ValidacionUsuario
	@ManyToOne
	@JoinColumn(name="ID_VALIDACION")
	private ValidacionUsuario validacionUsuario;

	public Usuario() {
	}

	public long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public BigDecimal getDocumento() {
		return this.documento;
	}

	public void setDocumento(BigDecimal documento) {
		this.documento = documento;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getHashContraseña() {
		return this.hashContraseña;
	}

	public void setHashContraseña(String hashContraseña) {
		this.hashContraseña = hashContraseña;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getSaltContraseña() {
		return this.saltContraseña;
	}

	public void setSaltContraseña(String saltContraseña) {
		this.saltContraseña = saltContraseña;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String teléfono) {
		this.telefono = teléfono;
	}

	public Analista getAnalista() {
		return this.analista;
	}

	public void setAnalista(Analista analista) {
		this.analista = analista;
	}

	public Estudiante getEstudiante() {
		return this.estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Tutor getTutore() {
		return this.tutore;
	}

	public void setTutore(Tutor tutore) {
		this.tutore = tutore;
	}

	public Itr getItr() {
		return this.itr;
	}

	public void setItr(Itr itr) {
		this.itr = itr;
	}

	public Localidad getLocalidade() {
		return this.localidade;
	}

	public void setLocalidade(Localidad localidade) {
		this.localidade = localidade;
	}

	public ValidacionUsuario getValidacionUsuario() {
		return this.validacionUsuario;
	}

	public void setValidacionUsuario(ValidacionUsuario validacionUsuario) {
		this.validacionUsuario = validacionUsuario;
	}

}
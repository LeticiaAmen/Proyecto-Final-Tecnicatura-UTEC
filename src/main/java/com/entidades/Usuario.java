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
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_usuario")
	@Column(name="ID_USUARIO")
	private long idUsuario;

	@Column(name = "APELLIDOS", length= 40)
	private String apellidos;

	@Column(name="DOCUMENTO", precision =38)
	private long documento;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_NACIMIENTO")
	private Date fechaNacimiento;

	@Column(columnDefinition="CHAR(1)")
	private char genero;

	@Column(name="HASH_CONTRASEÑA")
	private String hashContraseña;

	@Column( length=100)
	private String mail;

	@Column(name="NOMBRE_USUARIO")
	private String nombreUsuario;

	@Column( length=20)
	private String nombres;

	@Column(name="SALT_CONTRASEÑA")
	private String saltContraseña;

	@Column( length=20)
	private String telefono;


	//bi-directional many-to-one association to Itr
	@ManyToOne
	@JoinColumn(name="ID_ITR")
	private Itr itr;

	//bi-directional many-to-one association to Localidad
	@ManyToOne
	@JoinColumn(name="ID_LOCALIDAD")
	private Localidad localidad;

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

	public long getDocumento() {
		return this.documento;
	}

	public void setDocumento(long documento) {
		this.documento = documento;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public char getGenero() {
		return this.genero;
	}

	public void setGenero(char genero) {
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


	public Itr getItr() {
		return this.itr;
	}

	public void setItr(Itr itr) {
		this.itr = itr;
	}

	public Localidad getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public ValidacionUsuario getValidacionUsuario() {
		return this.validacionUsuario;
	}

	public void setValidacionUsuario(ValidacionUsuario validacionUsuario) {
		this.validacionUsuario = validacionUsuario;
	}

}
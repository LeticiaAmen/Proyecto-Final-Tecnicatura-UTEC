package com.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="ACCIONES")
@NamedQuery(name="Accion.findAll", query="SELECT a FROM Accion a")
public class Accion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accion_seq")
    @SequenceGenerator(name = "accion_seq", sequenceName = "SEQ_ACCION", allocationSize = 1)
    @Column(name="ID_ACCION")
    private long idAccion;

    private String detalle;

    @Temporal(TemporalType.DATE)
    @Column(name="FECHA_HORA")
    private Date fechaHora;

    @ManyToOne
    @JoinColumn(name="ID_USUARIO_ANALISTA")
    private Analista analista;

    @ManyToOne
    @JoinColumn(name="ID_CONSTANCIA")
    private Constancia constancia;

    @ManyToOne
    @JoinColumn(name="ID_ESTADO")
    private Estado estado;

    @ManyToOne
    @JoinColumn(name="ID_JUSTIFICACION")
    private Justificacion justificacion;

    @ManyToOne
    @JoinColumn(name="ID_RECLAMO")
    private Reclamo reclamo;

    @ManyToOne
    @JoinColumn(name="ID_REGISTRO_ACCION")
    private RegistroAccione registroAccion;

    public Accion() {}

    public long getIdAccion() {
        return this.idAccion;
    }

    public void setIdAccion(long idAccion) {
        this.idAccion = idAccion;
    }

    public String getDetalle() {
        return this.detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFechaHora() {
        return this.fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Analista getAnalista() {
        return this.analista;
    }

    public void setAnalista(Analista analista) {
        this.analista = analista;
    }

    public Constancia getConstancia() {
        return this.constancia;
    }

    public void setConstancia(Constancia constancia) {
        this.constancia = constancia;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Justificacion getJustificacion() {
        return this.justificacion;
    }

    public void setJustificacion(Justificacion justificacion) {
        this.justificacion = justificacion;
    }

    public Reclamo getReclamo() {
        return this.reclamo;
    }

    public void setReclamo(Reclamo reclamo) {
        this.reclamo = reclamo;
    }

    public RegistroAccione getRegistroAccion() {
        return this.registroAccion;
    }

    public void setRegistroAccion(RegistroAccione registroAccion) {
        this.registroAccion = registroAccion;
    }
}

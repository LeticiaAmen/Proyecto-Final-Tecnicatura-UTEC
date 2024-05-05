package com.servicios;

import com.entidades.Reclamo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ReclamosService {

    @PersistenceContext(unitName = "PFT2024")
    private EntityManager em;

    public List<Reclamo> obtenerTodosReclamos() {
        return em.createQuery("SELECT r FROM Reclamo r", Reclamo.class).getResultList();
    }

    public List<Reclamo> obtenerReclamosPorUsuario(Long idUsuario) {
        return em.createQuery("SELECT r FROM Reclamo r WHERE r.estudiante.idUsuario = :idUsuario", Reclamo.class)
                 .setParameter("idUsuario", idUsuario)
                 .getResultList();
    }

    public List<Reclamo> obtenerTodosReclamosConEstudiantes() {
        return em.createQuery("SELECT r FROM Reclamo r JOIN FETCH r.estudiante", Reclamo.class).getResultList();
    }

    public List<Reclamo> obtenerReclamosPorUsuarioConEstudiante(Long idUsuario) {
        return em.createQuery("SELECT r FROM Reclamo r JOIN FETCH r.estudiante WHERE r.estudiante.idUsuario = :idUsuario", Reclamo.class)
                 .setParameter("idUsuario", idUsuario)
                 .getResultList();
    }
}

package com.servicios;

import com.dao.ReclamoDAO;
import com.entidades.Reclamo;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ReclamosService {

    @PersistenceContext(unitName = "PFT2024")
    private EntityManager em;
    
    @EJB
	private ReclamoDAO reclamoDAO;

    public List<Reclamo> obtenerTodosReclamos() {
        return em.createQuery("SELECT r FROM Reclamo r", Reclamo.class).getResultList();
    }

    public List<Reclamo> obtenerReclamosPorUsuario(Long idUsuario) {
        System.out.println("Consultando reclamos para el usuario: " + idUsuario);
        List<Reclamo> resultados = em.createQuery("SELECT r FROM Reclamo r WHERE r.estudiante.idUsuario = :idUsuario", Reclamo.class)
                                      .setParameter("idUsuario", idUsuario)
                                      .getResultList();
        System.out.println("Número de reclamos encontrados: " + resultados.size());
        return resultados;
    }
    
    public Reclamo obtenerReclamo(long idReclamo) {
        return em.find(Reclamo.class, idReclamo);
    }

    public List<Reclamo> obtenerReclamosConFiltros(String filtroUsuario, String estadoReclamo) {
        String jpql = "SELECT r FROM Reclamo r LEFT JOIN r.registroAccione ra WHERE " +
                      "(:filtroUsuario IS NULL OR LOWER(r.estudiante.nombreUsuario) LIKE :filtroUsuario) AND " +
                      "(:estadoReclamo IS NULL OR ra.idRegistroAccion = :estadoReclamo)";

        TypedQuery<Reclamo> query = em.createQuery(jpql, Reclamo.class);
        query.setParameter("filtroUsuario", (filtroUsuario == null || filtroUsuario.trim().isEmpty()) ? null : '%' + filtroUsuario.trim().toLowerCase() + '%');
        query.setParameter("estadoReclamo", (estadoReclamo == null || estadoReclamo.isEmpty()) ? null : Long.parseLong(estadoReclamo)); 

        return query.getResultList();
    }

    public List<Reclamo> obtenerReclamosPorUsuarioConFiltros(Long idUsuario, String filtroUsuario, String estadoReclamo) {
        String jpql = "SELECT r FROM Reclamo r LEFT JOIN r.registroAccione ra WHERE " +
                      "r.estudiante.idUsuario = :idUsuario AND " +
                      "(:filtroUsuario IS NULL OR LOWER(r.estudiante.nombreUsuario) LIKE :filtroUsuario) AND " +
                      "(:estadoReclamo IS NULL OR ra.idRegistroAccion = :estadoReclamo)";
        TypedQuery<Reclamo> query = em.createQuery(jpql, Reclamo.class);
        query.setParameter("idUsuario", idUsuario);
        query.setParameter("filtroUsuario", (filtroUsuario == null || filtroUsuario.trim().isEmpty()) ? null : '%' + filtroUsuario.trim().toLowerCase() + '%');
        query.setParameter("estadoReclamo", (estadoReclamo == null || estadoReclamo.isEmpty()) ? null : Long.parseLong(estadoReclamo)); 

        return query.getResultList();
    }

    
    public void eliminarReclamo(long idReclamo) {
	    reclamoDAO.eliminarReclamo(idReclamo);
	}
}

package com.servicios;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.dao.ReclamoDAO;
import com.entidades.Estado;
import com.entidades.Reclamo;

@Stateless
public class ReclamoService {
    
    @EJB
    private ReclamoDAO reclamoDAO;

    public void crearReclamo(Reclamo reclamo) {
        reclamoDAO.crearReclamo(reclamo);
    }
    
    public Reclamo obtenerReclamo(long id) {
        return reclamoDAO.obtenerReclamo(id);
    }
    
    public Reclamo obtenerReclamoConAcciones(long id) {
        return reclamoDAO.obtenerReclamoConAcciones(id);
    }
    
    public List<Reclamo> obtenerReclamosTodos(){
        return reclamoDAO.obtenerReclamosTodos();
    }
    
    public void actualizarReclamo(Reclamo reclamo) {
        reclamoDAO.actualizarReclamo(reclamo);
    }
    
    public List<Reclamo> obtenerReclamosPorEstado(Estado estado){
        return reclamoDAO.obtenerReclamosPorEstado(estado);
    }
    
    public Reclamo obtenerReclamoDesdeBaseDeDatosNombre(String nombre) {
        return reclamoDAO.obtenerReclamoDesdeBaseDeDatosNombre(nombre);
    }
    
    public List<Reclamo> obtenerReclamosPorEstudiante(Long estudianteId) {
        return reclamoDAO.obtenerReclamosPorEstudianteId(estudianteId);
    }
    
    // Método para obtener reclamos de un estudiante filtrado por año y mes
    public List<Reclamo> obtenerReclamosPorEstudianteYFecha(Long idUsuarioEstudiante, int anio, int mes) {
        return reclamoDAO.obtenerReclamosPorEstudianteYFecha(idUsuarioEstudiante, anio, mes);
    }
}

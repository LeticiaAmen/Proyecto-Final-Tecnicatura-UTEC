package com.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.TutorDAO;
import com.entidades.Tutor;

@Stateless
public class TutorService {

    @EJB
    private TutorDAO tutorDAO;

    public Tutor obtenerTutor(long id) {
        return tutorDAO.obtenerTutor(id);
    }

    public List<Tutor> obtenerTutoresTodos() {
        return tutorDAO.obtenerTutoresTodos();
    }
}

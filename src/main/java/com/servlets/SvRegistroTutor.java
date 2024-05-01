package com.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Departamento;
import com.entidades.Itr;
import com.entidades.Localidad;
import com.servicios.DepartamentoService;
import com.servicios.ItrService;
import com.servicios.LocalidadService;
import com.servicios.UsuarioService;
import com.servicios.ValidacionUsuarioService;


@WebServlet("/SvRegistroTutor")
public class SvRegistroTutor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioService usuarioService;

	@EJB
	private DepartamentoService departamentoService; 

	@EJB
	private LocalidadService localidadService; 

	@EJB
	ValidacionUsuarioService validacionService; 

	@EJB
	private ItrService itrService; 


	public SvRegistroTutor() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Departamento> departamentos = departamentoService.obtenerTodosDepartamento();
		request.setAttribute("departamentos", departamentos);

		List<Localidad> localidades = localidadService.obtenerTodasLocalidades();
		request.setAttribute("localidades", localidades);

		List<Itr> itrs = itrService.obtenerItrTodos();
		request.setAttribute("itrs", itrs);

		//Definimos las opciones ya que no tenemos enum ahora
		List<String> generosLista = Arrays.asList("Masculino", "Femenino", "Otros");
		request.setAttribute("generos", generosLista);

		//Esta se la puse a mano porque ahora que es tabla hay que crear todo para generaciones
		List<String> areasLista = Arrays.asList("BASE DE DATOS", "PROGRAMACION");
		request.setAttribute("areas", areasLista);

		request.getRequestDispatcher("/registroTutor.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

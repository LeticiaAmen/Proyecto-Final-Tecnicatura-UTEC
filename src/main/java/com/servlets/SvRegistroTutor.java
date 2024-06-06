package com.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entidades.Area;
import com.entidades.Departamento;
import com.entidades.Estado;
import com.entidades.Itr;
import com.entidades.Localidad;
import com.entidades.Rol;
import com.entidades.Tutor;
import com.entidades.ValidacionUsuario;
import com.servicios.AreaService;
import com.servicios.DepartamentoService;
import com.servicios.EstadoService;
import com.servicios.ItrService;
import com.servicios.LocalidadService;
import com.servicios.RolService;
import com.servicios.UsuarioService;
import com.servicios.ValidacionUsuarioService;
import com.validaciones.Validacion;

import com.util.PasswordUtils;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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

    @EJB 
    private EstadoService estadoService; 

    @EJB
    private AreaService areaService; 

    @EJB
    private RolService rolService;

    private Validacion validacion;

    public SvRegistroTutor() {
        super();
        validacion = new Validacion(); 
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Departamento> departamentos = departamentoService.obtenerTodosDepartamento();
        request.setAttribute("departamentos", departamentos);

        List<Localidad> localidades = localidadService.obtenerTodasLocalidades();
        request.setAttribute("localidades", localidades);

        List<Itr> itrs = itrService.obtenerItrTodos();
        request.setAttribute("itrs", itrs);

        List<Area> areasLista = areaService.obtenerAreaTodas();
        request.setAttribute("areas", areasLista);

        List<Rol> roles = rolService.obtenerRolTodos();
        request.setAttribute("roles", roles);

        List<String> generosLista = Arrays.asList("Masculino", "Femenino", "Otros");
        request.setAttribute("generos", generosLista);

        request.getRequestDispatcher("/registroTutor.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String formSubmitted = request.getParameter("formSubmitted");
        if ("true".equals(formSubmitted)) {
            String nomUsuario = request.getParameter("nomUsuario");
            String documento = request.getParameter("documento");
            long documentoLong = 0;
            if (documento != null && !documento.isEmpty()) {
                documentoLong = Long.parseLong(documento);
            }

            String apellido = request.getParameter("apellido");
            String contrasenia = request.getParameter("contrasenia");
            		String mailInst = request.getParameter("mailInst");
            String mail = request.getParameter("mail");
            String nombre = request.getParameter("nombre");
            String telefono = request.getParameter("telefono");
            String idDepartamento = request.getParameter("idDepartamento");
            List<Localidad> localidades = localidadService.obtenerLocalidadesPorDepartamento(Long.parseLong(idDepartamento));
            String idLocalidad = request.getParameter("idLocalidad");
            String idItr = request.getParameter("idItr");
            Long idDepartamentoLong = Long.parseLong(idDepartamento);
            Long idLocalidadLong = Long.parseLong(idLocalidad);
            Long idItrLong = Long.parseLong(idItr);
            Departamento departamento = departamentoService.obtenerPorId(idDepartamentoLong);
            Localidad localidad = localidadService.obtenerLocalidadPorId(idLocalidadLong);
            Itr itr = itrService.obtenerItr(idItrLong);

            String idArea = request.getParameter("idArea");
            String idRol = request.getParameter("idRol");
            Long idAreaLong = Long.parseLong(idArea);
            Long idRolLong = Long.parseLong(idRol);
            Area area = areaService.obtenerArea(idAreaLong);
            Rol rol = rolService.obtenerRol(idRolLong);

            String generoSeleccionado = request.getParameter("genero");
            char genero = generoSeleccionado.charAt(0);

            String fechaNacimientoStr = request.getParameter("fechaNacimiento");
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

            Date fechaNacimiento = null;
            try {
                fechaNacimiento = formato.parse(fechaNacimientoStr);
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("Error al convertir la fecha");
            }

            if (validacion.validacionNombre(nombre)) {
                setErrorAndReturn(request, response, validacion.RespuestaValidacionNombre(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idArea, idRol, fechaNacimientoStr);
                return;
            }

            if (validacion.validacionApellido(apellido)) {
                setErrorAndReturn(request, response, validacion.RespuestaValidacionAepllido(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idArea, idRol, fechaNacimientoStr);
                return;
            }

            if (validacion.validacionUsiario(nomUsuario, nombre, apellido)) {
                setErrorAndReturn(request, response, validacion.RespuestaValidacionUsiario(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idArea, idRol, fechaNacimientoStr);
                return;
            }

            // Validar las contraseñas
	        if (contrasenia != null && !contrasenia.isEmpty()) {
	            if (!contrasenia.equals(contrasenia)) {
	                return;
	            }

	            if (!validacion.validacionContraseña(contrasenia)) {
	                setErrorAndReturn(request, response, validacion.RespuestaValidacionContraseña(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idArea, idRol, fechaNacimientoStr);
	                return;
	            }
	        }
	        
	        if (!validacion.validacionMail(mail)) {
                setErrorAndReturn(request, response, validacion.RespuestaValidacionMail(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idArea, idRol, fechaNacimientoStr);
                return;
            }
        

            if (validacion.validacionMailFuncionario(nomUsuario, mailInst)) {
                setErrorAndReturn(request, response, validacion.RespuestaValidacionMailFuncionario(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idArea, idRol, fechaNacimientoStr);
                return;
            }
            

            if (validacion.validacionTelefono(telefono)) {
                setErrorAndReturn(request, response, validacion.RespuestaValidacionTelefono(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idArea, idRol, fechaNacimientoStr);
                return;
            }

            if (validacion.validacionEdad(fechaNacimiento)) {
                setErrorAndReturn(request, response, validacion.RespuestaValidacionEdad(), nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idArea, idRol, fechaNacimientoStr);
                return;
            }

            ValidacionUsuario usuEstadoSinValidar = validacionService.obtenerValidacionUsuario(2);

            Tutor tutor = new Tutor();

            tutor.setNombreUsuario(nomUsuario);
            tutor.setDocumento(documentoLong);
            tutor.setApellidos(apellido);

            try {
                String salt = PasswordUtils.generateSalt();
                String hashedPassword = PasswordUtils.hashPassword(contrasenia, salt);

                tutor.setSaltContraseña(salt);
                tutor.setHashContraseña(hashedPassword);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
                request.setAttribute("error", "Error al procesar la contraseña.");
                setErrorAndReturn(request, response, "Error al procesar la contraseña.", nombre, apellido, documento, nomUsuario, contrasenia, mailInst, mail, telefono, generoSeleccionado, idDepartamento, idLocalidad, idItr, idArea, idRol, fechaNacimientoStr);
                return;
            }

            tutor.setMailInstitucional(mailInst);
            tutor.setMail(mail);
            tutor.setNombres(nombre);
            tutor.setTelefono(telefono);
            tutor.setValidacionUsuario(usuEstadoSinValidar);
            tutor.setEstado(estadoService.obtenerEstadoId(1));
            tutor.setLocalidad(localidad);
            tutor.setItr(itr);
            tutor.setArea(area);
            tutor.setRol(rol);
            tutor.setGenero(genero);
            tutor.setFechaNacimiento(fechaNacimiento);

            try {
                usuarioService.crearTutor(tutor);
                request.getSession().setAttribute("registroExitoso", "Su usuario ha sido registrado. Por favor, espere a que un analista valide su cuenta.");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al crear Tutor");
            }

            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            doGet(request, response);
        }
    }

    private void setErrorAndReturn(HttpServletRequest request, HttpServletResponse response, String errorMsg, String nombre, String apellido, String documento, String nomUsuario, String contrasenia, String mailInst, String mail, String telefono, String generoSeleccionado, String idDepartamento, String idLocalidad, String idItr, String idArea, String idRol, String fechaNacimientoStr) throws ServletException, IOException {
        request.setAttribute("error", errorMsg);

        request.setAttribute("nombre", nombre);
        request.setAttribute("apellido", apellido);
        request.setAttribute("documento", documento);
        request.setAttribute("nomUsuario", nomUsuario);
        request.setAttribute("contrasenia", contrasenia);
        request.setAttribute("mailInst", mailInst);
        request.setAttribute("mail", mail);
        request.setAttribute("telefono", telefono);
        request.setAttribute("generoSeleccionado", generoSeleccionado);
        request.setAttribute("idDepartamento", idDepartamento);
        request.setAttribute("idLocalidad", idLocalidad);
        request.setAttribute("idItr", idItr);
        request.setAttribute("idArea", idArea);
        request.setAttribute("idRol", idRol);
        request.setAttribute("fechaNacimiento", fechaNacimientoStr);

        doGet(request, response);
        request.getRequestDispatcher("/registroTutor.jsp").forward(request, response);
    }
}

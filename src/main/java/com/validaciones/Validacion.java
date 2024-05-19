package com.validaciones;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Validacion {
	//------------------ VALIDACION NOMBRE ------------------------------------------------------------
    public boolean validacionNombre(String nombre) {
        boolean validacionNombre =
                nombre.length() < 3
                || nombre.length() > 20
                || !nombre.matches("[a-zA-Z]+(?:\\s[a-zA-Z]+)*");

        return validacionNombre;
    }
    public String RespuestaValidacionNombre() {
        return  "El nombre debe tener entre 3 y 20 caracteres";

    }
    //------------------ VALIDACION APELLIDO ------------------------------------------------------------
    public boolean validacionApellido(String apellido) {
        boolean validacionApellido =
                apellido.length() < 3
                        || apellido.length() > 20
                        || !apellido.matches("[a-zA-Z]+(?:\\s[a-zA-Z]+)*");

        return validacionApellido;
    }
    public String RespuestaValidacionAepllido() {
        return  "El apellido debe tener entre 3 y 20 caracteres";

    }
    //------------------ VALIDACION DOCUMENTO ------------------------------------------------------------
    public boolean validacionDocumento(String documento) {

    	// Se define el arreglo de factores para el cálculo del dígito verificador
        int[] factor = {2, 9, 8, 7, 6, 3, 4};
        int suma = 0;

        // Verificar si la cédula tiene la longitud correcta
        if (documento.length() != 8) {
            return false;
        }

        // Verificar si todos los caracteres son dígitos
        for (int i = 0; i < documento.length(); i++) {
            if (!Character.isDigit(documento.charAt(i))) {

                return false;
            }
        }

        // Calcular la suma ponderada
        for (int i = 0; i < factor.length; i++) {
            suma += Character.getNumericValue(documento.charAt(i)) * factor[i];
        }

     // Calcular el dígito verificador
        int resto = suma % 10;
        int digitoVerificador = (resto == 0) ? 0 : 10 - resto;

     // Verificar si el dígito verificador coincide con el último dígito de la cédula
        return digitoVerificador == Character.getNumericValue(documento.charAt(7));
        }
    

    public String RespuestaValidacionDocumento() {
        return  "El formato del numero de documento no es correcto";

    }
    public boolean validacionDocumentoExistente(String documento) {
        boolean validacionDocumento;


        return true;
    }
    public String RespuestaValidacionDocumentoExistente() {
        return  "El documento ya existe.";

    }

//------------------ VALIDACION CONTRASEÑA ------------------------------------------------------------

    public boolean validacionContraseña(String contraseña) {
       boolean validacionContraseña =
               contraseña.length() < 8
               || !contraseña.matches(".*[A-Za-z].*")
               ||!contraseña.matches(".*[0-9].*");
       return validacionContraseña;

    }
    public String RespuestaValidacionContraseña() {
      return  "La contraseña debe tener al menos 8 caracteres y contener letras y números.";

    }
//------------------ VALIDACION USUARIO ------------------------------------------------------------

    public boolean validacionUsiario(String usuario,String nombre, String apellido) {
        boolean validacionUsiario =
                !usuario.equals(nombre.toLowerCase()+"."+apellido.toLowerCase());

        return validacionUsiario;
    }
    public String RespuestaValidacionUsiario() {
        return  "El formato del usuario no es correcto, debe ser nombre.apellido .";

    }
    //------------------ VALIDACION MAIL ------------------------------------------------------------
    public boolean validacionMail(String mail) {
        boolean validacionMail = !mail.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}");


        return validacionMail;
    }
    public String RespuestaValidacionMail() {
        return  "El formato del correo no es correcto";

    }
    public boolean validacionMailFuncionario(String usuario,String mailInstitucional) {
        boolean validacionMailFuncionario = !mailInstitucional.equals(usuario+"@utec.edu.uy");


        return validacionMailFuncionario;
    }
    public String RespuestaValidacionMailFuncionario() {
        return  "El mail institucional es incorrecto, el formato es nombre.apellido@utec.edu.uy";
    }
    public boolean validacionMailEstudiantes(String usuario,String mailInstitucional) {
        boolean validacionMailFuncionario = !mailInstitucional.equals(usuario+"@estudiantes.utec.edu.uy");


        return validacionMailFuncionario;
    }
    public String RespuestaValidacionMailEstudiantes() {
        return  "El mail institucional es incorrecto, el formato es nombre.apellido@estudiantes.utec.edu.uy";
    }
    //------------------ VALIDACION TELEFONO ------------------------------------------------------------
    public boolean validacionTelefono(String telefono) {
        boolean validacionTelefono =  !telefono.matches("^\\d{9}$");


        return validacionTelefono;
    }
    public String RespuestaValidacionTelefono() {
        return  "El formato del telefono no es correcto";

    }
  //------------------ VALIDACION fecha ------------------------------------------------------------  
    public boolean validacionEdad(Date fechaNacimiento) {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Convertir la fecha de nacimiento a LocalDate
        LocalDate fechaNacimientoLocalDate = fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Calcular la edad actual
        Period periodo = Period.between(fechaNacimientoLocalDate, fechaActual);
        int edad = periodo.getYears();

        // Validar que la edad sea menor o igual a 18 años
        return edad <= 18;
    }

    public String RespuestaValidacionEdad() {
        return "Debe ser mayor de 18 años para registrarse.";
    }

    
    public boolean validacionMailInstitucional(String mailInstitucional, String tipoUsuario) {
        if (tipoUsuario.equals("ANALISTA") || tipoUsuario.equals("TUTOR")) {
            return mailInstitucional.endsWith("@utec.edu.uy");
        } else if (tipoUsuario.equals("ESTUDIANTE")) {
            return mailInstitucional.endsWith("@estudiantes.utec.edu.uy");
        }
        return false;
    }
    
    public String RespuestaValidacionMailInstitucional(String tipoUsuario) {
        if (tipoUsuario.equals("ANALISTA") || tipoUsuario.equals("TUTOR")) {
            return "El correo institucional debe terminar en @utec.edu.uy";
        } else if (tipoUsuario.equals("ESTUDIANTE")) {
            return "El correo institucional debe terminar en @estudiantes.utec.edu.uy";
        }
        return "Tipo de usuario no válido para la validación de correo institucional.";
    }

}

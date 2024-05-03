package com.validaciones;

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

        int correcto = 0;
        int[] cedula;
        int[] factor = {8, 1, 2, 3, 4, 7, 6, 0};
        cedula = new int[8];
        int suma = 0;

        for (int i = 0; i < documento.length(); i++) {
            if (Character.isDigit(documento.charAt(i))) {
                correcto++;
                cedula[i] = Integer.parseInt("" + documento.charAt(i));
                suma = suma + (cedula[i] * factor[i]);
            }
        }

        if (correcto != 8) {
            System.out.println("Debe ingresar solo números o le faltaron dígitos");
            return false;
        } else {
            int resto = suma % 10;
            if (resto == cedula[7]) {
                System.out.println("Correcto");
                return true;
            } else {
                System.out.println("No coincide el dígito verificador : " + resto + " --> Dígito ingresado :" + cedula[7]);
                return false;
            }
        }


    }
    public String RespuestaValidacionDocumento() {
        return  "El formato del numero de documento no es correcto, debe ser 12345678.";

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
    //------------------ VALIDACION TELEFONO ------------------------------------------------------------
    public boolean validacionTelefono(String telefono) {
        boolean validacionTelefono =  !telefono.matches("^\\d{9}$");


        return validacionTelefono;
    }
    public String RespuestaValidacionTelefono() {
        return  "El formato del telefono no es correcto";

    }

}

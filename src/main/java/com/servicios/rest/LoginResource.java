package com.servicios.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.entidades.Usuario;
import com.servicios.UsuarioService;

@Path("/login")
public class LoginResource {

    @EJB
    private UsuarioService usuarioService;
    private LdapService ldapService = new LdapService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credentials credentials) {
        try {
            // Obtener usuario de la base de datos
            Usuario usuarioLogeado = usuarioService.obtenerUsuarioDesdeBaseDeDatosNombre(credentials.getUsername());

            if (usuarioLogeado != null) {               
                // Verificar si el usuario ha sido validado por un analista
                if (usuarioLogeado.getValidacionUsuario().getIdValidacion() != 1) {                  
                    return Response.status(Response.Status.UNAUTHORIZED)
                                   .entity("{\"message\":\"Su usuario aún no ha sido validado.\"}")
                                   .build();
                }

                // Autenticar contra Active Directory
                boolean isAuthenticated = ldapService.authenticate(credentials.getUsername(), credentials.getPassword());
                if (isAuthenticated) {                   
                    return Response.ok().entity("{\"message\":\"Login successful\"}").build();
                } else {                   
                    return Response.status(Response.Status.UNAUTHORIZED)
                                   .entity("{\"message\":\"Invalid credentials\"}")
                                   .build();
                }
            } else {              
                return Response.status(Response.Status.UNAUTHORIZED)
                               .entity("{\"message\":\"Invalid credentials\"}")
                               .build();
            }
        } catch (Exception e) {         
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"message\":\"Error during login: " + e.getMessage() + "\"}")
                           .build();
        }
    }
}

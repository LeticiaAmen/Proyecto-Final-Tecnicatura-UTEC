package com.servicios.rest;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.entidades.Analista;
import com.entidades.Estudiante;
import com.entidades.Usuario;
import com.servicios.UsuarioService;
import com.util.JwtUtil;
@Path("/login")
public class LoginResource {
   @EJB
   private UsuarioService usuarioService;
   private LdapService ldapService = new LdapService();
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response login(Credentials credentials, @Context HttpServletRequest request) {
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
                   boolean estadoActivo = true;
                   String tipoUsuario = usuarioService.determinarTipoUsuario(usuarioLogeado);
                   if (usuarioLogeado instanceof Estudiante) {
                       estadoActivo = ((Estudiante) usuarioLogeado).getEstado().getIdEstado() != 2;
                   } else if (usuarioLogeado instanceof Analista) {
                       estadoActivo = ((Analista) usuarioLogeado).getEstado().getIdEstado() != 2;
                   }
                   if (!estadoActivo) {
                       return Response.status(Response.Status.UNAUTHORIZED)
                                      .entity("{\"message\":\"Su usuario está inactivo.\"}")
                                      .build();
                   }
                   // token JWT
                   String token = JwtUtil.generateToken(usuarioLogeado.getIdUsuario(), usuarioLogeado.getNombreUsuario(), tipoUsuario);
                   // Crear respuesta con información del usuario y el token
                   UserResponse userResponse = new UserResponse();
                   userResponse.setId(usuarioLogeado.getIdUsuario());
                   userResponse.setUsername(usuarioLogeado.getNombreUsuario());
                   userResponse.setRole(tipoUsuario);
                   userResponse.setToken(token);
                   return Response.ok(userResponse).build();
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
   // Clase estática para representar las credenciales del usuario.
   public static class Credentials {
       private String username;
       private String password;
       // Getter para el nombre de usuario.
       public String getUsername() {
           return username;
       }
       // Setter para el nombre de usuario.
       public void setUsername(String username) {
           this.username = username;
       }
       // Getter para la contraseña.
       public String getPassword() {
           return password;
       }
       // Setter para la contraseña.
       public void setPassword(String password) {
           this.password = password;
       }
   }
   // Clase estática para representar la respuesta del usuario autenticado.
   public static class UserResponse {
       private Long id;
       private String username;
       private String role;
       private String token; // Nuevo campo para el token JWT
       // Getter para el ID del usuario.
       public Long getId() {
           return id;
       }
       // Setter para el ID del usuario.
       public void setId(Long id) {
           this.id = id;
       }
       // Getter para el nombre de usuario.
       public String getUsername() {
           return username;
       }
       // Setter para el nombre de usuario.
       public void setUsername(String username) {
           this.username = username;
       }
       // Getter para el rol del usuario.
       public String getRole() {
           return role;
       }
       // Setter para el rol del usuario.
       public void setRole(String role) {
           this.role = role;
       }
       // Getter para el token JWT.
       public String getToken() {
           return token;
       }
       // Setter para el token JWT.
       public void setToken(String token) {
           this.token = token;
       }
   }
}


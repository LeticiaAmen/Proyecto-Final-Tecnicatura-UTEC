package com.servicios.rest;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;

public class LdapService {
    private String ldapHost = "ldap://192.168.1.8"; // IP del servidor AD
    private String adminUsername = "CN=Administrator,CN=Users,DC=utec,DC=local"; // DN del administrador
    private String adminPassword = "P4$$word123"; // Contraseña del administrador

    public boolean authenticate(String username, String password) {
        // Verificar conexión usando credenciales del administrador
        if (!checkAdminConnection()) {
            return false;
        }

        // Usar UPN para juan.perez
        String userUpn = username + "@utec.local";
        System.out.println("Trying to authenticate user: " + userUpn);

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapHost);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, userUpn);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {
            DirContext context = new InitialDirContext(env);
            context.close();
            return true;
        } catch (NamingException e) {
            System.err.println("Error during LDAP authentication: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkAdminConnection() {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapHost);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminUsername);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);

        try {
            DirContext context = new InitialDirContext(env);
            context.close();
            System.out.println("Admin connection successful");
            return true;
        } catch (NamingException e) {
            System.err.println("Error during admin LDAP connection: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean createUser(String username, String password) {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapHost);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminUsername);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);

        try {
            DirContext context = new InitialDirContext(env);

            // Crear atributos para el nuevo usuario
            Attributes attributes = new BasicAttributes();
            Attribute objectClass = new BasicAttribute("objectClass");
            objectClass.add("top");
            objectClass.add("person");
            objectClass.add("organizationalPerson");
            objectClass.add("user");
            attributes.put(objectClass);
            attributes.put("cn", username);
            attributes.put("sn", username.split("\\.")[1]); // Asume que el apellido es la segunda parte del username
            attributes.put("userPrincipalName", username + "@utec.local");
            attributes.put("sAMAccountName", username);
            attributes.put("userPassword", password);

            // Set user account control to enable the account
            attributes.put("userAccountControl", "544"); // 544 = 512 + 32 (normal account + password not required to change at first login)

            // Construir el DN del nuevo usuario
            String userDn = "CN=" + username + ",OU=Estudiantes,DC=utec,DC=local";

            // Crear el usuario en AD
            context.createSubcontext(userDn, attributes);

            // Cerrar el contexto
            context.close();

            System.out.println("User created: " + userDn);
            return true;
        } catch (NamingException e) {
            System.err.println("Error creating user in LDAP: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

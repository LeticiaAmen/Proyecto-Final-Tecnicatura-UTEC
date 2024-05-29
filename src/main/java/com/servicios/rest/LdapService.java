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

        //UPN
        String userUpn = username + "@utec.local";
        
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
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
            return false;
        }
    }
}

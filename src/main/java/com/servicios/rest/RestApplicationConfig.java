package com.servicios.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class RestApplicationConfig extends Application {
    // Esta clase establece el path base para todos los servicios REST.
}

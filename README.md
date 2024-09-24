Proyecto Final de Tecnicatura: Infraestructura de Campus Universitario y Sistema de Gestión de Secretaría
Este proyecto fue desarrollado en equipo y consistió en la creación completa de la infraestructura tecnológica y operativa de un campus universitario, abarcando tanto la parte de redes como el diseño de aplicaciones para la gestión administrativa.

1. Diseño de la Infraestructura de Red
Utilizamos GNS3 para diseñar y simular la red del campus universitario, abarcando la configuración de routers, switches y otros elementos de infraestructura. Esto nos permitió crear una topología escalable y eficiente, optimizando la conectividad en el entorno educativo.

2. Desarrollo de Base de Datos Corporativa
Implementamos una base de datos corporativa utilizando Oracle para almacenar, gestionar y analizar datos clave del sistema. Esta base de datos fue el núcleo de la operativa administrativa, permitiendo la gestión eficiente de información relevante para la secretaría universitaria.

3. Aplicación Web de Gestión de Secretaría
Desarrollamos una aplicación web que simulaba la operación de una secretaría universitaria, con módulos de:

 - Gestión de usuarios: Creación, actualización y eliminación de cuentas, con controles para evitar la duplicación de correos electrónicos.
 - Gestión de reclamos: Registro y seguimiento de solicitudes y reclamos de estudiantes.
El backend fue desarrollado en Java y desplegado en WildFly, utilizando Eclipse como entorno de desarrollo integrado. La aplicación se conectaba directamente con la base de datos Oracle.

4. Aplicación Móvil
Como complemento, desarrollamos una aplicación móvil para la gestión de reclamos, permitiendo a los usuarios acceder al sistema desde sus teléfonos. La app fue creada en Android Studio y utilizaba Retrofit para la comunicación con la API, asegurando una interacción fluida con la base de datos.


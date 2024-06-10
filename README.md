============================

Este proyecto es una aplicacion de gestion de hoteles. En el que hay varios roles los cuales pueden acceder a la aplicación. Existe el rol de cliente el cual tiene acceso a reservar habitaciones en hoteles. Por otra parte tenemos las clases de manager del hotel el cual tienen permisos para añadir hoteles, ver y modificar las reservas de los clientes y por ultimo tenemos el rol de admin, que puede administrar tanto a los usuarios como a los hoteles para modificarlos o eliminarlos.

Para compilar el proyecto ejecutamos el siguiente comando:

    mvn clean compile
      

Para probar los test unitarios introduzca este comando:

    mvn test

Para hacer teses de integración:

    mvn verify -Pintegration-tests
    
Para hacer teses de rendimiento:

    mvn verify -Pperformance-tests

Para ver la cobertura:

    start target/site/jacoco/index.html
    
Para lanzar el servidor ejecutamos el siguiente comando:

    mvn spring-boot:run

Para acceder a la pagina entramos en:

    Acceder a la aplicacion en el navegador, entrando en http://localhost:8080/

Para generar la documentacion usamos:
mvn doxygen:report


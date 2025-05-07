# Spring Boot - API Gestion de Ordenes de Pedidos

Esta es una aplicación Java / Maven / Spring Boot (version 1.0.0) para creacio y consulta (con Paginación) de Ordenes de Pedidos.

### Ejecutar la aplicacion localmente

* Clona el repositorio
* Asegurate de usar JDK 17 o superior y Maven 3.x
* Hay algunas maneras de ejecutar la aplicacion Spring Boot localmente, una manera es ejecutar el metodo `main` de co.apexglobal.ordersmng.ApiOrdersManagementApplication desde un IDE.
* Alternativamente puedes usar [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) de esta manera

```
mvn spring-boot:run
```

* Tambien puedes ejecutarlo con el comando java directamente
```
java -jar target/api-orders-management-1.0.1-SNAPSHOT.jar
```

Una vez que la aplicacion se ejecuta visualizaras algo asi:

```
2025-05-07T15:43:51.272-05:00[0;39m [32m INFO[0;39m [35m10338[0;39m [2m--- [api-orders-management] [  restartedMain] [0;39m[36mo.s.b.w.embedded.tomcat.TomcatWebServer [0;39m [2m:[0;39m Tomcat started on port 8989 (http) with context path '/ordenManagement/v1'
2025-05-07T15:43:51.282-05:00[0;39m [32m INFO[0;39m [35m10338[0;39m [2m--- [api-orders-management] [  restartedMain] [0;39m[36mc.a.o.ApiOrdersManagementApplication    [0;39m [2m:[0;39m Started ApiOrdersManagementApplication in 2.177 seconds (process running for 2.558)
2025-05-07T15:43:51.911-05:00[0;39m [32m INFO[0;39m [35m10338[0;39m [2m--- [api-orders-management] [n(10)-127.0.0.1] [0;39m[36mo.a.c.c.C.[.[.[/ordenManagement/v1]     [0;39m [2m:[0;39m Initializing Spring DispatcherServlet 'dispatcherServlet'
2025-05-07T15:43:51.912-05:00[0;39m [32m INFO[0;39m [35m10338[0;39m [2m--- [api-orders-management] [n(10)-127.0.0.1] [0;39m[36mo.s.web.servlet.DispatcherServlet       [0;39m [2m:[0;39m Initializing Servlet 'dispatcherServlet'
2025-05-07T15:43:51.912-05:00[0;39m [32m INFO[0;39m [35m10338[0;39m [2m--- [api-orders-management] [n(10)-127.0.0.1] [0;39m[36mo.s.web.servlet.DispatcherServlet       [0;39m [2m:[0;39m Completed initialization in 0 ms
```

### Creación de DataBase MongoDB y Coleccion Name

La base de datos es ordenesdb y el nombre de la coleccion es orden

![Screenshot 2025-05-07 at 3 50 40 PM](https://github.com/user-attachments/assets/791d432a-183e-4929-aa97-41eca29ac135)

### Pruebas Unitarias con JUnit y Mockito

El Test de Pruebas Unitarias esta localizado en co.apexglobal.ordersmng.test.OrdenServiceImplTest, este Test cubre el Service para Creacion y Consulta de Ordenes

![Screenshot 2025-05-07 at 3 50 40 PM](https://github.com/user-attachments/assets/a3dc50cf-7584-4782-a521-1aa82a1719de)


### Creación de orden - Response: HTTP 201 (Created)

```
curl -X 'POST' \
  'http://localhost:8080/userManagement/v1/users' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "A23tara20$",
    "phones": [
            {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
            },
            {
            "number": "994373861",
            "cityCode": "12",
            "countryCode": "58"
            }
    ]
}'
```

### Consultar la lista de ordenes con paginación y filtrado por usuario - Response: HTTP 200 (OK)

```
curl -X 'GET' \
  'http://localhost:8989/ordenManagement/v1/ordenes' \
  -H 'accept: application/json'
```

```
curl -X 'GET' \
  'http://localhost:8989/ordenManagement/v1/ordenes?page=0&size=3' \
  -H 'accept: application/json'
```

```
curl -X 'GET' \
  'http://localhost:8989/ordenManagement/v1/ordenes?page=0&size=3&idUsuario=xtrivino3' \
  -H 'accept: application/json'
```

### Para visualizar Swagger 2 API docs

Ejecutar en el navegador http://localhost:8989/ordenManagement/v1/swagger-ui/index.html

![Screenshot 2025-05-07 at 4 06 28 PM](https://github.com/user-attachments/assets/73ebfe3e-3356-421b-8983-d5b2a7b69024)


### Arquitectura de la Aplicacion




### Preguntas y comentarios: fxtrivino@gmail.com

# Spring Boot - API Gestion de Ordenes de Compras

Esta es una aplicación Java / Maven / Spring Boot (version 1.0.0) para creacio y consulta (con Paginación) de Ordenes de Compras.

### 1. Ejecutar la aplicacion localmente

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
2025-05-07T15:43:51.272-05:00 [0;39m  [32m INFO [0;39m  [35m10338 [0;39m  [2m--- [api-orders-management] [  restartedMain]  [0;39m [36mo.s.b.w.embedded.tomcat.TomcatWebServer  [0;39m  [2m: [0;39m Tomcat started on port 8989 (http) with context path '/ordenManagement/v1'
2025-05-07T15:43:51.282-05:00 [0;39m  [32m INFO [0;39m  [35m10338 [0;39m  [2m--- [api-orders-management] [  restartedMain]  [0;39m [36mc.a.o.ApiOrdersManagementApplication     [0;39m  [2m: [0;39m Started ApiOrdersManagementApplication in 2.177 seconds (process running for 2.558)
2025-05-07T15:43:51.911-05:00 [0;39m  [32m INFO [0;39m  [35m10338 [0;39m  [2m--- [api-orders-management] [n(10)-127.0.0.1]  [0;39m [36mo.a.c.c.C.[.[.[/ordenManagement/v1]      [0;39m  [2m: [0;39m Initializing Spring DispatcherServlet 'dispatcherServlet'
2025-05-07T15:43:51.912-05:00 [0;39m  [32m INFO [0;39m  [35m10338 [0;39m  [2m--- [api-orders-management] [n(10)-127.0.0.1]  [0;39m [36mo.s.web.servlet.DispatcherServlet        [0;39m  [2m: [0;39m Initializing Servlet 'dispatcherServlet'
2025-05-07T15:43:51.912-05:00 [0;39m  [32m INFO [0;39m  [35m10338 [0;39m  [2m--- [api-orders-management] [n(10)-127.0.0.1]  [0;39m [36mo.s.web.servlet.DispatcherServlet        [0;39m  [2m: [0;39m Completed initialization in 0 ms
```

### 2. Creación de DataBase MongoDB y Coleccion Name

La base de datos es ordenesdb y el nombre de la coleccion es orden

![Screenshot 2025-05-07 at 3 50 40 PM](https://github.com/user-attachments/assets/791d432a-183e-4929-aa97-41eca29ac135)

### 3. Configuración de MongoDB, Redis y Kafka

- MongDB Configuration  
spring.data.mongodb.uri=mongodb://localhost:27017/ordenesdb  
spring.data.mongodb.port=27017  

- Redis Configuration  
spring.cache.type=redis  
spring.redis.host=localhost  
spring.redis.port=6379  

- Kafka Configuration  
spring.kafka.bootstrap-servers=localhost:9092  
spring.kafka.consumer.group-id=orders-group  
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer  
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer  

### 4. Pruebas Unitarias con JUnit y Mockito

El Test de Pruebas Unitarias esta localizado en co.apexglobal.ordersmng.test.OrdenServiceImplTest, este Test cubre el Service para Creacion y Consulta de Ordenes

![Screenshot 2025-05-07 at 3 52 30 PM](https://github.com/user-attachments/assets/623cdcd0-b81d-42c9-ae8a-15232044d7ef)


### 5. Creación de orden - Response: HTTP 201 (Created)
Se crea la orden en a Base de Datos MongoDB, se envia informacion de la Orden creada al topico "ordenes_creadas" en Kafka, y se invalida caché en Redis

```
curl -X 'POST' \
  'http://localhost:8989/ordenManagement/v1/ordenes' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "idUsuario": "xtrivino9",
  "items": [
    {
      "idProducto": "prod-001",
      "cantidad": 10,
      "precioUnitario": 23.45
    }
  ]
}'
```
![Screenshot 2025-05-07 at 6 39 44 PM](https://github.com/user-attachments/assets/e3fa057c-40b5-4c0f-b55d-490b59e5d342)

- Kafka
Topic: "ordenes_creadas"

![Screenshot 2025-05-07 at 6 41 28 PM](https://github.com/user-attachments/assets/3ebcb9ec-ea84-4f15-badf-c375430e0efa)


### 6. Consultar la lista de ordenes con paginación y filtrado por usuario - Response: HTTP 200 (OK)
Se consulta las ordenes en la Base de Datos MongoDB y se almacena en caché (Redis) las ordenes consultadas

```
curl -X 'GET' \
  'http://localhost:8989/ordenManagement/v1/ordenes' \
  -H 'accept: application/json'
```

```
curl -X 'GET' \
  'http://localhost:8989/ordenManagement/v1/ordenes?idUsuario=xtrivino7' \
  -H 'accept: application/json'
```

```
curl -X 'GET' \
  'http://localhost:8989/ordenManagement/v1/ordenes?page=0&size=3' \
  -H 'accept: application/json'
```

```
curl -X 'GET' \
  'http://localhost:8989/ordenManagement/v1/ordenes?page=0&size=3&idUsuario=xtrivino7' \
  -H 'accept: application/json'
```

- Redis

![Screenshot 2025-05-08 at 5 13 22 AM](https://github.com/user-attachments/assets/8c45e4da-2abc-4272-9605-953c181db17b)



### 7. Para visualizar Swagger 2 API docs

Ejecutar en el navegador http://localhost:8989/ordenManagement/v1/swagger-ui/index.html

![Screenshot 2025-05-07 at 4 06 28 PM](https://github.com/user-attachments/assets/73ebfe3e-3356-421b-8983-d5b2a7b69024)


### 8. Arquitectura de la Aplicacion

![API-Ordenes drawio (2)](https://github.com/user-attachments/assets/3597ba5c-fb43-4108-8acd-029ef969bdd8)


### 9. Preguntas y comentarios: fxtrivino@gmail.com

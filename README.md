# TPI – Programación II  
## Sistema Pedido → Envío (Relación 1 → 1)  
**UTN – Tecnicatura Universitaria en Programación a Distancia**  
**Comisión 8 – Año 2025**

---

## 📌 1. Descripción del dominio  
El proyecto implementa un **sistema de gestión de Pedidos y Envíos**, donde cada *Pedido* puede tener **un único Envío asociado** (relación **1 → 1 unidireccional**).

- **Pedido** incluye número, fecha, cliente, total, estado y baja lógica.  
- **Envío** incluye tracking, empresa, tipo, costo, fechas y estado.  
- La relación se garantiza mediante una **FK única en `envio.pedido_id`**.

Este trabajo integra conceptos de Programación II y Bases de Datos I aplicando:
- POO  
- JDBC  
- Patrón DAO  
- Transacciones (commit / rollback)  
- Validaciones  
- Arquitectura por capas  
- Manejo de excepciones  

---

## 📌 2. Requisitos del proyecto  
### ✔ Software necesario
- **Java 21** (o versión compatible)
- **NetBeans 18+ o 19+**
- **MySQL 8**
- **MySQL Connector/J 9.0+**
- **Git** (para clonar el repositorio)

---

## 📌 3. Cómo crear la base de datos  
Dentro del repositorio vas a encontrar la carpeta:

/sql
├── 01_create_database.sql
└── 02_insert_data.sql


### ▶ Crear la base  
Ejecutar en MySQL Workbench:

1. Abrir **01_create_database.sql**
2. Ejecutar todo el script (icono ⚡)
3. Verificar que se creó la BD: `tp_p2_pedido_envio`

### ▶ Insertar datos de prueba  
Ejecutar **02_insert_data.sql** de la misma forma.

La BD quedará con:
- 3 pedidos base  
- 3 envíos base  
- Relación 1 → 1 garantizada  

---

## 📌 4. Cómo compilar y ejecutar el sistema  
### ✔ 1. Clonar el repositorio  
git clone https://github.com/stefanmdev/TPI-TUPaD-P2.git


### ✔ 2. Importar en NetBeans
- File → Open Project  
- Seleccionar la carpeta del proyecto

### ✔ 3. Verificar dependencias
En *Libraries* debe aparecer:

mysql-connector-j-9.0.x.jar


Si no está:
1. Click derecho en Libraries → Add JAR/Folder  
2. Seleccionar el conector  

### ✔ 4. Ejecutar  
Run → Run Project (F6)

---

## 📌 5. Credenciales de conexión  
Configurar en:

`src/config/DatabaseConnection.java`

Valores utilizados:

```java
private static final String URL = "jdbc:mysql://localhost:3306/tp_p2_pedido_envio?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "TU_PASSWORD_DE_MYSQL";

## 📌 6. Flujo de uso del sistema

Al iniciar se muestra el menú:

1) Crear Pedido (sin envío)
2) Crear Pedido con Envío (transacción)
3) Listar todos los pedidos
4) Buscar pedido por número
5) Eliminar lógicamente un pedido
0) Salir

# Funciones principales:

- Creación de pedido

- Creación de pedido+envío con transacción real (commit/rollback)

- Búsqueda por número

- Baja lógica

- Validación de duplicados

- Listado general

# Errores controlados:

- Número de pedido duplicado

- Fecha inválida

- ID inexistente en borrado

- Estado incorrecto

## 📌 7. UML del proyecto

El diagrama se encuentra en:

/uml/pedido_envio.png
/uml/pedido_envio.puml


# Representa:

- Entidades

- Enums

- Relación 1 → 1

- Atributos + métodos públicos

## 📌 8. Integrantes del grupo

Grupo 212 – Comisión 8

| Integrante          | Rol                                    |
| ------------------- | -------------------------------------- |
| Stefan Dios Mayarin | Coordinador – Menú – QA – Pruebas      |
| Mathias Flor        | Modelo – Entidades – UML               |
| Joaquín Villaruel   | Base de Datos – Scripts SQL            |
| Ale Farfán          | DAO – JDBC – Servicios – Transacciones |

## 📌 9. Video de presentación

Enlace al video explicativo (YouTube):
👉 [Pendiente de cargar]

## 📌 10. Informe PDF

El informe completo del TPI se encuentra en:

/documentacion/informe_tpi.pdf

# Incluye:

- Justificación del dominio

- Arquitectura por capas

- Modelo UML

- Secuencia transaccional

- Validaciones

- Pruebas capturadas

- Conclusiones

## 📌 11. Licencia

Proyecto académico – UTN – Programación II.
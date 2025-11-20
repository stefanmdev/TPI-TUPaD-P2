# TPI – Programación II  
## 📦 Sistema Pedido → Envío (Relación 1 → 1)  
**UTN – Tecnicatura Universitaria en Programación a Distancia**  
**Comisión 8**

---

## 📌 1. Descripción del dominio  
El proyecto implementa un **sistema de gestión de Pedidos y Envíos**, donde cada *Pedido* puede tener **un único Envío asociado** (relación **1 → 1 unidireccional**).

- 📄 **Pedido**: número, fecha, cliente, total, estado, baja lógica.  
- 📦 **Envío**: tracking, empresa, tipo, costo, fechas, estado.  
- 🔗 Relación garantizada mediante **FK UNIQUE** en `envio.pedido_id`.

Incluye conceptos de Programación II y BD I:

- 🧱 POO  
- 🛢️ JDBC + PreparedStatement  
- 🧩 Patrón DAO  
- 🔄 Transacciones (commit / rollback)  
- 🛡️ Validaciones  
- 🏛️ Arquitectura por capas  
- ⚠️ Manejo de excepciones  

---

## 📌 2. Requisitos del proyecto  
### ✔ Software necesario
- Java 21  
- NetBeans 18+  
- MySQL 8  
- MySQL Connector/J 9+  
- Git  

---

## 📌 3. Creación de la base de datos  

Carpeta del repositorio:  

```
/sql
├── 01_create_database.sql
└── 02_insert_data.sql
```

### ▶️ Crear base  
1. Abrir **01_create_database.sql**  
2. Ejecutar todo  
3. Verificar BD: `tp_p2_pedido_envio`

### ▶️ Insertar datos de prueba  
Ejecutar **02_insert_data.sql**.

---

## 📌 4. Cómo compilar y ejecutar  
### ✔ Clonar el repositorio
```bash
git clone https://github.com/stefanmdev/TPI-TUPaD-P2.git
```

### ✔ Importar en NetBeans  
File → Open Project.

### ✔ Verificar dependencias  
Debe existir en *Libraries*:  
`mysql-connector-j-9.x.x.jar`

### ✔ Ejecutar  
Run Project (F6)

---

## 📌 5. Credenciales de conexión  
Archivo: `src/config/DatabaseConnection.java`

```java
private static final String URL = "jdbc:mysql://localhost:3306/tp_p2_pedido_envio?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "TU_PASSWORD";
```

---

## 📌 6. Flujo de uso del sistema  
### 🧭 Menú principal
```
1) Crear Pedido  
2) Crear Pedido con Envío (transacción)  
3) Listar pedidos  
4) Buscar por número  
5) Baja lógica  
0) Salir
```

### 🟦 Funciones principales
- Creación de Pedido  
- Creación de Pedido + Envío con transacción real  
- Validaciones  
- Errores controlados  
- Búsqueda por número  
- Baja lógica  

---

## 📌 7. UML del proyecto  

Ubicación:  
```
/uml/pedido_envio.png
/uml/pedido_envio.puml
```

Incluye: Entidades, atributos, enums y relación 1 → 1.

---

## 📌 8. Integrantes del grupo  
### 👥 Grupo 212 – Comisión 8

| Integrante | Rol |
|-----------|-----|
| **Stefan Dios Mayarin** | Coordinador – Menú – QA – Pruebas |
| **Mathias Flor** | Modelo – Entidades – UML |
| **Joaquín Villaruel** | Base de Datos – SQL |
| **Ale Farfán** | DAO – JDBC – Servicios – Transacciones |

---

## 📌 9. Video del TPI  
🎬 https://www.youtube.com/watch?v=ocRj5HeqzsA

---

## 📌 10. Informe PDF  
📄 `/documentacion/TPI-Prog2.pdf`

Incluye: dominio, arquitectura, UML, SQL, transacciones, pruebas y conclusiones.

---

## 📌 11. Licencia  
📘 Proyecto académico | UTN–TUPaD | Programación II.

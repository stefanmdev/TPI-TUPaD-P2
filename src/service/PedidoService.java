/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import config.DatabaseConnection;
import dao.EnvioDao;
import dao.EnvioDaoImpl;
import dao.PedidoDao;
import dao.PedidoDaoImpl;
import entities.Envio;
import entities.Pedido;

import java.sql.Connection;
import java.util.List;


/**
 *
 * @author DELL
 */
public class PedidoService {
    private final PedidoDao pedidoDao = new PedidoDaoImpl();
    private final EnvioDao envioDao = new EnvioDaoImpl();

    // ----------------- OPERACIONES SOLO PEDIDO -----------------

    /**
     * Crea un pedido simple.
     * Valida que NO exista otro pedido con el mismo número.
     */
    public void crearPedido(Pedido pedido) throws ServiceException {
        try {
            // 1) Validar número duplicado (usa el DAO que ya tenés)
            Pedido existente = pedidoDao.obtenerPorNumero(pedido.getNumero());
            if (existente != null) {
                // Mensaje claro para mostrar en el menú
                throw new ServiceException("ya existe un pedido con ese número");
            }

            // 2) Insertar el nuevo pedido
            pedidoDao.crear(pedido);

        } catch (ServiceException e) {
            // Ya viene con mensaje correcto
            throw e;

        } catch (Exception e) {
            // Otros errores técnicos (SQL, conexión, etc.)
            throw new ServiceException("Error al crear el pedido", e);
        }
    }

    public Pedido obtenerPedidoPorId(Long id) throws ServiceException {
        try {
            return pedidoDao.obtenerPorId(id);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener el pedido por id", e);
        }
    }

    public Pedido obtenerPedidoPorNumero(String numero) throws ServiceException {
        try {
            return pedidoDao.obtenerPorNumero(numero);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener el pedido por número", e);
        }
    }

    public List<Pedido> obtenerTodosLosPedidos() throws ServiceException {
        try {
            return pedidoDao.obtenerTodos();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Error al listar los pedidos", e);
        }
    }

    public void actualizarPedido(Pedido pedido) throws ServiceException {
        try {
            pedidoDao.actualizar(pedido);
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar el pedido", e);
        }
    }

    public void eliminarPedidoLogico(Long id) throws ServiceException {
    try {
        // Ver si existe un pedido ACTIVO con ese id (el DAO ya filtra eliminado = 0)
        Pedido pedido = pedidoDao.obtenerPorId(id);

        if (pedido == null) {
            // No existe o ya estaba eliminado
            throw new ServiceException("no existe un pedido activo con ese ID");
        }

        // Si existe y está activo, lo marcamos como eliminado
        pedidoDao.eliminarLogico(id);

    } catch (ServiceException e) {
        throw e;
    } catch (Exception e) {
        throw new ServiceException("Error al eliminar lógicamente el pedido", e);
    }
}    

    // ----------------- OPERACIÓN TRANSACCIONAL: PEDIDO + ENVÍO -----------------

    /**
     * Crea un pedido y su envío asociado en una sola transacción.
     * Si algo falla, hace rollback.
     */
    public void crearPedidoConEnvio(Pedido pedido, Envio envio) throws ServiceException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // inicia la transacción

            // 0) Validar número duplicado ANTES de insertar, usando la misma conexión
            Pedido existente = pedidoDao.obtenerPorNumero(pedido.getNumero(), conn);
            if (existente != null) {
                throw new ServiceException("ya existe un pedido con ese número");
            }

            // 1) Crear pedido
            pedidoDao.crear(pedido, conn);

            // 2) Crear envío
            envioDao.crear(envio, conn);

            // 3) Vincular envío con pedido
            envioDao.asignarPedido(envio.getId(), pedido.getId(), conn);

            conn.commit(); // todo OK

        } catch (ServiceException e) {
            // Error de negocio (por ejemplo, número duplicado)
            rollback(conn);
            throw e;

        } catch (Exception e) {
            // Error técnico (SQL, etc.)
            rollback(conn);
            throw new ServiceException("Error al crear pedido con envío (se hizo rollback)", e);

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception ignored) {}
            }
        }
    }

    private void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (Exception ignored) {}
    }
}

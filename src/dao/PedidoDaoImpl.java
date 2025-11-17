/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import entities.Envio;
import entities.EstadoPedido;
import entities.Pedido;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class PedidoDaoImpl implements PedidoDao {
    private final EnvioDao envioDao = new EnvioDaoImpl();

    private Pedido mapResultSet(ResultSet rs) throws SQLException {
    Pedido pedido = new Pedido();
    pedido.setId(rs.getLong("id"));
    pedido.setEliminado(rs.getBoolean("eliminado"));
    pedido.setNumero(rs.getString("numero"));
    pedido.setFecha(rs.getDate("fecha").toLocalDate());
    pedido.setClienteNombre(rs.getString("cliente_nombre"));
    pedido.setTotal(rs.getDouble("total"));

    String estadoStr = rs.getString("estado");
    if (estadoStr != null) {
        pedido.setEstado(EstadoPedido.valueOf(estadoStr));
    }

    // Por ahora NO cargamos el envío desde acá (se podría hacer con un JOIN),
    // así que lo dejamos en null:
    pedido.setEnvio(null);

    return pedido;
}


    // ---------- Métodos con conexión interna ----------

    @Override
    public void crear(Pedido entity) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            crear(entity, conn);
        }
    }

    @Override
    public Pedido obtenerPorId(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM pedido WHERE id = ? AND eliminado = 0";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapResultSet(rs);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<Pedido> obtenerTodos() throws Exception {
        List<Pedido> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM pedido WHERE eliminado = 0";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public void actualizar(Pedido entity) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            actualizar(entity, conn);
        }
    }

    @Override
    public void eliminarLogico(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            eliminarLogico(id, conn);
        }
    }

    @Override
    public Pedido obtenerPorNumero(String numero) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return obtenerPorNumero(numero, conn);
        }
    }

    // ---------- Métodos con conexión externa (transacciones) ----------

        @Override
    public void crear(Pedido entity, Connection conn) throws Exception {
        String sql = "INSERT INTO pedido (eliminado, numero, fecha, cliente_nombre, total, estado) " +
                     "VALUES (0, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getNumero());
            ps.setDate(2, Date.valueOf(entity.getFecha()));
            ps.setString(3, entity.getClienteNombre());
            ps.setDouble(4, entity.getTotal());
            ps.setString(5, entity.getEstado().name());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public void actualizar(Pedido entity, Connection conn) throws Exception {
        String sql = "UPDATE pedido SET numero = ?, fecha = ?, cliente_nombre = ?, " +
                     "total = ?, estado = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getNumero());
            ps.setDate(2, Date.valueOf(entity.getFecha()));
            ps.setString(3, entity.getClienteNombre());
            ps.setDouble(4, entity.getTotal());
            ps.setString(5, entity.getEstado().name());
            ps.setLong(6, entity.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminarLogico(Long id, Connection conn) throws Exception {
        String sql = "UPDATE pedido SET eliminado = 1 WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Pedido obtenerPorNumero(String numero, Connection conn) throws Exception {
        String sql = "SELECT * FROM pedido WHERE numero = ? AND eliminado = 0";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }
        return null;
    }
}


/**
 * comentario agregado por Alejandro Farfan revision DAO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import entities.Envio;
import entities.EmpresaEnvio;
import entities.EstadoEnvio;
import entities.TipoEnvio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class EnvioDaoImpl implements EnvioDao {
    private Envio mapResultSet(ResultSet rs) throws SQLException {
        Envio envio = new Envio();
        envio.setId(rs.getLong("id"));
        envio.setEliminado(rs.getBoolean("eliminado"));
        envio.setTracking(rs.getString("tracking"));

        String empresaStr = rs.getString("empresa");
        if (empresaStr != null) {
            envio.setEmpresa(EmpresaEnvio.valueOf(empresaStr));
        }

        String tipoStr = rs.getString("tipo");
        if (tipoStr != null) {
            envio.setTipo(TipoEnvio.valueOf(tipoStr));
        }

        envio.setCosto(rs.getDouble("costo"));

        Date fDesp = rs.getDate("fecha_despacho");
        if (fDesp != null) {
            envio.setFechaDespacho(fDesp.toLocalDate());
        }

        Date fEst = rs.getDate("fecha_estimada");
        if (fEst != null) {
            envio.setFechaEstimada(fEst.toLocalDate());
        }

        String estadoStr = rs.getString("estado");
        if (estadoStr != null) {
            envio.setEstado(EstadoEnvio.valueOf(estadoStr));
        }

        return envio;
    }

    // ---------- Métodos con conexión interna ----------

    @Override
    public void crear(Envio entity) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            crear(entity, conn);
        }
    }

    @Override
    public Envio obtenerPorId(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM envio WHERE id = ? AND eliminado = 0";
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
    public List<Envio> obtenerTodos() throws Exception {
        List<Envio> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM envio WHERE eliminado = 0";
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
    public void actualizar(Envio entity) throws Exception {
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

    // ---------- Métodos con conexión externa (para transacciones) ----------

    @Override
    public void crear(Envio entity, Connection conn) throws Exception {
        String sql = "INSERT INTO envio (eliminado, tracking, empresa, tipo, costo," +
                     " fecha_despacho, fecha_estimada, estado, pedido_id) " +
                     "VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getTracking());
            if (entity.getEmpresa() != null) {
                ps.setString(2, entity.getEmpresa().name());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }
            if (entity.getTipo() != null) {
                ps.setString(3, entity.getTipo().name());
            } else {
                ps.setNull(3, Types.VARCHAR);
            }
            ps.setDouble(4, entity.getCosto());

            if (entity.getFechaDespacho() != null) {
                ps.setDate(5, Date.valueOf(entity.getFechaDespacho()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            if (entity.getFechaEstimada() != null) {
                ps.setDate(6, Date.valueOf(entity.getFechaEstimada()));
            } else {
                ps.setNull(6, Types.DATE);
            }

            if (entity.getEstado() != null) {
                ps.setString(7, entity.getEstado().name());
            } else {
                ps.setNull(7, Types.VARCHAR);
            }

            // pedido_id se seteará luego, cuando se conozca el id del Pedido
            ps.setNull(8, Types.BIGINT);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public void actualizar(Envio entity, Connection conn) throws Exception {
        String sql = "UPDATE envio SET tracking = ?, empresa = ?, tipo = ?, costo = ?," +
                     " fecha_despacho = ?, fecha_estimada = ?, estado = ? " +
                     "WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.getTracking());
            if (entity.getEmpresa() != null) {
                ps.setString(2, entity.getEmpresa().name());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }
            if (entity.getTipo() != null) {
                ps.setString(3, entity.getTipo().name());
            } else {
                ps.setNull(3, Types.VARCHAR);
            }
            ps.setDouble(4, entity.getCosto());

            if (entity.getFechaDespacho() != null) {
                ps.setDate(5, Date.valueOf(entity.getFechaDespacho()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            if (entity.getFechaEstimada() != null) {
                ps.setDate(6, Date.valueOf(entity.getFechaEstimada()));
            } else {
                ps.setNull(6, Types.DATE);
            }

            if (entity.getEstado() != null) {
                ps.setString(7, entity.getEstado().name());
            } else {
                ps.setNull(7, Types.VARCHAR);
            }

            ps.setLong(8, entity.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminarLogico(Long id, Connection conn) throws Exception {
        String sql = "UPDATE envio SET eliminado = 1 WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
    @Override
    public void asignarPedido(Long envioId, Long pedidoId, Connection conn) throws Exception {
        String sql = "UPDATE envio SET pedido_id = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, pedidoId);
            ps.setLong(2, envioId);
            ps.executeUpdate();
        }
    }
}

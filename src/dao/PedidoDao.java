/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entities.Pedido;
import java.sql.Connection;

/**
 *
 * @author DELL
 */
public interface PedidoDao extends GenericDao<Pedido> {
    // Búsqueda por campo único (numero de pedido)
    Pedido obtenerPorNumero(String numero) throws Exception;

    // Versión con conexión externa
    Pedido obtenerPorNumero(String numero, Connection conn) throws Exception;
}

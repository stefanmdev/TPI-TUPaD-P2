/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entities.Envio;
import java.sql.Connection;

/**
 *
 * @author DELL
 */
public interface EnvioDao extends GenericDao<Envio> {
    // Método extra para vincular el envío con un pedido
    void asignarPedido(Long envioId, Long pedidoId, Connection conn) throws Exception;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface GenericDao<T>{
     // CRUD básico – usando una conexión interna
    void crear(T entity) throws Exception;

    T obtenerPorId(Long id) throws Exception;

    List<T> obtenerTodos() throws Exception;

    void actualizar(T entity) throws Exception;

    void eliminarLogico(Long id) throws Exception;

    // Versiones con conexión externa (para transacciones)
    void crear(T entity, Connection conn) throws Exception;

    void actualizar(T entity, Connection conn) throws Exception;

    void eliminarLogico(Long id, Connection conn) throws Exception;
}

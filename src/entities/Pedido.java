/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.time.LocalDate;

/**
 *
 * @author DELL
 */
public class Pedido {
    private Long id;
    private Boolean eliminado;
    private String numero;          // único, máx 20
    private LocalDate fecha;
    private String clienteNombre;   // máx 120
    private double total;           // 12,2
    private EstadoPedido estado;
    private Envio envio;            // relación 1 -> 1

    public Pedido() {
        this.eliminado = false;
    }

    public Pedido(Long id, Boolean eliminado, String numero, LocalDate fecha,
                  String clienteNombre, double total,
                  EstadoPedido estado, Envio envio) {
        this.id = id;
        this.eliminado = eliminado;
        this.numero = numero;
        this.fecha = fecha;
        this.clienteNombre = clienteNombre;
        this.total = total;
        this.estado = estado;
        this.envio = envio;
    }

    // Constructor sin id (para nuevas inserciones)
    public Pedido(String numero, LocalDate fecha,
                  String clienteNombre, double total,
                  EstadoPedido estado, Envio envio) {
        this(null, false, numero, fecha, clienteNombre, total, estado, envio);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Envio getEnvio() {
        return envio;
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
    }

    @Override
    public String toString() {
        return "Pedido{" +
               "id=" + id +
               ", eliminado=" + eliminado +
               ", numero='" + numero + '\'' +
               ", fecha=" + fecha +
               ", clienteNombre='" + clienteNombre + '\'' +
               ", total=" + total +
               ", estado=" + estado +
               ", envio=" + envio +
               '}';
    }
}

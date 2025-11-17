/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entities.EmpresaEnvio;
import entities.Envio;
import entities.EstadoEnvio;
import entities.EstadoPedido;
import entities.Pedido;
import entities.TipoEnvio;
import service.PedidoService;
import service.ServiceException;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author DELL
 */
public class AppMenu {
    private final Scanner scanner;
    private final PedidoService pedidoService;

    public AppMenu() {
        this.scanner = new Scanner(System.in);
        this.pedidoService = new PedidoService();
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Ingrese una opción: ");

            switch (opcion) {
                case 1 -> crearPedidoSimple();
                case 2 -> crearPedidoConEnvio();
                case 3 -> listarPedidos();
                case 4 -> buscarPedidoPorNumero();
                case 5 -> eliminarPedidoLogico();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("========================================");
        System.out.println("   SISTEMA PEDIDO → ENVÍO (TPI Prog2)   ");
        System.out.println("========================================");
        System.out.println("1) Crear Pedido (sin envío)");
        System.out.println("2) Crear Pedido con Envío (transacción)");
        System.out.println("3) Listar todos los pedidos");
        System.out.println("4) Buscar pedido por número");
        System.out.println("5) Eliminar lógicamente un pedido");
        System.out.println("0) Salir");
        System.out.println("========================================");
    }

    // ----------------- Opciones del menú -----------------

    private void crearPedidoSimple() {
        System.out.println("--- Crear Pedido (sin envío) ---");

        String numero = leerTexto("Número de pedido: ");
        LocalDate fecha = leerFecha("Fecha (DD/MM/YYYY): ");
        String cliente = leerTexto("Nombre del cliente: ");
        double total = leerDouble("Total: ");
        EstadoPedido estado = leerEstadoPedido();

        Pedido pedido = new Pedido(numero, fecha, cliente, total, estado, null);

        try {
            pedidoService.crearPedido(pedido);
            System.out.println("Pedido creado con ID: " + pedido.getId());
        } catch (ServiceException e) {
            System.out.println("Error al crear pedido: " + e.getMessage());
        }
    }

    private void crearPedidoConEnvio() {
        System.out.println("--- Crear Pedido + Envío (transacción) ---");

        // Datos del pedido
        String numero = leerTexto("Número de pedido: ");
        LocalDate fecha = leerFecha("Fecha (DD/MM/YYYY): ");
        String cliente = leerTexto("Nombre del cliente: ");
        double total = leerDouble("Total: ");
        EstadoPedido estado = leerEstadoPedido();

        Pedido pedido = new Pedido(numero, fecha, cliente, total, estado, null);

        // Datos del envío
        String tracking = leerTexto("Código de tracking: ");
        EmpresaEnvio empresa = leerEmpresaEnvio();
        TipoEnvio tipo = leerTipoEnvio();
        double costo = leerDouble("Costo de envío: ");
        LocalDate fechaDespacho = leerFechaOpcional("Fecha de despacho (DD/MM/YYYY, vacío para nulo): ");
        LocalDate fechaEstimada = leerFechaOpcional("Fecha estimada (DD/MM/YYYY, vacío para nulo): ");
        EstadoEnvio estadoEnvio = leerEstadoEnvio();

        Envio envio = new Envio(tracking, empresa, tipo, costo,
                fechaDespacho, fechaEstimada, estadoEnvio);

        try {
            pedidoService.crearPedidoConEnvio(pedido, envio);
            System.out.println("Pedido y envío creados correctamente.");
            System.out.println("ID Pedido: " + pedido.getId());
            System.out.println("ID Envío: " + envio.getId());
        } catch (ServiceException e) {
            System.out.println("Error en transacción Pedido+Envío: " + e.getMessage());
        }
    }

    private void listarPedidos() {
        System.out.println("--- Listar pedidos ---");
        try {
            List<Pedido> pedidos = pedidoService.obtenerTodosLosPedidos();
            if (pedidos.isEmpty()) {
                System.out.println("No hay pedidos cargados.");
            } else {
                pedidos.forEach(System.out::println);
            }
        } catch (ServiceException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }
    }

    private void buscarPedidoPorNumero() {
        System.out.println("--- Buscar pedido por número ---");
        String numero = leerTexto("Número de pedido: ");
        try {
            Pedido pedido = pedidoService.obtenerPedidoPorNumero(numero);
            if (pedido == null) {
                System.out.println("No se encontró un pedido con ese número.");
            } else {
                System.out.println(pedido);
            }
        } catch (ServiceException e) {
            System.out.println("Error al buscar pedido: " + e.getMessage());
        }
    }

    private void eliminarPedidoLogico() {
        System.out.println("--- Eliminar lógicamente un pedido ---");
        Long id = (long) leerEntero("ID del pedido: ");
        try {
            pedidoService.eliminarPedidoLogico(id);
            System.out.println("Pedido marcado como eliminado.");
        } catch (ServiceException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
        }
    }

    // ----------------- Métodos de lectura helper -----------------

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = scanner.nextLine();
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero.");
            }
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = scanner.nextLine();
                return Double.parseDouble(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }

    private LocalDate leerFecha(String mensaje) {
    DateTimeFormatter formatoDMA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    while (true) {
        try {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();

            if (linea.contains("/")) {
                // Formato día/mes/año -> 17/11/2025
                return LocalDate.parse(linea, formatoDMA);
            } else {
                // Formato ISO por si alguien usa 2025-11-17
                return LocalDate.parse(linea);
            }

        } catch (Exception e) {
            System.out.println("Formato inválido. Use DD/MM/YYYY o YYYY-MM-DD.");
        }
    }
}
    
    private LocalDate leerFechaOpcional(String mensaje) {
    DateTimeFormatter formatoDMA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    while (true) {
        System.out.print(mensaje);
        String linea = scanner.nextLine().trim();

        if (linea.isEmpty()) {
            return null;
        }

        try {
            if (linea.contains("/")) {
                // Formato día/mes/año -> 17/11/2025
                return LocalDate.parse(linea, formatoDMA);
            } else {
                // Formato ISO -> 2025-11-17
                return LocalDate.parse(linea);
            }
        } catch (Exception e) {
            System.out.println("Formato inválido. Use DD/MM/YYYY, YYYY-MM-DD o deje vacío.");
        }
    }
}

    private EstadoPedido leerEstadoPedido() {
        System.out.println("Estado del pedido:");
        System.out.println("1) NUEVO");
        System.out.println("2) FACTURADO");
        System.out.println("3) ENVIADO");
        int op = leerEntero("Seleccione opción: ");
        return switch (op) {
            case 2 -> EstadoPedido.FACTURADO;
            case 3 -> EstadoPedido.ENVIADO;
            default -> EstadoPedido.NUEVO;
        };
    }

    private EmpresaEnvio leerEmpresaEnvio() {
        System.out.println("Empresa de envío:");
        System.out.println("1) ANDREANI");
        System.out.println("2) OCA");
        System.out.println("3) CORREO_ARG");
        int op = leerEntero("Seleccione opción: ");
        return switch (op) {
            case 2 -> EmpresaEnvio.OCA;
            case 3 -> EmpresaEnvio.CORREO_ARG;
            default -> EmpresaEnvio.ANDREANI;
        };
    }

    private TipoEnvio leerTipoEnvio() {
        System.out.println("Tipo de envío:");
        System.out.println("1) ESTANDAR");
        System.out.println("2) EXPRES");
        int op = leerEntero("Seleccione opción: ");
        return (op == 2) ? TipoEnvio.EXPRES : TipoEnvio.ESTANDAR;
    }

    private EstadoEnvio leerEstadoEnvio() {
        System.out.println("Estado del envío:");
        System.out.println("1) EN_PREPARACION");
        System.out.println("2) EN_TRANSITO");
        System.out.println("3) ENTREGADO");
        int op = leerEntero("Seleccione opción: ");
        return switch (op) {
            case 2 -> EstadoEnvio.EN_TRANSITO;
            case 3 -> EstadoEnvio.ENTREGADO;
            default -> EstadoEnvio.EN_PREPARACION;
        };
    }
}

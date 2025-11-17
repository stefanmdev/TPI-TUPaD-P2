USE tp_p2_pedido_envio;

INSERT INTO pedido (numero, fecha, cliente_nombre, total, estado)
VALUES
('PED-0001', '2025-11-01', 'Juan Pérez', 15000.00, 'NUEVO'),
('PED-0002', '2025-11-02', 'María Gómez', 24500.50, 'FACTURADO'),
('PED-0003', '2025-11-03', 'Carlos López', 8000.00, 'ENVIADO');

INSERT INTO envio (tracking, empresa, tipo, costo,
                   fecha_despacho, fecha_estimada,
                   estado, pedido_id)
VALUES
('TRK-AND-0001', 'ANDREANI', 'ESTANDAR', 1500.00, '2025-11-02', '2025-11-05', 'EN_TRANSITO', 1),
('TRK-OCA-0002', 'OCA', 'EXPRES', 2000.00, '2025-11-03', '2025-11-04', 'EN_PREPARACION', 2),
('TRK-COR-0003', 'CORREO_ARG', 'ESTANDAR', 1300.00, '2025-11-04', '2025-11-08', 'ENTREGADO', 3);


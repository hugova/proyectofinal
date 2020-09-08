USE master
GO
--CREACION DE LA BASE DE DATOS db_Ruiz
CREATE DATABASE[DB_Ruiz] ON PRIMARY
(	NAME = 'DB_Ruiz_data', 
	FILENAME = 'H:\2020 UTN\data\DB_Ruiz_data.mdf', --E:\UTN\2020\I Cuatrimestre\1) Administración de Base de Datos\TODO SOBRE EL PROYECTO
	SIZE = 100MB,
	MAXSIZE = UNLIMITED,
	FILEGROWTH = 25MB )
LOG ON 
(	NAME = 'DB_Ruiz_log', 
	FILENAME = 'H:\2020 UTN\log\DB_Ruiz_log.ldf',
	SIZE = 20MB,
	MAXSIZE = 80MB,
	FILEGROWTH = 15% )
COLLATE Modern_Spanish_CI_AI

GO
USE DB_Ruiz;
GO
--Script para hacer el backup completo de la base de datos
CREATE PROCEDURE paBackUp 
	@pRuta varchar
AS
BEGIN	
	SET NOCOUNT ON;
	BACKUP DATABASE [DB_Ruiz]
	TO  DISK = @pRuta
	WITH CHECKSUM;
   
END

GO

CREATE TABLE TblClientes(
cedula varchar(9) NOT NULL,
nombre varchar(45) NOT NULL,
apellido varchar(45) NOT NULL,
telefono varchar(10),
correoElectronico varchar(45),
genero varchar(10)

CONSTRAINT PK_Cedula PRIMARY KEY (cedula)
);
GO
--INSERTAR CLIENTES
INSERT dbo.TblClientes (cedula, nombre, apellido, telefono, correoElectronico, genero)
		VALUES('500590061', 'Emerito', 'Lacayo', '84161718','', 'Masculino');
GO

CREATE TABLE TblEntrega(
idEntrega int NOT NULL,
descripcion varchar(45) NOT NULL,
estado varchar(10) NOT NULL,
fechaEntrega datetime NOT NULL,
fechaSalida datetime NOT NULL,
numeroCamion int NOT NULL

CONSTRAINT PK_idEntrega PRIMARY KEY (idEntrega)
);
GO

--INSERTAR ENTREGA
INSERT dbo.TblEntrega(idEntrega, descripcion, estado, fechaEntrega, fechaSalida, numeroCamion)
		VALUES(00001, '5 Cajas de Cerveza', 'En espera', '2020-03-16','2020-03-16', 01);
GO

--Vista que muestra el idpedido, cliente, estado de entrega y fechas entrega, salida.
CREATE VIEW VMostrarEstadoEntrega
AS
	SELECT ped.idPedido, cli.nombre, ent.estado, ent.fechaSalida,ent.fechaEntrega
	FROM TblPedido AS Ped 
	INNER JOIN TblClientes as cli ON Ped.cliente = cli.cedula
	INNER JOIN TblEntrega AS ent ON ent.idEntrega = Ped.numeroEntrega;

GO

CREATE TABLE TblPedido(
idPedido int NOT NULL,
numeroPedido varchar(45) NOT NULL,
fechaRegistro datetime NOT NULL,
cliente varchar(9) NOT NULL,
numeroEntrega int NOT NULL,
numeroFactura varchar(45) NOT NULL,
direccionEntrega varchar(45),
subTotal float,
precioTotal float

CONSTRAINT PK_IdPedido PRIMARY KEY (idPedido),
CONSTRAINT FK_ClientePedido FOREIGN KEY (cliente) REFERENCES TblClientes(cedula),
CONSTRAINT FK_EntregaPedido FOREIGN KEY (numeroEntrega) REFERENCES TblEntrega(idEntrega)
);
GO

--INSERTAR PEDIDO
INSERT dbo.TblPedido(idPedido, numeroPedido, fechaRegistro, cliente, numeroEntrega, numeroFactura, direccionEntrega, subTotal, precioTotal)
		VALUES(00001, 'PED0001', '2020-03-13', '500590061', 00001,'00001', 'Liberia', '', '');
GO
DELETE TblPedido WHERE idPedido = 00001



CREATE TABLE TblPresentacion(
idPresentacion varchar(45) NOT NULL,
descripcion varchar(45) NOT NULL,

CONSTRAINT PK_IdPresentacion PRIMARY KEY (idPresentacion)
);
GO
--INSERCION DE LA PRESENTACION
INSERT dbo.TblPresentacion (idPresentacion, descripcion)
	   VALUES('PRES01', 'Botella 350ml')
INSERT dbo.TblPresentacion (idPresentacion, descripcion)
	   VALUES('PRES02', 'Botella 1000ml');	
GO
SELECT * FROM TblPresentacion

CREATE TABLE TblProducto(
idProducto varchar(45) NOT NULL,
nombre varchar(45) NOT NULL,
descripcion varchar(45) NOT NULL,
precioUnidad float NOT NULL,
stock int NOT NULL,
cantidadDisponible int NOT NULL,
peso float(45),  
presentacion varchar(45),
marca varchar(45)

CONSTRAINT PK_IdProducto PRIMARY KEY (idProducto),
CONSTRAINT FK_ FOREIGN KEY (presentacion) REFERENCES TblPresentacion(idPresentacion)
);
GO
--INSERCION DE PRODUCTOS
INSERT dbo.TblProducto (IdProducto, nombre, descripcion, precioUnidad, stock, cantidadDisponible, peso, presentacion, marca) 
       VALUES ('PROD01', 'Cerveza', 'Cereales,Malta,Lúpulo,3.4%Alc', 750, 500, 500, '', 'PRES01', 'Imperial Light'),
              ('PROD02', 'Cerveza', 'Cereales,Malta,Lúpulo,4.5%Alc', 750, 500, 500, '', 'PRES01', 'Imperial Normal'),
              ('PROD03', 'Cerveza', 'Cereales,Malta,Lúpulo,4.5%Alc', 750, 500, 500, '', 'PRES01', 'Imperial Silver'),
			  ('PROD04', 'Cerveza', 'Cereales,Malta,Lúpulo,4.0%Alc', 750, 500, 500, '', 'PRES01', 'Imperial Ultra'),
              ('PROD05', 'Cerveza', 'Cereales,Malta,Lúpulo,5.1%Alc', 750, 500, 500, '', 'PRES01', 'Pilsen'),
			  ('PROD06', 'Guaro Cacique', 'Guaro', 4350, 400, 400, '', 'PRES02', 'Cacique');
INSERT dbo.TblProducto (IdProducto, nombre, descripcion, precioUnidad, stock, cantidadDisponible, peso, presentacion, marca) 
       VALUES ('PROD0101', 'Cerveza', 'Cereales,Malta,Lúpulo,3.4%Alc', 1100, 500, 500, '', 'PRES02', 'Imperial Light'),
              ('PROD0201', 'Cerveza', 'Cereales,Malta,Lúpulo,4.5%Alc', 1100, 500, 500, '', 'PRES02', 'Imperial Normal'),
              ('PROD0301', 'Cerveza', 'Cereales,Malta,Lúpulo,4.5%Alc', 1100, 500, 500, '', 'PRES02', 'Imperial Silver'),
			  
              ('PROD0501', 'Cerveza', 'Cereales,Malta,Lúpulo,5.1%Alc', 1100, 500, 500, '', 'PRES02', 'Pilsen');

INSERT dbo.TblProducto (IdProducto, nombre, descripcion, precioUnidad, stock, cantidadDisponible, peso, presentacion, marca) 
       VALUES ('PROD06', 'Guaro Cacique', 'Guaro 30%Alc', 4350, 400, 400, '', 'PRES02', 'Cacique');
              

GO
--select * from TblProducto
--DELETE TblProducto WHERE idProducto = 'PROD06'
--update TblProducto 
--set descripcion = 'Guara' where IdProducto = 'PROD05'


--Trigger para cantidad Disponible en caso que sea menor a 5, límite que debe de haber para no quedar en 0.
CREATE TRIGGER Dis_VerificarStock
ON Tblproducto
FOR UPDATE
AS
BEGIN
	IF(SELECT COUNT(cantidadDisponible) FROM TblProducto) < 5
	
	BEGIN
	
		RAISERROR('La cantidad  disponible ha llegado a su límite',16,1)
		ROLLBACK TRANSACTION;
	
	END
END
GO

CREATE TABLE TblDetallePedido(
idProducto varchar(45) NOT NULL,
idPedido int NOT NULL,
cantidad int NOT NULL,
precio float NOT NULL

CONSTRAINT FK_DetalleIDPedido FOREIGN KEY (idPedido) REFERENCES TblPedido(idPedido),
CONSTRAINT FK_DetalleIDProducto FOREIGN KEY (idProducto) REFERENCES TblProducto(idProducto)
);
GO
--INSERTAR DETALLE PEDIDO
INSERT dbo.TblDetallePedido (idProducto, idPedido, cantidad, precio)
		VALUES('PROD01', 00001, 24, 750);
GO

--Trigger cuando se inserta en TbldetallePedido cantidad, se actualiza la cantidad disponible de la TblProducto.
CREATE TRIGGER Dis_PedidoDisponible
ON TblDetallePedido
FOR INSERT

AS 
	DECLARE @pcantidad int

BEGIN
	SELECT @pcantidad = TblDetallePedido.cantidad FROM TblDetallePedido
	INNER JOIN inserted as i ON i.idPedido = TblDetallePedido.idPedido
	WHERE TblDetallePedido.idPedido = i.idPedido
	IF(@pcantidad >= (SELECT cantidad from inserted))
		UPDATE TblProducto set cantidadDisponible = cantidadDisponible-inserted.cantidad 
		FROM TblProducto INNER JOIN inserted ON inserted.idProducto = TblProducto.idProducto
		WHERE TblProducto.idProducto = inserted.idProducto

	ELSE
		BEGIN
			RAISERROR('No hay suficiente cantidad disponible en físico para completar el pedido, revisar stock',16,1)
			ROLLBACK TRANSACTION;
		END
	
END
GO

CREATE TABLE TblProveedor(
idProveedor varchar(45)NOT NULL,
nombre varchar(45) NOT NULL,
direccion varchar(45) NOT NULL, 
telefono varchar(45) ,
correoElectronico varchar(45), 
fechaRegistro datetime ,
pais varchar(45) ,
cedulaJuridica varchar(45) NOT NULL

CONSTRAINT PK_Idproveddor PRIMARY KEY (idProveedor)
);
GO

--INSERCION DE LOS PROVEEDORES
INSERT dbo.TblProveedor(idProveedor, nombre, direccion, telefono, correoElectronico, fechaRegistro, pais, cedulaJuridica) 
       VALUES ('PROV01', 'Productora la Florida S.A', 'Heredia, Flores', '(506)2437-6300', '', '2002-03-17', 'Costa Rica', '3-101-306901'),
              ('PROV02', 'Fabrica Nacional de Licores', 'Alajuela, Grecia', '(506)2494-0100', 'ventas@fanal.co.cr', '1998-06-21', 'Costa Rica', '4-000-042146');
GO

CREATE TABLE TblProveedorProducto(
idProducto varchar(45) NOT NULL,
idProveedor varchar(45) NOT NULL

CONSTRAINT FK_idProveedor FOREIGN KEY (idProveedor) REFERENCES TblProveedor(idProveedor),
CONSTRAINT FK_idProducto FOREIGN KEY (idProducto) REFERENCES TblProducto(idProducto)
);

GO

--
INSERT dbo.TblProveedorProducto (idProducto, idProveedor)
     VALUES ('PROD01', 'PROV01'),
			('PROD06', 'PROV02');
GO




--*******************************
-- PROCEDIMIENTOS ALMACENADOS ***
--*******************************
-- PROCEDIMIENTO ALMACENADO (PROVEEDOR)
CREATE PROCEDURE painsertarproveedor @pidproveedor VARCHAR(45)
	,@pnombre VARCHAR(45)
	,@pdireccion VARCHAR(45)
	,@ptelefono VARCHAR(45)
	,@pcorreo_electronico VARCHAR(45)
	,@pfecha_registro DATETIME
	,@ppais VARCHAR(45)
	,@pcedula_juridica VARCHAR(45)
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tblproveedor
			WHERE idproveedor = @pidproveedor
			)
		IF @pnombre = NULL
			OR @pdireccion = NULL
			OR @pcedula_juridica = NULL
			SELECT 'nombre, dirección y cedula jurídica son requeridos'
		ELSE
			INSERT INTO tblproveedor (
				idproveedor
				,nombre
				,direccion
				,telefono
				,correoelectronico
				,fecharegistro
				,pais
				,cedulajuridica
				)
			VALUES (
				@pidproveedor
				,@pnombre
				,@pdireccion
				,@ptelefono
				,@pcorreo_electronico
				,@pfecha_registro
				,@ppais
				,@pcedula_juridica
				)
	ELSE
		SELECT 'el registro ya se encuentra registrado en bd'
END;
GO

CREATE PROCEDURE paeliminarproveedor @pidproveedor VARCHAR(45)
AS
BEGIN
	IF EXISTS (
			SELECT *
			FROM tblproveedor
			WHERE idproveedor = @pidproveedor
			)
		DELETE
		FROM tblproveedor
		WHERE idproveedor = @pidproveedor;
	ELSE
		SELECT 'no hay registros'
END;
GO

CREATE PROCEDURE pabuscarproveedor @pbuscar TEXT
AS
BEGIN
	IF @pbuscar LIKE ''
		SELECT *
		FROM tblproveedor
	ELSE
		SELECT *
		FROM tblproveedor
		WHERE nombre LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR direccion LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR cedulajuridica LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
END;
GO

CREATE PROCEDURE paactualizarproveedor @pidproveedor VARCHAR(45)
	,@pnombre VARCHAR(45)
	,@pdireccion VARCHAR(45)
	,@ptelefono VARCHAR(45)
	,@pcorreo_electronico VARCHAR(45)
	,@pfecha_registro DATETIME
	,@ppais VARCHAR(45)
	,@pcedula_juridica VARCHAR(45)
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tblproveedor
			WHERE idproveedor = @pidproveedor
			)
		SELECT 'el registro no se encuentra registrado en la bd'
	ELSE
		UPDATE tblproveedor
		SET nombre = @pnombre
			,direccion = @pdireccion
			,telefono = @ptelefono
			,correoelectronico = @pcorreo_electronico
			,fecharegistro = @pfecha_registro
			,pais = @ppais
			,cedulajuridica = @pcedula_juridica
		WHERE idproveedor = @pidproveedor

	SELECT 'el registro se modificó correctamente'
END;
GO

CREATE PROCEDURE pabuscartodoproveedor
AS
BEGIN
	SELECT *
	FROM tblproveedor
END;

--***********************************
-- procedimiento almacenado (entrega)
--***********
CREATE PROCEDURE painsertarentrega @pidentrega INT
	,@pdescripcion VARCHAR(45)
	,@pestado VARCHAR(10)
	,@pfecha_entrega DATETIME
	,@pfecha_salida DATETIME
	,@pnumero_camion INT
	,
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tblentrega
			WHERE identrega = @pidentrega
			)
		IF @pdescripcion = NULL
			OR @pestado = NULL
			OR @pnumero_camion = NULL
			OR @pfecha_entrega = NULL
			OR @pfecha_salida = NULL
			SELECT 'la descripción, el estado, número de camión, fecha de entrega o salida;  son necesarios...!'
		ELSE
			INSERT INTO tblentrega (
				identrega
				,descripcion
				,estado
				,fechaentrega
				,fechasalida
				,numerocamion
				)
			VALUES (
				@pidentrega
				,@pdescripcion
				,@pestado
				,@pfecha_entrega
				,@pfecha_salida
				,@pnumero_camion
				)
	ELSE
		SELECT 'el registro ya está en la bd'
END;
GO

--************
CREATE PROCEDURE paeliminarentrega @pidentrega INT
AS
BEGIN
	IF EXISTS (
			SELECT *
			FROM tblentrega
			WHERE identrega = @pidentrega
			)
		DELETE
		FROM tblentrega
		WHERE identrega = @pidentrega;
	ELSE
		SELECT 'no hay ningún registro'
END;
GO

--**********
CREATE PROCEDURE pabuscarentrega @pbuscar TEXT
AS
BEGIN
	IF @pbuscar LIKE ''
		SELECT *
		FROM tblentrega
	ELSE
		SELECT *
		FROM tblentrega
		WHERE identrega LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR descripcion LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR estado LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR numerocamion LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
END;
GO

--**********
CREATE PROCEDURE paactualizarentrega @pidentrega INT
	,@pdescripcion VARCHAR(45)
	,@pestado VARCHAR(10)
	,@pfecha_entrega DATETIME
	,@pfecha_salida DATETIME
	,@pnumero_camion INT
	,
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tblentrega
			WHERE identrega = @pidentrega
			)
		SELECT 'el registro no se encuentra registrado en la bd'
	ELSE
		UPDATE tblentrega
		SET descripcion = @pdescripcion
			,estado = @pestado
			,fechaentrega = @pfecha_entrega
			,fechasalida = @pfecha_salida
			,numerocamion = @pnumero_camion
		WHERE identrega = @pidentrega

	SELECT 'el registro se modificó correctamente'
END;
GO

--**********
CREATE PROCEDURE pabuscartodaentrega
AS
BEGIN
	SELECT *
	FROM tblentrega
END;
GO

--***********************************
-- procedimiento almacenado (pedido)
--***********
CREATE PROCEDURE painsertarpedido @pidpedido INT
	,@pnumero_pedido VARCHAR(45)
	,@pfecha_registro DATETIME
	,@pcliente VARCHAR(9)
	,@pnumero_entrega INT
	,@pnumero_factura VARCHAR(45)
	,@pdireccion_entrega VARCHAR(45)
	,@psub_total FLOAT
	,@pprecio_total FLOAT
	,
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tblpedido
			WHERE idpedido = @pidpedido
			)
		IF @pnumero_pedido = NULL
			OR @pfecha_registro = NULL
			OR @pcliente = NULL
			OR @pnumero_entrega = NULL
			OR @pnumero_factura = NULL
			OR @pdireccion_entrega = NULL
			SELECT 'el número de pedido, fecha registro, cliente, número de entrega, número de factura, y dirección entrega;  son necesarios...!'
		ELSE
			INSERT INTO tblpedido (
				idpedido
				,numeropedido
				,fecharegistro
				,cliente
				,numeroentrega
				,numerofactura
				,direccionentrega
				,subtotal
				,preciototal
				)
			VALUES (
				@pidpedido
				,@pnumero_pedido
				,@pfecha_registro
				,@pcliente
				,@pnumero_entrega
				,@pnumero_factura
				,@pdireccion_entrega
				,@psub_total
				,@pprecio_total
				)
	ELSE
		SELECT 'el registro ya está en la bd'
END;
GO

--************
CREATE PROCEDURE paeliminarpedido @pidpedido INT
AS
BEGIN
	IF EXISTS (
			SELECT *
			FROM tblpedido
			WHERE idpedido = @pidpedido
			)
		DELETE
		FROM tblpedido
		WHERE idpedido = @pidpedido;
	ELSE
		SELECT 'no hay ningún registro'
END;
GO

--**********
CREATE PROCEDURE pabuscarpedido @pbuscar TEXT
AS
BEGIN
	IF @pbuscar LIKE ''
		SELECT *
		FROM tblpedido
	ELSE
		SELECT *
		FROM tblpedido
		WHERE idpedido LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR numeropedido LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR fecharegistro LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR cliente LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR numeroentrega LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR numerofactura LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR direccionentrega LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
END;
GO

--**********
CREATE PROCEDURE paactualizarpedido @pidpedido INT
	,@pnumero_pedido VARCHAR(45)
	,@pfecha_registro DATETIME
	,@pcliente VARCHAR(9)
	,@pnumero_entrega INT
	,@pnumero_factura VARCHAR(45)
	,@pdireccion_entrega VARCHAR(45)
	,@psub_total FLOAT
	,@pprecio_total FLOAT
	,
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tblpedido
			WHERE idpedido = @pidpedido
			)
		SELECT 'el registro no se encuentra registrado en la bd'
	ELSE
		UPDATE tblpedido
		SET numeropedido = @pnumero_pedido
			,fecharegistro = @pfecha_registro
			,cliente = @pcliente
			,numeroentrega = @pnumero_entrega
			,numerofactura = @pnumero_factura
			,direccionentrega = @pdireccion_entrega
			,subtotal = @psub_total
			,preciototal = @pprecio_total
		WHERE idpedido = @pidpedido

	SELECT 'el registro se modificó correctamente'
END;
GO

--**********
CREATE PROCEDURE pabuscartodopedido
AS
BEGIN
	SELECT *
	FROM tblpedido
END;
GO

--***********************************
-- procedimiento almacenado (producto)
--***********
CREATE PROCEDURE painsertarproducto @pidproducto INT
	,@pnombre_producto VARCHAR(45)
	,@pdescripcion_producto VARCHAR(45)
	,@pprecio_unidad FLOAT
	,@pstock_producto INT
	,@pcantidad_disponible INT
	,@ppeso_producto FLOAT
	,@ppresentacion_producto VARCHAR(45)
	,@pmarca_producto VARCHAR(45)
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tblproducto
			WHERE idproducto = @pidproducto
			)
		IF @pnombre_producto = NULL
			OR @pdescripcion_producto = NULL
			OR @pprecio_unidad = NULL
			OR @pstock_producto = NULL
			OR @pcantidad_disponible = NULL
			OR @ppeso_producto = NULL
			OR @ppresentacion_producto = NULL
			OR @pmarca_producto = NULL
			SELECT 'el nombre del producto, descripción, precio unidad, stock, cantidad disponible, peso del producto, presentación y marca;  son necesarios...!'
		ELSE
			INSERT INTO tblproducto (
				idproducto
				,nombre
				,descripcion
				,preciounidad
				,stock
				,cantidaddisponible
				,peso
				,presentacion
				,marca
				)
			VALUES (
				@pidproducto
				,@pnombre_producto
				,@pdescripcion_producto
				,@pprecio_unidad
				,@pstock_producto
				,@pcantidad_disponible
				,@ppeso_producto
				,@ppresentacion_producto
				,@pmarca_producto
				)
	ELSE
		SELECT 'el registro ya está en la bd'
END;
GO

--************
CREATE PROCEDURE paeliminarproducto @pidproducto INT
AS
BEGIN
	IF EXISTS (
			SELECT *
			FROM tblproducto
			WHERE idproducto = @pidproducto
			)
		DELETE
		FROM tblproducto
		WHERE idproducto = @pidproducto;
	ELSE
		SELECT 'no hay ningún registro'
END;
GO

--**********
CREATE PROCEDURE pabuscarproducto @pbuscar TEXT
AS
BEGIN
	IF @pbuscar LIKE ''
		SELECT *
		FROM tblproducto
	ELSE
		SELECT *
		FROM tblproducto
		WHERE idproducto LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR nombre LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR descripcion LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR preciounidad LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR stock LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR cantidaddisponible LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR peso LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR presentacion LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR marca LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
END;
GO

--**********
CREATE PROCEDURE paactualizarproducto @pidproducto INT
	,@pnombre_producto VARCHAR(45)
	,@pdescripcion_producto VARCHAR(45)
	,@pprecio_unidad FLOAT
	,@pstock_producto INT
	,@pcantidad_disponible INT
	,@ppeso FLOAT
	,@ppresentacion_producto VARCHAR(45)
	,@pmarca_producto VARCHAR(45)
	,
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tblproducto
			WHERE idproducto = @pidproducto
			)
		SELECT 'el registro no se encuentra registrado en la bd'
	ELSE
		UPDATE tblproducto
		SET nombre = @pnombre_producto
			,descripcion = @pdescripcion_producto
			,preciounidad = @pprecio_unidad
			,stock = @pstock_producto
			,cantidaddisponible = @pcantidad_disponible
			,peso = @ppeso_producto
			,presentacion = @ppresentacion_producto
			,marca = @pmarca_producto
		WHERE idproducto = @pidproducto

	SELECT 'el registro se modificó correctamente'
END;
GO

--**********
CREATE PROCEDURE pabuscartodoproducto
AS
BEGIN
	SELECT *
	FROM tblproducto
END;
GO

--**procedimientos de ana**--
CREATE PROCEDURE painsertarcliente @pcedula VARCHAR(9)
	,@pnombre VARCHAR(45)
	,@papellido VARCHAR(45)
	,@ptelefono VARCHAR(10)
	,@pcorreo_electronico VARCHAR(45)
	,@pgenero VARCHAR(10)
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tblclientes
			WHERE cedula = @pcedula
			)
		IF @pnombre = NULL
			OR @papellido = NULL
			SELECT 'nombre, apellido  son requeridos'
		ELSE
			INSERT INTO tblclientes (
				cedula
				,nombre
				,apellido
				,telefono
				,correoelectronico
				,genero
				)
			VALUES (
				@pcedula
				,@pnombre
				,@papellido
				,@ptelefono
				,@pcorreo_electronico
				,@pgenero
				)
	ELSE
		SELECT 'el registro ya se encuentra registrado en bd'
END;
GO

CREATE PROCEDURE paeliminarcliente @pcedula VARCHAR(9)
AS
BEGIN
	IF EXISTS (
			SELECT *
			FROM tblclientes
			WHERE cedula = @pcedula
			)
		DELETE
		FROM tblclientes
		WHERE cedula = @pcedula;
	ELSE
		SELECT 'no hay registros'
END;
GO

CREATE PROCEDURE pabuscarclientes @pbuscar TEXT
AS
BEGIN
	IF @pbuscar LIKE ''
		SELECT *
		FROM tblclientes
	ELSE
		SELECT *
		FROM tblclientes
		WHERE nombre LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
			OR apellido LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
END;
GO

CREATE PROCEDURE paactualizarclientes @pcedula VARCHAR(9)
	,@pnombre VARCHAR(45)
	,@papellido VARCHAR(45)
	,@ptelefono VARCHAR(45)
	,@pcorreo_electronico VARCHAR(45)
	,@pgenero VARCHAR(10)
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tblclientes
			WHERE cedula = @pcedula
			)
		SELECT 'el registro no se encuentra registrado en la bd'
	ELSE
		UPDATE tblclientes
		SET nombre = @pnombre
			,apellido = @papellido
			,telefono = @ptelefono
			,correoelectronico = @pcorreo_electronico
			,genero = @pgenero
		WHERE cedula = @pcedula

	SELECT 'el registro se modificó correctamente'
END;
GO

CREATE PROCEDURE pabuscartodocliente
AS
BEGIN
	SELECT *
	FROM tblclientes
END;
GO

CREATE PROCEDURE painsertarpresentacion @pidpresentacion VARCHAR(45)
	,@pdescripcion VARCHAR(45)
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tblpresentacion
			WHERE idpresentacion = @pidpresentacion
			)
		IF @pdescripcion = NULL
			SELECT 'descripcion   es requerida'
		ELSE
			INSERT INTO tblpresentacion (
				idpresentacion
				,descripcion
				)
			VALUES (
				@pidpresentacion
				,@pdescripcion
				)
	ELSE
		SELECT 'el registro ya se encuentra registrado en bd'
END;
GO

CREATE PROCEDURE paeliminarpresentacion @pidpresentacion VARCHAR(9)
AS
BEGIN
	IF EXISTS (
			SELECT *
			FROM tblpresentacion
			WHERE idpresentacion = @pidpresentacion
			)
		DELETE
		FROM tblpresentacion
		WHERE idpresentacion = @pidpresentacion;
	ELSE
		SELECT 'no hay registros'
END;
GO

CREATE PROCEDURE pabuscarpresentacion @pbuscar TEXT
AS
BEGIN
	IF @pbuscar LIKE ''
		SELECT *
		FROM tblpresentacion
	ELSE
		SELECT *
		FROM tblpresentacion
		WHERE idpresentacion LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
END;
GO

CREATE PROCEDURE paactualizarpresentacion @pidpresentacion VARCHAR(45)
	,@pdescripcion VARCHAR(45)
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tblpresentacion
			WHERE idpresentacion = @pidpresentacion
			)
		SELECT 'el registro no se encuentra registrado en la bd'
	ELSE
		UPDATE tblpresentacion
		SET descripcion = @pdescripcion
		WHERE idpresentacion = @pidpresentacion

	SELECT 'el registro se modificó correctamente'
END;
GO

CREATE PROCEDURE pabuscartodopresentacion
AS
BEGIN
	SELECT *
	FROM tblpresentacion
END;
GO

CREATE PROCEDURE painsertardetallepedido @pidproducto VARCHAR(45)
	,@pidpedido INT
	,@pcantidad INT
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tbldetallepedido
			WHERE idproducto = @pidproducto
			)
		IF @pcantidad = NULL
			SELECT 'cantidad  es requerida'
		ELSE
			INSERT INTO tbldetallepedido (
				idproducto
				,idpedido
				,cantidad
				)
			VALUES (
				@pidproducton
				,@pidpedido
				,@pcantidad
				)
	ELSE
		SELECT 'el registro ya se encuentra registrado en bd'
END;
GO

CREATE PROCEDURE paeliminardetallepedido @pidproducto VARCHAR(45)
AS
BEGIN
	IF EXISTS (
			SELECT *
			FROM tbldetallepedido
			WHERE idproducto = @pidproducto
			)
		DELETE
		FROM tbldetallepedido
		WHERE idproducto = @pidpproducto;
	ELSE
		SELECT 'no hay registros'
END;
GO

CREATE PROCEDURE pabuscardetallepedido @pbuscar TEXT
AS
BEGIN
	IF @pbuscar LIKE ''
		SELECT *
		FROM tbldetallepedido
	ELSE
		SELECT *
		FROM tbldetallepedido
		WHERE idproducto LIKE CONCAT (
				'%'
				,@pbuscar
				,'%'
				)
END;
GO

CREATE PROCEDURE paactualizardetallepedido @pidproducto VARCHAR(45)
	,@pidpedido INT
	,@pcantidad INT
AS
BEGIN
	IF NOT EXISTS (
			SELECT *
			FROM tbldetallepedido
			WHERE idproducto = @pidproducto
			)
		SELECT 'el registro no se encuentra registrado en la bd'
	ELSE
		UPDATE tbldetallepedido
		SET cantidad = @@connections
		WHERE idproducto = @pidproducto

	SELECT 'el registro se modificó correctamente'
END;
GO

CREATE PROCEDURE pabuscartododetallepedido
AS
BEGIN
	SELECT *
	FROM tbldetallepedido
END;
GO




CREATE DATABASE IF NOT EXISTS bdinnovaschool;
USE bdinnovaschool;

-- Crear tabla Usuario
DROP TABLE IF EXISTS `Usuario`;
CREATE TABLE `Usuario` (
  `CodUsuario` int NOT NULL AUTO_INCREMENT,
  `Contrasenia` varchar(20) NOT NULL,
  `Role` varchar(20) NOT NULL DEFAULT 'Alumno',
  PRIMARY KEY (`CodUsuario`),
  UNIQUE KEY `CodUsuario_UNIQUE` (`CodUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

SET SQL_SAFE_UPDATES = 1;

-- Actualizar los datos de usuario para asignar roles
-- UPDATE Usuario SET Role = 'Encargado' WHERE Contrasenia = 'UnicoEncargado';

-- Crear tabla Alumno
DROP TABLE IF EXISTS `Alumno`;
CREATE TABLE `Alumno` (
  `NombresApellidos` varchar(90) NOT NULL,
  `Estado` varchar(20) NOT NULL,
  `Usuario_CodUsuario` int NOT NULL,
  `DiasInhabilitado` INT DEFAULT 0,
  PRIMARY KEY (`Usuario_CodUsuario`),
  KEY `fk_Alumno_Usuario1_idx` (`Usuario_CodUsuario`),
  CONSTRAINT `fk_Alumno_Usuario1` FOREIGN KEY (`Usuario_CodUsuario`) REFERENCES `Usuario` (`CodUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Crear tabla Material
DROP TABLE IF EXISTS `Material`;
CREATE TABLE `Material` (
  `CodMaterial` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  `Descripcion` varchar(45) NOT NULL,
  `Stock` int NOT NULL,
  `Tipo` varchar(25) NOT NULL,
  PRIMARY KEY (`CodMaterial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Crear tabla Solicitud
DROP TABLE IF EXISTS `Solicitud`;
CREATE TABLE `Solicitud` (
  `IdSolicitud` int NOT NULL AUTO_INCREMENT,
  `Cantidad` int NOT NULL,
  `Estado` varchar(15) NOT NULL,
  `Material_CodMaterial` int NOT NULL,
  `Alumno_Usuario_CodUsuario` int NOT NULL,
  PRIMARY KEY (`IdSolicitud`),
  UNIQUE KEY `IdSolicitud_UNIQUE` (`IdSolicitud`),
  KEY `fk_Solicitud_Material1_idx` (`Material_CodMaterial`),
  KEY `fk_Solicitud_Alumno1_idx` (`Alumno_Usuario_CodUsuario`),
  CONSTRAINT `fk_Solicitud_Alumno1` FOREIGN KEY (`Alumno_Usuario_CodUsuario`) REFERENCES `Alumno` (`Usuario_CodUsuario`),
  CONSTRAINT `fk_Solicitud_Material1` FOREIGN KEY (`Material_CodMaterial`) REFERENCES `Material` (`CodMaterial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Crear tabla Prestamo
DROP TABLE IF EXISTS `Prestamo`;
CREATE TABLE `Prestamo` (
  `IdPrestamo` int NOT NULL AUTO_INCREMENT,
  `FechaPrestamo` datetime NOT NULL,
  `Solicitud_idSolicitud` int NOT NULL,
  `Estado` varchar(15) NOT NULL,
  `Fecha_dev_real` datetime DEFAULT NULL,
  PRIMARY KEY (`IdPrestamo`),
  UNIQUE KEY `IdPrestamo_UNIQUE` (`IdPrestamo`),
  KEY `fk_Prestamo_Solicitud1_idx` (`Solicitud_idSolicitud`),
  CONSTRAINT `fk_Prestamo_Solicitud1` FOREIGN KEY (`Solicitud_idSolicitud`) REFERENCES `Solicitud` (`IdSolicitud`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Crear tabla Penalizacion
DROP TABLE IF EXISTS `Penalizacion`;
CREATE TABLE `Penalizacion` (
  `IdPenalizacion` int NOT NULL AUTO_INCREMENT,
  `FechaPenalizacion` datetime NOT NULL,
  `Descripcion` varchar(255) NOT NULL,
  `Prestamo_idPrestamo` int NOT NULL,
  PRIMARY KEY (`IdPenalizacion`),
  UNIQUE KEY `IdPenalizacion_UNIQUE` (`IdPenalizacion`),
  KEY `fk_Penalizacion_Prestamo1_idx` (`Prestamo_idPrestamo`),
  CONSTRAINT `fk_Penalizacion_Prestamo1` FOREIGN KEY (`Prestamo_idPrestamo`) REFERENCES `Prestamo` (`IdPrestamo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Procedimiento para registrar una solicitud
DELIMITER //
CREATE PROCEDURE `registrarSolicitud` (
    IN p_alumno_codUsuario INT,
    IN p_material_cod INT,
    IN p_cantidad INT
)
BEGIN
    DECLARE v_stock INT;
    
    -- Verificar el stock disponible
    SELECT Stock INTO v_stock
    FROM Material
    WHERE CodMaterial = p_material_cod;
    
    IF v_stock >= p_cantidad THEN
        -- Insertar la nueva solicitud
        INSERT INTO Solicitud (Cantidad, Estado, Alumno_Usuario_CodUsuario, Material_CodMaterial)
        VALUES (p_cantidad, 'Generado', p_alumno_codUsuario, p_material_cod);
        
        -- Actualizar el stock del material
        UPDATE Material
        SET Stock = Stock - p_cantidad
        WHERE CodMaterial = p_material_cod;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Stock insuficiente para la solicitud';
    END IF;
END // 


-- Procedimiento para actualizar el estado de una solicitud
DELIMITER //
CREATE PROCEDURE `actualizarEstadoSolicitud` (
    IN p_solicitud_id INT,
    IN p_nuevo_estado VARCHAR(15)
)
BEGIN
    UPDATE Solicitud
    SET Estado = p_nuevo_estado
    WHERE IdSolicitud = p_solicitud_id;
END // 


-- Procedimiento para registrar un préstamo
DELIMITER //
CREATE PROCEDURE `registrarPrestamo` (
    IN p_solicitud_id INT,
    IN p_fecha_prestamo DATETIME
)
BEGIN
    INSERT INTO Prestamo (FechaPrestamo, Solicitud_idSolicitud, Estado)
    VALUES (p_fecha_prestamo, p_solicitud_id, 'En Curso');
    
    -- Actualizar el estado de la solicitud a 'Aprobado'
    CALL actualizarEstadoSolicitud(p_solicitud_id, 'Aprobado');
END// 


-- Procedimiento para registrar una devolución
DELIMITER //
CREATE PROCEDURE `registrarDevolucion` (
    IN p_prestamo_id INT,
    IN p_fecha_devolucion DATETIME
)
BEGIN
    DECLARE v_solicitud_id INT;
    DECLARE v_material_cod INT;
    DECLARE v_cantidad INT;
    DECLARE v_alumno_codUsuario INT;
    DECLARE v_estado_prestamo VARCHAR(255);
    DECLARE v_fecha_devolucion_programada DATE;
    DECLARE v_dias_tardios INT;

    -- Recuperar estado y fecha de devolución programada del préstamo
    SELECT Estado, FechaPrestamo + INTERVAL 7 DAY INTO v_estado_prestamo, v_fecha_devolucion_programada
    FROM Prestamo
    WHERE IdPrestamo = p_prestamo_id;

    -- Actualizar el estado del préstamo a 'Devuelto'
    UPDATE Prestamo
    SET Estado = 'Devuelto',
        Fecha_dev_real = p_fecha_devolucion
    WHERE IdPrestamo = p_prestamo_id;
    
    -- Recuperar idSolicitud del préstamo
    SELECT Solicitud_idSolicitud INTO v_solicitud_id
    FROM Prestamo
    WHERE IdPrestamo = p_prestamo_id;
    
    -- Recuperar idMaterial y cantidad de la solicitud
    SELECT Material_CodMaterial, Cantidad INTO v_material_cod, v_cantidad
    FROM Solicitud
    WHERE IdSolicitud = v_solicitud_id;
    
    -- Devolver el stock del material
    UPDATE Material
    SET Stock = Stock + v_cantidad
    WHERE CodMaterial = v_material_cod;
    
    -- Actualizar el estado de la solicitud a 'Completado'
    CALL actualizarEstadoSolicitud(v_solicitud_id, 'Completado');

    -- Inhabilitar al alumno si el préstamo estaba en estado 'Tardio'
    IF v_estado_prestamo = 'Tardio' THEN
        SELECT Alumno_Usuario_CodUsuario INTO v_alumno_codUsuario
        FROM Solicitud
        WHERE IdSolicitud = v_solicitud_id;
        
        -- Calcular días tardíos
        SET v_dias_tardios = DATEDIFF(p_fecha_devolucion, v_fecha_devolucion_programada);
        
        -- Inhabilitar al alumno por la cantidad de días tardíos
        UPDATE Alumno
        SET Estado = 'Inhabilitado', DiasInhabilitado = DiasInhabilitado + v_dias_tardios
        WHERE Usuario_CodUsuario = v_alumno_codUsuario;
    END IF;
END // 


-- Procedimiento para registrar una penalización
DELIMITER //
CREATE PROCEDURE `registrarPenalizacion` (
    IN p_prestamo_id INT,
    IN p_fecha_penalizacion DATETIME,
    IN p_descripcion VARCHAR(255)
)
BEGIN
    DECLARE v_solicitud_id INT;
    DECLARE v_alumno_codUsuario INT;
    DECLARE v_fecha_prestamo DATETIME;
    DECLARE v_fecha_devolucion_programada DATE;
    DECLARE v_dias_tardios INT;
    DECLARE v_estado_prestamo VARCHAR(50);

    -- Verificar si el préstamo existe
    IF (SELECT COUNT(*) FROM Prestamo WHERE IdPrestamo = p_prestamo_id) = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El préstamo no existe.';
    END IF;

    -- Obtener estado y fecha de devolución programada del préstamo
    SELECT Estado, FechaPrestamo + INTERVAL 7 DAY INTO v_estado_prestamo, v_fecha_devolucion_programada
    FROM Prestamo
    WHERE IdPrestamo = p_prestamo_id;

    -- Insertar la penalización
    INSERT INTO Penalizacion (FechaPenalizacion, Descripcion, Prestamo_idPrestamo)
    VALUES (p_fecha_penalizacion, p_descripcion, p_prestamo_id);

    -- Actualizar el estado del préstamo a 'Penalizado'
    UPDATE Prestamo
    SET Estado = 'Penalizado'
    WHERE IdPrestamo = p_prestamo_id;

    -- Si el préstamo está tardío, inhabilitar al alumno
    IF v_estado_prestamo = 'Tardio' THEN
        -- Obtener el código de usuario del alumno
        SELECT Alumno_Usuario_CodUsuario INTO v_alumno_codUsuario
        FROM Solicitud
        WHERE IdSolicitud = (SELECT Solicitud_idSolicitud FROM Prestamo WHERE IdPrestamo = p_prestamo_id);

        -- Calcular días tardíos
        SET v_dias_tardios = DATEDIFF(p_fecha_penalizacion, v_fecha_devolucion_programada);

        -- Inhabilitar al alumno por la cantidad de días tardíos
        UPDATE Alumno
        SET Estado = 'Inhabilitado', DiasInhabilitado = DiasInhabilitado + v_dias_tardios
        WHERE Usuario_CodUsuario = v_alumno_codUsuario;
    END IF;
END // 

	
DELIMITER //
CREATE PROCEDURE filtrarMateriales (
    IN p_categoria VARCHAR(50),
    IN p_buscar VARCHAR(100)
)
BEGIN
    SELECT CodMaterial, Nombre, Descripcion, Tipo, Stock
    FROM Material
    WHERE (p_categoria = 'TODOS' OR Tipo = p_categoria)
      AND (Nombre LIKE CONCAT('%', p_buscar, '%') OR Descripcion LIKE CONCAT('%', p_buscar, '%'));
END // 


DELIMITER //
CREATE PROCEDURE `actualizarPrestamo` (
    IN p_prestamo_id INT,
    IN p_nueva_fecha_prestamo DATETIME
)
BEGIN
    -- Actualizar la fecha de préstamo del préstamo existente
    UPDATE Prestamo
    SET FechaPrestamo = p_nueva_fecha_prestamo
    WHERE IdPrestamo = p_prestamo_id;
END // 



-- Insertar datos en la tabla Usuario
INSERT INTO Usuario (Contrasenia) VALUES ('Jhon Quiñones');
INSERT INTO Usuario (Contrasenia) VALUES ('Juan Trejo');
INSERT INTO Usuario (Contrasenia) VALUES ('Bug Bunny');
INSERT INTO Usuario (Contrasenia) VALUES ('Ian Bazan');
INSERT INTO Usuario (Contrasenia) VALUES ('Cristofer Jesus');

INSERT INTO Usuario (Contrasenia) VALUES ('UnicoEncargado');


-- Insertar datos en la tabla Alumno
INSERT INTO Alumno (NombresApellidos, Estado, Usuario_CodUsuario, DiasInhabilitado) VALUES ('Jhon Quiñones', 'Activo', 1, 0);
INSERT INTO Alumno (NombresApellidos, Estado, Usuario_CodUsuario, DiasInhabilitado) VALUES ('Juan Trejo', 'Activo', 2, 0);
INSERT INTO Alumno (NombresApellidos, Estado, Usuario_CodUsuario, DiasInhabilitado) VALUES ('Bug Bunny', 'Activo', 3, 0);
INSERT INTO Alumno (NombresApellidos, Estado, Usuario_CodUsuario, DiasInhabilitado) VALUES ('Ian Bazan', 'Activo', 4, 0);
INSERT INTO Alumno (NombresApellidos, Estado, Usuario_CodUsuario, DiasInhabilitado) VALUES ('Cristofer Jesus', 'Activo', 5, 0);

-- Insertar datos en la tabla Material
INSERT INTO Material (CodMaterial, Nombre, Descripcion, Stock, Tipo) VALUES ('100001', 'Libro de Matemáticas', 'Libro de Algebra', 10, 'Libro');
INSERT INTO Material (CodMaterial, Nombre, Descripcion, Stock, Tipo) VALUES ('100002', 'Libro de Ciencias', 'Libro de Biología', 8, 'Libro');
INSERT INTO Material (CodMaterial, Nombre, Descripcion, Stock, Tipo) VALUES ('100003', 'Tablet', 'Tablet para investigación', 5, 'Electrónico');
INSERT INTO Material (CodMaterial, Nombre, Descripcion, Stock, Tipo) VALUES ('100004', 'Proyector', 'Proyector para presentaciones', 3, 'Electrónico');
INSERT INTO Material (CodMaterial, Nombre, Descripcion, Stock, Tipo) VALUES ('100005', 'Calculadora', 'Calculadora científica', 15, 'Instrumento');

-- Insertar datos en la tabla Solicitud
INSERT INTO Solicitud (Cantidad, Estado, Material_CodMaterial, Alumno_Usuario_CodUsuario) VALUES (2, 'Generado', '100001', 1);
INSERT INTO Solicitud (Cantidad, Estado, Material_CodMaterial, Alumno_Usuario_CodUsuario) VALUES (1, 'Generado', '100002', 2);
INSERT INTO Solicitud (Cantidad, Estado, Material_CodMaterial, Alumno_Usuario_CodUsuario) VALUES (1, 'Generado', '100003', 3);
INSERT INTO Solicitud (Cantidad, Estado, Material_CodMaterial, Alumno_Usuario_CodUsuario) VALUES (1, 'Generado', '100004', 4);
INSERT INTO Solicitud (Cantidad, Estado, Material_CodMaterial, Alumno_Usuario_CodUsuario) VALUES (3, 'Generado', '100005', 5);

-- Insertar datos en la tabla Prestamo
INSERT INTO Prestamo (FechaPrestamo, Solicitud_idSolicitud, Estado) VALUES ('2024-06-01 10:00:00', 1, 'En Curso');
INSERT INTO Prestamo (FechaPrestamo, Solicitud_idSolicitud, Estado) VALUES ('2024-06-02 11:00:00', 2, 'En Curso');
INSERT INTO Prestamo (FechaPrestamo, Solicitud_idSolicitud, Estado) VALUES ('2024-06-03 12:00:00', 3, 'En Curso');
INSERT INTO Prestamo (FechaPrestamo, Solicitud_idSolicitud, Estado) VALUES ('2024-06-04 13:00:00', 4, 'En Curso');
INSERT INTO Prestamo (FechaPrestamo, Solicitud_idSolicitud, Estado) VALUES ('2024-06-05 14:00:00', 5, 'En Curso');

-- Insertar datos en la tabla Penalizacion
INSERT INTO Penalizacion (FechaPenalizacion, Descripcion, Prestamo_idPrestamo) VALUES ('2024-06-15 10:00:00', 'Devolución tardía', 1);
INSERT INTO Penalizacion (FechaPenalizacion, Descripcion, Prestamo_idPrestamo) VALUES ('2024-06-16 11:00:00', 'Material dañado',  2);
INSERT INTO Penalizacion (FechaPenalizacion, Descripcion, Prestamo_idPrestamo) VALUES ('2024-06-17 12:00:00', 'Pérdida de material', 3);
INSERT INTO Penalizacion (FechaPenalizacion, Descripcion, Prestamo_idPrestamo) VALUES ('2024-06-18 13:00:00', 'Devolución incompleta', 4);
INSERT INTO Penalizacion (FechaPenalizacion, Descripcion, Prestamo_idPrestamo) VALUES ('2024-06-19 14:00:00', 'Uso indebido del material', 5);

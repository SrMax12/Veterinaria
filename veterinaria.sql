-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-10-2026 a las 21:20:14
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `veterinaria`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consulta`
--

CREATE TABLE `consulta` (
  `id` int(50) NOT NULL,
  `procedimiento` varchar(50) NOT NULL,
  `fecha_hora` datetime(6) NOT NULL,
  `id_mascota` int(45) NOT NULL,
  `id_veterinario` int(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `consulta`
--

INSERT INTO `consulta` (`id`, `procedimiento`, `fecha_hora`, `id_mascota`, `id_veterinario`) VALUES
(1, 'Vacunación Anual y Desparasitación', '2026-01-15 09:30:00.000000', 1, 1),
(2, 'Control de peso y Nutrición', '2026-02-10 11:15:00.000000', 2, 2),
(3, 'Limpieza dental profunda', '2026-02-28 15:00:00.000000', 3, 9),
(4, 'Tratamiento por otitis', '2026-03-12 08:45:00.000000', 4, 3),
(5, 'Ecografía abdominal', '2026-03-25 14:20:00.000000', 5, 19),
(6, 'Castración / Esterilización', '2026-04-05 08:00:00.000000', 6, 12),
(7, 'Control post-operatorio', '2026-04-12 10:30:00.000000', 6, 12),
(8, 'Extracción de puntos', '2026-04-20 16:00:00.000000', 7, 1),
(9, 'Chequeo cardíaco preventivo', '2026-05-02 11:00:00.000000', 8, 4),
(10, 'Consulta por alergia cutánea', '2026-05-18 17:15:00.000000', 9, 14),
(11, 'Recorte de pico y plumas', '2026-06-03 09:00:00.000000', 18, 6),
(12, 'Control dermatológico', '2026-06-22 13:45:00.000000', 10, 3),
(13, 'Radiografía por renguera', '2026-07-08 10:00:00.000000', 11, 5),
(14, 'Sesión de fisioterapia', '2026-07-15 15:30:00.000000', 11, 11),
(15, 'Tratamiento conjuntivitis', '2026-07-29 08:30:00.000000', 12, 7),
(16, 'Consulta de rutina cachorro', '2026-08-05 12:00:00.000000', 13, 15),
(17, 'Análisis de sangre general', '2026-08-14 07:30:00.000000', 14, 17),
(18, 'Urgencia por intoxicación', '2026-08-20 22:15:00.000000', 15, 20),
(19, 'Control de sarna', '2026-08-28 16:45:00.000000', 16, 14),
(20, 'Atención por trauma leve', '2026-09-02 10:00:00.000000', 17, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dueño`
--

CREATE TABLE `dueño` (
  `id` int(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `ci` int(20) NOT NULL,
  `telefono` int(15) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `dueño`
--

INSERT INTO `dueño` (`id`, `nombre`, `ci`, `telefono`, `email`) VALUES
(1, 'Juan Pérez', 1234, 99123456, 'juan.perez@email.com'),
(2, 'María Rodríguez', 2345, 98234567, 'maria.rod@email.com'),
(3, 'Carlos González', 3456, 97345678, 'carlos.g@email.com'),
(4, 'Ana Silva', 4567, 96456789, 'ana.silva@email.com'),
(5, 'Luis Martínez', 5123, 95567890, 'luis.mar@email.com'),
(6, 'Laura Gómez', 1987, 94678901, 'laura.gomez@email.com'),
(7, 'Diego Fernández', 2876, 93789012, 'diego.f@email.com'),
(8, 'Lucía Álvarez', 3765, 92890123, 'lucia.alv@email.com'),
(9, 'Gabriel Pereira', 4654, 91901234, 'gabriel.p@email.com'),
(10, 'Sofía Díaz', 5543, 99987654, 'sofia.diaz@email.com'),
(11, 'Andrés Castro', 1357, 98876543, 'andres.c@email.com'),
(12, 'Valentina Ríos', 2468, 97765432, 'vale.rios@email.com'),
(13, 'Martín Sosa', 3579, 96654321, 'martin.sosa@email.com'),
(14, 'Elena Suárez', 4680, 95543210, 'elena.s@email.com'),
(15, 'Santiago Torres', 5791, 94432109, 'santi.torres@email.com'),
(16, 'Camila López', 1122, 93321098, 'camila.lopez@email.com'),
(17, 'Nicolas Machado', 2233, 92210987, 'nico.mac@email.com'),
(18, 'Mariana Acosta', 3344, 91109876, 'mariana.a@email.com'),
(19, 'Alejandro Núñez', 4455, 99234561, 'ale.nunez@email.com'),
(20, 'Florencia Morales', 5566, 98345672, 'flor.morales@email.com'),
(21, 'Magali', 5814923, 92456208, 'magallovera@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mascota`
--

CREATE TABLE `mascota` (
  `id` int(50) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `edad` int(5) NOT NULL,
  `especie` varchar(15) NOT NULL,
  `raza` varchar(25) NOT NULL,
  `peso` int(15) NOT NULL,
  `genero` varchar(10) NOT NULL,
  `id_dueño` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mascota`
--

INSERT INTO `mascota` (`id`, `nombre`, `edad`, `especie`, `raza`, `peso`, `genero`, `id_dueño`) VALUES
(1, 'Toby', 3, 'Perro', 'Cruza', 13, 'Macho', 1),
(2, 'Luna', 2, 'Gato', 'Siamés', 4, 'Hembra', 2),
(3, 'Rocco', 5, 'Perro', 'Ovejero Alemán', 32, 'Macho', 3),
(4, 'Mia', 1, 'Gato', 'Común Europeo', 4, 'Hembra', 4),
(5, 'Coco', 4, 'Perro', 'Caniche', 6, 'Macho', 5),
(6, 'Lola', 6, 'Perro', 'Labrador', 29, 'Hembra', 6),
(7, 'Simba', 2, 'Gato', 'Persa', 5, 'Macho', 7),
(8, 'Milo', 7, 'Perro', 'Golden Retriever', 34, 'Macho', 8),
(9, 'Olivia', 3, 'Gato', 'Bengala', 5, 'Hembra', 9),
(10, 'Teo', 8, 'Perro', 'Chihuahua', 3, 'Macho', 10),
(11, 'Bella', 4, 'Perro', 'French Poodle', 6, 'Hembra', 11),
(12, 'Tom', 1, 'Gato', 'Angora', 4, 'Macho', 12),
(13, 'Baco', 5, 'Perro', 'Boxer', 29, 'Macho', 13),
(14, 'Cleo', 2, 'Gato', 'Cruza', 4, 'Hembra', 14),
(15, 'Rocky', 6, 'Perro', 'Bulldog Francés', 12, 'Macho', 15),
(16, 'Nina', 3, 'Perro', 'Beagle', 13, 'Hembra', 16),
(17, 'Frida', 4, 'Gato', 'Siamés', 4, 'Hembra', 17),
(18, 'Paco', 1, 'Loro', 'Amazonas', 0, 'Macho', 18),
(19, 'Ciro', 2, 'Perro', 'Dachshund', 7, 'Macho', 19),
(20, 'Zoe', 5, 'Gato', 'Maine Coon', 7, 'Hembra', 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vacuna`
--

CREATE TABLE `vacuna` (
  `id` int(50) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `fecha_hora` datetime(6) NOT NULL,
  `vencimiento` datetime(6) NOT NULL,
  `id_mascota` int(45) NOT NULL,
  `id_consulta` int(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vacuna`
--

INSERT INTO `vacuna` (`id`, `tipo`, `fecha_hora`, `vencimiento`, `id_mascota`, `id_consulta`) VALUES
(1, 'Antirrábica', '2026-01-15 09:30:00.000000', '2027-01-15 00:00:00.000000', 1, 1),
(2, 'Quíntuple (Vanguard)', '2026-01-15 10:00:00.000000', '2027-01-15 00:00:00.000000', 1, 1),
(3, 'Triple Felina', '2026-02-10 11:15:00.000000', '2027-02-10 00:00:00.000000', 2, 2),
(4, 'Antirrábica Felina', '2026-02-10 11:30:00.000000', '2029-02-10 00:00:00.000000', 2, 2),
(5, 'Sextuple Canina', '2026-02-28 15:15:00.000000', '2027-02-28 00:00:00.000000', 3, 3),
(6, 'Leucemia Felina', '2026-03-12 09:00:00.000000', '2027-03-12 00:00:00.000000', 4, 4),
(7, 'Giardia', '2026-03-25 14:45:00.000000', '2026-09-25 00:00:00.000000', 5, 5),
(8, 'Antirrábica', '2026-04-05 08:30:00.000000', '2027-04-05 00:00:00.000000', 6, 6),
(9, 'Polivalente Canina', '2026-05-02 11:30:00.000000', '2027-05-02 00:00:00.000000', 8, 9),
(10, 'Triple Felina (Refuerzo)', '2026-05-18 17:30:00.000000', '2027-05-18 00:00:00.000000', 9, 10),
(11, 'Antirrábica Felina', '2026-05-18 17:45:00.000000', '2029-05-18 00:00:00.000000', 9, 10),
(12, 'KC (Tos de las perreras)', '2026-06-22 14:00:00.000000', '2027-06-22 00:00:00.000000', 10, 12),
(13, 'Quíntuple Canina', '2026-07-08 10:30:00.000000', '2027-07-08 00:00:00.000000', 11, 13),
(14, 'Leucemia Felina', '2026-07-29 08:45:00.000000', '2027-07-29 00:00:00.000000', 12, 15),
(15, 'Puppy DP (Primera dosis)', '2026-08-05 12:15:00.000000', '2026-09-05 00:00:00.000000', 13, 16),
(16, 'Antirrábica', '2026-08-14 08:00:00.000000', '2027-08-14 00:00:00.000000', 14, 17),
(17, 'Triple Felina', '2026-08-14 08:15:00.000000', '2027-08-14 00:00:00.000000', 14, 17),
(18, 'Sextuple Canina', '2026-08-20 22:45:00.000000', '2027-08-20 00:00:00.000000', 15, 18),
(19, 'KC (Tos de las perreras)', '2026-08-28 17:00:00.000000', '2027-08-28 00:00:00.000000', 16, 19),
(20, 'Antirrábica', '2026-09-02 10:30:00.000000', '2027-09-02 00:00:00.000000', 17, 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `veterinario`
--

CREATE TABLE `veterinario` (
  `id` int(50) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `password` int(25) NOT NULL,
  `especialidad` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `veterinario`
--

INSERT INTO `veterinario` (`id`, `nombre`, `password`, `especialidad`) VALUES
(1, 'Dr. Esteban Rossi', 0, 0),
(2, 'Dra. Andrea Méndez', 0, 0),
(3, 'Dr. Hugo Fontana', 0, 0),
(4, 'Dra. Paula Varela', 0, 0),
(5, 'Dr. Javier Ortega', 0, 0),
(6, 'Dra. Silvina Rios', 0, 0),
(7, 'Dr. Marcos Benítez', 0, 0),
(8, 'Dra. Natalia Morales', 0, 0),
(9, 'Dr. Roberto Cáceres', 0, 0),
(10, 'Dra. Florencia Silva', 0, 0),
(11, 'Dr. Alejandro Peña', 0, 0),
(12, 'Dra. Camila Garay', 0, 0),
(13, 'Dr. Gonzalo Pereyra', 0, 0),
(14, 'Dra. Lucía Domínguez', 0, 0),
(15, 'Dr. Matías Alonso', 0, 0),
(16, 'Dra. Valentina Castr', 0, 0),
(17, 'Dr. Fernando Guerra', 0, 0),
(18, 'Dra. Martina Bruno', 0, 0),
(19, 'Dr. Rodrigo Sosa', 0, 0),
(20, 'Dra. Sofía Martínez', 0, 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `consulta`
--
ALTER TABLE `consulta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_mascota` (`id_mascota`),
  ADD KEY `id_veterinario` (`id_veterinario`);

--
-- Indices de la tabla `dueño`
--
ALTER TABLE `dueño`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `mascota`
--
ALTER TABLE `mascota`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_dueño` (`id_dueño`);

--
-- Indices de la tabla `vacuna`
--
ALTER TABLE `vacuna`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_consulta` (`id_consulta`),
  ADD KEY `id_macota` (`id_mascota`);

--
-- Indices de la tabla `veterinario`
--
ALTER TABLE `veterinario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `consulta`
--
ALTER TABLE `consulta`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `dueño`
--
ALTER TABLE `dueño`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `mascota`
--
ALTER TABLE `mascota`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `vacuna`
--
ALTER TABLE `vacuna`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `veterinario`
--
ALTER TABLE `veterinario`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `consulta`
--
ALTER TABLE `consulta`
  ADD CONSTRAINT `id_mascota` FOREIGN KEY (`id_mascota`) REFERENCES `mascota` (`id`),
  ADD CONSTRAINT `id_veterinario` FOREIGN KEY (`id_veterinario`) REFERENCES `veterinario` (`id`);

--
-- Filtros para la tabla `mascota`
--
ALTER TABLE `mascota`
  ADD CONSTRAINT `id_dueño` FOREIGN KEY (`id_dueño`) REFERENCES `dueño` (`id`);

--
-- Filtros para la tabla `vacuna`
--
ALTER TABLE `vacuna`
  ADD CONSTRAINT `id_consulta` FOREIGN KEY (`id_consulta`) REFERENCES `consulta` (`id`),
  ADD CONSTRAINT `id_macota` FOREIGN KEY (`id_mascota`) REFERENCES `mascota` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

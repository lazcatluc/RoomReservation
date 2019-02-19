-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.9-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for rdbms
CREATE DATABASE IF NOT EXISTS `rdbms` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `rdbms`;

-- Dumping structure for table rdbms.addresses
CREATE TABLE IF NOT EXISTS `addresses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `city` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `street` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table rdbms.addresses: ~8 rows (approximately)
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` (`id`, `country`, `city`, `street`) VALUES
	(1, 'France', 'Paris', '2927, Donec Ave'),
	(2, 'USA', 'Rome', '5765, Ultrices Road, Kentucky'),
	(3, 'USA', 'Paris', '273-5124, Lectus Road, Illinois'),
	(4, 'Italy', 'Rome', '618-9647, Nunc Av.'),
	(5, 'Italy', 'Rome', '9282, Semper Street'),
	(6, 'Italy', 'Verona', '8631, Tellus Street'),
	(7, 'France', 'Paris', '7497, Euismod Ave'),
	(8, 'Italy', 'Palermo', '963-3851, Est. Rd.');
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;

-- Dumping structure for table rdbms.amenities
CREATE TABLE IF NOT EXISTS `amenities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table rdbms.amenities: ~6 rows (approximately)
/*!40000 ALTER TABLE `amenities` DISABLE KEYS */;
INSERT INTO `amenities` (`id`, `name`) VALUES
	(1, 'AC'),
	(2, 'Minibar'),
	(3, 'Parking'),
	(4, 'Personal Butler'),
	(5, 'Room Service'),
	(6, 'WiFi');
/*!40000 ALTER TABLE `amenities` ENABLE KEYS */;

-- Dumping structure for table rdbms.hotels
CREATE TABLE IF NOT EXISTS `hotels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `stars` tinyint(1) NOT NULL DEFAULT 3,
  `address_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `address_id_UNIQUE` (`address_id`),
  CONSTRAINT `FK_address` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table rdbms.hotels: ~8 rows (approximately)
/*!40000 ALTER TABLE `hotels` DISABLE KEYS */;
INSERT INTO `hotels` (`id`, `name`, `stars`, `address_id`) VALUES
	(1, 'Masseria', 3, 1),
	(2, 'Tongabezi', 4, 7),
	(3, 'Hotel Escondido', 2, 2),
	(4, 'Blackberry Farm', 1, 3),
	(5, 'Ritz', 4, 6),
	(6, 'Longitude 131', 4, 8),
	(7, 'Le Grand Bellevue', 3, 5),
	(8, 'Belmond Grand Hotel', 5, 4);
/*!40000 ALTER TABLE `hotels` ENABLE KEYS */;

-- Dumping structure for table rdbms.rooms
CREATE TABLE IF NOT EXISTS `rooms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `type_id` int(11) NOT NULL,
  `floor` tinyint(4) NOT NULL DEFAULT 0,
  `hotel_id` int(11) NOT NULL,
  `capacity` tinyint(4) NOT NULL DEFAULT 2,
  `price` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_ROOM_HOTEL_NAME` (`name`,`hotel_id`),
  KEY `INDEX_TYPE_ID` (`type_id`),
  KEY `INDEX_HOTEL_ID` (`hotel_id`),
  CONSTRAINT `FK_hotel` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`id`),
  CONSTRAINT `FK_type` FOREIGN KEY (`type_id`) REFERENCES `types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table rdbms.rooms: ~55 rows (approximately)
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` (`id`, `name`, `type_id`, `floor`, `hotel_id`, `capacity`, `price`) VALUES
	(1, '10', 1, 1, 1, 1, 81),
	(2, '11', 1, 1, 1, 1, 112),
	(3, '12', 1, 1, 1, 1, 44),
	(4, '21', 2, 2, 1, 2, 52),
	(5, '22', 2, 2, 1, 2, 56),
	(6, '23', 2, 2, 1, 2, 63),
	(7, '10', 1, 1, 2, 1, 105),
	(8, '11', 1, 1, 2, 1, 120),
	(9, '12', 2, 1, 2, 2, 46),
	(10, '13', 2, 1, 2, 2, 42),
	(11, '14', 2, 1, 2, 2, 25),
	(12, '15', 2, 1, 2, 3, 120),
	(13, '20', 3, 2, 2, 4, 119),
	(14, '21', 3, 2, 2, 4, 81),
	(15, '22', 3, 2, 2, 4, 64),
	(16, '30', 3, 3, 2, 6, 123),
	(17, 'President Ford', 4, 3, 2, 2, 78),
	(18, 'Ronald Reagan', 4, 3, 2, 4, 37),
	(19, '100', 2, 1, 3, 2, 38),
	(20, '101', 2, 1, 3, 2, 93),
	(21, '201', 2, 2, 3, 3, 96),
	(22, '202', 2, 2, 3, 3, 87),
	(23, '301', 3, 3, 3, 4, 51),
	(24, '302', 3, 3, 3, 6, 25),
	(25, '1', 2, 0, 4, 4, 72),
	(26, '2', 2, 0, 4, 8, 53),
	(27, '2001', 1, 20, 5, 1, 15),
	(28, '2002', 1, 20, 5, 1, 112),
	(29, '4001', 3, 40, 5, 4, 61),
	(30, '4002', 3, 40, 5, 4, 63),
	(31, 'Summer', 4, 40, 5, 2, 147),
	(32, '500', 2, 50, 6, 2, 126),
	(33, '501', 2, 50, 6, 2, 126),
	(34, '502', 2, 50, 6, 2, 38),
	(35, '503', 2, 50, 6, 2, 93),
	(36, '504', 3, 50, 6, 2, 54),
	(37, '505', 3, 50, 6, 2, 37),
	(38, '801', 4, 80, 6, 3, 5),
	(39, '802', 4, 80, 6, 3, 136),
	(40, '10', 1, 1, 7, 1, 82),
	(41, '11', 1, 1, 7, 1, 64),
	(42, '12', 1, 1, 7, 1, 71),
	(43, '13', 1, 1, 7, 1, 76),
	(44, 'Spring', 2, 2, 7, 2, 80),
	(45, 'Autumn', 2, 2, 7, 2, 97),
	(46, 'Winter', 2, 2, 7, 2, 84),
	(47, 'Summer', 2, 2, 7, 2, 17),
	(60, '801', 1, 80, 8, 1, 94),
	(61, '802', 1, 80, 8, 1, 67),
	(62, '901', 2, 90, 8, 2, 54),
	(63, '902', 2, 90, 8, 2, 70),
	(64, '1001', 3, 100, 8, 4, 71),
	(65, '1002', 3, 100, 8, 4, 111),
	(66, '1003', 4, 100, 8, 2, 56),
	(67, '1004', 4, 100, 8, 4, 48);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;

-- Dumping structure for table rdbms.room_amenities
CREATE TABLE IF NOT EXISTS `room_amenities` (
  `room_id` int(11) NOT NULL,
  `amenity_id` int(11) NOT NULL,
  PRIMARY KEY (`room_id`,`amenity_id`),
  KEY `FK_AMENETY_idx` (`amenity_id`),
  CONSTRAINT `FK_AMENETY` FOREIGN KEY (`amenity_id`) REFERENCES `amenities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ROOM` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table rdbms.room_amenities: ~195 rows (approximately)
/*!40000 ALTER TABLE `room_amenities` DISABLE KEYS */;
INSERT INTO `room_amenities` (`room_id`, `amenity_id`) VALUES
	(1, 1),
	(1, 2),
	(1, 3),
	(1, 6),
	(2, 1),
	(2, 2),
	(2, 3),
	(2, 6),
	(3, 1),
	(3, 2),
	(3, 3),
	(3, 6),
	(4, 1),
	(4, 2),
	(4, 3),
	(4, 6),
	(5, 1),
	(5, 2),
	(5, 3),
	(5, 6),
	(6, 1),
	(6, 2),
	(6, 3),
	(6, 6),
	(7, 1),
	(7, 5),
	(7, 6),
	(8, 1),
	(8, 5),
	(8, 6),
	(9, 1),
	(9, 5),
	(9, 6),
	(10, 1),
	(10, 5),
	(10, 6),
	(11, 1),
	(11, 5),
	(11, 6),
	(12, 1),
	(12, 5),
	(12, 6),
	(13, 1),
	(13, 5),
	(13, 6),
	(14, 1),
	(14, 5),
	(14, 6),
	(15, 1),
	(15, 5),
	(15, 6),
	(16, 1),
	(16, 5),
	(16, 6),
	(17, 1),
	(17, 5),
	(17, 6),
	(18, 1),
	(18, 5),
	(18, 6),
	(19, 1),
	(19, 3),
	(20, 1),
	(20, 3),
	(21, 1),
	(21, 3),
	(22, 1),
	(22, 3),
	(23, 1),
	(23, 3),
	(24, 1),
	(24, 3),
	(25, 3),
	(26, 3),
	(27, 1),
	(27, 2),
	(27, 3),
	(27, 5),
	(27, 6),
	(28, 1),
	(28, 2),
	(28, 3),
	(28, 5),
	(28, 6),
	(29, 1),
	(29, 2),
	(29, 3),
	(29, 5),
	(29, 6),
	(30, 1),
	(30, 2),
	(30, 3),
	(30, 5),
	(30, 6),
	(31, 1),
	(31, 2),
	(31, 3),
	(31, 5),
	(31, 6),
	(32, 1),
	(32, 2),
	(32, 5),
	(32, 6),
	(33, 1),
	(33, 2),
	(33, 5),
	(33, 6),
	(34, 1),
	(34, 2),
	(34, 5),
	(34, 6),
	(35, 1),
	(35, 2),
	(35, 5),
	(35, 6),
	(36, 1),
	(36, 2),
	(36, 5),
	(36, 6),
	(37, 1),
	(37, 2),
	(37, 5),
	(37, 6),
	(38, 1),
	(38, 2),
	(38, 5),
	(38, 6),
	(39, 1),
	(39, 2),
	(39, 5),
	(39, 6),
	(40, 1),
	(40, 6),
	(41, 1),
	(41, 6),
	(42, 1),
	(42, 6),
	(43, 1),
	(43, 6),
	(44, 1),
	(44, 6),
	(45, 1),
	(45, 6),
	(46, 1),
	(46, 6),
	(47, 1),
	(47, 6),
	(60, 1),
	(60, 2),
	(60, 3),
	(60, 4),
	(60, 5),
	(60, 6),
	(61, 1),
	(61, 2),
	(61, 3),
	(61, 4),
	(61, 5),
	(61, 6),
	(62, 1),
	(62, 2),
	(62, 3),
	(62, 4),
	(62, 5),
	(62, 6),
	(63, 1),
	(63, 2),
	(63, 3),
	(63, 4),
	(63, 5),
	(63, 6),
	(64, 1),
	(64, 2),
	(64, 3),
	(64, 4),
	(64, 5),
	(64, 6),
	(65, 1),
	(65, 2),
	(65, 3),
	(65, 4),
	(65, 5),
	(65, 6),
	(66, 1),
	(66, 2),
	(66, 3),
	(66, 4),
	(66, 5),
	(66, 6),
	(67, 1),
	(67, 2),
	(67, 3),
	(67, 4),
	(67, 5),
	(67, 6);
/*!40000 ALTER TABLE `room_amenities` ENABLE KEYS */;

-- Dumping structure for table rdbms.types
CREATE TABLE IF NOT EXISTS `types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table rdbms.types: ~4 rows (approximately)
/*!40000 ALTER TABLE `types` DISABLE KEYS */;
INSERT INTO `types` (`id`, `name`) VALUES
	(3, 'Apartment'),
	(2, 'Double'),
	(4, 'Executive'),
	(1, 'Single');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

CREATE TABLE IF NOT EXISTS `reservations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `guest` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  `room_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_reservation_room` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



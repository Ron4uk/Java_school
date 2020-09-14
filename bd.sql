

CREATE DATABASE IF NOT EXISTS `bd_operator`;

CREATE TABLE IF NOT EXISTS `authorizations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4;


INSERT INTO `authorizations` (`id`, `password`) VALUES
	(1, '$2a$10$xmiK0AvfA0RQUga3VJhz3e1yprdQV2KZFlaiQppMESWEES0OHuDIO'),
	(2, '$2a$10$xmiK0AvfA0RQUga3VJhz3e1yprdQV2KZFlaiQppMESWEES0OHuDIO'),
	(5, '$2a$10$xmiK0AvfA0RQUga3VJhz3e1yprdQV2KZFlaiQppMESWEES0OHuDIO'),
	(6, '$2a$10$xmiK0AvfA0RQUga3VJhz3e1yprdQV2KZFlaiQppMESWEES0OHuDIO'),
	(11, '$2a$10$xmiK0AvfA0RQUga3VJhz3e1yprdQV2KZFlaiQppMESWEES0OHuDIO'),
	(13, '$2a$10$nVQvG./v/Qu/B2FAdGJqZe0mqHWH7c6YyCyWcC2RCT0BQceu/2KLW'),
	(14, '$2a$10$eTiMJjViKQYS75n/ztjBXuZMfmH.PqlVwpqzrxD8hnBR7W0GijUOS'),
	(15, '$2a$10$73NGkj1AJKfX9Nir.ktDw.Fg.mRaedncp6A.cbbFbdkYbvNR3ezuC'),
	(16, '$2a$10$0WqgRRbKV2DiJaTgziQpnutb34mDhuB9T.aZxQyF1I9Pza2nDrWry'),
	(17, '$2a$10$2KkAbVhu.ooApkU7OwedyufaBI7yHTsc/RHMHprjyNFpml.EO9FeS'),
	(18, '$2a$10$y2Ir0v4Ngh8QX6v/Q4aoeOMUZWWn4QevHnuwCxcvfD3n56jhVp8dq'),
	(19, '$2a$10$hReb960a.WM36F7YnN6Uj.YRDMh2mg61pw8oe2GnGlpFvVehjkFTi'),
	(20, '$2a$10$3QT29uSLjIW6lt3DIIfo3.RTouKaggYmPZXt2Ots6BgVMAiGBEO9a'),
	(21, '$2a$10$rPChpNnKC4aHiKlqTXr7SuSmwTjRO8dgeYVsl3cOtThcpuDE3sgn2'),
	(22, '$2a$10$MRL/eam7PjYZQehz8f9ceuBu7ZqZ2grh46Q90jtjyKZHO1Y6ar.hu'),
	(23, '$2a$10$K5tWSsYn7Ae105rReIrTh.KQMBmELUTF8BWiapeElbHvH0wRkVTP.'),
	(24, '$2a$10$FyJVFQxA8CroJTN4l680Z.7wEBUKDKlPg.r8WB.pLvwBRZ/t2wRoK'),
	(25, '$2a$10$h3pFxtM7xtdChIM8hAXGN.cf4MycKTTWsiNOZ1KlXEw4lVhtQidRK'),
	(26, '$2a$10$TblZ1u4NoEOpSFVi58n6e.sU6DbRowmdqWiamY6QGpgMfWm2AHN7a'),
	(27, '$2a$10$9En2zH68p8.KzCsTgZwf9uSttkTxB0eN0G4hhS3GQEcvd8UH.Rbdi'),
	(28, '$2a$10$PD4pJkKyp2GgVkee5tHNaOlARdNqKB0hO43mBzNBNu2mij0xbLnTC'),
	(29, '$2a$10$j1.suwnwMOPxDqClyDc4du291RVuLoUMei/87u6E0WRcB.yq5Sd32'),
	(30, '$2a$10$5IBPr2audCP/niMcEw0K7uTolbIjHucI6JYsz36PVPhVikW93Uwqy');


CREATE TABLE IF NOT EXISTS `auth_roles` (
  `auth_id` int NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FKo5r9xl43o33pjjhwjaxpjcns0` (`auth_id`),
  CONSTRAINT `FKo5r9xl43o33pjjhwjaxpjcns0` FOREIGN KEY (`auth_id`) REFERENCES `authorizations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `auth_roles` (`auth_id`, `roles`) VALUES
	(1, 'CLIENT'),
	(2, 'EMPL'),
	(5, 'CLIENT'),
	(6, 'CLIENT'),
	(11, 'CLIENT'),
	(13, 'CLIENT'),
	(14, 'CLIENT'),
	(15, 'CLIENT'),
	(16, 'CLIENT'),
	(17, 'CLIENT'),
	(18, 'CLIENT'),
	(19, 'CLIENT'),
	(20, 'CLIENT'),
	(21, 'CLIENT'),
	(22, 'CLIENT'),
	(23, 'CLIENT'),
	(24, 'CLIENT'),
	(25, 'CLIENT'),
	(26, 'CLIENT'),
	(27, 'CLIENT'),
	(28, 'CLIENT'),
	(29, 'CLIENT'),
	(30, 'CLIENT');


CREATE TABLE IF NOT EXISTS `clients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `series` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `block_client` bit(1) DEFAULT NULL,
  `block_operator` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKoj4xxyl2vcxydwqkothnc9d1b` (`series`,`number`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 ;


INSERT INTO `clients` (`id`, `firstname`, `lastname`, `birthday`, `email`, `number`, `series`, `address`, `block_client`, `block_operator`) VALUES
	(1, 'John', 'Wick', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(2, 'Brandon', 'Lee', '1955-02-10', '', '', '', 'Spb', NULL, NULL),
	(3, 'Harry', 'Smith', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(4, 'Rom', 'Drom', '2005-02-09', 'mail@mail.ru', '2222', '1111', NULL, NULL, NULL),
	(14, 'James', 'Bond', '1950-10-10', 'bond@bond.ru', '22223', '1111', 'Spb 21', NULL, NULL),
	(15, 'Gragory', 'Lucker', '1963-02-10', 'grag@mail.ru', '2222', '23', 'Spb', NULL, NULL),
	(17, 'Bom', 'bom', '1111-11-11', 'mail@mail.ruk', '123', '11', 'Spb', NULL, NULL),
	(31, 'Jinny', 'Holms', '2001-12-01', 'jinny@gmail.com', '56789', '1234', 'NY', NULL, NULL),
	(32, 'Robin', 'Hood', '2000-02-10', 'robin@robin.com', '12', '12', 'Spb', NULL, NULL),
	(33, 'Robin', 'bobin', '1950-12-10', 'imail@mail.ruu', '123432', '23', 'Spb', NULL, NULL),
	(34, 'Bom', 'Drom', '2000-01-01', 'mail@mail.rudd', '123213', '2312', 'Piter', NULL, NULL);


CREATE TABLE IF NOT EXISTS `tariffs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price` decimal(6,2) NOT NULL,
  `tariff` varchar(255) NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4;


INSERT INTO `tariffs` (`id`, `price`, `tariff`, `deleted`) VALUES
	(1, 1000.20, 'tar1', b'0'),
	(2, 250.00, 'tar2', b'0'),
	(4, 101.99, 'tar3', b'0'),
	(10, 100.25, 'MyTariff', b'0'),
	(27, 99.00, 'All Or Nothing', b'0'),
	(31, 100.11, 'TESTtariff', b'0');




CREATE TABLE IF NOT EXISTS `contracts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) DEFAULT NULL,
  `auth_id` int DEFAULT NULL,
  `client_id` int NOT NULL,
  `tariff_id` int NOT NULL,
  `block_client` bit(1) DEFAULT NULL,
  `block_operator` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2ufphny19w3fiqe0tkg91jr8k` (`auth_id`),
  KEY `FK2moerylc99q3vbvvq6e4upg66` (`client_id`),
  KEY `FKix2nn0s1mhcwefhwvapxuksb` (`tariff_id`),
  CONSTRAINT `FK2moerylc99q3vbvvq6e4upg66` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`),
  CONSTRAINT `FK2ufphny19w3fiqe0tkg91jr8k` FOREIGN KEY (`auth_id`) REFERENCES `authorizations` (`id`),
  CONSTRAINT `FKix2nn0s1mhcwefhwvapxuksb` FOREIGN KEY (`tariff_id`) REFERENCES `tariffs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;


INSERT INTO `contracts` (`id`, `phone`, `auth_id`, `client_id`, `tariff_id`, `block_client`, `block_operator`) VALUES
	(1, '89581735935', 2, 1, 2, b'0', b'0'),
	(2, '89213950964', 1, 2, 1, b'0', b'0'),
	(3, '89001001010', 5, 14, 10, b'0', b'0'),
	(5, '89001001000', 6, 15, 2, b'0', b'0'),
	(7, '89001001022', 11, 17, 4, b'0', b'0'),
	(22, '89119112020', 26, 31, 4, b'0', b'0'),
	(24, '89876543210', 28, 32, 27, b'0', b'0'),
	(25, '89876554210', 29, 33, 4, NULL, NULL),
	(26, '89541234565', 30, 34, 4, NULL, NULL);
	
	
CREATE TABLE IF NOT EXISTS `options` (
  `id` int NOT NULL AUTO_INCREMENT,
  `connection_cost` decimal(6,2) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(6,2) NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKb61pbyx1h9dkcsxj4i7mgvikc` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4;


INSERT INTO `options` (`id`, `connection_cost`, `name`, `price`, `deleted`) VALUES
	(8, 3.00, 'opt3', 3.00, b'0'),
	(10, 5.00, 'opt5', 5.00, b'0'),
	(13, 12.00, 'opt4', 10.00, b'0'),
	(17, 1.00, 'opt10', 1.00, b'0'),
	(18, 1.00, 'opt8', 1.00, b'0'),
	(19, 1.00, 'opt6', 1.00, b'0'),
	(23, 1.00, 'opt7', 1.00, b'0'),
	(50, 2.00, 'TESTopt1', 2.00, b'0'),
	(51, 1.00, 'TESTopt2', 1.00, b'0');



CREATE TABLE IF NOT EXISTS `exclusion_options` (
  `Option_id` int NOT NULL,
  `exclusionOptions_id` int NOT NULL,
  PRIMARY KEY (`Option_id`,`exclusionOptions_id`),
  KEY `FKrp6atuyovfdh621msf8ml5ovl` (`exclusionOptions_id`),
  CONSTRAINT `FK2qhpc1i8wqth7ukjw97tbx4vp` FOREIGN KEY (`Option_id`) REFERENCES `options` (`id`),
  CONSTRAINT `FKrp6atuyovfdh621msf8ml5ovl` FOREIGN KEY (`exclusionOptions_id`) REFERENCES `options` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `exclusion_options` (`Option_id`, `exclusionOptions_id`) VALUES
	(8, 19);



CREATE TABLE IF NOT EXISTS `options_in_tariff` (
  `tariff_id` int NOT NULL,
  `option_id` int NOT NULL,
  PRIMARY KEY (`tariff_id`,`option_id`),
  KEY `FK9c3ruccvlj3h0m2oyiepdtwk9` (`option_id`),
  CONSTRAINT `FK9c3ruccvlj3h0m2oyiepdtwk9` FOREIGN KEY (`option_id`) REFERENCES `options` (`id`),
  CONSTRAINT `FKlolgc2h0fsbf6x6oen6mjmddj` FOREIGN KEY (`tariff_id`) REFERENCES `tariffs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `options_in_tariff` (`tariff_id`, `option_id`) VALUES
	(1, 8),
	(2, 8),
	(4, 8),
	(10, 8),
	(31, 8),
	(1, 10),
	(2, 10),
	(4, 10),
	(10, 10),
	(31, 10),
	(1, 13),
	(2, 13),
	(4, 13),
	(10, 13),
	(31, 13),
	(10, 17),
	(1, 18),
	(2, 18),
	(4, 18),
	(10, 18),
	(1, 19),
	(2, 19),
	(4, 19),
	(10, 19),
	(31, 19),
	(10, 23),
	(27, 23),
	(31, 50),
	(31, 51);


CREATE TABLE IF NOT EXISTS `required_options` (
  `Option_id` int NOT NULL,
  `requiredOptions_id` int NOT NULL,
  PRIMARY KEY (`Option_id`,`requiredOptions_id`),
  KEY `FK80xpsbwparxv043ielahsdcdi` (`requiredOptions_id`),
  CONSTRAINT `FK80xpsbwparxv043ielahsdcdi` FOREIGN KEY (`requiredOptions_id`) REFERENCES `options` (`id`),
  CONSTRAINT `FKkeipprv9jnmjndbfsrtyj8wju` FOREIGN KEY (`Option_id`) REFERENCES `options` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `required_options` (`Option_id`, `requiredOptions_id`) VALUES
	(8, 10),
	(50, 51);

CREATE TABLE IF NOT EXISTS `connected_options` (
  `contract_id` int NOT NULL,
  `option_id` int NOT NULL,
  PRIMARY KEY (`contract_id`,`option_id`),
  KEY `FKblo48lbggxi13797kivy1gjm6` (`option_id`),
  CONSTRAINT `FK3v9lfuvwl9x3ilojvvnt2dv0` FOREIGN KEY (`contract_id`) REFERENCES `contracts` (`id`),
  CONSTRAINT `FKblo48lbggxi13797kivy1gjm6` FOREIGN KEY (`option_id`) REFERENCES `options` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `connected_options` (`contract_id`, `option_id`) VALUES
	(2, 8),
	(25, 8),
	(26, 8),
	(2, 10),
	(3, 10),
	(5, 10),
	(7, 10),
	(22, 10),
	(25, 10),
	(26, 10),
	(2, 13),
	(5, 13),
	(7, 13),
	(22, 13),
	(3, 19);
	

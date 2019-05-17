-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1:3306
-- Létrehozás ideje: 2019. Ápr 23. 05:07
-- Kiszolgáló verziója: 5.7.24
-- PHP verzió: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `Kolcsonzo`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `autok`
--

DROP TABLE IF EXISTS `autok`;
CREATE TABLE IF NOT EXISTS `autok` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `tipus` varchar(100) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `szin` varchar(50) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `evjarat` int(4) NOT NULL,
  `rendszam` varchar(7) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `berelve` tinyint(1) NOT NULL,
  `napidij` int(7) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rendszam` (`rendszam`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `berlok`
--

DROP TABLE IF EXISTS `berlok`;
CREATE TABLE IF NOT EXISTS `berlok` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nev` varchar(100) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `jogositvanyszam` varchar(8) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `telefonszam` varchar(25) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `cim` varchar(150) COLLATE utf8mb4_hungarian_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `jogositvanyszam` (`jogositvanyszam`),
  UNIQUE KEY `telefonszam` (`telefonszam`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `kolcsonzesek`
--

DROP TABLE IF EXISTS `kolcsonzesek`;
CREATE TABLE IF NOT EXISTS `kolcsonzesek` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `autoid` int(10) NOT NULL,
  `berloid` int(10) NOT NULL,
  `kezdete` date NOT NULL,
  `vege` date DEFAULT NULL,
  `fizetett` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `autoid` (`autoid`),
  KEY `berloid` (`berloid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `kolcsonzesek`
--
ALTER TABLE `kolcsonzesek`
  ADD CONSTRAINT `kolcsonzesek_ibfk_1` FOREIGN KEY (`autoid`) REFERENCES `autok` (`id`),
  ADD CONSTRAINT `kolcsonzesek_ibfk_2` FOREIGN KEY (`berloid`) REFERENCES `berlok` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

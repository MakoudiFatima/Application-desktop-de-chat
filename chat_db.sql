-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 30 avr. 2022 à 03:20
-- Version du serveur :  10.4.19-MariaDB
-- Version de PHP : 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `chat_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `contacte`
--

CREATE TABLE `contacte` (
  `id_contact` int(10) NOT NULL,
  `user_name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `contact` varchar(50) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `contacte`
--

INSERT INTO `contacte` (`id_contact`, `user_name`, `contact`) VALUES
(2, 'oumaima', 'rabab'),
(6, 'hajar', 'oumaima'),
(11, 'oumaima', 'hajar'),
(13, 'oumaima', 'soumaya'),
(14, 'hajar', 'rania'),
(18, 'hajar', 'rabab'),
(19, 'rania', 'rabab'),
(20, 'rabab', 'rania'),
(21, 'rabab', 'fatima'),
(22, 'fatima', 'rabab'),
(23, 'fatima', 'hajar'),
(24, 'hajar', 'fatima'),
(25, 'fatima', 'rania'),
(27, 'soumaya', 'oumaima'),
(28, 'test1', 'fatima'),
(29, 'fatima', 'test1'),
(30, 'test1', 'rabab'),
(31, 'rabab', 'test1'),
(32, 'test3', 'oumaima'),
(34, 'oumaima', 'fatima'),
(35, 'fatima', 'oumaima'),
(36, 'oumaima', 'rania'),
(37, 'rania', 'oumaima'),
(38, 'rabab', 'oumaima'),
(39, 'oumaima', 'rabab'),
(41, 'test6', 'oumaima');

-- --------------------------------------------------------

--
-- Structure de la table `messages`
--

CREATE TABLE `messages` (
  `id_message` int(11) NOT NULL,
  `id_emetteur` varchar(50) NOT NULL,
  `id_recepteur` varchar(50) NOT NULL,
  `message_text` varchar(5000) NOT NULL,
  `date` varchar(50) NOT NULL,
  `filename` varchar(200) DEFAULT NULL,
  `path` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `messages`
--

INSERT INTO `messages` (`id_message`, `id_emetteur`, `id_recepteur`, `message_text`, `date`, `filename`, `path`) VALUES
(90, 'oumaima', 'soumaya', 'salut', '2022-04-22', '', NULL),
(91, 'oumaima', 'soumaya', '', '2022-04-22', '1.jpg', NULL),
(92, 'oumaima', 'soumaya', '', '2022-04-22', '1.jpg', NULL),
(93, 'oumaima', 'soumaya', 'salut', '2022-04-22', '', NULL),
(94, 'soumaya', 'oumaima', 'saluuut', '2022-04-22', '', NULL),
(95, 'oumaima', 'soumaya', '', '2022-04-22', 'Projet_UML_final.docx', NULL),
(96, 'oumaima', 'soumaya', '', '2022-04-22', 'a retenir.docx', NULL),
(97, 'oumaima', 'soumaya', '', '2022-04-22', '1.jpg', NULL),
(98, 'oumaima', 'soumaya', '', '2022-04-22', '1.jpg', NULL),
(99, 'oumaima', 'soumaya', '', '2022-04-22', 'a retenir.docx', NULL),
(100, 'oumaima', 'soumaya', 'salut', '2022-04-22', '', NULL),
(101, 'soumaya', 'oumaima', '', '2022-04-22', 'La première interface est l.docx', NULL),
(102, 'soumaya', 'oumaima', 'Le Java est un langage de programmation', '2022-04-22', '', NULL),
(103, 'oumaima', 'soumaya', 'Une socket est une interface de communication introduite par les systèmes Unix.', '2022-04-22', '', NULL),
(104, 'soumaya', 'rabab', 'salut rabab', '2022-04-22', '', NULL),
(105, 'soumaya', 'rabab', '', '2022-04-22', '1.jpg', NULL),
(106, 'soumaya', 'rabab', '', '2022-04-22', '1.jpg', NULL),
(107, 'soumaya', 'rabab', '', '2022-04-22', 'a retenir.docx', NULL),
(108, 'soumaya', 'rabab', '', '2022-04-22', 'a retenir.docx', NULL),
(109, 'oumaima', 'rabab', 'saluuut', '2022-04-22', '', NULL),
(110, 'oumaima', 'fatima', 'hiiiiiiiii', '2022-04-24', '', NULL),
(111, 'hajar', 'soumaya', '', '2022-04-28', '1.jpg', NULL),
(112, 'hajar', 'oumaima', '', '2022-04-28', '1.jpg', NULL),
(113, 'hajar', 'rania', 'saaaaaaaaaaaaaaaalamsaaaa', '2022-04-28', '', NULL),
(114, 'hajar', 'rania', '', '2022-04-28', '1.jpg', NULL),
(115, 'hajar', 'rania', '', '2022-04-28', '1.jpg', NULL),
(116, 'hajar', 'fatima', '', '2022-04-28', '1.jpg', NULL),
(117, 'oumaima', 'hajar', '', '2022-04-28', '1.jpg', NULL),
(118, 'oumaima', 'hajar', '', '2022-04-28', 'a retenir.docx', NULL),
(119, 'oumaima', 'hajar', '', '2022-04-29', 'a retenir.docx', NULL),
(120, 'oumaima', 'soumaya', '', '2022-04-29', '1.jpg', NULL),
(127, 'oumaima', 'rabab', '', '2022-04-29', 'a retenir.docx', NULL),
(128, 'oumaima', 'rania', '', '2022-04-29', 'Projet_UML_final.docx', NULL),
(129, 'soumaya', 'oumaima', ' salam', '2022-04-29', '', NULL),
(130, 'oumaima', 'rabab', 'salam', '2022-04-29', '', NULL),
(131, 'oumaima', 'oumaima', 'hell', '2022-04-29', '', NULL),
(132, 'oumaima', 'soumaya', 'salut', '2022-04-29', '', NULL),
(133, 'soumaya', 'oumaima', 'ooo', '2022-04-29', '', NULL),
(134, 'oumaima', 'soumaya', 'hello', '2022-04-29', '', NULL),
(135, 'oumaima', 'soumaya', '', '2022-04-29', '1.jpg', NULL),
(136, 'oumaima', 'fatima', 'salut', '2022-04-29', '', NULL),
(137, 'oumaima', 'soumaya', 'salut', '2022-04-29', '', NULL),
(138, 'soumaya', 'oumaima', 'bonjour', '2022-04-29', '', NULL),
(139, 'soumaya', 'oumaima', '', '2022-04-29', '1.jpg', NULL),
(140, 'soumaya', 'oumaima', '', '2022-04-29', '1.jpg', NULL),
(141, 'oumaima', 'soumaya', 'salam', '2022-04-29', '', NULL),
(142, 'oumaima', 'soumaya', 'salut', '2022-04-29', '', NULL),
(143, 'soumaya', 'oumaima', 'salut', '2022-04-29', '', NULL),
(144, 'oumaima', 'soumaya', 'sala,', '2022-04-29', '', NULL),
(145, 'oumaima', 'soumaya', 'salut', '2022-04-29', '', NULL),
(146, 'oumaima', 'oumaima', 'lkjhg', '2022-04-29', '', NULL),
(147, 'oumaima', 'oumaima', '', '2022-04-29', '1.jpg', NULL),
(148, 'oumaima', 'soumaya', '', '2022-04-29', '1.jpg', NULL),
(149, 'oumaima', 'soumaya', '', '2022-04-29', '1.jpg', NULL),
(150, 'oumaima', 'soumaya', 'salam', '2022-04-29', '', NULL),
(151, 'oumaima', 'oumaima', 'o', '2022-04-29', '', NULL),
(152, 'soumaya', 'oumaima', 'hooo', '2022-04-29', '', NULL),
(153, 'oumaima', 'soumaya', 'hello', '2022-04-29', '', NULL),
(154, 'soumaya', 'oumaima', 'hi', '2022-04-29', '', NULL),
(155, 'oumaima', 'soumaya', 'salut', '2022-04-29', '', NULL),
(156, 'soumaya', 'oumaima', '', '2022-04-29', '1.jpg', NULL),
(157, 'soumaya', 'oumaima', 'salam 12', '2022-04-29', '', NULL),
(158, 'oumaima', 'soumaya', 'hello', '2022-04-29', '', NULL),
(159, 'soumaya', 'oumaima', 'salam', '2022-04-29', '', NULL),
(160, 'soumaya', 'oumaima', '', '2022-04-29', 'Projet_UML_final.docx', NULL),
(161, 'oumaima', 'soumaya', '', '2022-04-29', '1.jpg', NULL),
(162, 'soumaya', 'oumaima', 'ddd', '2022-04-29', '', NULL),
(163, 'soumaya', 'oumaima', 'oooo', '2022-04-29', '', NULL),
(164, 'soumaya', 'oumaima', '', '2022-04-29', 'Sure.docx', NULL),
(165, 'oumaima', 'soumaya', 'bien recu', '2022-04-29', '', NULL),
(166, 'oumaima', 'soumaya', '', '2022-04-29', 'Sure.docx', NULL),
(167, 'soumaya', 'oumaima', 'salam', '2022-04-29', '', NULL),
(168, 'oumaima', 'soumaya', 'saluut', '2022-04-29', '', NULL),
(169, 'oumaima', 'soumaya', 'salam', '2022-04-29', '', NULL),
(170, 'soumaya', 'oumaima', 'salam', '2022-04-29', '', NULL),
(171, 'soumaya', 'oumaima', '', '2022-04-29', 'a retenir.docx', NULL),
(172, 'oumaima', 'soumaya', '', '2022-04-29', 'Projet_UML_final.docx', NULL),
(173, 'soumaya', 'oumaima', 'salam', '2022-04-29', '', NULL),
(174, 'oumaima', 'soumaya', 'hello', '2022-04-29', '', NULL),
(175, 'soumaya', 'oumaima', '', '2022-04-29', '1.jpg', NULL),
(176, 'oumaima', 'soumaya', '', '2022-04-29', 'a retenir.docx', NULL),
(177, 'oumaima', 'soumaya', 'salam', '2022-04-29', '', NULL),
(178, 'soumaya', 'oumaima', 'salam', '2022-04-29', '', NULL),
(179, 'soumaya', 'oumaima', '', '2022-04-29', 'Sure.docx', NULL),
(180, 'soumaya', 'oumaima', 'boooonjjjoournoobb', '2022-04-29', '', NULL),
(181, 'soumaya', 'oumaima', 'salam kif bqiti', '2022-04-29', '', NULL),
(182, 'soumaya', 'oumaima', 'salaam', '2022-04-29', '', NULL),
(183, 'oumaima', 'soumaya', 'kooo', '2022-04-29', '', NULL),
(184, 'soumaya', 'oumaima', 'kooo', '2022-04-29', '', NULL),
(185, 'soumaya', 'oumaima', 'hello', '2022-04-29', '', NULL),
(186, 'oumaima', 'soumaya', '', '2022-04-29', '1.jpg', NULL),
(187, 'rabab', 'oumaima', 'hiiiiiiiiiloooo my frieeeeeeeeeeeend', '2022-04-29', '', NULL),
(188, 'oumaima', 'rabab', '', '2022-04-29', 'Projet_UML_final.docx', NULL),
(190, 'oumaima', 'soumaya', 'ouiiii ! je suis toute prete', '2022-04-29', '', NULL),
(191, 'oumaima', 'soumaya', 'courage a notre equipe', '2022-04-29', '', NULL),
(192, 'soumaya', 'oumaima', '', '2022-04-29', '1.jpg', NULL),
(193, 'oumaima', 'soumaya', 'bieeen recuuu oumaima c gentiiiil', '2022-04-29', '', NULL),
(194, 'soumaya', 'oumaima', 'salut', '2022-04-29', '', NULL),
(197, 'oumaima', 'soumaya', 'saluuut ', '2022-04-30', '', NULL),
(198, 'soumaya', 'oumaima', '', '2022-04-30', 'Projet_UML_final.docx', NULL),
(199, 'soumaya', 'oumaima', '', '2022-04-30', 'site web.rar', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `user_tb`
--

CREATE TABLE `user_tb` (
  `name` varchar(50) NOT NULL,
  `pass` varchar(45) NOT NULL,
  `state` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `user_tb`
--

INSERT INTO `user_tb` (`name`, `pass`, `state`) VALUES
('amina', '1234', 0),
('fatima', '1234', 0),
('fatine', '1234', 0),
('hajar', '1234', 0),
('oumaima', '12345', 1),
('rabab', '1234', 0),
('rania', '1234', 0),
('soumaya', '1234', 1),
('test1', '1234', 0),
('test3', '1234', 2),
('test6', '1234', 0);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `contacte`
--
ALTER TABLE `contacte`
  ADD PRIMARY KEY (`id_contact`),
  ADD KEY `contact` (`user_name`),
  ADD KEY `contact1` (`contact`);

--
-- Index pour la table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id_message`),
  ADD KEY `id_emetteur` (`id_emetteur`),
  ADD KEY `id_recepteur` (`id_recepteur`);

--
-- Index pour la table `user_tb`
--
ALTER TABLE `user_tb`
  ADD PRIMARY KEY (`name`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `contacte`
--
ALTER TABLE `contacte`
  MODIFY `id_contact` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT pour la table `messages`
--
ALTER TABLE `messages`
  MODIFY `id_message` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=200;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `contacte`
--
ALTER TABLE `contacte`
  ADD CONSTRAINT `contact1` FOREIGN KEY (`contact`) REFERENCES `user_tb` (`name`),
  ADD CONSTRAINT `user_name` FOREIGN KEY (`user_name`) REFERENCES `user_tb` (`name`);

--
-- Contraintes pour la table `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `id_emetteur` FOREIGN KEY (`id_emetteur`) REFERENCES `user_tb` (`name`),
  ADD CONSTRAINT `id_recepteur` FOREIGN KEY (`id_recepteur`) REFERENCES `user_tb` (`name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

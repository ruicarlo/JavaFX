-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tempo de Geração: 
-- Versão do Servidor: 5.5.24-log
-- Versão do PHP: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Banco de Dados: `cinema`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `filme`
--

CREATE TABLE IF NOT EXISTS `filme` (
  `codigo` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `genero` varchar(255) NOT NULL,
  `sinopse` varchar(255) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `filme`
--

INSERT INTO `filme` (`codigo`, `nome`, `genero`, `sinopse`) VALUES
('comoeuera', 'Como Eu Era Antes de Você', 'Drama', 'Rico e bem sucedido, Will (Sam Claflin) leva uma vida repleta de conquistas, viagens e esportes radicais até ser atingido por uma moto'),
('eradogelo', 'A Era do Gelo: O Big Bang', 'Animação', 'Depois que o esquilo Scrat, involuntariamente, provoca um acidente espacial em sua incansável perseguição pela noz, um enorme meteoro entra em rota'),
('independenceday', 'Independence Day: O Ressurgimento', 'Ficção', 'Após o devastador ataque alienígena ocorrido em 1996, todas as nações da Terra se uniram para combater os extra-terrestres, caso eles'),
('procurandoDory', 'Procurando Dory', 'Animação', 'Um ano após ajudar Marlin (Albert Brooks) a reencontrar seu filho Nemo, Dory (Ellen DeGeneres) tem um insight e lembra de sua amada família.');

-- --------------------------------------------------------

--
-- Estrutura da tabela `sala`
--

CREATE TABLE IF NOT EXISTS `sala` (
  `numero` int(9) NOT NULL,
  `capacidade` int(9) NOT NULL,
  PRIMARY KEY (`numero`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `sala`
--

INSERT INTO `sala` (`numero`, `capacidade`) VALUES
(10, 5),
(20, 10),
(30, 300),
(40, 401);

-- --------------------------------------------------------

--
-- Estrutura da tabela `secao`
--

CREATE TABLE IF NOT EXISTS `secao` (
  `numero_sala` int(9) NOT NULL,
  `codigo_filme` varchar(255) NOT NULL,
  `horario` datetime NOT NULL,
  `codigo` int(9) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`codigo`),
  KEY `numero_sala` (`numero_sala`),
  KEY `codigo_filme` (`codigo_filme`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=32 ;

--
-- Extraindo dados da tabela `secao`
--

INSERT INTO `secao` (`numero_sala`, `codigo_filme`, `horario`, `codigo`) VALUES
(10, 'comoeuera', '1970-01-01 12:00:00', 28),
(20, 'eradogelo', '1970-01-01 12:00:00', 29),
(30, 'independenceday', '1970-01-01 14:00:00', 30),
(40, 'procurandoDory', '1970-01-01 15:00:00', 31);

-- --------------------------------------------------------

--
-- Estrutura da tabela `venda`
--

CREATE TABLE IF NOT EXISTS `venda` (
  `id_venda` int(11) NOT NULL AUTO_INCREMENT,
  `codigo_secao` int(11) NOT NULL,
  PRIMARY KEY (`id_venda`),
  KEY `codigo_secao` (`codigo_secao`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=61 ;

--
-- Extraindo dados da tabela `venda`
--

INSERT INTO `venda` (`id_venda`, `codigo_secao`) VALUES
(49, 28),
(50, 28),
(55, 28),
(56, 28),
(51, 29),
(54, 29),
(60, 29),
(53, 31),
(57, 31),
(58, 31),
(59, 31);

--
-- Restrições para as tabelas dumpadas
--

--
-- Restrições para a tabela `secao`
--
ALTER TABLE `secao`
  ADD CONSTRAINT `secao_ibfk_4` FOREIGN KEY (`numero_sala`) REFERENCES `sala` (`numero`) ON DELETE CASCADE,
  ADD CONSTRAINT `secao_ibfk_5` FOREIGN KEY (`codigo_filme`) REFERENCES `filme` (`codigo`) ON DELETE CASCADE;

--
-- Restrições para a tabela `venda`
--
ALTER TABLE `venda`
  ADD CONSTRAINT `venda_ibfk_2` FOREIGN KEY (`codigo_secao`) REFERENCES `secao` (`codigo`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

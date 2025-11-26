CREATE DATABASE  IF NOT EXISTS `ClinicaFX` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ClinicaFX`;
-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: ClinicaFX
-- ------------------------------------------------------
-- Server version	8.0.44-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Agenda`
--

DROP TABLE IF EXISTS `Agenda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Agenda` (
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Agenda`
--

LOCK TABLES `Agenda` WRITE;
/*!40000 ALTER TABLE `Agenda` DISABLE KEYS */;
/*!40000 ALTER TABLE `Agenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Atendente`
--

DROP TABLE IF EXISTS `Atendente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Atendente` (
  `usuario_id` int NOT NULL,
  `matricula` int NOT NULL,
  `turno` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  UNIQUE KEY `matricula` (`matricula`),
  CONSTRAINT `fk_atendente_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `Usuario` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Atendente`
--

LOCK TABLES `Atendente` WRITE;
/*!40000 ALTER TABLE `Atendente` DISABLE KEYS */;
/*!40000 ALTER TABLE `Atendente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cobertura`
--

DROP TABLE IF EXISTS `Cobertura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cobertura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `paciente_id` int NOT NULL,
  `plano_id` int NOT NULL,
  `numeroCarteirinha` varchar(50) NOT NULL,
  `validade` date DEFAULT NULL,
  `ativo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_cobertura_paciente` (`paciente_id`),
  KEY `fk_cobertura_plano` (`plano_id`),
  CONSTRAINT `fk_cobertura_paciente` FOREIGN KEY (`paciente_id`) REFERENCES `Paciente` (`usuario_id`),
  CONSTRAINT `fk_cobertura_plano` FOREIGN KEY (`plano_id`) REFERENCES `PlanoDeSaude` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cobertura`
--

LOCK TABLES `Cobertura` WRITE;
/*!40000 ALTER TABLE `Cobertura` DISABLE KEYS */;
/*!40000 ALTER TABLE `Cobertura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Consulta`
--

DROP TABLE IF EXISTS `Consulta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Consulta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `paciente_id` int NOT NULL,
  `medico_id` int NOT NULL,
  `atendente_id` int DEFAULT NULL,
  `dataConsulta` date NOT NULL,
  `horarioConsulta` time NOT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `valorConsulta` float DEFAULT NULL,
  `agenda_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_consulta_paciente` (`paciente_id`),
  KEY `fk_consulta_medico` (`medico_id`),
  KEY `fk_consulta_atendente` (`atendente_id`),
  KEY `fk_consulta_agenda` (`agenda_id`),
  CONSTRAINT `fk_consulta_agenda` FOREIGN KEY (`agenda_id`) REFERENCES `Agenda` (`id`),
  CONSTRAINT `fk_consulta_atendente` FOREIGN KEY (`atendente_id`) REFERENCES `Atendente` (`usuario_id`),
  CONSTRAINT `fk_consulta_medico` FOREIGN KEY (`medico_id`) REFERENCES `Medico` (`usuario_id`),
  CONSTRAINT `fk_consulta_paciente` FOREIGN KEY (`paciente_id`) REFERENCES `Paciente` (`usuario_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Consulta`
--

LOCK TABLES `Consulta` WRITE;
/*!40000 ALTER TABLE `Consulta` DISABLE KEYS */;
INSERT INTO `Consulta` VALUES (1,1,8,NULL,'2025-11-28','10:30:00','Agendada',150,NULL),(2,1,8,NULL,'2025-12-06','10:30:00','Agendada',100,NULL);
/*!40000 ALTER TABLE `Consulta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Especialidade`
--

DROP TABLE IF EXISTS `Especialidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Especialidade` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `descricao` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Especialidade`
--

LOCK TABLES `Especialidade` WRITE;
/*!40000 ALTER TABLE `Especialidade` DISABLE KEYS */;
INSERT INTO `Especialidade` VALUES (1,'Cardiologia','Cuida do coração'),(2,'Pediatria','Cuida de crianças'),(3,'Dermatologia','Cuida da pele'),(4,'Ortopedia','Cuida dos ossos e músculos');
/*!40000 ALTER TABLE `Especialidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Medico`
--

DROP TABLE IF EXISTS `Medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Medico` (
  `usuario_id` int NOT NULL,
  `crm` varchar(20) NOT NULL,
  `agenda_id` int DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  UNIQUE KEY `crm` (`crm`),
  UNIQUE KEY `agenda_id` (`agenda_id`),
  CONSTRAINT `fk_medico_agenda` FOREIGN KEY (`agenda_id`) REFERENCES `Agenda` (`id`),
  CONSTRAINT `fk_medico_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `Usuario` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Medico`
--

LOCK TABLES `Medico` WRITE;
/*!40000 ALTER TABLE `Medico` DISABLE KEYS */;
INSERT INTO `Medico` VALUES (8,'000000',NULL),(11,'01',NULL);
/*!40000 ALTER TABLE `Medico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Medico_Especialidade`
--

DROP TABLE IF EXISTS `Medico_Especialidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Medico_Especialidade` (
  `medico_id` int NOT NULL,
  `especialidade_id` int NOT NULL,
  PRIMARY KEY (`medico_id`,`especialidade_id`),
  KEY `fk_me_especialidade` (`especialidade_id`),
  CONSTRAINT `fk_me_especialidade` FOREIGN KEY (`especialidade_id`) REFERENCES `Especialidade` (`id`),
  CONSTRAINT `fk_me_medico` FOREIGN KEY (`medico_id`) REFERENCES `Medico` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Medico_Especialidade`
--

LOCK TABLES `Medico_Especialidade` WRITE;
/*!40000 ALTER TABLE `Medico_Especialidade` DISABLE KEYS */;
INSERT INTO `Medico_Especialidade` VALUES (11,1);
/*!40000 ALTER TABLE `Medico_Especialidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Paciente`
--

DROP TABLE IF EXISTS `Paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Paciente` (
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`usuario_id`),
  CONSTRAINT `fk_paciente_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `Usuario` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Paciente`
--

LOCK TABLES `Paciente` WRITE;
/*!40000 ALTER TABLE `Paciente` DISABLE KEYS */;
INSERT INTO `Paciente` VALUES (1),(2),(4),(5),(6),(7);
/*!40000 ALTER TABLE `Paciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PlanoDeSaude`
--

DROP TABLE IF EXISTS `PlanoDeSaude`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PlanoDeSaude` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nomePlano` varchar(100) NOT NULL,
  `nomeOperadora` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PlanoDeSaude`
--

LOCK TABLES `PlanoDeSaude` WRITE;
/*!40000 ALTER TABLE `PlanoDeSaude` DISABLE KEYS */;
INSERT INTO `PlanoDeSaude` VALUES (1,'Básico Enfermaria','Unimed');
/*!40000 ALTER TABLE `PlanoDeSaude` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Usuario`
--

DROP TABLE IF EXISTS `Usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nomeCompleto` varchar(255) NOT NULL,
  `dataNascimento` date DEFAULT NULL,
  `cpf` varchar(11) NOT NULL,
  `cep` varchar(8) DEFAULT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cpf` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Usuario`
--

LOCK TABLES `Usuario` WRITE;
/*!40000 ALTER TABLE `Usuario` DISABLE KEYS */;
INSERT INTO `Usuario` VALUES (1,'Fulano de Tal','2025-10-01','123','123','123'),(2,'sdhuhdughdug','2025-11-05','34234234','14141314','sdjghdh'),(4,'João da Silva','2025-11-05','12345678910','2390000','123456'),(5,'Ana Júlia Matheus Carioca','2005-05-14','16051953752','23895200','13141924'),(6,'José Silva','2025-11-03','000','000','000'),(7,'Varão Yuri','2025-11-03','12345678900','12343245','456'),(8,'Matheus Castro','2004-03-30','123456','23900','123'),(11,'Joao da Silva','1994-11-16','345678','57891','123');
/*!40000 ALTER TABLE `Usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-26 20:35:02

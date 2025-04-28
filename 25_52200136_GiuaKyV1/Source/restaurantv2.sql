-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: restaurant_manage
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k8h1bgqoplx0rkngj01pm1rgp` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'123','KITCHEN','kitchen'),(2,'123','EMPLOYEE','employee'),(3,'123','MANAGER','manager');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` bigint DEFAULT NULL,
  `status` int DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food`
--

LOCK TABLES `food` WRITE;
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
INSERT INTO `food` VALUES (1,'Đồ ăn','Bánh mì kẹp trứng',20000,1,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTec8zUfRIhICBszXhD7Fv7jAyTKRe7dkAYpQ&s'),(2,'Đồ uống','Fanta',10000,1,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTec8zUfRIhICBszXhD7Fv7jAyTKRe7dkAYpQ&s'),(5,'Đồ ăn','3',3,0,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTec8zUfRIhICBszXhD7Fv7jAyTKRe7dkAYpQ&s'),(6,'Đồ ăn','Súp lơ',30000,1,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTec8zUfRIhICBszXhD7Fv7jAyTKRe7dkAYpQ&s'),(7,'Tráng miệng','Sữa chua trân châu đen',30000,1,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTec8zUfRIhICBszXhD7Fv7jAyTKRe7dkAYpQ&s'),(9,'Tráng miệng','Sữa chua trân châu đen',30000,1,'https://dayphache.edu.vn/wp-content/uploads/2022/01/mon-sua-chua-tran-chau-duong-den.jpg');
/*!40000 ALTER TABLE `food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `food_id` bigint DEFAULT NULL,
  `food_name` varchar(255) DEFAULT NULL,
  `food_price` bigint DEFAULT NULL,
  `quantity` bigint DEFAULT NULL,
  `total_price` bigint DEFAULT NULL,
  `order_id` bigint NOT NULL,
  `food_category` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `timestamp` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrws2q0si6oyd6il8gqe2aennc` (`order_id`),
  CONSTRAINT `FKrws2q0si6oyd6il8gqe2aennc` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1,1,'coca cola',10000,21,400000,1,'Đồ ăn',NULL,'PENDING',1743240428102),(2,2,'Fanta',10000,24,240000,1,'Đồ uống','1','PENDING',1743240428102),(3,1,'Bánh mì kẹp trứng',20000,1,20000,12,'Đồ ăn','','PENDING',1743240428102),(4,2,'Fanta',10000,2,20000,12,'Đồ uống','','PENDING',1743240428102),(5,1,'Bánh mì kẹp trứng',20000,2,40000,13,'Đồ ăn','','PENDING',1743240428102),(6,2,'Fanta',10000,2,20000,13,'Đồ uống','','PENDING',1743240428102),(7,1,'Bánh mì kẹp trứng',20000,5,100000,14,'Đồ ăn','','DONE',1743606270305),(8,2,'Fanta',10000,3,30000,14,'Đồ uống','','DONE',1743240481236),(10,1,'Bánh mì kẹp trứng',20000,2,40000,16,'Đồ ăn','','PENDING',1743608017166),(11,2,'Fanta',10000,3,30000,16,'Đồ uống','','PENDING',1743608019486),(12,6,'Súp lơ',30000,4,120000,16,'Đồ ăn','','PENDING',1743608021326),(20,2,'Fanta',10000,4,40000,18,'Đồ uống','','PENDING',1744445252361),(21,1,'Bánh mì kẹp trứng',20000,1,20000,18,'Đồ ăn','','PENDING',1744445255728),(22,6,'Súp lơ',30000,3,90000,18,'Đồ ăn','','PENDING',1744445257778),(23,1,'Bánh mì kẹp trứng',20000,1,20000,19,'Đồ ăn','','PENDING',1744445324684),(24,7,'Sữa chua trân châu đen',30000,2,60000,19,'Tráng miệng','Ít đường','DONE',1744445328117),(25,1,'Bánh mì kẹp trứng',20000,2,40000,20,'Đồ ăn','','PENDING',1744470168226),(26,2,'Fanta',10000,1,10000,20,'Đồ uống','','PENDING',1744470170197),(27,1,'Bánh mì kẹp trứng',20000,1,20000,21,'Đồ ăn','','PENDING',1744471494703);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(255) DEFAULT NULL,
  `customer_phone` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `timestamp` bigint DEFAULT NULL,
  `table_id` bigint NOT NULL,
  `grand_total` bigint DEFAULT NULL,
  `tax` bigint DEFAULT NULL,
  `total` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK20yukfcsa5bqb4knx3y2kof2g` (`table_id`),
  CONSTRAINT `FK20yukfcsa5bqb4knx3y2kof2g` FOREIGN KEY (`table_id`) REFERENCES `r_table` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'van ds','093281838',1,1743232851776,1,NULL,NULL,506000),(12,'Tran van A','0931930133',1,1743235579962,2,44000,4000,40000),(13,'Tran van b','321939138',1,1743434537438,1,66000,6000,60000),(14,'Tran van A','22222222',1,1743434459092,2,77000,7000,70000),(16,'Minh quân','0939129393',1,1743608049465,2,209000,19000,190000),(18,'Binh van 2','09391231',1,1744445262218,2,165000,15000,150000),(19,'1','1',1,1744470125213,1,88000,8000,80000),(20,'1','1',1,1744470174443,1,55000,5000,50000),(21,'Tran van A','123',0,NULL,1,0,0,0);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_table`
--

DROP TABLE IF EXISTS `r_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `r_table` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `capacity` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_table`
--

LOCK TABLES `r_table` WRITE;
/*!40000 ALTER TABLE `r_table` DISABLE KEYS */;
INSERT INTO `r_table` VALUES (1,2,0),(2,3,1);
/*!40000 ALTER TABLE `r_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-14  0:09:27

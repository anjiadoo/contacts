-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: contacts
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_communication_info`
--

DROP TABLE IF EXISTS `tb_communication_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_communication_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `office_phone` varchar(20) DEFAULT NULL,
  `mobile_phone` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `qq` varchar(15) DEFAULT NULL,
  `available` char(1) NOT NULL DEFAULT 'Y',
  `create_time` datetime NOT NULL,
  `last_update_time` datetime NOT NULL,
  `operator` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_communication_info`
--

LOCK TABLES `tb_communication_info` WRITE;
/*!40000 ALTER TABLE `tb_communication_info` DISABLE KEYS */;
INSERT INTO `tb_communication_info` VALUES (1,1,'123456','239665','zhangsan@126.com','98765432','N','2015-11-11 17:52:23','2018-03-24 11:32:36','admin'),(3,4,'123123','345345','sdfsdf@123.com','234234234','Y','2015-11-11 17:52:23','2018-03-24 11:25:09','admin'),(4,5,'152245226','1264254166','mmmm@mmasd.com','211543254','Y','2015-11-11 17:52:23','2018-03-24 11:26:04','admin'),(5,5,'9546215233','2112423431','oooo@adsd.com','6984125451','Y','2015-11-11 17:52:23','2018-03-24 11:26:04','admin'),(10,5,'123123123123','00000000000','aaa@aaa.com','111111','N','2015-11-11 17:52:23','2016-08-25 16:37:08','admin'),(12,10,'23432423423','234234234','213@123.com','123123','N','2015-11-11 17:52:23','2016-07-27 08:44:38','admin'),(13,7,'123','123','ASD@ASD.COM','123123','N','2015-11-11 19:29:26','2016-07-27 08:43:48','admin'),(24,16,'123456789','18611','','','Y','2016-07-27 08:45:31','2018-03-24 11:33:56','admin'),(26,1,'123','123123123','zhangsan@126.com','','N','2016-08-16 16:46:31','2018-03-24 11:32:34','admin'),(31,6,'010010101','0202020202','sunwukong@xitian.com','','N','2016-08-25 16:26:17','2018-03-24 11:26:56','admin'),(32,8,'123456','654321','','','N','2016-08-26 09:22:20','2018-03-24 11:28:28','admin'),(33,6,'999999999','0303030303','sunwukong@xitian.com','','N','2017-01-23 13:54:30','2017-01-23 13:55:14','admin'),(34,14,'1122333','5566789','lisi@163.com','','Y','2017-01-23 14:04:19','2018-03-24 11:31:14','admin'),(36,14,'0000001','7777551','lisi@126.com','','N','2017-01-23 14:08:08','2017-01-23 14:09:05','admin'),(39,1,'9999887','1010111','zhangsan@163.com','','N','2017-01-23 14:32:27','2017-01-23 14:32:53','admin'),(40,1,NULL,NULL,NULL,NULL,'N','2018-03-24 11:32:37','2018-03-24 11:32:41','admin'),(41,18,'','','','','Y','2018-03-24 17:33:30','2018-03-24 17:33:30','admin'),(42,19,'','','','','Y','2018-03-24 17:34:32','2018-03-24 17:34:32','admin'),(43,20,'','','','','Y','2018-03-24 17:35:25','2018-03-24 17:35:25','admin'),(44,1,'13314420572','15260605670','1769021369@qq.com','1769021369','Y','2018-03-24 17:36:08','2018-03-24 17:36:53','admin');
/*!40000 ALTER TABLE `tb_communication_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_customer_info`
--

DROP TABLE IF EXISTS `tb_customer_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_customer_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `work_unit` varchar(300) DEFAULT NULL,
  `work_addr` varchar(300) DEFAULT NULL,
  `home_addr` varchar(300) DEFAULT NULL,
  `role` varchar(100) DEFAULT NULL,
  `available` char(1) NOT NULL DEFAULT 'Y',
  `create_time` datetime NOT NULL,
  `last_update_time` datetime NOT NULL,
  `operator` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_customer_info`
--

LOCK TABLES `tb_customer_info` WRITE;
/*!40000 ALTER TABLE `tb_customer_info` DISABLE KEYS */;
INSERT INTO `tb_customer_info` VALUES (1,'安家栋','男','1994-09-08','百度','深圳','贵阳','架构师','Y','2015-11-11 17:52:17','2018-03-24 17:36:53','admin'),(3,'彭亮','男','1996-10-29','腾讯','深圳','不详','java工程师','Y','2015-11-11 17:52:17','2018-03-24 11:23:46','admin'),(4,'王泽鹏','男','1994-02-24','IBM','中国','不详','算法工程师','Y','2015-11-11 17:52:17','2018-03-24 11:25:09','admin'),(5,'欧曼阳','男','1996-08-16','体育公司','贵阳','贵阳','足球教练','Y','2015-11-11 17:52:17','2018-03-24 11:26:04','admin'),(6,'龙佳君','男','1995-01-01','上海','上海','贵阳','职业电竞选手','Y','2015-11-11 17:52:17','2018-03-24 11:26:58','admin'),(7,'方潆','女','1996-08-16','娱乐公司','北京','福建','演员','Y','2015-11-11 17:52:17','2018-03-24 11:27:36','admin'),(8,'余端芳','女','1995-08-02','厦门市医院','厦门','厦门翔安','护士长','Y','2015-11-11 17:52:17','2018-03-24 11:28:30','admin'),(10,'罗廷丽','女','1996-01-12','北京','北京','贵州','篮球选手','Y','2015-11-11 17:52:17','2018-03-24 11:29:14','admin'),(14,'邹鸿','男','1996-08-03','贵阳设计院','上海','贵阳','建筑工程师','Y','2016-07-26 17:04:08','2018-03-24 11:31:14','admin'),(16,'万丽','女','1994-09-10','深圳市医院','深圳','贵阳','护士长','Y','2016-07-27 08:45:31','2018-03-24 11:33:56','admin'),(17,'曾波','男','1995-04-28','南京精密仪器制造有限公司','南京','贵州','设计师','Y','2018-03-24 17:31:53','2018-03-24 17:31:53','admin'),(18,'张瑶','女','1994-09-23','厦门中医院','厦门','厦门','外科大夫','Y','2018-03-24 17:33:30','2018-03-24 17:33:30','admin'),(19,'陆霞','女','1995-12-12','碧桂园','贵阳','遵义','销售经理','Y','2018-03-24 17:34:32','2018-03-24 17:34:32','admin'),(20,'程阳','男','1994-09-06','腾讯','北京','遵义','Golang工程师','Y','2018-03-24 17:35:25','2018-03-24 17:35:25','admin');
/*!40000 ALTER TABLE `tb_customer_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_users`
--

DROP TABLE IF EXISTS `tb_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(30) NOT NULL,
  `password` varchar(200) NOT NULL,
  `status` varchar(5) NOT NULL DEFAULT 'guest',
  `available` char(1) NOT NULL DEFAULT 'Y',
  `create_time` datetime NOT NULL,
  `last_update_time` datetime NOT NULL,
  `operator` varchar(30) NOT NULL,
  PRIMARY KEY (`id`,`account_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_users`
--

LOCK TABLES `tb_users` WRITE;
/*!40000 ALTER TABLE `tb_users` DISABLE KEYS */;
INSERT INTO `tb_users` VALUES (1,'admin','admin','admin','Y','2015-11-11 17:50:38','2015-11-11 17:50:38','admin'),(2,'guest','guest','guest','Y','2015-11-11 17:50:38','2015-11-11 19:32:48','guest'),(3,'hib','asd','guest','Y','2015-11-11 17:50:38','2015-11-11 17:50:38','admin'),(4,'zhangsan','123','guest','Y','2015-11-11 17:50:38','2015-11-11 17:50:38','admin'),(5,'lisi','123456','guest','Y','2015-11-11 17:50:38','2015-11-11 17:50:38','admin'),(6,'wangwu','111','guest','Y','2016-07-27 09:50:25','2016-07-27 09:50:25','admin'),(7,'mr','mrsoft','admin','Y','2016-08-25 16:24:09','2016-08-25 16:24:09','admin');
/*!40000 ALTER TABLE `tb_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-24 20:59:31

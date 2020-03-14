-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: human_resources
-- ------------------------------------------------------
-- Server version	5.5.47

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
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES ('0010022','テスト 五郎1','ﾃｽﾄ0010022 ｺﾞﾛｳ',0,'0010032','1','2016-03-23','1200001','','','','03-0000-1111',''),('0010033','テスト0033','ﾃｽﾄ',0,'001002','1',NULL,'','','','','',''),('001004','テスト0033','ﾔｼﾞﾏ ｼﾛｳ',1,'001001102','1','2016-03-17','','','','','03-0000-1111',''),('0010044','テスト0010044','ﾃｽﾄ',0,'001002','1','2016-03-31','','','','','03-0000-1111',''),('001005','テスト　次郎','ﾃｽﾄ ｼﾞﾛｳ',0,'001004','1','1982-03-17','','','','','03-0000-1111',''),('0010055','テスト0010055','ﾃｽﾄ0010055',0,'001001102','1','2016-03-10','1200021','東京都中央区','茅場町','テストマンション300号','',''),('001006','テスト三郎','ﾃｽﾄｻﾌﾞｵﾛｳ',0,'001001102','1','2017-02-17','','','','','',''),('001007','テスト四朗','ﾃｽﾄｼﾛｳ',1,'00100199','1','2017-02-23','','','','','',''),('001009','山田　五郎３','ﾔﾏﾀﾞ ｺﾞﾛｳ',0,'00100199','2','1974-05-01','','','','','03-0000-2222',''),('001111','テスト太郎1','ﾃｽﾄﾀﾛｳ',1,'001001102','2','2017-02-02','','','','','',''),('002002','顧客　太郎','ｺｷﾔｸ ﾀﾛｳ',1,'0010032','1','2016-03-10','','','','','03-0000-1111','\r\n\r\n'),('0020022','山田　花子','ﾔﾏﾀﾞ ﾊﾅｺ',0,'001002','2','1968-05-02','','','','','03-0000-2222',''),('009009','山田　次郎','ﾃｳｽﾄ',0,'001002','2','2016-03-18','1200021','','','','',''),('0233232','山田　次郎','ﾔﾏﾀﾞ ｼﾞﾛｳ',0,'00100199','1',NULL,'','東京都','','','','');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-17 14:39:30

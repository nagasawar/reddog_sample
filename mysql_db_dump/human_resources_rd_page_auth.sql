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
-- Dumping data for table `rd_page_auth`
--

LOCK TABLES `rd_page_auth` WRITE;
/*!40000 ALTER TABLE `rd_page_auth` DISABLE KEYS */;
INSERT INTO `rd_page_auth` VALUES ('001001','/','トップページ',1,'0,1,5,9'),('002001','/employee','社員検索',1,'1,5,9'),('002002','/employee/addNew','社員新規登録',1,'1,5,9'),('002003','/employee/addNewCreate','社員新規登録実行',1,'1,5,9'),('002004','/employee/edit','社員編集',1,'1,5,9'),('002005','/employee/editUpdate','社員編集 更新実行',1,'1,5,9'),('002006','/employee/delete','社員削除',1,'1,5,9'),('002007','/employee/outputCsv','社員CSV出力',1,'1,5,9'),('002008','/employee/outputExcel','社員Excel出力',1,'1,5,9'),('002009','/employee/outputPDF','社員PDF出力',1,'1,5,9'),('003001','/department','所属マスタ',1,'5,9'),('003002','/department/addNew','所属マスタ新規登録',1,'5,9'),('003003','/department/addNewCreate','所属マスタ新規登録実行',1,'5,9'),('003004','/department/edit','所属マスタ編集',1,'5,9'),('003005','/department/editUpdate','所属マスタ編集 更新実行',1,'5,9'),('003006','/department/delete','所属マスタ削除',1,'5,9'),('004001','/user','ユーザ検索',1,'9'),('004002','/user/addNew','ユーザ新規登録',1,'9'),('004003','/user/addNewCreate','ユーザ新規登録実行',1,'9'),('004004','/user/edit','ユーザ編集',1,'9'),('004005','/user/editUpdate','ユーザ編集 更新実行',1,'9'),('004006','/user/delete','ユーザ削除',1,'9'),('005001','/employeeBulk','社員一括編集',1,'1,5,9'),('005002','/employeeBulk/editUpdate','社員一括編集 更新実行',1,'1,5,9'),('006001','/employeeOutput','社員出力',1,'1,5,9'),('006002','/employeeOutput/outputExcel','社員出力Excel出力',1,'1,5,9'),('006003','/employeeOutput/outputPDF','社員出力PDF出力',1,'1,5,9'),('007001','/userRole','ユーザ権限検索',1,'9'),('007002','/userRole/addNew','ユーザ権限新規登録',1,'9'),('007003','/userRole/addNewCreate','ユーザ権限新規登録実行',1,'9'),('007004','/userRole/edit','ユーザ権限編集',1,'9'),('007005','/userRole/editUpdate','ユーザ権限編集 更新実行',1,'9'),('007006','/userRole/delete','ユーザ権限削除',1,'9');
/*!40000 ALTER TABLE `rd_page_auth` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-17 14:39:26

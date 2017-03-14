/*
SQLyog Ultimate v8.32 
MySQL - 5.1.54-community : Database - lotteryTools
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`lotteryTools` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `lotteryTools`;

/*Data for the table `RELA_LT_AUTHORITY_ROLE` */

insert  into `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) values ('4028b8815a8e37e3015a8e3c4b2c0003','1');
insert  into `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) values ('4028b8815a8e37e3015a8e3c4b2c0003','4028813a505fe87201505ff93aa80002');
insert  into `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) values ('4028b8815a8e37e3015a8e3c4b2c0003','4028813a506515ad01506519290a0001');
insert  into `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) values ('0','1');
insert  into `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) values ('0','4028b8815ac591e3015ac596dbf40000');
insert  into `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) values ('0','4028b8815ac59900015ac59a07d90001');
insert  into `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) values ('0','4028b8815ac59900015ac59991b80000');
insert  into `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) values ('0','4028813a505fe87201505ff93aa80002');
insert  into `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) values ('0','4028813a506a4de701506a79c6990000');
insert  into `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) values ('0','4028813a506515ad01506519e1720002');
insert  into `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) values ('0','4028813a506515ad01506519290a0001');

/*Data for the table `RELA_LT_USER_ROLE` */

insert  into `RELA_LT_USER_ROLE`(`USER_ID`,`ROLE_ID`,`ID`) values ('0','0','');
insert  into `RELA_LT_USER_ROLE`(`USER_ID`,`ROLE_ID`,`ID`) values ('4028b8815a8e37e3015a8e3cc9740004','4028b8815a8e37e3015a8e3c4b2c0003','4028b8815a91efd8015a91f6c7c60000');

/*Data for the table `T_LOTTERYTOOLS_AUTHORITY` */

insert  into `T_LOTTERYTOOLS_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) values ('1','2015-10-15 09:35:14','admin','1','admin','2015-10-15 09:35:14',NULL,'权限树根','1','1','','1',NULL);
insert  into `T_LOTTERYTOOLS_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) values ('4028813a505fe87201505ff93aa80002','2015-10-13 14:53:00','admin','1','admin','2015-10-14 14:39:08','icon-nav','用户','1','1','1','1','');
insert  into `T_LOTTERYTOOLS_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) values ('4028813a506515ad01506519290a0001','2015-10-14 14:45:59','admin','1','admin','2015-10-14 15:21:26','icon-nav','账号管理','11','1','4028813a505fe87201505ff93aa80002','1','/menu/useraccount.action');
insert  into `T_LOTTERYTOOLS_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) values ('4028813a506515ad01506519e1720002','2015-10-14 14:46:46','admin','1','admin','2015-10-14 15:21:19','icon-nav','权限管理','12','1','4028813a505fe87201505ff93aa80002','1','/menu/authority.action');
insert  into `T_LOTTERYTOOLS_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) values ('4028813a506a4de701506a79c6990000','2015-10-15 15:49:37','admin','1','admin','2015-10-15 15:49:37','icon-nav','角色管理','tole55','1','4028813a505fe87201505ff93aa80002','1','/menu/roleManage.action');
insert  into `T_LOTTERYTOOLS_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) values ('4028b8815a8e37e3015a8e39940d0002','2017-03-02 16:53:42','admin','0','admin','2017-03-02 16:53:52','icon-nav','测试','ceshi','0','1','1','');
insert  into `T_LOTTERYTOOLS_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) values ('4028b8815ac591e3015ac596dbf40000','2017-03-13 10:54:42','admin','1','admin','2017-03-13 10:54:42','icon-nav','彩种管理','cai1','0','1','1','');
insert  into `T_LOTTERYTOOLS_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) values ('4028b8815ac59900015ac59991b80000','2017-03-13 10:57:40','admin','1','admin','2017-03-13 10:57:40','icon-nav','基础彩种信息管理','cai1-1','0','4028b8815ac591e3015ac596dbf40000','1','/menu/baseLottery.action');
insert  into `T_LOTTERYTOOLS_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) values ('4028b8815ac59900015ac59a07d90001','2017-03-13 10:58:10','admin','1','admin','2017-03-13 10:58:10','icon-nav','区域彩种管理','cai1-2','0','4028b8815ac591e3015ac596dbf40000','1','/menu/areaLottery.action');

/*Data for the table `T_LOTTERYTOOLS_ROLES` */

insert  into `T_LOTTERYTOOLS_ROLES`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`CODE`,`ISSYSTEM`,`NAME`,`PARENT_ROLE`,`PARENT_ROLENAME`) values ('0','2015-10-13 14:53:00','admin','1','admin','2015-10-20 10:20:38','1','1','超级管理员','','');
insert  into `T_LOTTERYTOOLS_ROLES`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`CODE`,`ISSYSTEM`,`NAME`,`PARENT_ROLE`,`PARENT_ROLENAME`) values ('4028b8815a8e37e3015a8e3910de0000','2017-03-02 16:53:08','admin','0','admin','2017-03-02 16:53:57','ceshi','0','测试','0','');
insert  into `T_LOTTERYTOOLS_ROLES`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`CODE`,`ISSYSTEM`,`NAME`,`PARENT_ROLE`,`PARENT_ROLENAME`) values ('4028b8815a8e37e3015a8e3c4b2c0003','2017-03-02 16:56:40','admin','1','admin','2017-03-02 16:56:40','admin2','0','管理员','0','');

/*Data for the table `T_LOTTERYTOOLS_USERS` */

insert  into `T_LOTTERYTOOLS_USERS`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`ADDRESS`,`CITY_CODE`,`CODE`,`LOTTERY_TYPE`,`NAME`,`PARENT_UID`,`PASSWORD`,`PROVINCE_CODE`,`REGION_CODE`,`STATUS`,`TELEPHONE`) values ('0','2015-10-29 13:09:12','admin','1','admin','2015-10-29 13:09:12',NULL,NULL,'admin',NULL,'admin',NULL,'7DF724D870B08EF6112086C045200BBB931D69C98C63759403067AC5',NULL,NULL,'1','');
insert  into `T_LOTTERYTOOLS_USERS`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`ADDRESS`,`CITY_CODE`,`CODE`,`LOTTERY_TYPE`,`NAME`,`PARENT_UID`,`PASSWORD`,`PROVINCE_CODE`,`REGION_CODE`,`STATUS`,`TELEPHONE`) values ('4028b8815a8e37e3015a8e3cc9740004','2017-03-02 16:57:12','admin','1','admin','2017-03-03 10:52:53',NULL,'110100','admin2','0','管理员','0','3F4861B3BF4568EE05E2E190FA5022AFFCD21131A39172A80ECB705F','110000',NULL,'1','13358696548');
insert  into `T_LOTTERYTOOLS_USERS`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`ADDRESS`,`CITY_CODE`,`CODE`,`LOTTERY_TYPE`,`NAME`,`PARENT_UID`,`PASSWORD`,`PROVINCE_CODE`,`REGION_CODE`,`STATUS`,`TELEPHONE`) values ('4028b8815a91fc46015a9204ba9d0000','2017-03-03 10:34:27','admin','1','admin','2017-03-03 10:34:27',NULL,'110100','ceshi','0','测试',NULL,'BD0C6275C0007B59BDD14392B4147FBDC5113C22A058ECA146A38384','110000',NULL,'1','13358696548');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
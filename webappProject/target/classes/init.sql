INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATOR`,`CREATE_TIME`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`PARANT_AUTH_ID`,`STATUS`,`URL`,`IS_DELETED`,`ISSYSTEM`) 
VALUES ('1','admin','2015-10-15 09:35:14','admin','2015-10-15 09:35:14',NULL,'权限树根','1','','1',NULL,'1','1');
INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATOR`,`CREATE_TIME`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`PARANT_AUTH_ID`,`STATUS`,`URL`,`IS_DELETED`,`ISSYSTEM`) 
VALUES ('4028813a505fe87201505ff93aa80002','admin','2015-10-13 14:53:00','admin','2015-10-14 14:39:08','icon-nav','用户','1','1','1','','1','1');
INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATOR`,`CREATE_TIME`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`PARANT_AUTH_ID`,`STATUS`,`URL`,`IS_DELETED`,`ISSYSTEM`) 
VALUES ('4028813a506515ad01506519290a0001','admin','2015-10-14 14:45:59','admin','2015-10-14 15:21:26','icon-nav','账号管理','11','4028813a505fe87201505ff93aa80002','1','/menu/useraccount.action','1','1');
INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATOR`,`CREATE_TIME`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`PARANT_AUTH_ID`,`STATUS`,`URL`,`IS_DELETED`,`ISSYSTEM`) 
VALUES ('4028813a506515ad01506519e1720002','admin','2015-10-14 14:46:46','admin','2015-10-14 15:21:19','icon-nav','权限管理','12','4028813a505fe87201505ff93aa80002','1','/menu/authority.action','1','1');
INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATOR`,`CREATE_TIME`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`PARANT_AUTH_ID`,`STATUS`,`URL`,`IS_DELETED`,`ISSYSTEM`) 
VALUES ('4028813a506a4de701506a79c6990000','admin','2015-10-15 15:49:37','admin','2015-10-15 15:49:37','11','角色管理','tole55','4028813a505fe87201505ff93aa80002','1','/menu/roleManage.action','1','1');

INSERT  INTO `T_LT_ROLES`(`ID`,`CREATOR`,`CREATE_TIME`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`CODE`,`NAME`,`PARENT_ROLE`,`PARENT_ROLENAME`,`ISSYSTEM`) 
VALUES ('0','admin','2015-10-13 14:53:00','1','admin','2015-10-20 10:20:38','1','超级管理员','','','1');

INSERT  INTO `T_LT_USERS`(`ID`,`CREATOR`,`CREATE_TIME`,`MODIFY`,`MODIFY_TIME`,`ADDRESS`,`CITY_CODE`,`CODE`,`NAME`,`PARENT_UID`,`PASSWORD`,`PROVINCE_CODE`,`REGION_CODE`,`IS_DELETED`,`status`,`TELEPHONE`) 
VALUES ('0','admin','2015-10-29 13:09:12','admin','2015-10-29 13:09:12',NULL,NULL,'admin','admin',NULL,'1',NULL,NULL,'1','1','');


INSERT  INTO `RELA_LT_USER_ROLE`(`ROLE_ID`,`USER_ID`) 
VALUES ('0','0');

INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) 
VALUES ('0','1');/*超级管理员权限*/
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) 
VALUES ('0','4028813a506536910150653be3ed0001');/*超级管理员权限*/
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) 
VALUES ('0','4028813a506515ad01506519e1720002');/*超级管理员权限*/
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) 
VALUES ('0','4028813a506515ad01506519290a0001');/*超级管理员权限*/
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) 
VALUES ('0','4028813a506a4de701506a79c6990000');/*超级管理员权限*/

INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) VALUES ('1','2015-10-15 09:35:14','admin','1','admin','2015-10-15 09:35:14',NULL,'权限树根','1','1','','1',NULL);
INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) VALUES ('4028813a505fe87201505ff93aa80002','2015-10-13 14:53:00','admin','1','admin','2015-10-14 14:39:08','icon-nav','用户','1','1','1','1','');
INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) VALUES ('4028813a506515ad01506519290a0001','2015-10-14 14:45:59','admin','1','admin','2015-10-14 15:21:26','icon-nav','账号管理','11','1','4028813a505fe87201505ff93aa80002','1','/menu/useraccount.action');
INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) VALUES ('4028813a506515ad01506519e1720002','2015-10-14 14:46:46','admin','1','admin','2015-10-14 15:21:19','icon-nav','权限管理','12','1','4028813a505fe87201505ff93aa80002','1','/menu/authority.action');
INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) VALUES ('4028813a506a4de701506a79c6990000','2015-10-15 15:49:37','admin','1','admin','2015-10-15 15:49:37','icon-nav','角色管理','tole55','1','4028813a505fe87201505ff93aa80002','1','/menu/roleManage.action');
INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) VALUES ('4028b8815a8e37e3015a8e39940d0002','2017-03-02 16:53:42','admin','0','admin','2017-03-02 16:53:52','icon-nav','测试','ceshi','0','1','1','');
INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) VALUES ('4028b8815ac591e3015ac596dbf40000','2017-03-13 10:54:42','admin','1','admin','2017-03-13 10:54:42','icon-nav','彩种管理','cai1','0','1','1','');
INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) VALUES ('4028b8815ac59900015ac59991b80000','2017-03-13 10:57:40','admin','1','admin','2017-03-13 10:57:40','icon-nav','基础彩种信息管理','cai1-1','0','4028b8815ac591e3015ac596dbf40000','1','/menu/baseLottery.action');
INSERT  INTO `T_LT_AUTHORITY`(`ID`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`ISSYSTEM`,`PARANT_AUTH_ID`,`STATUS`,`URL`) VALUES ('4028b8815ac59900015ac59a07d90001','2017-03-13 10:58:10','admin','1','admin','2017-03-13 10:58:10','icon-nav','区域彩种管理','cai1-2','0','4028b8815ac591e3015ac596dbf40000','1','/menu/areaLottery.action');


INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) VALUES ('4028b8815a8e37e3015a8e3c4b2c0003','1');
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) VALUES ('4028b8815a8e37e3015a8e3c4b2c0003','4028813a505fe87201505ff93aa80002');
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) VALUES ('4028b8815a8e37e3015a8e3c4b2c0003','4028813a506515ad01506519290a0001');
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) VALUES ('0','1');
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) VALUES ('0','4028b8815ac591e3015ac596dbf40000');
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) VALUES ('0','4028b8815ac59900015ac59a07d90001');
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) VALUES ('0','4028b8815ac59900015ac59991b80000');
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) VALUES ('0','4028813a505fe87201505ff93aa80002');
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) VALUES ('0','4028813a506a4de701506a79c6990000');
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) VALUES ('0','4028813a506515ad01506519e1720002');
INSERT  INTO `RELA_LT_AUTHORITY_ROLE`(`ROLE_ID`,`authority_ID`) VALUES ('0','4028813a506515ad01506519290a0001');

--初始化默认的头像附件图片和图片附件数据
insert  into `UPLOADFILE`(`id`,`CREATE_TIME`,`CREATOR`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`deleteServiceFile`,`des`,`newsUuid`,`uploadContentType`,`uploadFileName`,`uploadRealName`,`uploadfilepath`) values (7,'2017-04-26 14:25:39','admin','1','admin','2017-04-26 14:25:39',0,NULL,'0','.jpg','touxiangmoren.jpg','touxiangmoren.jpg','/upload/');




CREATE TABLE `T_SDF_PROVINCE` (
  `PID` INT(11) NOT NULL AUTO_INCREMENT,
  `Pcode` INT(11) DEFAULT NULL,
  `Pname` VARCHAR(30) DEFAULT NULL,
  `NationCode` INT(11) DEFAULT NULL,
  `PrecordCreationDate` DATETIME DEFAULT NULL,
  `PrecordCreator` VARCHAR(20) DEFAULT NULL,
  `PrecordVersion` VARCHAR(200) DEFAULT NULL,
  `CREATER` VARCHAR(255) DEFAULT NULL,
  `CREATER_TIME` DATETIME DEFAULT NULL,
  `IS_DELETED` VARCHAR(255) DEFAULT NULL,
  `MODIFY` VARCHAR(255) DEFAULT NULL,
  `MODIFY_TIME` DATETIME DEFAULT NULL,
  PRIMARY KEY (`PID`)
) ENGINE=INNODB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8



CREATE TABLE `T_SDF_NATION` (
  `NID` INT(11) NOT NULL AUTO_INCREMENT,
  `Ncode` INT(11) DEFAULT NULL,
  `Nname` VARCHAR(30) DEFAULT NULL,
  `NrecordCreationDate` DATETIME DEFAULT NULL,
  `NrecordCreator` VARCHAR(20) DEFAULT NULL,
  `NrecordVersion` VARCHAR(200) DEFAULT NULL,
  PRIMARY KEY (`NID`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8

CREATE TABLE `T_SDF_CITY` (
  `CID` INT(11) NOT NULL AUTO_INCREMENT,
  `Ccode` INT(11) DEFAULT NULL,
  `Cname` VARCHAR(30) DEFAULT NULL,
  `ProvinceCode` INT(11) DEFAULT NULL,
  `CrecordCreationDate` DATETIME DEFAULT NULL,
  `CrecordCreator` VARCHAR(20) DEFAULT NULL,
  `CrecordVersion` VARCHAR(200) DEFAULT NULL,
  `CREATER` VARCHAR(255) DEFAULT NULL,
  `CREATER_TIME` DATETIME DEFAULT NULL,
  `IS_DELETED` VARCHAR(255) DEFAULT NULL,
  `MODIFY` VARCHAR(255) DEFAULT NULL,
  `MODIFY_TIME` DATETIME DEFAULT NULL,
  PRIMARY KEY (`CID`)
) ENGINE=INNODB AUTO_INCREMENT=341 DEFAULT CHARSET=utf8



CREATE TABLE `T_SDF_AREACOUNTY` (
  `AID` INT(11) NOT NULL AUTO_INCREMENT,
  `Acode` INT(11) DEFAULT NULL,
  `Aname` VARCHAR(30) DEFAULT NULL,
  `CityCode` INT(11) DEFAULT NULL,
  `ArecordCreationDate` DATETIME DEFAULT NULL,
  `ArecordCreator` VARCHAR(20) DEFAULT NULL,
  `ArecordVersion` VARCHAR(200) DEFAULT NULL,
  `CREATER` VARCHAR(255) DEFAULT NULL,
  `CREATER_TIME` DATETIME DEFAULT NULL,
  `IS_DELETED` VARCHAR(255) DEFAULT NULL,
  `MODIFY` VARCHAR(255) DEFAULT NULL,
  `MODIFY_TIME` DATETIME DEFAULT NULL,
  `CrecordCreationDate` DATETIME DEFAULT NULL,
  `CrecordCreator` VARCHAR(255) DEFAULT NULL,
  `CrecordVersion` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`AID`)
) ENGINE=INNODB AUTO_INCREMENT=1334 DEFAULT CHARSET=utf8
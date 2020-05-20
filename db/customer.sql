# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: cdb-r8rnnc1s.cd.tencentcdb.com (MySQL 5.7.18-20170830-log)
# Database: leaf-jobs
# Generation Time: 2020-05-20 09:37:22 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_task
# ------------------------------------------------------------

CREATE TABLE `t_task` (
  `TASK_ID` bigint(20) NOT NULL COMMENT '服务ID',
  `TASK_GROUP` varchar(128) DEFAULT NULL COMMENT '服务组',
  `TASK_SERVICE_NAME` varchar(256) DEFAULT NULL COMMENT '服务名称',
  `TASK_METHOD_NAME` varchar(128) DEFAULT NULL COMMENT '服务方法名称',
  `TASK_NAME` varchar(64) DEFAULT NULL COMMENT '任务名称',
  `PARAMS` varchar(512) DEFAULT NULL,
  `CRON` varchar(64) DEFAULT NULL,
  `TASK_STATUS` varchar(32) DEFAULT NULL COMMENT '运行状态',
  `TASK_SCRIPT` text COMMENT '任务类型',
  `TASK_TYPE` varchar(32) DEFAULT NULL COMMENT '任务类型',
  `TIME_OUT` int(11) DEFAULT NULL COMMENT '超时时间（毫秒）',
  `PRINCIPAL` varchar(64) DEFAULT NULL,
  `ONLINE_ADDRESS` varchar(32) DEFAULT NULL COMMENT '在线服务地址',
  `RISK_EMAIL` varchar(256) DEFAULT NULL,
  `REMARK` varchar(256) DEFAULT NULL,
  `CREATOR` varchar(20) DEFAULT NULL,
  `UPDATER` varchar(20) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `COMMENTS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`TASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='在线的服务任务列表';



# Dump of table t_task_group
# ------------------------------------------------------------

CREATE TABLE `t_task_group` (
  `GROUP_ID` bigint(20) DEFAULT NULL COMMENT '服务组ID',
  `GROUP_NAME` varchar(128) DEFAULT NULL COMMENT '服务组',
  `ONLINE_ADDRESS` varchar(32) DEFAULT NULL COMMENT '在线服务地址',
  `CREATOR` varchar(20) DEFAULT NULL,
  `UPDATER` varchar(20) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `COMMENTS` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务组';



# Dump of table t_task_invoke_record
# ------------------------------------------------------------

CREATE TABLE `t_task_invoke_record` (
  `RECORD_ID` bigint(20) DEFAULT NULL COMMENT '记录id',
  `TASK_ID` mediumtext COMMENT '服务组',
  `INVOKE_DATE` datetime DEFAULT NULL,
  `INVOKE_IP` varchar(32) DEFAULT NULL,
  `INVOKE_RESULT` varchar(32) DEFAULT NULL,
  `COMPLETE_DATE` datetime DEFAULT NULL,
  `RESPONSE` text,
  `STACK_TRACE` varchar(512) DEFAULT NULL,
  `CREATOR` varchar(20) DEFAULT NULL,
  `UPDATER` varchar(20) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `COMMENTS` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='执行记录';



# Dump of table t_user
# ------------------------------------------------------------

CREATE TABLE `t_user` (
  `USER_ID` bigint(20) NOT NULL COMMENT '用户ID',
  `MOBILE_PHONE` varchar(64) DEFAULT NULL COMMENT '手机号码',
  `USER_NAME` varchar(64) DEFAULT NULL,
  `EMAIL` varchar(32) DEFAULT NULL,
  `PASSWORD` varchar(32) DEFAULT NULL,
  `CREATOR` varchar(20) DEFAULT NULL,
  `UPDATER` varchar(20) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `COMMENTS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基础信息表';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

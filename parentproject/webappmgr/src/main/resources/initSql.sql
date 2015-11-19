
/*
	※※※11/3更新的初始化数据主要是用来初始化超级管理员的初始权限，包括"用户模块"下的一系列功能
*/

/*
	初始化权限表数据
*/
insert  into `t_sdf_authority`(`ID`,`CREATER`,`CREATER_TIME`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`PARANT_AUTH_ID`,`STATUS`,`URL`,`IS_DELETED`,`ISSYSTEM`) 
values ('1','admin','2015-10-15 09:35:14','admin','2015-10-15 09:35:14',NULL,'权限树根','1','','1',NULL,'1','1');
insert  into `t_sdf_authority`(`ID`,`CREATER`,`CREATER_TIME`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`PARANT_AUTH_ID`,`STATUS`,`URL`,`IS_DELETED`,`ISSYSTEM`) 
values ('4028813a505fe87201505ff93aa80002','admin','2015-10-13 14:53:00','admin','2015-10-14 14:39:08','icon-nav','用户','1','1','1','','1','1');
insert  into `t_sdf_authority`(`ID`,`CREATER`,`CREATER_TIME`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`PARANT_AUTH_ID`,`STATUS`,`URL`,`IS_DELETED`,`ISSYSTEM`) 
values ('4028813a506515ad01506519290a0001','admin','2015-10-14 14:45:59','admin','2015-10-14 15:21:26','icon-nav','账号管理','11','4028813a505fe87201505ff93aa80002','1','/user/useraccount.jsp','1','1');
insert  into `t_sdf_authority`(`ID`,`CREATER`,`CREATER_TIME`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`PARANT_AUTH_ID`,`STATUS`,`URL`,`IS_DELETED`,`ISSYSTEM`) 
values ('4028813a506515ad01506519e1720002','admin','2015-10-14 14:46:46','admin','2015-10-14 15:21:19','icon-nav','权限管理','12','4028813a505fe87201505ff93aa80002','1','/user/authority.jsp','1','1');
insert  into `t_sdf_authority`(`ID`,`CREATER`,`CREATER_TIME`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`PARANT_AUTH_ID`,`STATUS`,`URL`,`IS_DELETED`,`ISSYSTEM`) 
values ('4028813a506536910150653be3ed0001','admin','2015-10-14 15:23:55','admin','2015-10-14 15:23:55','icon-nav','站点管理','auth5','4028813a505fe87201505ff93aa80002','1','/station/stationmanager.jsp','1','1');
insert  into `t_sdf_authority`(`ID`,`CREATER`,`CREATER_TIME`,`MODIFY`,`MODIFY_TIME`,`AUTH_IMG`,`AUTH_NAME`,`CODE`,`PARANT_AUTH_ID`,`STATUS`,`URL`,`IS_DELETED`,`ISSYSTEM`) 
values ('4028813a506a4de701506a79c6990000','admin','2015-10-15 15:49:37','admin','2015-10-15 15:49:37','11','角色管理','tole55','4028813a505fe87201505ff93aa80002','1','/user/roleManage.jsp','1','1');


/*
	初始化角色表数据
*/
insert  into `t_sdf_roles`(`ID`,`CREATER`,`CREATER_TIME`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`CODE`,`NAME`,`PARENT_ROLE`,`PARENT_ROLENAME`,`ISSYSTEM`) 
values ('0','admin','2015-10-13 14:53:00','1','admin','2015-10-20 10:20:38','1','超级管理员','','','1');
insert  into `t_sdf_roles`(`ID`,`CREATER`,`CREATER_TIME`,`IS_DELETED`,`MODIFY`,`MODIFY_TIME`,`CODE`,`NAME`,`PARENT_ROLE`,`PARENT_ROLENAME`,`ISSYSTEM`) 
values ('1','admin','2015-10-13 14:53:00','1','admin','2015-10-20 10:20:38','2','财务管理员','0','超级管理员','1');

/*
	初始化用户表数据
 */

insert  into `t_sdf_users`(`ID`,`CREATER`,`CREATER_TIME`,`MODIFY`,`MODIFY_TIME`,`ADDRESS`,`CITY_CODE`,`CODE`,`NAME`,`PARENT_UID`,`PASSWORD`,`PROVINCE_CODE`,`REGION_CODE`,`IS_DELETED`,`status`,`TELEPHONE`,`roles`) 
values ('0','admin','2015-10-29 13:09:12','admin','2015-10-29 13:09:12',NULL,NULL,'admin','admin',NULL,'1',NULL,NULL,'1','1','',NULL);

/*
	初始化用户角色关联表数据
*/
INSERT  INTO `rela_sdf_user_role`(`ID`,`ROLE_ID`,`USER_ID`) 
VALUES ('4028813a50b2024b0150b203b0b90000','0','0');


/*
	初始化角色权限关联表数据
*/
insert  into `rela_sdf_authority_role`(`ROLE_ID`,`authority_ID`) 
values ('0','1');/*超级管理员权限*/
insert  into `rela_sdf_authority_role`(`ROLE_ID`,`authority_ID`) 
values ('0','4028813a505fe87201505ff93aa80002');/*超级管理员权限*/
insert  into `rela_sdf_authority_role`(`ROLE_ID`,`authority_ID`) 
values ('0','4028813a506a4de701506a79c6990000');/*超级管理员权限*/
insert  into `rela_sdf_authority_role`(`ROLE_ID`,`authority_ID`) 
values ('0','4028813a506536910150653be3ed0001');/*超级管理员权限*/
insert  into `rela_sdf_authority_role`(`ROLE_ID`,`authority_ID`) 
values ('0','4028813a506515ad01506519e1720002');/*超级管理员权限*/
insert  into `rela_sdf_authority_role`(`ROLE_ID`,`authority_ID`) 
values ('0','4028813a506515ad01506519290a0001');/*超级管理员权限*/
insert into `rela_sdf_authority_role` (`ROLE_ID`, `authority_ID`)
values('1','4028813a506515ad01506519e1720002');/*财务管理员权限*/

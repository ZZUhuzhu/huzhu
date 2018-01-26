<?php
	if(!@defined('TAG')) exit("error");
	define("HOST","139.199.38.177:3306");//数据库所在的主机名或IP地址，记得加端口
	define("ACCOUNT","huzhu");//数据库登录帐户
	define("PASSWORD","zzu");//数据库登录密码
	define("DATABASE","huzhu");//选择某个数据库
	$dba=new mysqli(HOST,ACCOUNT,PASSWORD,DATABASE);
	$dba->set_charset("utf8");


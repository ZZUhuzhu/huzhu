<?php
header("Content-Type:text/html;charset=utf-8");
define('TAG',true);
require(__DIR__.'/config.php');
$account=$_POST['account'];
$password=$_POST['password'];
//入：acconut password
//出：成功与否， user_id, user_name, user_head
//$account = '1'; //测试用的样例
//$password = '147258369';

$ret['status'] = 300;
$ret['User_id'] = 0;
$ret['User_name'] = NULL;
$ret['User_head'] = NULL;
//$result=$dba->query("select user_password, user_id, user_head, user_name from User where user_id = " . $account);
$result=$dba->query("select * from User where User_account = " . $account);
$row=$result->fetch_assoc();
//var_dump($row);//显示查询到的内容
if($dba->affected_rows == 1) {
    //echo '检索成功';
    if($row["User_password"] == $password) {
        $ret['status'] = 200;
    	$ret['User_id'] = $row['User_id']; //大小写敏感
		$ret['User_head'] = $row['User_head'];
		$ret['User_name'] = $row['User_name'];
    }
}

$json = json_encode($ret);
//$json = substr($json,0,-1).']';
echo $json;


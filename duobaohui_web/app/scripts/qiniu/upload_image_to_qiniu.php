<?php

/* 调用七牛接口
 * @author    majianchao@shinc.com
 * @version   v0.1
 *@copyright shinc
 */
namespace App\Script\Qiniu; //定义命名空间

require 'vendor/autoload.php';

use Qiniu\Auth;
use Qiniu\Storage\UploadManager;

class qiniu {

	private	$accessKey;
	private $secretKey;

	public  function __construct() {
		$this->accessKey = 'h591Hrv-oh3BornRVEQqlDE7IJQYFgM-dkA44tKM';
		$this->secretKey = 'XFwQNCCycfAf6fv_Ox-teKB8Tf2Bk21Xr5cqYXEm';
	}

	public function upload163Info(){
		$auth = new Auth($this->accessKey , $this->secretKey);
		$bucket		= 'imgs';
		$token		= $auth->uploadToken($bucket);
		
		$conn		= $this->_connectMysql();

		mysql_select_db('sh_duobaohui' , $conn);
		$sql = 'select * from sh_goods_163_info';
		$result		= mysql_query($sql , $conn);
		while($row= mysql_fetch_array($result)){
			$filePath	= $this->_curl($row['goods_img']);
			$upload		= new UploadManager();
			$key		= rand(1000 , 9999).time() . '.jpg';

			list($ret , $err) = $upload->put($token , $key , $filePath);
			if($err == NULL){
				$imgUrl = 'http://7xlbf0.com1.z0.glb.clouddn.com/'.$key;
				$sql	= "UPDATE sh_goods_163_info SET goods_img_qiniu='".$imgUrl."' WHERE id=".$row['id'];
				mysql_query($sql);
			}

			$goodsImg	= array();
			$goodsImg	= explode(',' , $row['goods_desc']);

			$preg		= '/<img .*src="(.*)".*\/>/Uis';
			$tmp_goods_img = array();
			foreach($goodsImg as $gv){
				preg_match_all($preg , $gv , $goodsImg_tmp);
				$tmp_goods_img[] = $goodsImg_tmp[1][0];
			}

			$goodsDesc	= '';
			foreach($tmp_goods_img as $tv){
				$filePath	= $this->_curl($tv);
				$upload		= new UploadManager();
				$key		= rand(1000 , 9999).time() . '.jpg';

				list($ret , $err) = $upload->put($token , $key , $filePath);
				if($err == NULL){
					$imgUrl = 'http://7xlbf0.com1.z0.glb.clouddn.com/'.$key;
					$goodsDesc .= "<img src=\"".$imgUrl . "\" />" . ',';
				}
			}

			$sql	= "UPDATE sh_goods_163_info SET goods_desc_qiniu='".$goodsDesc."' WHERE id=".$row['id'];
			mysql_query($sql);
			
		}


		var_dump($err);
	}

	
	public function upload163Pic(){
		$auth = new Auth($this->accessKey , $this->secretKey);
		$bucket		= 'imgs';
		$token		= $auth->uploadToken($bucket);
		
		$conn		= $this->_connectMysql();

		mysql_select_db('sh_duobaohui' , $conn);
		$sql = 'select * from sh_goods_163_pic';
		$result		= mysql_query($sql , $conn);
		$ch = curl_init();
		while($row= mysql_fetch_array($result)){
			$filePath	= $this->_curl($row['pic_url']);
			$upload		= new UploadManager();
			$key		= rand(1000 , 9999).time() . '.jpg';

			list($ret , $err) = $upload->put($token , $key , $filePath);
			if($err == NULL){
				$imgUrl = 'http://7xlbf0.com1.z0.glb.clouddn.com/'.$key;
				$sql	= "UPDATE sh_goods_163_pic SET pic_url_qiniu='".$imgUrl."' WHERE id=".$row['id'];
				mysql_query($sql);
			}

		}

	}

	private function _connectMysql(){
		$HOST		= '192.168.1.226';
		$USER		= 'shihe';
		$PASSWORD	= 'shinc123456';

		$conn = mysql_connect($HOST , $USER , $PASSWORD);

		if(!$conn){
			die('Could not connect' . mysql_error());
		}

		return $conn;
	}

	private function _curl($url){
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, $url);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_HEADER, 0);	
		$output = curl_exec($ch);
		curl_close($ch);
		return $output;
	}
}

$qiniu = new qiniu();

$qiniu->upload163Info();
$qiniu->upload163Pic();
?>

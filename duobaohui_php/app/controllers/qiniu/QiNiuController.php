<?php

/* 调用七牛接口
 * @author    majianchao@shinc.com
 * @version   v0.1
 *@copyright shinc
 */
namespace Laravel\Controller\QiNiu; //定义命名空间

use  ApiController;  //引入接口公共父类，用于继承
use  Illuminate\Support\Facades\View; //引入视图类
use  Illuminate\Support\Facades\Input;//引入参数类
use  Illuminate\Support\Facades\Response;
use  App\Libraries\Qiniu\Auth;              //引入七牛
use  Qiniu\Processing\PersistentFop;
use  Qiniu\Storage\UploadManager;
use  Illuminate\Support\Facades\Redirect;
use  Illuminate\Routing\Controller;


class  QiNiuController extends  ApiController{

	public  function __construct() {

		parent::__construct();
	}


    //上传后的文件返回查看
    public function anyUploadToken(){
       
        $data  = Input::get('upload_ret');
        
        $ret = base64_decode($data);

        $cbody = json_decode($ret, true);

        $dn = 'http://7xlbf0.com1.z0.glb.clouddn.com/';  
        error_log(print_r($cbody, true));
        $url = $dn.$cbody['fname'];
        
        error_log($url);
         // $stat_ = file_get_contents($url.'?stat');
         //debug($stat_);
         //$stat = json_decode($stat_, true);
         //$mtype = $stat['mimeType']; 
         //$isImage = substr($mtype, 0, 6) == 'image/'
         

         //$data$url
        
                  
           


        return View::make('images.uploads', array('url' => $url));

    }
    //上传的文件
    public function anyUpload() {
        $bucket = 'imgs';
        $accessKey = 'h591Hrv-oh3BornRVEQqlDE7IJQYFgM-dkA44tKM';
        $secretKey = 'XFwQNCCycfAf6fv_Ox-teKB8Tf2Bk21Xr5cqYXEm';
        $policy = array(
            'returnUrl' => 'http://alipay.localhost/qiniu/qiNiu/upload-token',
            'returnBody' => '{"fname": $(fname)}',
        );
        $qiniu  = new Auth($accessKey , $secretKey);
        $upload = $qiniu->uploadToken($bucket, null, 3600, $policy);
      
        return View::make('images.upload' , array('upload' => $upload));

    }
    //获取token
    public function anyUploadKey() {
        $bucket = 'eduonline';
        $accessKey = 'h591Hrv-oh3BornRVEQqlDE7IJQYFgM-dkA44tKM';
        $secretKey = 'XFwQNCCycfAf6fv_Ox-teKB8Tf2Bk21Xr5cqYXEm';
        $policy = array(
            'returnUrl' => 'http://alipay.localhost/qiniu/qiNiu/upload-token',
            'returnBody' => '{"fname": $(fname)}',
        );
        $qiniu  = new Auth($accessKey , $secretKey);
        $upload = $qiniu->uploadToken($bucket, null, 3600, $policy);
      
        return $upload;

    }
    //下载私有资源
    public function getToken(){
       //资源下载参数
        if(!Input::has('parameter')){
            return Response::json(array('code' => '0', 'msg' => '缺少参数'));
        }
        $parameter = Input::get('parameter');
        //资源下载token
        $accessKey = 'h591Hrv-oh3BornRVEQqlDE7IJQYFgM-dkA44tKM';
        $secretKey = 'XFwQNCCycfAf6fv_Ox-teKB8Tf2Bk21Xr5cqYXEm';
        $qiniu = new Auth($accessKey , $secretKey);
       //资源下载链接
        $data['token'] = $qiniu->privateDownloadUrl($parameter);
         
        return Response::json(array('code' => '1', 'msg' => '成功',$data));
    }





}

?>

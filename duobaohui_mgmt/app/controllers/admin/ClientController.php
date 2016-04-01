<?php
/**
 * 客户端版本管理
 * User: guoshijie
 * Date: 15/10/29
 * Time: 下午3:43
 */
namespace Laravel\Controller\Admin;

use AdminController;
use Illuminate\Support\Facades\View;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Redirect;
use Laravel\Model\ClientModel;

class ClientController extends AdminController {

    private $_length;

    public function __construct() {
        parent::__construct();
        $this->_length = 10;
    }

    public function getReleaseNewVersionPre() {
        $data = array();
        $data['selected']	= "client";
        $data['msg']		= "";
        $data = array_merge($data, $this->viewData);
        return Response::view('admin.client.releaseNewVersion',$data);
    }

    /**
     * 发布新版本
     * @param version_number 版本号
     * @param version_desc 版本描述
     * @param is_force 是否强制更新
     * @param os_type 系统类型
     * @param apk_url 安装包地址
     * @return \Illuminate\Http\Response
     */
    public function postReleaseNewVersion() {
        $msg['selected']	= "client";
        if ( !Input::has( 'version_number' ) || !Input::has( 'version_desc' ) || !Input::has( 'is_force' ) || !Input::has( 'os_type' ) || !Input::has( 'apk_url' ) ) {
            $msg['msg']='参数错误';
            return Response::view('admin.client.releaseNewVersion',$msg);
        }

        $data['version_number'] = Input::get( 'version_number' );
        $data['version_desc'] = Input::get( 'version_desc' );
        $data['is_force'] = Input::get( 'is_force' );
        $data['os_type'] = Input::get( 'os_type' );
        $data['url'] = Input::get( 'apk_url' );
        $data['create_time'] = date('y-m-d H:i:s',time());
        $data['update_time'] = date('y-m-d H:i:s',time());

        $clientM = new ClientModel();
        $res = $clientM->addAppVersion($data);
        if($res) {
            $msg['msg']='添加成功';
            return Response::view('admin.client.releaseNewVersion',$msg);
        }
    }

    /**
	 * 获取版本列表
	 */
    public function anyVersionList() {
        $os_type = Input::has( 'os_type' )?Input::get('os_type'):1;
        $clientM = new ClientModel();
        $data = array();
        $data['selected']	= "client";
        $data['msg']		= "";
        $data['os_type']    = $os_type;

        $totalNum =$clientM->getVersionTotalNum($os_type);
        $totalNum = $totalNum[0]->sum;
        $pageinfo = $this->_pageInfo($this->_length , $totalNum);

        $list = $clientM->getVersionList($os_type,$pageinfo);
        $data['list'] = $list;
        $data = array_merge($data, $this->viewData);
        return Response::view('admin.client.versionList',$data);
    }

    private function _pageInfo($length=40 , $totalNum){
        $pageInfo               = new \stdClass;
        $pageInfo->length       = Input::has('length') ? Input::get('length') : $length;
        $pageInfo->page         = Input::has('page') ? Input::get('page') : 0;
        $pageInfo->offset		= $pageInfo->page<=1 ? 0 : ($pageInfo->page-1) * $pageInfo->length;
        $pageInfo->totalNum     = $totalNum;
        $pageInfo->totalPage    = ceil($pageInfo->totalNum/$pageInfo->length);
        return $pageInfo;
    }

	/*
	 * 客户端配置
	 */
	public function anyConfig(){
		$clientM = new ClientModel();
		if(Input::has('is_save')){
			if(Input::has('ios_audit')){
				$iosAudit = Input::has('ios_audit');
			}else{
				$iosAudit = 0;
			}
			$clientM->audit($iosAudit);
		}
		
		$data['ios_audit'] = $clientM->getIOSAudit();
        $data = array_merge($data, $this->viewData);
        return Response::view('admin.client.config', $data);
	}

}

<?php

/*
 *  红包发放
 *	@author		liangfeng@shinc.net
 *	@version	v1.3.1
 *	@copyright	shinc
 */

namespace Laravel\Service;

use Laravel\Service\PushService;
use Illuminate\Support\Facades\Log;
use Carbon\Carbon;
use Illuminate\Support\Facades\Queue;
use Laravel\Model\RedpacketModel;
use Laravel\Model\LoginModel;			//引入model
use App\Libraries\Smtp;

class RedpacketService{

	private   $redpacket;


	public function __construct(){
		$this->redpacket = new RedpacketModel();
		$this->loginM    = new LoginModel();
	}


	/*
	 * 发放新用户红包
	 */
	public function sendNewUserRedpacket($tel){
		$userId    = $this->loginM->getUserInfoByMobile($tel);
		$sendStatus=  $this->redpacket->getRedpacketInfo('new_user','6',$userId[0]->id);
		if($sendStatus){
			$push = new PushService();
			$data = array(
				'alias'         =>$userId,
				'alias_type'    =>'userId',
				'title'         =>'红包来啦~୧(๑•̀⌄•́๑)૭',
				'text'          =>"哟，新人，红包一个，拿去花",
				'after_open'    =>'go_activity',
				'activity'      =>'com.shinc.duobaohui.MyCouponsActivity'
			);

			$type = $this->getUserPhoneInfoByUserId($userId[0]->os_type);
			if(!$type){
				$os_type = '00';
			}else{
				$os_type = $type->os_type;
			}
//			usleep(20);
			$push->sendPushByUserPhoneType($os_type,$data);
			return $sendStatus;
		}else{
			return $sendStatus;
		}
	}

	/*
	 * 连续签到5天发红包
	 */
	public function sendSignRedpacket($userId){
		$sendStatus= $this->redpacket->getRedpacketInfo('sign','4',$userId);
		if($sendStatus['code'] == 1){
			$push = new PushService();
			$data = array(
				'alias'         =>$userId,
				'alias_type'    =>'userId',
				'ticker'        =>'红包来啦~୧(๑•̀⌄•́๑)૭',
				'title'         =>'红包来啦~୧(๑•̀⌄•́๑)૭',
				'text'          =>"连续签到满5天啦，红包奉上",
				'after_open'    =>'go_activity',
				'activity'      =>'com.shinc.duobaohui.MyCouponsActivity'
			);
			$this->sendSmtp("感谢您使用夺宝会，签到满5天的红包已发放到您的账户中,祝您夺宝愉快,userid:".$userId);
			$type = $this->getUserPhoneInfoByUserId($userId);
			if(!$type){
				$os_type = 00;
			}else{
				$os_type = $type->os_type;
			}
			$push->sendPushByUserPhoneType($os_type,$data);
			return $sendStatus;
		}else{
			return $sendStatus;
		}
	}


	/*
	 * 充值满n元发放红包
	 * @param $userId   用户ID
	 * @param $recharge 充值金额
	 */
	public function sendRechargeRedpacket($userId,$recharge){

		if($recharge >= 100){
			$condition = 100;
			$sendStatus= $this->redpacket->getRedpacketInfo('recharge','3',$userId);
		}elseif($recharge >= 50 && $recharge <100) {
			$condition = 50;
			$sendStatus= $this->redpacket->getRedpacketInfo('recharge', '2', $userId);
		}elseif($recharge >= 30 && $recharge <50) {
			$condition = 30;
			$sendStatus= $this->redpacket->getRedpacketInfo('recharge', '1', $userId);
		}
		Log::info("充值送红包的红发送出状态:".print_r($sendStatus,1));
		if($sendStatus['code'] == 1){
			$push = new PushService();
			$data = array(
				'alias'         =>$userId,
				'alias_type'    =>'userId',
				'ticker'        =>'红包来啦~୧(๑•̀⌄•́๑)૭',
				'title'         =>'红包来啦~୧(๑•̀⌄•́๑)૭',
				'text'          =>"土豪您已累计充值".$condition."元，送您一个大红包",
				'after_open'    =>'go_activity',
				'activity'      =>'com.shinc.duobaohui.MyCouponsActivity'
			);
			$type = $this->getUserPhoneInfoByUserId($userId);
			if(!$type){
				$os_type = 00;
			}else{
				$os_type = $type->os_type;
			}
			$push->sendPushByUserPhoneType($os_type,$data);
			return $sendStatus;
		}else{
			return $sendStatus;
		}
	}

	/*
	 * 购买满100元送红包
	 * @param $userId   用户ID
	 * @param $buy      购买金额
	 */
	public function sendBuyRedpacket($userId,$buy){
		if($buy >= 100){
			$condition = 100;
			$sendStatus =  $this->redpacket->getRedpacketInfo('buy','5',$userId);
			if($sendStatus['code'] == 1){

				$push = new PushService();
				$data = array(
					'alias'         =>$userId,
					'alias_type'    =>'userId',
					'ticker'        =>'红包来啦~୧(๑•̀⌄•́๑)૭',
					'title'         =>'红包来啦~୧(๑•̀⌄•́๑)૭',
					'text'          =>"购买过".$condition."人次，送个红包，以表敬意",
					'after_open'    =>'go_activity',
					'activity'      =>'com.shinc.duobaohui.MyCouponsActivity'
				);
				$type = $this->getUserPhoneInfoByUserId($userId);
				if(!$type){
					$os_type = 00;
				}else{
					$os_type = $type->os_type;
				}
				$push->sendPushByUserPhoneType($os_type,$data);
				return $sendStatus;
			}else{
				return $sendStatus;
			}
		}
	}




	/*
	 * 发放成功的邮件通知
	 */
	private function sendSmtp($cont){
		$smtpserver         =   'smtp.126.com';
		$smtpserverport     =   25;
		$smtpusermail       =   "breeze323136@126.com";
		$smtpemailto        =   "1092313007@qq.com";
		$smtpuser           =   "breeze323136";
		$smtppass           =   "liang15521755250";
		$mailsubject        =   "夺宝会送红包啦";
		$mailbody           =   $cont;
		$mailtype           =   "txt";
		$smtp               =   new smtp($smtpserver,$smtpserverport,true,$smtpuser,$smtppass);
		$smtp->debug        =   false;
		$smtp->sendmail($smtpemailto,$smtpusermail,$mailsubject,$mailbody,$mailtype);
	}


	/*
	 * 使用红包
	 */
	public function getIssueRedpacket($redpacketId){
		return $this->redpacket->issueRedpacket($redpacketId);
	}


	/*
	 * 根据用户ID获取用户使用设备信息
	 */
	public function getUserPhoneInfoByUserId($userId){
		return $this->redpacket->userPhoneInfo($userId);
	}


}

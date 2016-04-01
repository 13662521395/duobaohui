<?php
/**
 * 用户修改密码
 *
 * @author         
 * @version        v1.0
 * @copyright      shinc
 */
namespace Laravel\Controller\User;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session 
use Illuminate\Support\Facades\Response;	//引入response
use App\Libraries\Captcha;					

use Laravel\Model\NewUserModel;
use Laravel\Model\VerifyModel;

class NewresetController extends ApiController {
	public function __construct() {
		if (Session::hasOldInput('id')) Session::reflash('id');
	}


	/**
	* 生成随机验证码
	*/
	public function anyValidate(){
		session_start();
	
		$image=imagecreatetruecolor(100, 30);
		$bgcolor=imagecolorallocate($image, 255, 255, 255);
		imagefill($image, 0, 0, $bgcolor);
		
		$captch_code='';
		//写字母数字混合体
		for($i=0;$i<=4;$i++){
			$fontsize=8;//字体大小
			$fontcolor=imagecolorallocate($image, rand(0,120), rand(0,120), rand(0,120));//字体颜色

			$data='ABCDEFGHIJKLMNPQRSTUVWXYZ0123456789';
			$fontcontent = substr($data, rand(0,strlen($data)),1);
			$captch_code .= $fontcontent;

			$x=($i*25)+rand(5,10);
			$y=rand(5,10);

			imagestring($image, $fontsize, $x, $y, $fontcontent, $fontcolor);


		} 
		$_SESSION['authcode']= $captch_code;

		//干扰点
		for ($i=0; $i < 200; $i++) { 
			$pointcolor=imagecolorallocate($image, rand(50,200), rand(50,200), rand(50,200));
			imagesetpixel($image, rand(1,99), rand(1,29), $pointcolor);
		}
		//线扰点
		for ($i=0; $i < 3; $i++) { 
			$linecolor=imagecolorallocate($image, rand(80,220), rand(80,220), rand(80,220));
			imageline($image, rand(1,99), rand(1,29),rand(1,99), rand(1,29), $linecolor);
		}
		header('content-type: image/png');	//创建图片
		imagepng($image);	//输出图片
		imagedestroy($image);	//释放图片所占内存
		return $image;
	
	}


	/**
	*	校验随机验证码是否正确
	*/
	public function anyCaptcha(){
		session_start();
		if (!Input::has('tel')){ 
			return Response::json(array('code' => '-1','msg' => '无电话号码'));
		}
		$userModel = new NewUserModel();
		//检测用户是否存在
		if (!$userModel->checkUser(Input::get('tel'))){
		 return Response::json(array('code' => '-2','msg' => '用户未注册'));
		}
		$captch_code='';
		if(isset($_REQUEST['authcode'])) {
			
			if (strtolower($_REQUEST['authcode'])==$_SESSION['authcode']) {
				header('Content-type: image/png'); 
				return Response::json(array('code' => '1', 'msg' => '验证成功'));
			}else{
				return Response::json(array('code' => '0', 'msg' => '验证错误'));
			}
		
		}


	}
	

	/**
	 * 发送短信接口
	 * @param string $tel 电话号码
	 * @return json
	 */
	public function anySendVerifyCode() {

		if (!Input::has('tel')){ 
			return Response::json(array('code' => '-1','msg' => '无电话号码'));
		}
		$userModel = new NewUserModel();

		if (!$userModel->checkUser(Input::get('tel'))){
		 return Response::json(array('code' => '-2','msg' => '用户未注册'));

		}
		$verifyModel = new VerifyModel();
		$data = $verifyModel->sendVerifyCode(Input::get('tel'));
		return Response::json($data);
	}

	/**
	 * 验证短信并设置会话接口
	 * @param tel 电话
	 * @param code 验证码
	 * @return json
	 */
	public function anyCheckVerify() {

		if (!Input::has('tel') || !Input::has('code')) 

			return Response::json(array('code' => '10005','msg' => '缺少参数'));

		$verifyModel = new VerifyModel();

		if ($verifyModel->checkVerify(Input::get('tel'), Input::get('code'))) {

			Session::put('verify_tel', Input::get('tel'));

			return Response::json(array('code' => '1','msg' => '验证成功'));
		} else {

			Session::pull('verify_tel');
			
			return Response::json(array('code' => '0','msg' => '验证码错误'));
		}
	}


	/**
	 * 修改密码接口
	 *
	 * @param password 新密码
	 * @return \Illuminate\Http\JsonResponse
	 */
	public function anyResetPwd() {
		if (!Session::has('verify_tel')) return Response::json(array('code' => '-1', 'msg' => '验证失效'));
		if (!Input::has('password')) return Response::json(array('code' => '-2', 'msg' => '请输入新密码'));

		$userModel = new NewUserModel();
		$data = $userModel->updatePwd(Session::get('verify_tel'), md5(Input::get('password')));
		if ($data) {
			Session::pull('verify_tel');
			return Response::json(array('code' => '1', 'msg' => '修改成功'));
		}
		else return Response::json(array('code' => '0', 'msg' => '修改失败'));
	}
     
}
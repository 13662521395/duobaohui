<?php
use Illuminate\Routing\Controller;

class BaseController extends Controller {
	
	public $logPath;
	public $fileName;
	public $baseDir;
	public $time;
	
	protected function __construct(){
		$this->baseDir = dirname(dirname(dirname(__FILE__)));
		$this->logPath = $this->baseDir .'/public/logs';
		$this->fileName = date('Y-m-d', time());
		$this->time = date('Y-m-d H:i:s', time());
	}

	/**
	 * Setup the layout used by the controller.
	 *
	 * @return void
	 */
	protected function setupLayout()
	{
		if ( ! is_null($this->layout))
		{
			$this->layout = View::make($this->layout);
		}
	}
	
	/**
     * curl模拟post请求，使用json格式
     * $param, $url
     * return json
     */
    public function curlPost($param, $url){
        $param  = json_encode($param);
        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");
        curl_setopt($ch, CURLOPT_POSTFIELDS, $param);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER,true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array(
                'Content-Type: application/json; charset=utf-8',
                'Content-Length: ' . strlen($param))
        );

        return  curl_exec($ch);
    }
    
    //记录日志
    public function log($key, $data){
    	$path = $this->logPath .'/'. $key;
    	
    	if(!isset($data['code']) || !isset($data['msg'])) {
    		$log_data =  $this->time. ' | ' . $data . "\r\n";
    	} else {
    		$log_data =  $this->time. ' | ' . $data['title'] . ' | '. $data['code'] . ' | ' . $data['msg'] . "\r\n";
    	}
    	$this->writelog($path, $this->fileName, $log_data);
    }
    
    
    //写日志
    public  function writelog($path, $filename , $data)
    {
    	if(!file_exists($path)){
    		mkdir($path, 0755, true);   //递归创建目录
    	}
    	$file_name = $path.'/'.$filename;
    	if(!file_exists($file_name)) {
    		file_put_contents($file_name, $data);
    	} else {
    		file_put_contents($file_name, $data, FILE_APPEND);
    	}
    
    }

}

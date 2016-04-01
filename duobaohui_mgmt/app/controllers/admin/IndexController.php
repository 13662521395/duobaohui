<?php
/**
 * @author		wuhui@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Admin;

use AdminController;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\View;
use Laravel\Service\ShopService;
use Illuminate\Support\Facades\Log;
use Laravel\Model\Admin\ActivityModel;

class IndexController extends AdminController {

	public function __construct(){
		$this->date = $date = date('Y-m-d',time());
		parent::__construct();
	}

    public function getIndex(){
		$data = array();

		$data['selected'] = 'index';
		$data = array_merge($this->viewData, $data);
		$data['date'] = $this->date;
		$data['today'] = $this->date;
		$data['daily'] = $this->getDailyReport();
		$data['money'] = $this->getMoney();
		Log::info($data);
        return Response::view('admin.index.index' , $data);
	}

	public function getDailyReport(){
		$shopService = new ShopService();
		$list = $shopService->getDailyReport($this->date);
		return (object)$list;
	}

	public function getMoney(){
		$activityM = new ActivityModel();
		$list = $activityM->getDataCountForIndex2();
		return (object)$list;
	}

}

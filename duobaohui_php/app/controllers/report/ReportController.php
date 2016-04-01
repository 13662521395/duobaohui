<?php

/**
 * 晒单举报
 * @author		guoshijie@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */
namespace Laravel\Controller\Report;

use ApiController;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Response;
use Laravel\Model\ReportModel;

class ReportController extends ApiController {
    protected $timestamp        = NULL;
    private $reportModel;

    public function __construct(){
        $this->timestamp = strval(time());
        parent::__construct();
        $this->reportModel = new ReportModel();
    }

    /**
     * 晒单举报
     */
    public function anyReportShaidan() {
        if(!Input::has('shaidan_id') || !Input::has('report_id')) {
            return Response::json( $this->response( '10005' ) );
        }
        $data = Array();
        $shaidan_id = Input::get('shaidan_id');
        $report_id = Input::get('report_id');
        $report_user_id = Input::has('user_id')?Input::get('user_id'):-1;

        $data['shaidan_id'] = $shaidan_id;
        $data['report_id'] = $report_id;
        $data['user_id'] = $report_user_id;

        if ($this->reportModel->getReportShaiDan($shaidan_id, $report_id)) {
            $this->reportModel->updateReportShaiDan($shaidan_id, $report_id, $report_user_id);
        } else {
            $this->reportModel->addReportShaiDan($data);
        }
        return Response::json( $this->response(1) );
    }

}
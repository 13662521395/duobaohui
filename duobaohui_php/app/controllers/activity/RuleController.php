<?php
namespace Laravel\Controller\Activity;

use Illuminate\Routing\Controller;
use Illuminate\Support\Facades\Response;

class RuleController extends Controller {
	public function getIndex() {
		return Response::view('activity.rule');
	}
}

<?php
use App\Libraries\TokenUtil;//引入token工具类
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Session;
/*
|--------------------------------------------------------------------------
| Application & Route Filters
|--------------------------------------------------------------------------
|
| Below you will find the "before" and "after" events for the application
| which may be used to do any work before or after a request into your
| application. Here you may also register your custom route filters.
|
*/

//载入自定义函数
App::before(function($request)
{
	// require app_path().'/libraries/function/alipay_core.function.php'; 
	// require app_path().'/libraries/function/alipay_rsa.function.php';
//	Log::info($request);
});


App::after(function($request, $response)
{
//	Log::info($response);
});

/*
|--------------------------------------------------------------------------
| Authentication Filters
|--------------------------------------------------------------------------
|
| The following filters are used to verify that the user of the current
| session is logged into this application. The "basic" filter easily
| integrates HTTP Basic authentication for quick, simple checking.
|
*/

Route::filter('auth', function()
{
	if(empty(Session::get('userInfo'))) {
		return Redirect::guest(\Illuminate\Support\Facades\URL::route('login'));
	}

	$accessList = Session::get('userInfo')->accessList;

	$request_url = $_SERVER['REQUEST_URI'];
	$request_url = explode('?' , $request_url);
	$url = $request_url[0];
	if(empty($accessList) || !in_array($url,$accessList)) {
		return \Illuminate\Support\Facades\Redirect::to('/no-auth');
	}
});


Route::filter('auth.basic', function()
{
	return Auth::basic();
});

/*
|--------------------------------------------------------------------------
| Guest Filter
|--------------------------------------------------------------------------
|
| The "guest" filter is the counterpart of the authentication filters as
| it simply checks that the current user is not logged in. A redirect
| response will be issued if they are, which you may freely change.
|
*/

Route::filter('guest', function()
{
	if (Auth::check()) return Redirect::to('/');
});

/*
|--------------------------------------------------------------------------
| CSRF Protection Filter
|--------------------------------------------------------------------------
|
| The CSRF filter is responsible for protecting your application against
| cross-site request forgery attacks. If this special token in a user
| session does not match the one given in this request, we'll bail.
|
*/

Route::filter('csrf', function()
{
	if (Session::token() !== Input::get('_token'))
	{
		throw new Illuminate\Session\TokenMismatchException;
	}
});

/*
 * 验证令牌
 */
Route::filter('token_validate', function()
{
	if( !Input::has('form_token')){
		$response = array('code' => 0,'msg' => '请传递令牌');
		return Response::json( $response);
	}

    $form_token = Input::get('form_token');
    $res = TokenUtil::token_validate($form_token);
    if ( !$res ) {
		//throw new Symfony\Component\Security\Core\Exception\InvalidCsrfTokenException;
		$response = array('code' => 0,'msg' => '令牌无效');
		return Response::json( $response);
    }
});



Route::filter('token_create', function()
{
    TokenUtil::token_create();
/*
	$content = $response->getContent();
	$content = json_decode($content);
	if(is_null($content->data)){
		$content->data = new \stdClass;
	}
	$content->data = TokenUtil::getToken();
	$content = json_encode($content);
	$response->setContent($content);
 */
});

 Event::listen('illuminate.query', function($query, $bindings, $time, $name)
 {
     $data = compact('bindings', 'time', 'name');

     // Format binding data for sql insertion
     foreach ($bindings as $i => $binding)
     {
         if ($binding instanceof \DateTime)
         {
             $bindings[$i] = $binding->format('\'Y-m-d H:i:s\'');
         }
         else if (is_string($binding))
         {
             $bindings[$i] = "'$binding'";
         }
     }

     // Insert bindings into query
     $query = str_replace(array('%', '?'), array('%%', '%s'), $query);
     $query = vsprintf($query, $bindings);

     Log::info($query, $data);
 });






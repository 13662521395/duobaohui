<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <link rel="shortcut icon" href="/flatlab/img/favicon.png">

    <title>夺宝会后台管理-登录</title>

    <!-- Bootstrap core CSS -->
    {{ HTML::style('/flatlib/css/bootstrap.min.css' ) }}
    {{ HTML::style('/flatlib/css/bootstrap-reset.css') }}

    <!--external css-->
    {{ HTML::style('/flatlib/assets/font-awesome/css/font-awesome.css') }}
    <!-- Custom styles for this template -->
    {{ HTML::style('/flatlib/css/style.css') }}
    {{ HTML::style('/flatlib/css/style-responsive.css') }}

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
    {{ HTML::script('/flatlib/js/html5shiv.js') }}
    {{ HTML::script('/flatlib/js/respond.min.js') }}
    <![endif]-->
</head>

  <body class="login-body">

    <div class="container">

      <form class="form-signin" action="/login" method='post'>
        <h2 class="form-signin-heading">夺宝会后台管理</h2>
        <div class="login-wrap">
            <input type="text" class="form-control" placeholder="用户昵称" autofocus name='nick_name'/>
            <input type="password" class="form-control" placeholder="密码" name='password' />
			{{--
            <label class="checkbox">
                <input type="checkbox" value="remember-me"> Remember me
                <span class="pull-right">
                    <a data-toggle="modal" href="#myModal"> Forgot Password?</a>

                </span>
            </label>
			--}}
            <button class="btn btn-lg btn-login btn-block" type="submit">登陆</button>
        </div>

      </form>

    </div>



    <!-- js placed at the end of the document so the pages load faster -->
    {{ HTML::script('/flatlib/js/jquery.js') }}
    {{ HTML::script('/flatlib/js/bootstrap.min.js') }}


  </body>
</html>

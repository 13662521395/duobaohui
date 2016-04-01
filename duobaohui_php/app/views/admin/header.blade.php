<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <link rel="shortcut icon" href="img/favicon.png">

    <title>夺宝会后台管理系统</title>

    <!-- Bootstrap core CSS -->
    {{ HTML::style('/flatlib/css/bootstrap.min.css') }}
    {{ HTML::style('/flatlib/css/bootstrap-reset.css') }}
    <!--external css-->
    {{ HTML::style('/flatlib/assets/font-awesome/css/font-awesome.css') }}
    {{ HTML::style('/flatlib/assets/jquery-easy-pie-chart/jquery.easy-pie-chart.css') }}
    {{ HTML::style('/flatlib/css/owl.carousel.css') }}
    <!-- Custom styles for this template -->
    {{ HTML::style('/flatlib/css/style.css') }}
    {{ HTML::style('/flatlib/css/common.css') }}
    {{ HTML::style('/flatlib/css/style-responsive.css') }}
	{{ HTML::style('/flatlib/assets/dropzone/css/dropzone.css') }}
	{{ HTML::style('/flatlib/assets/bootstrap-datepicker/css/datepicker.css') }}

    {{ HTML::script('/flatlib/js/jquery-1.8.3.min.js') }}
<!--     {{ HTML::script('/flatlib/assets/morris.js-0.4.3/raphael-min.js') }}
    {{ HTML::script('/flatlib/assets/morris.js-0.4.3/example/lib/prettify.js') }}
    {{ HTML::script('/flatlib/assets/morris.js-0.4.3/example/lib/example.js') }}

    {{ HTML::style('/flatlib/assets/morris.js-0.4.3/example/lib/example.css') }}
    {{ HTML::style('/flatlib/assets/morris.js-0.4.3/example/lib/prettify.css') }}
    {{ HTML::style('/flatlib/assets/morris.js-0.4.3/morris.css') }} -->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
      {{ HTML::script('/flatlib/js/html5shiv.js') }}
      {{ HTML::script('/flatlib/js/respond.min.js') }}
    <![endif]-->

   

	<!--introduce sea module-->
	{{ HTML::script('/js/seajs/seajs/seajs/2.2.0/sea.js') }}
	{{ HTML::script('/js/seajs/seajs/seajs/seajs-text.js')}}

	<script>
            seajs.config({

			base:"/js/seajs/",
			alias:{
				'jquery':"modules/jquery/jquery/1.10.1/jquery.js",
				'artTemplate':"modules/artTemplate.js"
			}
		});
    </script>
    {{ HTML::script('/flatlib/js/jquery.validate.min.js')}}
  </head>

  <body>

  <section id="container" >
      <!--header start-->
      <header class="header white-bg">
            <div class="sidebar-toggle-box">
                <div data-original-title="Toggle Navigation" data-placement="right" class="icon-reorder tooltips"></div>
            </div>
            <!--logo start-->
            <a href="index.html" class="logo"><span>夺宝会后台管理系统</span></a>
            <!--logo end-->
            <div class="top-nav ">
                <!--search & user info start-->
                <ul class="nav pull-right top-menu">
                    <li>
                        <input type="text" class="form-control search" placeholder="Search">
                    </li>
                    <!-- user login dropdown start-->
                    <li class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <img alt="" src="/flatlib/img/avatar1_small.jpg">
                            <span class="username">admin</span>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu extended logout">
                            <div class="log-arrow-up"></div>
                            <li><a href="#"><i class=" icon-suitcase"></i>Profile</a></li>
                            <li><a href="#"><i class="icon-cog"></i> Settings</a></li>
                            <li><a href="#"><i class="icon-bell-alt"></i> Notification</a></li>
                            <li><a href="login.html"><i class="icon-key"></i> Log Out</a></li>
                        </ul>
                    </li>
                    <!-- user login dropdown end -->
                </ul>
                <!--search & user info end-->
            </div>
        </header>
      <!--header end-->

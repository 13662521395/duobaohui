<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <link rel="shortcut icon" href="img/favicon.png">

    <title>no authority</title>

    <!-- Bootstrap core CSS -->
    {{ HTML::style('/flatlib/css/bootstrap.min.css') }}
    {{ HTML::style('/flatlib/css/bootstrap-reset.css') }}
    <!--external css-->
    {{ HTML::style('/flatlib/assets/font-awesome/css/font-awesome.css') }}
    {{ HTML::style('/flatlib/assets/jquery-easy-pie-chart/jquery.easy-pie-chart.css') }}
    {{ HTML::style('/flatlib/css/owl.carousel.css') }}
    <!-- Custom styles for this template -->
    {{ HTML::style('/flatlib/css/style.css') }}
    {{ HTML::style('/flatlib/css/style-responsive.css') }}
	{{ HTML::style('/flatlib/assets/dropzone/css/dropzone.css') }}

    {{ HTML::script('/flatlib/js/jquery-1.8.3.min.js') }}
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
      {{ HTML::script('/flatlib/js/html5shiv.js') }}
      {{ HTML::script('/flatlib/js/respond.min.js') }}
    <![endif]-->

</head>




  <body class="body-404">

    <div class="container">

      <section class="error-wrapper">
          <i class="icon-404"></i>
          <h1>您没有权限访问这个页面</h1>
          <h2>no authority</h2>
          <p class="page-404">Something went wrong or that page doesn’t exist yet. <a href="index.html">Return Home</a></p>
      </section>

    </div>


  </body>

<script>
$(document).ready(function(){
	setTimeout(function(){
	history.back( -1 );
	} , 2000);
});

</script>
</html>

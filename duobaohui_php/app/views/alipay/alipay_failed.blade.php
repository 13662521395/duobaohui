<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="Author" content="shinc" />
    <meta name="Copyright" content="shinc" />
    <meta name="keywords" content="shinc" />
    <meta name="description" content="shinc" />
    <title></title>
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
    <link href="/flatlib/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
        body {TEXT-ALIGN: center;width:100%;height:100%;background-color: #efefef; margin: 0;padding: 0;}
        div.main{ width: 100%; height: 100%;  background-color: transparent; font-size: 62.5%; }
        div.content,div.error_msg,div.error_detail{background-color: #f7f7f7;}
        div.content{width: 100%; height: 150px;;text-align: center;}
        div.content span{height: 100%; display: inline-block; vertical-align: middle;}
        div.content img{width:127px; height: 70%;vertical-align: middle;}
        div.error_msg,div.error_detail{padding-left: 10%; padding-right: 10%; line-height: 1.5; font-size: 2em; color: darkgray;}
        div.error_detail{text-align: left; padding-top: 5px; padding-bottom: 10px;}
    </style>
</head>
<body>
    <div class="main">
        <div class="content">
            <span></span>
            <img src="/imgs/icon_payfail@3x.png" id="fail_img">
        </div>
        <div class="error_msg">交易失败</div>
        <div class="error_detail">
            {{$msg}}，所支付的金额已经充入您的账户
        </div>
    </div>

    <script>
        $(document).ready(function(){
            var img = $('#fail_img');
            var height = img.height();
            img.width(height+3);
            console.log(height);
        });
    </script>
</body>
</html>

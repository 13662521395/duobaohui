<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>下载夺宝会APP,更多奖品等你来拿 - 夺宝会</title>
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
    <style type="text/css" rel="stylesheet">
        body,div,p{margin: 0px;padding: 0px;}
        body{
            /*background: #F6D64F;*/
        }
        #master{width: 100%;margin: 0 auto;position: relative;}
        .browser_master{width:100%;background: url('http://7xlbf0.com1.z0.glb.clouddn.com/dbh_bg_3.png') no-repeat;position: absolute;z-index: 10;display: none;}
    </style>
</head>
<body>
<div id="master">
    <div class="browser_master"></div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        var winHeight = $(window).height();
        setWinWidth(winHeight);

        if(isWeiXin()){
            $('.browser_master').show();
        }else{
            if(isiphone()){
                alert("iPhone客户端正在研发中...敬请期待！");
            }else{
                var url = '/system/download/download-app-url';
                var data = {
                    'os_type' : isiphone()? '2' : '1'
                }
                var callback = function(res){
                    console.log(res);
                    if(res.code == 1){
                        window.location.href = res.data+'?'+Math.random();
                    }
                }
                $.get(url , data , callback , 'json');
            }
        }

        function setWinWidth(winHeight) {
            var img_url = 'http://7xlbf0.com1.z0.glb.clouddn.com/dbh_bg_2.jpg';
            var img = new Image();
            img.src = img_url;
            img.onload = function(){
                var width = img.width;
                var height = img.height;
                var newWidth = winHeight*width/height;
                var winWidth = ispc() ? newWidth : '100%';

                $('.browser_master').css({'background-size' : winWidth + ' ' + winHeight+'px' , 'height' : winHeight});
                $('#master').width(winWidth);
            }
        }

        function isWeiXin(){
            var ua = window.navigator.userAgent.toLowerCase();
            if(ua.match(/MicroMessenger/i) == 'micromessenger'){
                return true;
            }else{
                return false;
            }
        }

        function isiphone(){
            if(navigator.userAgent.match(/iPhone/i)){
                return true;
            }
            return false;
        }

        function ispc(){
            var userAgentInfo = navigator.userAgent;
            var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");
            var flag = true;
            for (var v = 0; v < Agents.length; v++){
                if (userAgentInfo.indexOf(Agents[v]) > 0){
                    flag = false; break;
                }
            }
            return flag;
        }
    });

</script>
</body>
</html>
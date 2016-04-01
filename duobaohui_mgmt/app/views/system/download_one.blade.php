<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<title>下载夺宝会APP,更多奖品等你来拿 - 夺宝会</title>
		<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
		<style type="text/css" rel="stylesheet">
		body,div,p{margin: 0px;padding: 0px;}
		body{
			background: #F6D64F;
			/*background-image:url('http://7xlbf0.com1.z0.glb.clouddn.com/dbh_download_bg_1.png');
			background-repeat:no-repeat;
			background-size:100%;
			font-size: 20px;*/

		}
		#master{width: 100%;margin: 0 auto;position: relative;}
		.browser_master{width:100%;background: url('http://7xlbf0.com1.z0.glb.clouddn.com/dbh_bg_3.png') no-repeat;position: absolute;z-index: 10;display: none;}
		.dbh_panel{width: 100%;background: url('http://7xlbf0.com1.z0.glb.clouddn.com/dbh_bg_2.jpg') no-repeat;position: relative;text-align: center; background-size: 100%;}
		.main{position: absolute;width: 100%;top: 65%;}
		.dbh_panel .get_app{border: 1px solid #E2BE4B; background: #F94F46; width: 70%;height:40px;color:#E2BE4B;margin-top:20px;border-radius: 10px;cursor: pointer;font-size: 18px}
		</style>
	</head>	
    <body>
    	<div id="master">
    		<div class="browser_master"></div>
	 	  	<div class="dbh_panel">
	 	  		<div class="main">
	    		<p>
	    			<button class="get_app">下载APP</button>
					<input id="download_url" type="hidden" value="{{ $url or 'http://7xlbf0.com1.z0.glb.clouddn.com/DuoBaoHui_alipay_11_3.apk' }}"/>
	    		</p>
	    		</div>
	    	</div>
    	</div>





   		<script type="text/javascript">
   			$(document).ready(function(){

     			var winHeight = $(window).height();
				setWinWidth(winHeight);

				var download_url = $('#download_url');
   				$('.get_app').bind('click' , function(){
   					if(isWeiXin()){
   						$('.browser_master').show();
   					}else{
   						if(isiphone()){
   							alert("iPhone客户端正在研发中...敬请期待！");
   						}else{
   							window.location.href = download_url.val()+'?'+Math.random();
   						}
   						
   					}
   				});

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
						$('.dbh_panel').css({'background-size' : winWidth + ' ' + winHeight+'px' , 'height' : winHeight});
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
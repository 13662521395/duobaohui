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
		.dbh_panel{width: 100%;background: url('http://7xlbf0.com1.z0.glb.clouddn.com/dbh_bg_1.jpg') no-repeat;position: relative;text-align: center; background-size: 100%}
		.main{position: absolute;width: 100%;top: 65%;}
		.dbh_panel .phone{border: 0px; width: 70%;text-align: center;height:40px;border-radius: 10px;outline: none;}
		.dbh_panel .get_money{border: 1px solid #FB4047; background: #F3D55B; width: 70%;height:40px;outline:none;color:#FB4047;margin-top:20px;border-radius: 10px;cursor: pointer;}
		</style>
		<script>

		</script>
	</head>	
    <body>
    	<div id="master">
	 	  	<div class="dbh_panel">
	 	  		<div class="main">
	    		<p>
	    			<input class="phone" type="tel" maxlength="11" placeholder="请输入手机号" />
	    			<input id="shopId" type="hidden" value="{{ $shopId or '-1' }}"/>
	    			<input id="scene" type="hidden" value="{{ $scene or '1' }}"/>
	    			<input id="shareType" type="hidden" value="{{ $shareType }}"/>
					<input id="activityId" type="hidden" value="{{ $activityId }}"/>
					<input id="userId" type="hidden" value="{{ $userId or '-1' }}"/>
	    		</p>
	    		<p>
	    			<button class="get_money">领取夺宝币</button>
	    		</p>
	    		</div>
	    	</div>
    	</div>




   		<script type="text/javascript">
   			$(document).ready(function(){
     			var winHeight = $(window).height();
				setWinWidth(winHeight);

				var shopId = $('#shopId').val();
				var activityId = $('#activityId').val();
				var shareType = $('#shareType').val();
				var userId = $('#userId').val();
				var os_type = isiphone()? '2' : '1';

				$('.get_money').bind('click' , function(){
					var phoneNumber = $('.phone').val();
   					if(!isphone(phoneNumber)){
   						alert("请输入正确的手机号");
   					}else{
   						var url = getUrl(shareType);
						var data = getData(shareType,phoneNumber);
   						var callback = function(res){
   							if(res.code == 20503){
   		   						window.location.href="/system/download/app-one?os_type="+os_type;
							}else{
								alert(res.msg);
								window.location.href="/system/download/app-one?os_type="+os_type;
							}
						}
						$.get(url , data , callback , 'json');
   					}
   				});

				function setWinWidth(winHeight) {
					var img_url = 'http://7xlbf0.com1.z0.glb.clouddn.com/dbh_bg_1.jpg';
					var img = new Image();
					img.src = img_url;
					img.onload = function(){
						var width = img.width;
						var height = img.height;
						var newWidth = winHeight*width/height;
						var winWidth = ispc() ? newWidth : '100%';
						$('.dbh_panel').css({'background-size' : winWidth + ' ' + winHeight+'px' , 'height' : winHeight});
						$('#master').width(winWidth);
					}
				}

				function getUrl(typeStr){
					if(typeStr == '1') {
						return '/activity/share/give-red-packet';
					}
					return '/activity/share/share-red-packet';
				}

				function getData(shareType,phoneNumber) {
					var os_type = isiphone()? '2' : '1';
					var data = {
						'tel' : phoneNumber,
						'activityId' : activityId
					}
					data.os_type = os_type
					if(shareType == '1'){
						data.shopId = shopId;
					} else {
						data.userId = userId;
					}
					return data;
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

				function isphone(inpurStr){
	          		var partten = /^1[3|4|5|8|7][0-9]\d{8}$/;
	          		if(partten.test(inpurStr)){
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
   			});
   			
   		</script>
    </body>
</html>
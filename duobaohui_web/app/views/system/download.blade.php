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
				<input id="shopChannelId" type="hidden" value="{{ $shopChannelId or '0' }}"/>
				<input id="scene" type="hidden" value="{{ $scene or '1' }}"/>
				<input id="shareType" type="hidden" value="{{ $shareType }}"/>
				<input id="activityId" type="hidden" value="{{ $activityId }}"/>
				<input id="userId" type="hidden" value="{{ $userId or '-1' }}"/>
				<input id="channel" type="hidden" value="{{ $channel or '7' }}"/>
				<input id="source" type="hidden" value="{{ $source or '1' }}"/>
				<input id="sessionId" type="hidden" value="{{ $sessionId}}"/>
			</p>
			<p>
				<button class="get_money">领取夺宝币</button>
			</p>
		</div>
	</div>
</div>

<script src="/js/download.js"></script>

</body>
</html>
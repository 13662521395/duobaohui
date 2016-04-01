<?php
/**
* FEROS™ PHP template engine
* @author feros<admin@feros.com.cn>
* @copyright ©2015 feros.com.cn
* @link http://www.feros.com.cn
* @version 2.0.2
*/
?><style type="text/css">
	.model{
		z-index: 1003; 
		width:320px; height:120px; text-align:center;
		background-color:#fff; display: none;
		border:1px solid #ccc;
		padding:10px;
}
</style><div id="model" class="model">
			您正在删除<p class="name"></p><div class="alert alert-danger" style="display:none"><span></span></div><div class="alert alert-success" style="display:none"><span></span></div><button class="btn btn-danger" type="button" ><i class="icon-remove bigger-110"></i>
					取消
				</button>&nbsp;
				<button class="btn btn-success" type="button" ><i class="icon-ok bigger-110"></i>
					确认
				</button></div><script type="text/javascript">
$(function(){ 
	var postData = {id: 0};
	var postUrl = '';
	var letDivCenter = function (name){ 
		var left = ($(window).width() - $('#model').width())/2; 
		var scrollTop = $(document).scrollTop(); 
		var scrollLeft = $(document).scrollLeft(); 
		$('body').css({position:'relative'});
		$('#model').css( { position : 'fixed', 'top' : 100, left : left + scrollLeft } ).show();
		$('#model .name').text(name);
		return true;
	};


	var del = function(){
		$.ajax({
			url: postUrl,
			type:'post',
			data:postData,
			dataType:"json",
			success:function(data){
				$('#model').css('height','180px');
				if(data.status){
					$('#model .alert-danger').css('display','none');
					$('#model .alert-success').css('display','block');
					$('#model .alert-success span').text(data.message);
					$('#model .btn-danger').hide();
					$('#model .btn-success').hide();
					if(postUrl.indexOf('node_del')<0){ /*删除节点单独处理, 显示返回信息*/
						location.reload();
					}
				}else{
					$('#model .alert-danger').css('display','block');
					$('#model .alert-danger span').text(data.message);
				}
			}
		});
	};

	$('.del_btn').click(function (){
		postData.id = $(this).attr('iid');
		postUrl = $(this).attr('iurl');
		letDivCenter($(this).attr('iname'));
	});

	$('#model .btn-success').click(function (){
		del();
	});

	$('#model .btn-danger').click(function (){
		$('#model').hide();
		$('#model .alert-danger').css('display','none');;
		$('#model').css('height','120px');
	});
}); 
</script>

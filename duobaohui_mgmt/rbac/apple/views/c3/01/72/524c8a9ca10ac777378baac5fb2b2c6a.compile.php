<?php
/**
* FEROS™ PHP template engine
* @author feros<admin@feros.com.cn>
* @copyright ©2015 feros.com.cn
* @link http://www.feros.com.cn
* @version 2.0.2
*/
?><h3 class="smaller lighter blue">添加权限用户</h3><div class="alert alert-danger" style="display:none"><i class="icon-remove"></i><span></span></div><div class="alert alert-success" style="display:none"><i class="icon-ok"></i><span></span></div><form class="form-horizontal" id="validation-form" method="post" novalidate="novalidate" action="<?php echo $fromDomain;?>user_add"><input type="hidden" name="is_save" class="col-xs-12 col-sm-5" value="1"><div class="form-group"><label class="control-label col-xs-12 col-sm-3 no-padding-right" for="login_name">登录名:</label><div class="col-xs-12 col-sm-9"><div class="clearfix"><input type="text" id="login_name" name="login_name" class="col-xs-12 col-sm-5" value=""></div></div></div><div class="form-group"><label class="control-label col-xs-12 col-sm-3 no-padding-right" for="password">密码:</label><div class="col-xs-12 col-sm-9"><div class="clearfix"><input type="PASSWORD" id="password" name="password" class="col-xs-12 col-sm-5" value=""></div></div></div><div class="form-group"><label class="control-label col-xs-12 col-sm-3 no-padding-right" for="password2">重复密码:</label><div class="col-xs-12 col-sm-9"><div class="clearfix"><input type="PASSWORD" id="password2" name="password2" class="col-xs-12 col-sm-5" value=""></div></div></div><div class="form-group"><label class="control-label col-xs-12 col-sm-3 no-padding-right" for="nick_name">昵称:</label><div class="col-xs-12 col-sm-9"><div class="clearfix"><input type="text" id="nick_name" name="nick_name" class="col-xs-12 col-sm-5" value=""></div></div></div><div class="form-group"><label class="control-label col-xs-12 col-sm-3 no-padding-right" for="role_id">授权:</label><div class="col-xs-12 col-sm-9"><div class="clearfix"><?php foreach($roleList as $v): ?><input type="checkbox" name="role_id[]" value="<?php echo $v->role_id; ?>" /><?php echo $v->name;  endforeach; ?></div></div></div><div class="clearfix form-actions"><div class="col-md-offset-3 col-md-9"><button class="btn btn-info" type="button" id="form_submit"><i class="icon-ok bigger-110"></i>
													保存
												</button></div></div></form><script>
$(function(){ 
	$(".readonly").click( 
		function(){ 
			this.checked = !this.checked; 
		} 
	); 

	$('#form_submit').click(function (){
		var form = $('#validation-form');
		$.ajax({
			url:form.attr('action'),
			type:form.attr('method'),
			data:form.serialize(),
			dataType:"json",
			success:function(data){
				if(data.status){
					$('.alert-success span').html(data.message);
					$('.alert-success').show(300);
					$('.alert-danger').hide(300);
				}else{
					$('.alert-danger span').html(data.message);
					$('.alert-danger').show(300);
					$('.alert-success').hide(300);
				}
			}
		});
	});

}); 
</script>

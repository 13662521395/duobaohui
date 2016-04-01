<?php
/**
* FEROS™ PHP template engine
* @author feros<admin@feros.com.cn>
* @copyright ©2015 feros.com.cn
* @link http://www.feros.com.cn
* @version 2.0.2
*/
?><script>/*
// 在使用页面定义 如下参数
var vform = {
	id: '#form-id',
	submitBtn: 'form_submit',
};
 */

$(function(){ 
	$(".readonly").click( 
		function(){ 
			this.checked = !this.checked; 
		} 
	); 

	if($(vform.id).find('.alert').length==0){
		var alertHtml	= '<div class="alert alert-danger" style="display:none"><i class="icon-remove"></i><span></span></div>';
		alertHtml		+= '<div class="alert alert-success" style="display:none"><i class="icon-ok"></i><span></span></div>';
		$(vform.id).prepend(alertHtml);
	}
	$(vform.submitBtn).click(function (){
		var form = $(vform.id);
		$.ajax({
			url:form.attr('action'),
			type:form.attr('method'),
			data:form.serialize(),
			dataType:"json",
			success:function(data){
				if(data.status){
					$('.alert-success').hide(300); 
					$('.alert-success').show(300);
					$('.alert-danger').hide(300);
					$('.alert-success span').html(data.message);
				}else{
					$('.alert-danger').hide(300); 
					$('.alert-danger').show(300);
					$('.alert-success').hide(300);
					$('.alert-danger span').html(data.message || data);
				}
			}
		});
	});

}); 
</script>

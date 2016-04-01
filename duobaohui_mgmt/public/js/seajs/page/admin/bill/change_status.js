define(function(require , exports , module) {
	var $ = require('jquery');

	var getChangeStatus =  function() {
		$('.change-status').bind('click' , function(){
			var status = confirm('您确定现在就处理吗');
			if(status == true ){
				var _this = this;
				var billId = $(_this).attr('bill_id');
				$.ajax({
					type:'GET',
					dataType:'json',
					url:'/admin/bill/change-status',
					data:{
						id:billId
					}
				})
				.done(function(data){

					// $(_this).parent().parent().html('');
					$(_this).html('已处理');
					$('.status').html('已处理');

				})
				.fail(function(data){
					$(_this).html('已处理');
					$('.status').html('已处理');
				});
			}else{
				return false;
			}
			
		});
	}



	var editAlipay =  function() {
		$('.edit-alipay').bind('click' , function(){
			var status = confirm('危险订单，建议立即修复');
			if(status == true ){
				var _this = this;
				var billId = $(_this).attr('bill_id');
				$.ajax({
					type:'GET',
					dataType:'json',
					url:'/admin/bill/edit-alipay',
					data:{
						id:billId
					}
				})
				.done(function(data){

					// $(_this).parent().parent().html('');
					$(_this).html('已修复');
					$('.status').html('已修复');

				})
				.fail(function(data){
					// $(_this).html('已修复');
					// $('.status').html('已修复');
					alert('修复失败，请重试');
				});
			}else{
				return false;
			}
			
		});
	}

	exports.getChangeStatus = getChangeStatus;
	exports.editAlipay 		= editAlipay;
});
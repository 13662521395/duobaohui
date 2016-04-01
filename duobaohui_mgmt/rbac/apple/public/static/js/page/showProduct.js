frm.define('page/showProduct' , ['component/shareTmp' , 'jquery'] , function(require , exports){
	var shareTmp = require('component/shareTmp');
	var $ = require('jquery');
	return function(){
		$('.show_product').bind('click' , function(){
			var _this = this;
			
			var orderId = $(_this).find('.product_id').html();

			var url = '/goods/ajax_order_product';
			var data = {
				productId : orderId
			}
			var type='json';
			var callback = function(data){
				var html = shareTmp('showTable' , data);
				$('#collapse_' + orderId).find('.panel-body').html(html)
			}
			$.post(url , data , callback ,type);
		});	

	} 
});

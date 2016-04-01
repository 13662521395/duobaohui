frm.define('page/companyProduct' , ['component/shareTmp', 'component/dialog' , 'jquery'] , function(require , exports){
	var shareTmp = require('component/shareTmp');
	var $ = require('jquery');
	var dialog = require('component/dialog');

	var showWarehouse = function(){

		$('.dd-product').bind('click' , function(){

			var productId = $(this).attr('productId');
			
			if(!$('.dd-div-'+productId).hasClass('show')){
				$('.dd-div').hide(300).removeClass('show');
			}else{
				$('.dd-div-'+productId).hide(300).removeClass('show');
				return false;
			}


			var url = '/goods/warehouse_list';
			var data = {
				productId 	: productId,
				call		: 'showWarehouseInventory'
			};
			var callback = function(obj){
				var html = shareTmp('showWarehouseInventory' , obj);
				$('.dd-div-'+productId).addClass('show').show(300).html(html);
			}
			var type = 'json';

			$.post(url , data , callback , type);
		});

	}
	
	exports.showWarehouse = showWarehouse;

});

frm.define('page/purchaseOrderList' , ['component/shareTmp', 'component/dialog' , 'jquery'] , function(require , exports){
	var shareTmp = require('component/shareTmp');
	var $ = require('jquery');
	var dialog = require('component/dialog');

	var dialogChangeNum = function(){
		
		$('.purchase-num').bind('click' , function(){
			$('.info').html('');
			var father = $($(this).parent());
			var span = $(father.find('span'));

			var real = {
				num			: parseInt(span.html()),
				purchaseId	: parseInt(father.attr('purchaseid'))
			};
			//var real.num = parseInt(span.html());
			var dialogContent = shareTmp('changePurchaseNum' , real);
			closeDialog = dialog.meiliDialog({
					dialogWidth:220,
					dialogTitle:'实际采购数量',
					isOverflow:true,
					dialogContent:dialogContent
			});
		});
			
	}

	var changeNum = function(){
		$('body').on('click' , '.changeNum' , function(){
			var real_number = parseInt($('#num').val());
			var purchaseId = parseInt($('#num').attr('purchaseid'));

			var url = '/goods/purchase_edit';
				var data = {
					is_save			: 1,
					purchase_id		: purchaseId,
					real_number		: real_number 
				};
				var type = 'json';

				var callback = function(obj){
					closeDialog();	
					$('.realNumber_' + purchaseId).html(real_number);
				}

				$.post(url , data , callback , type);
		});
	}

	var changeWarehouse = function(){
		$('.warehouseSelect').bind('change' , function(){
			var warehouseId = $(this).val();
			if(!warehouseId){
				return false;
			}
			var purchaseId = $(this).attr('purchaseid');

			var realNumber = parseInt($('.realNumber_' + purchaseId).html());
			console.log(realNumber);

			if(!realNumber){
				$('.info').html('');
				$('.error_'+purchaseId).html('请填写实际采购数量');
				return false;
			}

			$('.btn').hide();
			$('.btn_'+purchaseId).show();
			$('.btn_'+purchaseId).attr('warehouseid' , warehouseId);

				
		});
	}

	var confirmChangeWarehouse = function(){

		$('.btn').bind('click' , function(){
			var purchaseId = $(this).attr('purchaseid');
			var warehouseId = $(this).attr('warehouseid');
			var real_number = $('.realNumber_' + purchaseId).html();

			var url = '/goods/purchase_edit';
			var data = {
				is_save				: 1,
				purchase_id			: purchaseId,
				status				: 1,
				warehouse_id		: warehouseId,
				real_number			: real_number
			};
			var type = 'json';

			var callback = function(obj){
				$('.btn').hide();
				$('.info').html('');
				$('.error_'+purchaseId).html('入库成功');
			}

			$.post(url , data , callback , type);
		});
	}
	
	exports.dialogChangeNum = dialogChangeNum;
	exports.changeNum = changeNum;
	exports.changeWarehouse = changeWarehouse;
	exports.confirmChangeWarehouse = confirmChangeWarehouse;

});

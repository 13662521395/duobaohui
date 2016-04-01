frm.define('page/outLibrary' , ['component/shareTmp', 'component/dialog' , 'jquery'] , function(require , exports){
	var shareTmp = require('component/shareTmp');
	var $ = require('jquery');
	var dialog = require('component/dialog');

	var showProduct = function(){

		$('.warehouseSelect').bind('change' , function(){

			var warehouseId = $(this).val();
			$.when(
					$.ajax({url:'/goods/product_list' , data:{call : 'warehouseShowProduct' , warehouse_id : warehouseId} , dataType:'json' , type:'post'}) ,
					$.ajax({url:'/goods/product_category_list' , data:{call:'openCategory'} , dataType:'json' , type:'post'})
				).done(function(product , category){
	
					var data = {
						product		: product[0].product,
						category	: category[0].category
					}

					closeDialog = dialog.meiliDialog({
						dialogWidth:1000,
						dialogTitle:'商品',
						isOverflow:true
					});
					
					var html = shareTmp('js_product' , data);	
					$('#dialogContent').html(html);
			});
		});

	}

	var changeNum = function(){

		//点击“-”按钮的操作
		$('body').on('click' , '.btn-dec' , function(){
			var product_id	= $(this).attr('product_id');
			var product_num = $('.product-num-'+product_id);
			var checkbox	= $('.table-checkbox-'+product_id);
			
			var num = parseInt(product_num.val());
			if(num >= 1){
				product_num.val(num - 1);
				//num==1,就取消选择
				if(num == 1){
					checkbox.attr('checked' , false);
					checkbox.attr('product_num' , num - 1);
				}else{
					checkbox.attr('checked' , true);
					checkbox.attr('product_num' , num - 1);

				}

			}
		});

		//点击“+”按钮的操作
		$('body').on('click' , '.btn-add' , function(){
			var product_id	= $(this).attr('product_id');
			var product_num = $('.product-num-'+product_id);
			var checkbox	= $('.table-checkbox-'+product_id);
			
			var num = parseInt(product_num.val());
			if(!num){
				num = 0;
			}
			product_num.val(num + 1);
			checkbox.attr('checked' , true);
			checkbox.attr('product_num' , num + 1);

		});
	}

	var getProduct = function(){
		$('body').on('click' , '.btn-cancel' , function(){
				closeDialog();
		});
		$('body').on('click' , '.btn-confirm' , function(){
			var product_id = new Array();
			var product_num = new Array();
			$('.table-checkbox').each(function(){
				if($(this).attr('checked') == 'checked'){
					product_id.push($(this).attr('product_id'));
					product_num.push($(this).attr('product_num'));
				}
			});

			var url = '/goods/product_list';
			var data = {
				call			: 'ajaxAddProduct',
				product_id		: product_id,
				product_num		: product_num
			};
			callback = function(obj){
				closeDialog();
				var html = shareTmp('order_tabe' , obj);
				$('.order_table').append(html);

				var sales = $('.sales');
				var number = $('.number');
				var total_sales = 0;
				$.each(sales , function (idx , value) {
					total_sales += parseFloat($(value).html()) * parseFloat($(number[idx]).html());
				});

				$('.total_price').val(total_sales);
			}

			$.post(url , data , callback , 'json');
		});
	}


	exports.showProduct = showProduct;
	exports.changeNum = changeNum;
	exports.getProduct = getProduct;

});

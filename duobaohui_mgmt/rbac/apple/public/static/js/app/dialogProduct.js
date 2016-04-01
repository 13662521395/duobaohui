frm.define('app/dialogProduct' , ['component/shareTmp' , 'component/dialog' , 'jquery'] , function(require , exports){
	var $			= require('jquery');
	var shareTmp	= require('component/shareTmp');
	var dialog	= require('component/dialog');

	var closeDialog;

	var dialogProduct =  function(){
		$(".page-content").on('mouseenter' , ".table-tr" ,  function(){
			$(this).find('div').css('display' , '');
			$(this).find('input').css({'border':'none' , 'background-color':'#f6f6f6'}).bind('mouseenter' , function(){
				$(this).css({'border':'1px solid #fff','background-color':'#fff'});
			});
			$(this).find('.icon-one').hide();
			$(this).find('.icon-two').show();
		}).on('mouseleave' , '.table-tr' ,  function(){
			$(this).find('div').css('display' , 'none');
			$(this).find('.icon-one').show();
			$(this).find('.icon-two').hide();
		});

		$('.page-content').on('click' , '.btn-select' ,  function(){
						
			$.when(
					$.ajax({url:'/goods/company_product' , data:{call:'ajaxShowProduct'} , dataType:'json'}) ,
					$.ajax({url:'/goods/product_category_list' , data:{call:'openCategory'} , dataType:'json'})
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
					var id = $(this).attr('product_id');
					product_id.push(id);
					product_num.push($('.product-num-'+id).val());
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
				$('.product-data').each(function(){

					var haveData = $(this).hasClass('haveData');

					if(!haveData){
						var product = obj.product.pop();
						var productNum = obj.productNum.pop();
						if(product){
							$(this).find('.product-id').html(product.product_id);
							$(this).find('.product-name').html(product.name);
							$(this).find('.product-format').html(product.format);
							$(this).find('.product-num').html(productNum);
							$(this).find('.product-price').html(product.sales);
							$(this).find('.product-total-price').html(parseInt(productNum) * parseInt(product.sales));
							$(this).find('.product-remark').html(product.remark);

							$(this).addClass('haveData');
						}

					}
					
				});
			}

			$.post(url , data , callback , 'json');
		});
	}

	var addTr = function(){
		$('.btn-add-tr').bind('click' , function(){
			var html = shareTmp('addTr' , $('.table > tbody').find('tr'));
			$('.table').append(html);
		});

		$('.page-content').on('click' , '.icon-two' , function(){
			$(this).parent().parent().remove();
			$('.table > tbody').find('tr').each(function(idx){
				$(this).find('.product-No').html(idx + 1);
			});
		});
	}

	var save = function(){
		$('.purchase_confirm').bind('click' , function(){
			
			var productIds = new Array();
			var productNums = new Array();

			$($('.product-data').find('.product-id')).each(function(){
				var productId = $(this).html();
				if(productId){
					productIds.push(productId);
				}
			});

			$($('.product-data').find('.product-num')).each(function(){
				var productNum = $(this).html();
				if(productNum){
					productNums.push(productNum);
				}
			});

			var url = '/goods/purchase_add';
			var data = {
				productIds	: productIds,
				productNums : productNums,
				is_save		: 1
			};

			var callback = function(obj){
				window.location.reload();
			}

			$.post(url , data , callback , 'json');
		});
	}

	exports.dialogProduct = dialogProduct;
	exports.changeNum = changeNum;
	exports.getProduct = getProduct;
	exports.addTr = addTr;
	exports.save = save;

});
	

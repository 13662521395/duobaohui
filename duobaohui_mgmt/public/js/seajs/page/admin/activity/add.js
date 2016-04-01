define(function(require , exports , module) {
	/*
		后台管理 活动添加页面JS

	 */
	var $			= require('jquery');
	var artTemplate	= require('artTemplate');
	var layer = require('../../../app/layer');

	var init = function() {
		var addForm = $('#addForm')

		//产品分类变化，则商品变化
		addForm.on('change','select[name="category_id"]',function() {

			var _this = $(this)
			var goodsSelect = $('#goods_id')
			var selected = $(this).find('option:selected').attr('value')
			if(!selected) {
				renderSelect(goodsSelect)
			}
			var param = {
				'category_id':selected
			}
			$.ajax({
				url			: '/admin/activity/goods-by-category',
				type		: 'GET',
				dataType	: 'json',
				data:param
			})
			.done(function(data){
				renderSelect(goodsSelect,data)
			})
			.fail(function(){

			});

		})

		var renderSelect = function(target,data) {
			console.log(target)
			if(data && data.length && data.length > 0) {
				target.empty()
				$(data).each(function(index,value) {

					var option = $('<option>').val(value.id).text(value.goods_name)
					target.append(option)
				})
			} else {
				target.empty()
				var option = $('<option>').text('请选择商品')
				target.append(option)
			}
		}
	}

	var wangyi = function(){
		$('#wangyi').bind('click' , function(){
			var goodsId = $(this).attr('goods_id');

			layer.createLayer();
			layer.banScroll();

			$.ajax({
				url			: '/admin/shaidan/shaidan-list-by-wangyi?goods_id='+goodsId,
				type		: 'GET',
				dataType	: 'json'
			})
			.done(function(data){
				if(data.code == 0){
					layer.allowScroll();
					layer.closeLayer();
				}
				artTemplate.compile('shaidan_with_wangyi' , data);	
				var html = artTemplate('shaidan_with_wangyi' , data);
				$('body').append(html);
			})
			.fail(function(){
			
			});
		});
	}

	var select = function(){
		$('body').on('click' , '#select-wangyi' , function(){
			var wangyiId	= $(this).attr('wangyi-id');
			var nickname	= $('.nickname_' + wangyiId).html();
			var title		= $('.title_' + wangyiId).html();
			var content		= $('.content_' + wangyiId).html();

			_close();

			$('#title').val(title);
			$('#content').val(content);

			$('#shaidan').attr('wangyi-id' , wangyiId);
		});
	}


	var _close = function(){

		

		$('#wangyiModal').remove();
		layer.allowScroll();
		layer.closeLayer();
	}

	$('body').on('click' , '.close' , function(){
		_close();
	});

	/*return function() {

		var data = {
			'name':'wuhui'
		}
		
		artTemplate.compile('test' , data);
		var html = 	artTemplate('test' , data);


		//$('body').html(html);
		console.log(html);
	}*/

	exports.wangyi = wangyi;
	exports.select = select;
	exports.init = init;
});

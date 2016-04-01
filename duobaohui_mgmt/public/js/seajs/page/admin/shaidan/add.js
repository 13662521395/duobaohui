define(function(require , exports , module) {
	var $			= require('jquery');
	var artTemplate	= require('artTemplate');
	var layer = require('../../../app/layer');

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
				}else{
					artTemplate.compile('shaidan_with_wangyi' , data);	
					var html = artTemplate('shaidan_with_wangyi' , data);
					$('body').append(html);
				}
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

	var updateUserName	= function(){
		$('#user-name').bind('focus' , function(){
			$('#update-user-name').show();
		});

		$('#update-user-name').bind('click' , function(){
			var data = {
				'nick_name' : $('#user-name').val(),
				'user_id'	: $('#user-id').val()
			}
			$.ajax({
				url			: '/admin/user/update-user-nick-name',
				type		: 'post',
				dataType	: 'json',
				data		: data
			})
			.done(function(jsonData){
				if(jsonData.code == 1){
					artTemplate.compile('tip-success' , jsonData);	
					var html = artTemplate('tip-success' , jsonData);
					$('#tip').prepend(html);
				}else{
					artTemplate.compile('tip-error' , jsonData);	
					var html = artTemplate('tip-error' , jsonData);
					$('#tip').prepend(html);
					
				}
				setTimeout(function(){
					$('.tip-information').remove();	
				} , 1000);
			})
			.fail(function(){
				var jsonData = {
					'msg': '请求错误'
				}
				artTemplate.compile('tip-error' , jsonData);	
				var html = artTemplate('tip-error' , jsonData);
				$('#tip').prepend(html);
				setTimeout(function(){
					$('.tip-information').remove();	
				} , 1000);
			});
		});
	}			

	exports.wangyi = wangyi;
	exports.select = select;
	exports.updateUserName	= updateUserName;
});

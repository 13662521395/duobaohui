define(function(require , exports , module) {
	/*
		后台管理 活动列表页面JS

	 */
	var $			= require('jquery');
	var artTemplate	= require('artTemplate');
	var layer = require('../../../app/layer');

	var init = function() {

		//修改活动按钮
		$('body').on('click','button[action="updateActivity"]',function(event) {
			var target = event.target
			var activity_id = $(target).attr('activity_id')
			location.href = '/admin/activity/update-pre?activity_id=' + activity_id
		})

		//新增一期按钮
		$('body').on('click','button[action="addPeriod"]',function(event) {
			var target = event.target
			var activity_id = $(target).attr('activity_id')
			var param = {
				'activity_id':activity_id
			}
			$.ajax({
				url			: '/admin/activity/create-period',
				type		: 'post',
				dataType	: 'json',
				data:param
			})
			.done(function(data){
				var param = {
					msg:data.message
				}
				showMessage(param)
			})
			.fail(function(){

			});
		})
		//查看期列表
		$('body').on('click','button[action="lookPeriod"]',function(event) {
			var target = event.target
			var activity_id = $(target).attr('activity_id')
			location.href = '/admin/activity/period-list?activity_id=' + activity_id
		})

		//上/下线活动
		$('body').on('click','button[action="doOnLine"]',function(event) {
			var target = event.target
			var activity_id = $(target).attr('activity_id')
			var param = {
				'activity_id':activity_id,
				'is_online':$(target).attr('flag')
			}
			$.post('/admin/activity/update-online',param,'json')
				.success(function(data) {
					showMessage(data)
					setTimeout(function() {
						location.href = location.href
					},2000)
				})
				.fail(function(err) {
					console.log(err)
				})
		})

		//改为手动/自动
		$('body').on('click','button[action="doAuto"]',function(event) {
			var target = event.target
			var activity_id = $(target).attr('activity_id')
			var param = {
				'activity_id':activity_id,
				'is_auto':$(target).attr('flag')
			}
			$.post('/admin/activity/update-auto',param,'json')
				.success(function(data) {

					showMessage(data)
					setTimeout(function() {
						location.href = location.href
					},2000)
				})
				.fail(function(err) {
					console.log(err)
				})
		})

		var showMessage = function(param) {
			var html = artTemplate('tip-error',param);
			$('#_msg').html(html)
		}
	}


	exports.init = init;
});

define(function(require , exports , module) {
	/*
		后台管理 刷单设置JS

	 */
	var $			= require('jquery');
	var artTemplate	= require('artTemplate');
	var layer = require('../../../app/layer');

	var init = function() {

		$('body').on('click','button[id="queryIt"]',function(event) {
			var target = event.target
			var need_times_min = $('#need_times_min').val();
			var need_times_max = $('#need_times_max').val();
			var activity_name = $('#activity_name').val();
			var sh_autopay_main_setting_id = $('#sh_autopay_main_setting_id').val();
			var url = '/admin/setting/activity-setting-list-pre?1=1';
			if(sh_autopay_main_setting_id != undefined){
				url = url + '&sh_autopay_main_setting_id=' + sh_autopay_main_setting_id;
			}
			if(need_times_min != undefined){
				url = url + '&need_times_min=' + need_times_min;
			}
			if(need_times_max != undefined){
				url = url + '&need_times_max=' + need_times_max;
			}
			if(activity_name != undefined && activity_name != ''){
				url = url + '&activity_name=' + activity_name;
			}
			location.href = url;
		})

		$('body').on('click','button[id="reset"]',function(event) {
			$('#need_times_min').val("");
			$('#need_times_max').val("");
			$('#activity_name').val("");

		})

		//加入方案按钮
		$('body').on('click','button[action="add-activity-setting"]',function(event) {
			var target = event.target;
			var sh_autopay_main_setting_id = $(target).attr('sh_autopay_main_setting_id');
			var sh_activity_id = $(target).attr('sh_activity_id');
			var param = {
				'sh_autopay_main_setting_id':sh_autopay_main_setting_id,
				'sh_activity_id':sh_activity_id
			}
			$.ajax({
				url			: '/admin/setting/add-activity-setting',
				type		: 'post',
				dataType	: 'json',
				data:param
			})
			.done(function(data){
				var param = {
					msg:data.message
				}
				showMessage(param);
				setTimeout(function() {
					location.href = location.href
				},1000)
			})
			.fail(function(){

			});
		})

		var showMessage = function(param) {
			var html = artTemplate('tip-error',param);
			$('#_msg').html(html)
		}
	};

	//删除方案按钮
	$('body').on('click','button[action="delete-activity-setting"]',function(event) {
		var target = event.target;
		var sh_autopay_activity_setting_id = $(target).attr('sh_autopay_activity_setting_id');
		var param = {
			'sh_autopay_activity_setting_id':sh_autopay_activity_setting_id
		}
		$.ajax({
			url			: '/admin/setting/delete-activity-setting',
			type		: 'post',
			dataType	: 'json',
			data:param
		})
			.done(function(data){
				var param = {
					msg:data.message
				}
				showMessage(param);
				setTimeout(function() {
					location.href = location.href
				},1000)
			})
			.fail(function(){

			});
	});

	var showMessage = function(param) {
		var html = artTemplate('tip-error',param);
		$('#_msg').html(html)
	}


	exports.init = init;
});

<?php

/*
 * by zhangtaichao
 * 左侧菜单的激活配置
 *
 * 配置渲染 某个页面时 所带的参数
 * @see menu.blade.php
 */
use Illuminate\Support\Facades\View;

View::composer(array(
	'admin.activity.list',
	'admin.activity.updatePre',

), function($view)
{
	$view->with('selected', 'activity-list-pre');
	$view->with('parentMenu', 'activity');
});


View::composer(array(
	'admin.activity.add',
	'admin.activity.addResult',
	'admin.activity.periodList',

), function($view)
{
	$view->with('selected', 'activity-add-activity');
	$view->with('parentMenu', 'activity');
});
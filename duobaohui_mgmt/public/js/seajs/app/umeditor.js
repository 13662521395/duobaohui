define(function(require , exports , module) {
	var $ = require('jquery');

	require('modules/umeditor1_2_2-utf8-php/umeditor.config.js');
	require('modules/umeditor1_2_2-utf8-php/umeditor.js');
	require('modules/umeditor1_2_2-utf8-php/lang/zh-cn/zh-cn.js');

	return function(){
		var um = UM.getEditor('myEditor');	
	}

});

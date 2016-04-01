frm.define('page/register' , ['component/shareTmp', 'component/dialog' , 'jquery'] , function(require , exports){
	var shareTmp = require('component/shareTmp');
	var $ = require('jquery');
	var dialog = require('component/dialog');

	var closeDialog;

	return function(){
		$('.dialogCompany').bind('focus' , function(){
			var url = '/user/out_interface';
			var data = {
				call : 'dialogCompany'
			};
			var callback = function(obj){
				if(!obj.company){
					return false;
				}
				var html = shareTmp('JS_dialog_company' , obj);

				closeDialog = dialog.meiliDialog({
					dialogWidth:520,
					dialogTitle:'公司列表',
					isOverflow:true,
					dialogContent:html
				});
			}

			$.post(url  , data , callback , 'json');
		});

		$('body').on('click' , '#cancel' ,  function(){
			closeDialog();
		});

		$('body').on('click' , '#confirm' ,  function(){
			var companyId = $('input:radio:checked').val();
			var companyName = $('#company-name-'+companyId).html();
			$('#companyId').val(companyId);
			$('#companyName').val(companyName);
			closeDialog();
		});
	}	
	

});

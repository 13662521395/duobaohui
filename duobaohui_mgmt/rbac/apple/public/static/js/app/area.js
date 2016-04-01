frm.define('app/area' , ['jquery'] , function(require , exports){
	var $			= require('jquery');
	var area = function(){

		$('.combox').bind("change" , function(){

			var _this = this;
			
			var num = -1;

			//清空后面的城市内容
			$('.combox').each(function(index ,obj){
				if(obj == _this){
					num = index;
				}
				if(num != -1){
					if(index > num){
						$(obj).html('')
					}
				}
			});
			var province = $(_this).val();
			if(province == 0){
				$('.selector').each(function(idx , ele){

					if($(ele).attr('ref') == $(_this).attr('id')){
						province = $(ele).val();
					}
				});
			}
			//选择地域以后出现该地域的子地域信息
			var ref = $(_this).attr('ref');
			var type = 1;
			if($(_this).attr('name') == 'area_county'){
				type = 2;
			}

			var url="/area/ajax_area";
			var data = {
				province :province,
				type:type
			};
			var ajaxType="json";
			var callback = function(json){
					var obj = json.area;
					// 省市县
					if(!obj.length){
						return false;
					}
					var option = '';
					for(var i = 0 ; i < obj.length ; i ++){
						option += '<option value='+obj[i][0]+'>'+obj[i][1]+'</option>';
					}
					$('#'+ref).html(option);
			}
			$.post(url , data , callback , ajaxType);
		});
	}

	exports.area = area;

});



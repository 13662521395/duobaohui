define(function(require , exports , module) {
	var $			= require('jquery');
	var uploadImg	= require('../../../app/upload_img');
	
	var uploadImg = function(){
		$('#goods_img').bind('change' , function(){
			var imgPath = $(this).val();	

			if(imgPath == ''){
				alert('请选择上传图片');
				return;
			}

			var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);

			if (strExtension != 'jpg' && strExtension != 'gif'
					&& strExtension != 'png' && strExtension != 'bmp') {
				alert("请选择图片文件");
				return;
			}

			$.ajax({
				type: "POST",
				url: '/admin/goods/upload-img-to-local',
				data: { imgPath: imgPath },
				cache: false,
				success: function(data) {
					if(data.code == 0){
						alert("上传失败，请检查网络后重试");
						return;
					}else{
						return data.url;
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("上传失败，请检查网络后重试");
					return;
				}
			});
			uploadImg('#goods_img' , '/admin/goods/upload-img-local');
		});	
	}

	exports.uploadImg	= uploadImg;
});

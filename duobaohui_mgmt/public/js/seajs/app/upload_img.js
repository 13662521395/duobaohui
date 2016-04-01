define(function(require , exports , module) {
	var $ = require('jquery');

	return function(key , url){
		var imgPath = $(key).val();	
		conosle.log(imgPath);

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
			url: url,
			data: { imgPath: imgPath },
			enctype: 'multipart/form-data',
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

	}

});

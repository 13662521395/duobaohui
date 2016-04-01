{{ HTML::script('/js/qiniu-js-sdk/demo/js/plupload/plupload.full.min.js') }}
{{ HTML::script('/js/qiniu-js-sdk/demo/js/plupload/i18n/zh_CN.js') }}
{{ HTML::script('/js/qiniu-js-sdk/demo/js/ui.js') }}
{{ HTML::script('/js/qiniu-js-sdk/demo/js/qiniu.js') }}
{{ HTML::script('/js/qiniu-js-sdk/demo/js/highlight/highlight.js') }}

<div id="container">
	<a class="btn btn-default btn-lg " id="pullfiles" href="#" >
		<i class="glyphicon glyphicon-plus"></i>
		<sapn>选择文件</sapn>
	</a>
</div>
<div style="display:none" id="success" class="col-md-12">
	<div class="alert-success">
		队列全部文件处理完毕
	</div>
</div>
<div class="col-md-12 ">
	<table class="table table-striped table-hover text-left"   style="display:none">
		<thead>
		  <tr>
			<th class="col-md-4">Filename</th>
			<th class="col-md-2">Size</th>
			<th class="col-md-6">Detail</th>
		  </tr>
		</thead>
		<tbody id="fsUploadProgress">
		</tbody>
	</table>
</div>

<div id="shaidan_img" style="display:none">
</div>

<script>
	
	var uploader = Qiniu.uploader({
        runtimes: 'html5,flash,html4',
		browse_button: 'pullfiles',       //上传选择的点选按钮，**必需**
        container: 'container',
        drop_element: 'container',
        max_file_size: '100mb',
		flash_swf_url: '/js/qiniu-js-sdk/plupload/Moxie.swf',  //引入flash,相对路径
        dragdrop: true,
        chunk_size: '4mb',
		uptoken_url: '/admin/shaidan/js-qiniu-token?image=imgs',            //Ajax请求upToken的Url，**强烈建议设置**（服务端提供）
		domain: 'http://7xlbf0.com1.z0.glb.clouddn.com/',   //bucket 域名，下载资源时用到，**必需**
        get_new_uptoken: false,
        // downtoken_url: '/downtoken',
        // unique_names: true,
        // save_key: true,
        // x_vars: {
        //     'id': '1234',
        //     'time': function(up, file) {
        //         var time = (new Date()).getTime();
        //         // do something with 'time'
        //         return time;
        //     },
        // },
        auto_start: true,
        init: {
            'FilesAdded': function(up, files) {
                $('table').show();
                $('#success').hide();
                plupload.each(files, function(file) {
                    var progress = new FileProgress(file, 'fsUploadProgress');
                    progress.setStatus("等待...");
                    progress.bindUploadCancel(up);
                });
            },
            'BeforeUpload': function(up, file) {
                var progress = new FileProgress(file, 'fsUploadProgress');
                var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                if (up.runtime === 'html5' && chunk_size) {
                    progress.setChunkProgess(chunk_size);
                }
            },
            'UploadProgress': function(up, file) {
                var progress = new FileProgress(file, 'fsUploadProgress');
                var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                progress.setProgress(file.percent + "%", file.speed, chunk_size);
            },
            'UploadComplete': function() {
                $('#success').show();
            },
            'FileUploaded': function(up, file, info) {
                var progress = new FileProgress(file, 'fsUploadProgress');
                progress.setComplete(up, info);
				var domain = up.getOption('domain');
				var res = $.parseJSON(info);
				var sourceLink = domain + res.key; //获取上传成功后的文件的Url

				var input = "<input type='hidden' name='img_url[]' value='"+sourceLink+"' />";
				$('#shaidan_img').append(input);
            },
            'Error': function(up, err, errTip) {
                $('table').show();
                var progress = new FileProgress(err.file, 'fsUploadProgress');
                progress.setError();
                progress.setStatus(errTip);
            },
			'Key': function(up, file) {
				// 若想在前端对每个文件的key进行个性化处理，可以配置该函数
				// 该配置必须要在 unique_names: false , save_key: false 时才生效

				function exten(file_name){
					var result =/\.[^\.]+/.exec(file_name);
					return result[0];
				}
				var key = file.id + exten(file.name);
				// do something with key here
				return key
			}
                // ,
                // 'Key': function(up, file) {
                //     var key = "";
                //     // do something with key
                //     return key
                // }
        }
    });

    uploader.bind('FileUploaded', function() {
        console.log('hello man,a file is uploaded');
    });
    $('#container').on(
        'dragenter',
        function(e) {
            e.preventDefault();
            $('#container').addClass('draging');
            e.stopPropagation();
        }
    ).on('drop', function(e) {
        e.preventDefault();
        $('#container').removeClass('draging');
        e.stopPropagation();
    }).on('dragleave', function(e) {
        e.preventDefault();
        $('#container').removeClass('draging');
        e.stopPropagation();
    }).on('dragover', function(e) {
        e.preventDefault();
        $('#container').addClass('draging');
        e.stopPropagation();
    });



    $('#show_code').on('click', function() {
        $('#myModal-code').modal();
        $('pre code').each(function(i, e) {
            hljs.highlightBlock(e);
        });
    });


    $('body').on('click', 'table button.btn', function() {
        $(this).parents('tr').next().toggle();
    });


    var getRotate = function(url) {
        if (!url) {
            return 0;
        }
        var arr = url.split('/');
        for (var i = 0, len = arr.length; i < len; i++) {
            if (arr[i] === 'rotate') {
                return parseInt(arr[i + 1], 10);
            }
        }
        return 0;
    };

    $('#myModal-img .modal-body-footer').find('a').on('click', function() {
        var img = $('#myModal-img').find('.modal-body img');
        var key = img.data('key');
        var oldUrl = img.attr('src');
        var originHeight = parseInt(img.data('h'), 10);
        var fopArr = [];
        var rotate = getRotate(oldUrl);
        if (!$(this).hasClass('no-disable-click')) {
            $(this).addClass('disabled').siblings().removeClass('disabled');
            if ($(this).data('imagemogr') !== 'no-rotate') {
                fopArr.push({
                    'fop': 'imageMogr2',
                    'auto-orient': true,
                    'strip': true,
                    'rotate': rotate,
                    'format': 'png'
                });
            }
        } else {
            $(this).siblings().removeClass('disabled');
            var imageMogr = $(this).data('imagemogr');
            if (imageMogr === 'left') {
                rotate = rotate - 90 < 0 ? rotate + 270 : rotate - 90;
            } else if (imageMogr === 'right') {
                rotate = rotate + 90 > 360 ? rotate - 270 : rotate + 90;
            }
            fopArr.push({
                'fop': 'imageMogr2',
                'auto-orient': true,
                'strip': true,
                'rotate': rotate,
                'format': 'png'
            });
        }

        $('#myModal-img .modal-body-footer').find('a.disabled').each(function() {

            var watermark = $(this).data('watermark');
            var imageView = $(this).data('imageview');
            var imageMogr = $(this).data('imagemogr');

            if (watermark) {
                fopArr.push({
                    fop: 'watermark',
                    mode: 1,
                    image: 'http://www.b1.qiniudn.com/images/logo-2.png',
                    dissolve: 100,
                    gravity: watermark,
                    dx: 100,
                    dy: 100
                });
            }

            if (imageView) {
                var height;
                switch (imageView) {
                    case 'large':
                        height = originHeight;
                        break;
                    case 'middle':
                        height = originHeight * 0.5;
                        break;
                    case 'small':
                        height = originHeight * 0.1;
                        break;
                    default:
                        height = originHeight;
                        break;
                }
                fopArr.push({
                    fop: 'imageView2',
                    mode: 3,
                    h: parseInt(height, 10),
                    q: 100,
                    format: 'png'
                });
            }

            if (imageMogr === 'no-rotate') {
                fopArr.push({
                    'fop': 'imageMogr2',
                    'auto-orient': true,
                    'strip': true,
                    'rotate': 0,
                    'format': 'png'
                });
            }
        });

        var newUrl = Qiniu.pipeline(fopArr, key);

        var newImg = new Image();
        img.attr('src', 'loading.gif');
        newImg.onload = function() {
            img.attr('src', newUrl);
            img.parent('a').attr('href', newUrl);
        };
        newImg.src = newUrl;
        return false;
    });

</script>

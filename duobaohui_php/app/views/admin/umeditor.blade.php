{{ HTML::style('/js/umeditor1_2_2-utf8-php/themes/default/css/umeditor.css') }}
{{ HTML::script('/js/umeditor1_2_2-utf8-php/umeditor.config.js') }}
{{ HTML::script('/js/umeditor1_2_2-utf8-php/umeditor.min.js') }}
{{ HTML::script('/js/umeditor1_2_2-utf8-php/lang/zh-cn/zh-cn.js') }}

<script type="text/plain" id="description" name="description" style="width:100%;height:240px;">
{{ $content }}
</script>

<script type='text/javascript'>
	var um = UM.getEditor('description' , {
		imageUrl			: '/admin/goods/umeditor-img',
	});
	
</script>

@include('admin.header')

@include('admin.menu')
{{ HTML::script('/js/laydate/laydate.js') }}

<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<ol class="breadcrumb">
			<li class="active">编辑welcome图片</li>
		</ol>
		<section class="panel clearfix" style="margin-bottom:42px">
			<header class="panel-heading">编辑welcome图片</header>
			<div class="panel-body">
				<form class="form-horizontal tasi-form">
					<!-- banner_id -->
					<input type="hidden" id="banner_id" value="{{ $banner->id }}">
					<!-- title -->
					<div class="form-group">
						<label for="banner_title" class="col-sm-2 control-label">标题</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="banner_title" placeholder="请输入标题(限255字)" maxlength="255" value="{{ $banner->title }}">
						</div>
					</div>
					<!-- link_url -->
					<div class="form-group">
						<label for="banner_link_url" class="col-sm-2 control-label">点击跳转链接</label>
						<div class="col-sm-10">
							<textarea class="form-control" id="banner_link_url" placeholder="请输入跳转链接(限255字)" maxlength="255">{{ $banner->link_url }}</textarea>
						</div>
					</div>
					<!-- pic_url -->
					<div class="form-group">
						<label for="banner_pic_file" class="col-sm-2 control-label">上传图片</label>
						<div class="col-sm-3">
							<div>原图:</div>
							<img src="{{ $banner->pic_url }}" id="banner_original_pic" alt="原图" style="height:120px">
						</div>
						<div class="col-sm-7">@include('admin.qiniu_js_upload_img')</div>
					</div>
					<!-- type -->
							<input type="hidden" id="banner_type" value="2"/>
					<!-- start_time -->
					<div class="form-group">
						<label for="banner_start_time" class="col-sm-2 control-label">开始时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control datetimepicker" id="banner_start_time" placeholder="请选择开始时间" maxlength="19" value="{{ $banner->start_time }}" readonly>
						</div>
					</div>
					<!-- end_time -->
					<div class="form-group">
						<label for="banner_end_time" class="col-sm-2 control-label">结束时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control datetimepicker" id="banner_end_time" placeholder="请选择结束时间" maxlength="19" value="{{ $banner->end_time }}" readonly>
						</div>
					</div>
					<!-- sort -->
					<div class="form-group">
						<label for="banner_sort" class="col-sm-2 control-label">排列顺序</label>
						<div class="col-sm-10">
							<input type="number" class="form-control" id="banner_sort" placeholder="从小到大排序(限10位整数)" maxlength="10" value="{{ $banner->sort }}">
						</div>
					</div>
				</form>
				<div class="clearfix" style="margin-top:20px">
					<button class="btn btn-shadow btn-success pull-right" id="banner_add_submit_btn" style="margin-right:10px"><i class="icon-ok"></i>&nbsp;提交</button>
				</div>
			</div>
		</section>
	</section>
</section>
<!--main content end-->
<style>
.site-footer {position:fixed;bottom:0px;}
</style>
<script>
//初始化
$('#banner_type').val('{{ $banner->type }}');
//////
laydate({
	elem: '#banner_start_time',
    format: 'YYYY-MM-DD hh:mm:ss',
    istime: true
});

laydate({
	elem: '#banner_end_time',
    format: 'YYYY-MM-DD hh:mm:ss',
    istime: true
});

$('#banner_add_submit_btn').unbind();
$('#banner_add_submit_btn').click(function() {
	var banner_id  = $('#banner_id').val();
	var title      = $('#banner_title').val();
	var link_url   = $('#banner_link_url').val();
	var pic_url    = $('#shaidan_img input').val() ? $('#shaidan_img input').val() : $('#banner_original_pic').attr('src');
	var type       = $('#banner_type').val();
	var start_time = $('#banner_start_time').val();
	var end_time   = $('#banner_end_time').val();
	var sort       = $('#banner_sort').val();

	if (banner_id == '') {alert('【banner_id】不能为空');return;}
	if (title == '')     {alert('【标题】不能为空');return;}
	if (link_url == '')  {alert('【跳转链接】不能为空');return;}
	if (pic_url == '')   {alert('【图】不能为空');return;}
	if (type == '')      {alert('【分类】不能为空');return;}
	
	$.post('/admin/banner/edit', {
		banner_id  :banner_id,
		title      :title,
		link_url   :link_url,
		pic_url    :pic_url,
		type       :type,
		start_time :start_time,
		end_time   :end_time,
		sort       :sort
	}, function(data) {
		if (data.code == '1') location.href = "/admin/banner/welcome";
		else {
			alert('编辑失败');
		}
	});
});
</script>
@include('admin.footer')


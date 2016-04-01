@include('admin.header')

@include('admin.menu')


<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class='row'>
			<div class='col-lg-12'>
				<section class="panel">
					<header class="panel-heading clearfix">
						Banner
						<a href="/admin/banner/add" class="btn btn-danger add_banner pull-right"><i class="icon-plus"></i>&nbsp;新增</a>
					</header>
					<table class="table table-striped table-advance table-hover">
						<thead>
							<tr>
								<th>ID</th>
								<th>标题</th>
								<th>链接地址</th>
								<th>图片</th>
								<th>分类</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>排序</th>
							</tr>
						</thead>
						<tbody>
							@foreach ($banner_list as $lv)
							<tr>
								<td><a href="javascript:void(0)">{{ $lv->id }}</a></td>
								<td>{{ $lv->title }}</td>
								<td>{{ $lv->link_url }}</td>
								<td><img src="{{ $lv->pic_url }}" alt="Banner图" style="height:50px"></td>
								<td>@if($lv->type==0) 移动端跳转@elseif($lv->type==1) web页面跳转 @elseif($lv->type==3) 支付页跳转 @endif</td>
								<td>{{ $lv->start_time }}</td>
								<td>{{ $lv->end_time }}</td>
								<td>{{ $lv->sort }}</td>
								<td>
									<a href="/admin/banner/edit?banner_id={{ $lv->id }}" class="btn btn-primary btn-xs"><i class="icon-pencil"></i></a>
									<button class="btn btn-danger btn-xs  del_banner" bannerid="{{ $lv->id }}"><i class="icon-trash"></i></button>
				  				</td>
							</tr>
							@endforeach
						</tbody>
					</table>
				</section>
			</div>
		</div>
	</section>
</section>
<!--main content end-->
<style>
.site-footer {position:fixed;bottom:0px;}
</style>
<script>
$('body').on('click', '.del_banner', function(){
	var banner_id = $(this).attr('bannerid');
	if(confirm('是否要删除【id = ' + banner_id + '】的Banner?')) {
		$.post('/admin/banner/del', { banner_id:banner_id }, function(data) {
			if (data.code == '1') location.replace('/admin/banner/index');
			else {
				alert('删除失败');
				console.log(data);
			}
		});
	}
});
</script>
@include('admin.footer')


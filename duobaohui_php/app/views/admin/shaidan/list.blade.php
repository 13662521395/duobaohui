@include('admin.header')

@include('admin.menu')

<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class='row'>
			<div class='col-lg-12'>
				<section class="panel">
					<header class="panel-heading">
						晒单列表
					</header>
					
					@if ($list)
					<table class="table table-striped table-advance table-hover">
						<thead>
							<tr>
								<th >ID</th>
								<th >用户昵称</th>
								<th >晒单标题</th>
								<th >晒单内容</th>
								<th >晒单的图片(前三张)</th>
								<th >订单号</th>
								<th >商品名称</th>
								<th >商品图片</th>
								<th >中奖时间</th>
								<th >操作</th>
							</tr>
						</thead>
						<tbody>
							@foreach ($list as $lv)
							<tr>
								<td><a href="#">{{ $lv->id }}</a></td>
								<td>{{ $lv->nick_name }}</td>
								<td>{{ $lv->title }}</td>
								<td><div style="width:100px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis">{{{ $lv->content }}}</div></td>
								<td>
									<div style="width:150px;">
									@for ($i = 1 ; !empty($lv->img_url) && $i <= count($lv->img_url) && $i < 4 ; $i++)
									<img src="{{ $lv->img_url[$i - 1] }}" style="width:40px;height:50px;"/>
									@endfor
									</div>
								</td>
								<td>{{ $lv->order_sn }}</td>
								<td><div style="width:100px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis">{{ $lv->goods_name }}</div></td>
								<td><img src="{{ $lv->goods_img }}" height=50 /></td>
								<td>{{ $lv->luck_code_create_time }}</td>
								<td>
									<a href="/admin/shaidan/edit-shaidan?shaidan_id={{ $lv->id }}" title="修改信息" class="btn btn-primary btn-xs"><i class="icon-pencil"></i></a>
									<button shaidan_id="{{ $lv->id }}" title="删除记录" class="btn btn-danger btn-xs delete-shaidan"><i class="icon-trash "></i></button>
								</td>
							</tr>
							@endforeach
						</tbody>
					</table>
					@include('admin.page')	
					@else
						<div class="alert alert-warning fade in">
							<button data-dismiss="alert" class="close close-sm" type="button">
								<i class="icon-remove"></i>
							</button>
							没有晒单内容
						</div>
					@endif
				</div>
			</div>
		</section>
	</section>
</section>
<!--main content end-->

<script>
seajs.use('page/admin/shaidan/list' , function(mod){
	mod.deleteShaidan();	
});

$('.delete-shaidan').bind('click' , function(){
	var _this = this;
	var shaidanId = $(_this).attr('shaidan_id');

	$.ajax({
		type:'GET',
		dataType:'json',
		url:'/admin/shaidan/delete-shaidan',
		data:{
			shaidan_id:shaidanId
		}
	})
	.done(function(data){
		$(_this).parent().parent().remove();
	})
	.fail(function(data){
	});
});
</script>

@include('admin.footer')


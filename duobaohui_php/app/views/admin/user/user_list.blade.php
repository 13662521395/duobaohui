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
								<th >手机号</th>
								<th >是否锁定</th>
								<th >是否删除</th>
								<th >余额</th>
								<th >头像</th>
								<th >年龄</th>
								<th >性别</th>
								<th >生日</th>
								<th >创建时间</th>
								<th >操作</th>
							</tr>
						</thead>
						<tbody>
							@foreach ($list as $lv)
							<tr>
								<td><a href="#">{{ $lv->id }}</a></td>
								<td>{{ $lv->nick_name }}</td>
								<td>{{ $lv->tel }}</td>
								<td>@if ($lv->locked === 0) 否  @else 是  @endif</td>
								<td>@if ($lv->is_delete === 0) 否  @else 是  @endif</td>
								<td>{{ $lv->money }}</td>
								<td><img src="{{ $lv->head_pic }}" height=50 /></td>
								<td>@if ($lv->age == NULL) 0  @else {{ $lv->age }}  @endif</td>
								<td>@if ($lv->sex == 0) 女  @else 男  @endif</td>
								<td>@if ($lv->born == NULL) 0000:00:00  @else {{ $lv->born }}  @endif</td>
								<td>{{ date('Y-m-d H:i:s',$lv->create_time) }}</td>
								<td>
									<a href="#" title="修改信息" class="btn btn-primary btn-xs"><i class="icon-pencil"></i></a>
									<button user_id="{{ $lv->id }}" title="删除记录" class="btn btn-danger btn-xs delete-user"><i class="icon-trash "></i></button>
								</td>
							</tr>
							@endforeach
						</tbody>
					</table>
					<ul class="pagination pagination-lg">
						<li><a href="?page=">«</a></li>
						<li><a href="?page=">»</a></li>
					</ul>
					@endif
				</div>
			</div>
		</section>
	</section>
</section>
<!--main content end-->
<script>
seajs.use('page/admin/user/list' , function(mod){
	mod.user();	
});

</script>

@include('admin.footer')


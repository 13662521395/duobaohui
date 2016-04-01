@include('admin.header')

@include('admin.menu')

<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class='row'>
			<div class='col-lg-12'>
				<section class="panel">
					<header class="panel-heading">
						管理员列表
						<a type="button" href='/admin/authority/add-authority-user' class="btn btn-success" style='margin-left:20px;' >添加管理员</a>
					</header>
					
					@if ($list)
					<table class="table table-striped table-advance table-hover">
						<thead>
							<tr>
								<th >ID</th>
								<th >用户名</th>
								<th >手机号</th>
								<th >操作</th>
							</tr>
						</thead>
						<tbody>
							@foreach ($list as $lv)
							<tr>
								<td><a href="#">{{ $lv->id }}</a></td>
								<td>{{ $lv->realname }}</td>
								<td>{{ $lv->tel }}</td>
								<td>
									<a href="/admin/authority/edit-authority-user?user_id={{ $lv->id }}" title="修改信息" class="btn btn-primary btn-xs"><i class="icon-pencil"></i></a>
									<button authority_user_id="{{ $lv->id }}" title="删除节点" class="btn btn-danger btn-xs delete-shaidan"><i class="icon-trash "></i></button>
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
							没有管理员
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


</script>

@include('admin.footer')


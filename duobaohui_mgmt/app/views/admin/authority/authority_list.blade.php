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
						<a type="button" href='/admin/authority/add-authority' class="btn btn-success" style='margin-left:20px;' >添加权限节点</a>
					</header>
					
					@if ($list)
					<table class="table table-striped table-advance table-hover">
						<thead>
							<tr>
								<th >ID</th>
								<th >访问路径</th>
								<th >备注</th>
								<th >操作</th>
							</tr>
						</thead>
						<tbody>
							@foreach ($list as $lv)
							<tr>
								<td><a href="#">{{ $lv->id }}</a></td>
								<td>{{ $lv->url }}</td>
								<td>{{ $lv->url_notes }}</td>
								<td>
									<a href="/admin/authority/edit-authority?authority_id={{ $lv->id }}" title="修改信息" class="btn btn-primary btn-xs"><i class="icon-pencil"></i></a>
									<a href="/admin/authority/delete-authority?authority_id={{ $lv->id }}?page={{ $pageInfo->page }}" authority_id="{{ $lv->id }}" title="删除节点" class="btn btn-danger btn-xs delete-shaidan"><i class="icon-trash "></i></a>
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
							没有权限列表
						</div>
					@endif
				</div>
			</div>
		</section>
	</section>
</section>
<!--main content end-->


</script>

@include('admin.footer')


@include('admin.header')

@include('admin.menu')

<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class='row'>
			<div class='col-lg-12'>
				<section class="panel">
					<header class="panel-heading">
						中奖用户(虚拟用户)
					</header>
					<table class="table table-striped table-advance table-hover">
						<thead>
							<tr>
								<th >用户ID</th>
								<th >用户昵称</th>
								<th >中奖商品</th>
								<th >商品图片</th>
								<th >中奖时间</th>
								<th >订单号</th>
								<th >操作</th>
							</tr>
						</thead>
						<tbody>
							@foreach ($list as $lv)
							<tr>
								<td><a href="#">{{ $lv->id }}</a></td>
								<td>{{ $lv->nick_name }}</td>
								<td><div style="width:100px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis">{{ $lv->goods_name }}</div></td>
								<td><img src="{{ $lv->goods_img }}" height=50 /></td>
								<td>{{ $lv->luck_code_create_time }}</td>
								<td>{{ $lv->order_sn }}</td>
								<td>
									<a href="/admin/shaidan/add-shaidan?period_result_id={{ $lv->period_result_id }}" title="晒单" class="btn btn-primary btn-xs"><i class=" icon-camera"></i></a>
								</td>
							</tr>
							@endforeach
						</tbody>
					</table>
					@include('admin.page')	
				</div>
			</div>
		</section>
	</section>
</section>

<!--main content end-->
@include('admin.footer')


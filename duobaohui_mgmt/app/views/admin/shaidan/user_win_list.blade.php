@include('admin.header')

@include('admin.menu')

<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class='row'>
			<div class='col-lg-12'>
				<section class="panel">
					<header class="panel-heading">
						<form action='/admin/shaidan/win-user-list' method='get' style='float:right'>
							<div style='float:right'>
								<button type="submit" class="btn btn-shadow btn-success" id='search' style='float:right;margin:-5px 30px 0 8px;'>搜索</button>
							</div>

							@if(isset($start_time))
								<div class="input-append date dpYears" style='float:right;width:170px;margin:-5px 20px 0 10px;'>
									<input class="form-control form-control-inline input-append form_date" data-date-format="yyyy-mm-dd hh:ii:ss" data-date="{{$end_time}}" name="end_time" size="16" type="text" value="{{$end_time}}"  />
								</div>

								<div class="input-append date dpYears" style='float:right;width:170px;margin:-5px 20px 0 10px;'>
									<input class="form-control form-control-inline input-append form_date" data-date-format="yyyy-mm-dd hh:ii:ss" data-date="{{$start_time}}" name="start_time" size="16" type="text" value="{{$start_time}}"   />
								</div>
							@else
								<div class="input-append date dpYears" style='float:right;width:170px;margin:-5px 20px 0 10px;'>
									<input class="form-control form-control-inline input-append form_date" data-date-format="yyyy-mm-dd hh:ii:ss" data-date="{{$tomorow}}" name="end_time" size="16" type="text" value="{{$tomorow}}"  />
								</div>

								<div class="input-append date dpYears" style='float:right;width:170px;margin:-5px 20px 0 10px;'>
									<input class="form-control form-control-inline input-append form_date" data-date-format="yyyy-mm-dd hh:ii:ss" data-date="{{$now_date}}" name="start_time" size="16" type="text" value="{{$now_date}}"   />
								</div>
							@endif

							<div class="btn-group" style='float:right;'>
								中奖时间
							</div>
						</form>
						中奖用户(虚拟用户)
					</header>
					<br	/><br	/>
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
				@if(isset($start_time))
					{{$list->appends(array('start_time'=>$start_time,'end_time'=>$end_time))->links('admin.pageInfo')}}
				@else
					{{$list->links('admin.pageInfo')}}
				@endif

				</div>
			</div>
		</section>
	</section>
</section>

<!--main content end-->
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/sample/jquery/jquery-1.8.3.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="/flatlib/js/bootstrap-paginator.js"></script>
<script>
	$('.form_date').datetimepicker({
		language:  'fr',
		format:'yyyy-mm-dd hh:ii:ss',
		weekStart: 1,
		todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 1,
		minView: 1,
		forceParse: 0
	});

</script>
@include('admin.footer')


@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>

<!--main content start-->
<section id="main-content">
  	<section class="wrapper site-min-height">
		<div class="col-xs-12 panel">
			<div id="_msg"></div>
			<section class="panel">
				<header class="panel-heading">
					活动列表
				</header>
				<table class="table table-striped table-advance table-hover">
					<thead>
						<tr>
							<th>活动ID</th>
							<th>商品名称</th>
							<th>当前期数</th>
							<th>最大期数</th>
							<th>所需人次</th>
							<th>更新时间</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>是否开启</th>
							<th>是否自动</th>
							<th>是否热门</th>
							<th>操作人</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					@foreach($list->getItems() as $key=>$value)
						<tr>
							<td>{{$value->id}}</td>
							<td>{{$value->goods_name}}</td>
							<td>{{$value->current_period}}</td>
							<td>{{$value->max_period}}</td>
							<td>{{$value->need_times}}</td>
							<td>{{$value->update_time}}</td>
							<td>{{$value->begin_date}}</td>
							<td>{{$value->end_date}}</td>
							<td>
								@if($value->is_online == '1')
									开启
								@else
									关闭
								@endif
							</td>

							<td>
								@if($value->is_auto == '1')
									自动
								@else
									手动
								@endif
							</td>
							<td>
								@if($value->is_hot == '1')
									热门
								@else
									普通
								@endif
							</td>
							<td>{{$value->nickname}}</td>
							<td>


								<div class="btn-group">
									<button data-toggle="dropdown" class="btn btn-primary dropdown-toggle btn-sm" type="button">操作<span class="caret"></span></button>
									<ul role="menu" class="dropdown-menu">
										<li><button type="button" class="btn btn-default btn-sm" action="updateActivity" activity_id="{{$value->id}}">修改活动</button></li>
										<li>
											<button type="button" class="btn btn-default btn-sm" action="doOnLine" activity_id="{{$value->id}}"
													flag="{{$value->is_online == '1' ? '0' : '1'}}"
													>
												{{$value->is_online == '1' ? '下线活动' : '上线活动'}}
											</button>
										</li>
										<li>
											<button type="button" class="btn btn-default btn-sm" action="doAuto" activity_id="{{$value->id}}"
													flag="{{$value->is_auto == '1' ? '0' : '1'}}"
													>
												{{$value->is_auto == '1' ? '改为手动' : '改为自动'}}
											</button>
										</li>
										<li class="divider"></li>
										<li><button type="button" class="btn btn-info btn-sm" action="lookPeriod" activity_id="{{$value->id}}">活动期数</button></li>
										<li><button type="button" class="btn btn-info btn-sm" action="addPeriod" activity_id="{{$value->id}}">新增一期</button></li>

									</ul>
								</div>

							</td>
						</tr>
					@endforeach
					</tbody>
				</table>
				{{$list->links('admin.pageInfo')}}
			</section>
		</div>
  	</section>
</section>
<!--main content end-->
@include('admin.tip_information')
<script type="text/javascript">
	seajs.use('page/admin/activity/list' , function(mod){
		mod.init()
	});
</script>

@include('admin.footer')


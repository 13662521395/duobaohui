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
							<th>选择</th>
							<th>活动ID</th>
							<th>商品名称</th>
							<th>当前期数</th>
							<th>最大期数</th>
							<th>所需人次</th>
							<th>更新时间</th>
							<th>开始时间</th>
							<th>结束时间</th>
						</tr>
					</thead>
					<tbody>
					@foreach($list->getItems() as $key=>$value)
						<tr>
							<td>
								<label class="label_check" for="checkbox-01">
									<input name="setting_activity" id="checkbox-01" value="{{$value->id}}" type="checkbox" checked />
								</label>
							</td>
							<td>{{$value->id}}</td>
							<td>{{$value->goods_name}}</td>
							<td>{{$value->current_period}}</td>
							<td>{{$value->max_period}}</td>
							<td>{{$value->need_times}}</td>
							<td>{{$value->update_time}}</td>
							<td>{{$value->begin_date}}</td>
							<td>{{$value->end_date}}</td>
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


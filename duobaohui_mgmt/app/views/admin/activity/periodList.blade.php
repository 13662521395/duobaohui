@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>

<!--main content start-->
<section id="main-content">
  	<section class="wrapper site-min-height">
		<div class="col-xs-10">
			<div id="_msg"></div>
			<section class="panel">
				<header class="panel-heading">
					期信息
				</header>
					<table class="table table-striped table-advance table-hover">
						<thead>
							<tr>
								<th>期ID</th>
								<th>当前期数</th>
								<th>创建时间</th>
								<th>当前人次</th>
								<th>所需人次</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						@foreach($list->getItems() as $key=>$value)
							<tr>
								<td>{{$value->id}}</td>
								<td>{{$value->period_number}}</td>
								<td>{{$value->create_time}}</td>
								<td>{{$value->current_times}}</td>
								<td>{{$value->real_need_times}}</td>
								<td>{{$value->flag == '0' ? '已关闭' : '进行中'}}</td>
								<td>


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


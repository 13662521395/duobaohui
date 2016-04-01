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
					刷单方案列表
				</header>
				<table class="table table-striped table-advance table-hover">
					<thead>
						<tr>
							<th>方案名称</th>
							<th>购买频率</th>
							<th>单次购买人次</th>
							<th>购买几率分布</th>
							<th>购买速度</th>
							<th>操作</th>
							<th>编辑方案</th>
						</tr>
					</thead>
					<tbody>
					@foreach($list as $value)
						@if($value->sh_autopay_main_setting_id == '1')
						<tr>
							<td>{{$value->name}}</td>
							<td>{{$value->frequency}}秒/次</td>
							<td>{{$value->scope_and_rate}}</td>
							<td>各活动平均</td>
							<td>{{$value->speed}} 人次/小时</td>
							<td>
								@if($value->flag == '1')
									<a href="/admin/setting/control-auto-pay-setting?sh_autopay_main_setting_id={{$value->sh_autopay_main_setting_id}}&flag=0" class="btn btn-primary btn-xs">开启</a>
								@else
									<a href="/admin/setting/control-auto-pay-setting?sh_autopay_main_setting_id={{$value->sh_autopay_main_setting_id}}&flag=1" class="btn btn-danger btn-xs">关闭</a>
								@endif
							</td>
							<td><a href="/admin/setting/auto-pay-setting-detail?sh_autopay_main_setting_id={{$value->sh_autopay_main_setting_id}}" class="btn btn-info btn-xs"><i class="icon-refresh"></i>编辑方案</a></td>
							<td>

							</td>
						</tr>
						@endif
					@endforeach
					</tbody>
				</table>

				<hr/>

				<header class="panel-heading">
					热门刷单设置
				</header>
				<table class="table table-striped table-advance table-hover">
					<thead>
					<tr>
						<th>方案名称</th>
						<th>购买频率</th>
						<th>单次购买人次</th>
						<th>购买速度</th>
						<th>对应活动数</th>
						<th>对应活动总人次</th>
						<th>操作</th>
						<th>编辑方案</th>
						<th>编辑活动</th>
					</tr>
					</thead>
					<tbody>
					@foreach($list as $value)
						@if($value->sh_autopay_main_setting_id != '1')
						<tr>
							<td>{{$value->name}}</td>
							<td>{{$value->frequency}} 秒/次</td>
							<td>{{$value->scope_and_rate}}</td>
							<td>{{$value->speed}} 人次/小时</td>
							<td>{{$value->activity_counts}}</td>
							<td>
								@if($value->all_need_times == '')
									0
								@endif
									{{$value->all_need_times}}
							</td>
							<td>
								@if($value->flag == '1')
									<a href="/admin/setting/control-auto-pay-setting?sh_autopay_main_setting_id={{$value->sh_autopay_main_setting_id}}&flag=0" class="btn btn-primary btn-xs">开启</a>
								@else
									<a href="/admin/setting/control-auto-pay-setting?sh_autopay_main_setting_id={{$value->sh_autopay_main_setting_id}}&flag=1" class="btn btn-danger btn-xs">关闭</a>
								@endif
							</td>
							<td><a href="/admin/setting/auto-pay-setting-detail?sh_autopay_main_setting_id={{$value->sh_autopay_main_setting_id}}" class="btn btn-info btn-xs"><i class="icon-refresh"></i>编辑方案</a></td>
							<td>
								<a href="/admin/setting/activity-setting-list-pre?sh_autopay_main_setting_id={{$value->sh_autopay_main_setting_id}}" class="btn btn-info btn-xs"><i class="icon-refresh"></i>编辑活动</a>
							</td>
						</tr>
						@endif
					@endforeach
					</tbody>
				</table>
			</section>
		</div>
  	</section>
</section>
<!--main content end-->
@include('admin.tip_information')


@include('admin.footer')


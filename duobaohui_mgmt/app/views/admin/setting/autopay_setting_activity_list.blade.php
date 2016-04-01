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
					查询条件
				</header>
				<div class="panel-body">
					<form id="f1" class="form-horizontal tasi-form" method="post" action="/admin/setting/activity-setting-list-pre">
						<div class="row col-xs-8">
							<div class="col-xs-4">
								<div class='form-group'>
									<label for="goods">所需最小人次</label>
									<div class="col-sm-12">
										<input type="number" class="form-control" id="need_times_min" name="need_times_min" placeholder＝“” value="{{Input::old('need_times_min')}}" >
										<label class="error">{{$errors->first('need_times_min')}}</label>
									</div>
								</div>
							</div>
							<div class="col-xs-4">
								<div class='form-group'>
									<label for="goods">所需最大人次</label>
									<div class="col-sm-12">
										<input type="number" class="form-control" id="need_times_max" name="need_times_max" placeholder＝“” value="{{Input::old('need_times_max')}}" >
										<label class="error">{{$errors->first('need_times_max')}}</label>
									</div>
								</div>
							</div>
							<div class="col-xs-4">
								<div class="form-group">
									<label for="goods">活动名称</label>
									<input type="text" class="form-control" id="activity_name" name="activity_name" placeholder="" value="{{Input::old('activity_name')}}" >
								</div>
							</div>
						</div>
						<input type="hidden" name="sh_autopay_main_setting_id" id="sh_autopay_main_setting_id" value="{{$result['sh_autopay_main_setting_id']}}">
						<div class="form-group">
							<div class="col-xs-2 col-xs-offset-10" >
								<button type="button" id="queryIt" class="btn btn-success">查询</button>
								<button class="btn btn-info btn-lg" id="reset" type="button" >重置</button>
							</div>
						</div>
					</form>
				</div>
			</section>
			<section class="panel">
				<header class="panel-heading">
					<a href="/admin/setting/auto-pay-setting-list-pre" >刷单方案列表</a> -> 方案活动列表
				</header>
				<table class="table table-striped table-advance table-hover">
					<thead>
						<tr>
							<th>活动id</th>
							<th>活动名称</th>
							<th>所需人次</th>
							<th>已关联方案</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					@foreach($list as $value)
						<tr>
							<td>{{$value->sh_activity_id}}</td>
							<td>{{$value->goods_name}}</td>
							<td>{{$value->need_times}}</td>
							<td>{{$value->name}}</td>
							<td>
								@if(empty($value->sh_autopay_main_setting_id))
									<button type="button" class="btn btn-info btn-sm" action="add-activity-setting" sh_autopay_main_setting_id="{{$result['sh_autopay_main_setting_id']}}" sh_activity_id="{{$value->sh_activity_id}}">加入方案</button>
								@else
									<button type="button" class="btn btn-danger btn-sm" action="delete-activity-setting" sh_autopay_activity_setting_id="{{$value->sh_autopay_activity_setting_id}}">删除</button>
								@endif
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
	seajs.use('page/admin/setting/autopay' , function(mod){
		mod.init()
	});
</script>

@include('admin.footer')


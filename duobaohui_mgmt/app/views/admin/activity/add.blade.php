@include('admin.header')

@include('admin.menu')
<!--main content start-->
<section id="main-content">
	<section class="wrapper  site-min-height">
		<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading">
					添加活动
				</header>
				<div class="panel-body">

					<form class='form-horizontal tasi-form cmxform' method='post' action='/admin/activity/save-activity' id="addForm">
						<div class='form-group'>
							<label class='col-sm-2 control-label'>商品分类</label>
							<div class="col-sm-10">
								<select class='form-control' name='category_id' >
									<option>选择分类</option>
									@if ($category)
									@foreach ($category as $cv)
									<option value="{{$cv->id}}"
											@if($cv->id == Input::old('category_id'))
												selected
											@endif
											>{{ $cv->cat_name }}</option>
									@endforeach
									@endif
								</select>
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>商品名称</label>
							<div class="col-sm-10">
								<select class='form-control' name='goods_id' id="goods_id">
									<option>选择商品</option>
								</select>
								<label class="error">{{$errors->first('goods_id')}}</label>
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>所需人次</label>
							<div class="col-sm-10">
								<input type="text" id='needTimes' class="form-control" name='needTimes' value="{{Input::old('needTimes')}}"/>
								<label class="error">{{$errors->first('needTimes')}}</label>

							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>最大期数</label>
							<div class="col-sm-10">
								<input type="text" id='maxPeriod' class="form-control" name='maxPeriod' value="{{Input::old('maxPeriod')}}"/>
								<label class="error">{{$errors->first('maxPeriod')}}</label>
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>开始时间</label>
							<div class="col-sm-10">
								<input type="text" id='begin_date' name='begin_date' readonly class="form-control form-control-inline input-medium default-date-picker" value="{{Input::old('begin_date')}}"/>
								<label class="error">{{$errors->first('begin_date')}}</label>
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>结束时间</label>
							<div class="col-sm-10">
								<input type="text" id='end_date' name='end_date' readonly class="form-control form-control-inline input-medium default-date-picker" value="{{Input::old('end_date')}}"/>
								<label class="error">{{$errors->first('end_date')}}</label>
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>是否上线</label>
							<div class="col-sm-10">
								<label class='col-sm-2 control-label'>上线
									<input type='radio' name='is_online' value='1'
									@if(strcmp(Input::old('is_online'),'1') == 0)
										checked
									@endif
								    @if(Input::old('is_online') == null)
								   		checked
									@endif
											/>
								</label>
								<label class='col-sm-2 control-label'>下线
									<input type='radio' name='is_online' value='0'
								    @if(strcmp(Input::old('is_online'),'0') == 0)
								    checked
									@endif
											/>
								</label>
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>是否自动</label>
							<div class="col-sm-10">
								<label class='col-sm-2 control-label'>自动
									<input type='radio' name='is_auto' value='1'
									@if(Input::old('is_auto') == '1')
										checked
									@endif
								    @if(Input::old('is_auto') == null)
									   checked
									@endif

											/>
								</label>
								<label class='col-sm-2 control-label'>手动
									<input type='radio' name='is_auto' value='0'
								    @if(Input::old('is_auto') == '0')
										checked
									@endif
											/>
								</label>
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>是否热门</label>
							<div class="col-sm-10">
								<label class='col-sm-2 control-label'>否
									<input type='radio' name='is_hot' value='0'
										   @if(Input::old('is_hot') == '0')
										   checked
										   @endif
										   @if(Input::old('is_hot') == null)
										   checked
											@endif

											/>
								</label>
								<label class='col-sm-2 control-label'>是
									<input type='radio' name='is_hot' value='1'
										   @if(Input::old('is_hot') == '1')
										   checked
											@endif
											/>
								</label>
							</div>
						</div>
						<div class="col-xs-2 col-xs-offset-5">
							<button type="submit" class="btn btn-shadow btn-success" id='shaidan'>添加</button>
						</div>
					</form>
				</div>
			</section>
		</div>
	</section>
</section>
<!--main content end-->
{{ HTML::script('/flatlib/assets/bootstrap-datepicker/js/bootstrap-datepicker.js') }}
<script>
	$(function() {
		$(".default-date-picker").datepicker({
			format: "yyyy-mm-dd"
		});
	})
	seajs.use('page/admin/activity/add' , function(mod){
		mod.init()
	});
</script>

@include('admin.footer')


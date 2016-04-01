@include('admin.header')

@include('admin.menu')
		<!--main content start-->
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>

<script type="text/javascript" src="/flatlib/js/jquery.validate.min.js"></script>

<section id="main-content">
	<section class="wrapper  site-min-height">
		<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading">
					<a href="/admin/setting/auto-pay-setting-list-pre" >刷单方案列表</a> -> 方案详情
				</header>
				<div class="panel-body">

					<form class='form-horizontal tasi-form cmxform' method='post' action='/admin/setting/update-auto-pay-setting' id="addForm">
						<div class='form-group'>
							<label class='col-sm-2 control-label'>方案id</label>
							<div class="col-sm-10">
								<input type="text" id='sh_autopay_main_setting_id' class="form-control" readonly name='sh_autopay_main_setting_id' value="{{$auto_pay_setting_detail->id}}"/>
								<label class="error">{{$errors->first('name')}}</label>

							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>方案名称</label>
							<div class="col-sm-10">
								<input type="text" id='name' class="form-control" name='name' value="{{$auto_pay_setting_detail->name}}"/>
								<label class="error">{{$errors->first('name')}}</label>

							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>方案人次及概率<label style="color:red">(正确格式：最低购买人次~最高购买人次|购买概率,多套购买规则用“,”分割，符号均为英文半角字符，例如：“1~1|50,2~10|30,15~15|1,20~20|5”)</label></label>
							<div class="col-sm-10">
								<input type="text" id='scope_and_rate' class="form-control" name='scope_and_rate' value="{{$auto_pay_setting_detail->scope_and_rate}}"/>
								<label class="error">{{$errors->first('scope_and_rate')}}</label>
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>购买频率</label>
							<div class="col-sm-10">
								<input type="text" id='frequency' class="form-control" name='frequency' value="{{$auto_pay_setting_detail->frequency}}"/>
								<label class="error">{{$errors->first('frequency')}}</label>
							</div>
						</div>
						<div class="col-xs-2 col-xs-offset-5">
							<button type="submit" class="btn btn-shadow btn-success" id='shaidan'>修改</button>
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
	$(document).ready(function() {

//		jQuery.validator.setDefaults({
//			debug: true
//		});

		var addForm = $('#addForm')
		var addFormValidate = addForm.validate({

			rules: {
				name: {
					required: true,
					maxlength:128
				},
				scope_and_rate: {
					required: true,
					scope_and_rate_style : true,
					maxlength:1024
				},
				frequency: {
					required: true,
					frequency_style : true,
					maxlength:11
				}
			},
			messages: {
				name: {
					required: "请输入方案名称",
				},
				scope_and_rate: {
					required: "请输入方案人次及概率",
					scope_and_rate_style:'方案人次及概率格式错误'
				},
				frequency: {
					required: "请输入购买频率",
					frequency_style : "购买频率格式错误,请输入小于100的正整数"
				}
			}
		});

		jQuery.validator.addMethod("scope_and_rate_style", function(value, element) {
			var scope_and_rate_style = /^[1-9]\d*~[1-9]\d*\|(100|[1-9]\d?)(,[1-9]\d*~[1-9]\d*\|(100|[1-9]\d?))*$/
			return this.optional(element) || (scope_and_rate_style.test(value));
		}, "方案人次及概率格式错误");

		jQuery.validator.addMethod("frequency_style", function(value, element) {
			var frequency_style = /^(100|[1-9]\d?)$/
			return this.optional(element) || (frequency_style.test(value));
		}, "购买频率格式错误,请输入小于100的正整数");

		$(function () {
			$(".default-date-picker").datepicker({
				format: "yyyy-mm-dd"
			});
		})
		seajs.use('page/admin/activity/add', function (mod) {
			mod.init()
		});
	});



</script>

@include('admin.footer')


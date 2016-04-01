@include('admin.header')

@include('admin.menu')

<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class='row'>
			<div class='col-lg-12'>
				<section class="panel" id='tip'>
					<header class="panel-heading">
						添加访问权限
					</header>
					<div class='panel-body'>
						@if (!empty($msg))
						<div class="alert alert-success alert-block fade in">
							  <button data-dismiss="alert" class="close close-sm" type="button">
								  <i class="icon-remove"></i>
							  </button>
							  <h4>
								  <i class="icon-ok-sign"></i>
								 {{ $msg }}
							  </h4>
						</div>
						@endif
						<form class='form-horizontal tasi-form' method='post' action='/admin/authority/save-authority-user' >
							<div class='form-group'>
								<label class='col-sm-2 control-label'>用户名称</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" name='nick_name' />
								</div>
							</div>
							<div class='form-group'>
								<label class='col-sm-2 control-label'>密码</label>
								<div class="col-sm-10">
									<input type="password" class="form-control" name="password" />
								</div>
							</div>
							<div class='form-group'>
								<label class='col-sm-2 control-label'>确认密码</label>
								<div class="col-sm-10">
									<input type="password" class="form-control" name="repassword" />
								</div>
							</div>
							<div class='form-group'>
								<label class='col-sm-2 control-label'>手机号</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" name="tel" />
								</div>
							</div>
							<div class='form-group'>
								<label class='col-sm-2 control-label'>邮箱</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" name="email" />
								</div>
							</div>
							<div class='form-group'>
								<label class='col-sm-2 control-label'>可访问的管理组</label>
								<div class="col-sm-10">
									@if (!empty($groupList))
									@foreach ($groupList as $glv)
									<label class='col-sm-2 control-label'>
										{{ $glv->group_name }}
										<input type="checkbox" class="form-control" name="group[]" value={{ $glv->id }} />
									</label>
									@endforeach
									@endif
								</div>
							</div>
							<div>
								<button type="submit" class="btn btn-shadow btn-success" id='shaidan' style='float:right;'>添加用户</button>
							</div>
						</form>
					</div>	
				</div>
			</div>
		</section>
	</section>
</section>

<!--main content end-->
@include('admin.footer')


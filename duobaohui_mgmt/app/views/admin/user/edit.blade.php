@include('admin.header')

@include('admin.menu')
<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading">
					编辑用户
				</header>
				<div class="panel-body">
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
					<form class='form-horizontal tasi-form' method='post' action='/admin/user/edit' >
						<div class='form-group'>
							<label class='col-sm-2 control-label'>ID</label>
							<div class="col-sm-3">
								<input type="hidden"  name="id" value="{{$detail->id}}" />
								<span>{{$detail->id}}</span>
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>昵称</label>
							<div class="col-sm-3">
								<input type="text"  name="nick_name" value="{{$detail->nick_name}}" />
							</div>
						</div>
						<div class="form-group">
							<label class='col-sm-2 control-label'>手机号</label>
							<div class="col-sm-3">
								<input type="text"  name="tel" value="{{$detail->tel}}" />
							</div>
						</div>
						<div>
							<button type="submit" class="btn btn-shadow btn-success" id='shaidan' style='float:right;'>添加</button>
						</div>
					</form>
				</div>
			</section>
		</div>
	</section>
</section>
<!--main content end-->


@include('admin.footer')


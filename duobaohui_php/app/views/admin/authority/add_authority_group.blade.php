@include('admin.header')

@include('admin.menu')

<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class='row'>
			<div class='col-lg-12'>
				<section class="panel" id='tip'>
					<header class="panel-heading">
						添加权限组
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
						<form class='form-horizontal tasi-form' method='post' action='/admin/authority/save-authority-group' >
							<div class='form-group'>
								<label class='col-sm-2 control-label'>组名称</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" name='group_name' />
								</div>
							</div>
							<div>
								<button type="submit" class="btn btn-shadow btn-success" id='shaidan' style='float:right;'>添加组</button>
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


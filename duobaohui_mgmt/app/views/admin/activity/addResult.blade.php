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
					@if($code == 'success')
						{{$msg}}
					@else
						添加活动失败
					@endif
				</div>
				<div>

					<a href="/admin/activity/list-pre" class="btn btn-shadow btn-success" id='shaidan'>点此返回</a>

				</div>
			</section>
		</div>
	</section>
</section>
<!--main content end-->

@include('admin.footer')


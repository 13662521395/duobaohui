@include('admin.header')
@include('admin.menu')

<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class='row'>
			<div class='col-lg-12'>
				<section class="panel" id='tip'>
					<div class='panel-body'>

						{{ $content }}

					</div>
				</section>
			</div>
		</div>
	</section>
</section>


<!--main content end-->
@include('admin.footer')

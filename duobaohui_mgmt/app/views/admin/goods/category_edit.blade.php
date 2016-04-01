@include('admin.header')

@include('admin.menu')
<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading">
					添加分类
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
					<form class='form-horizontal tasi-form' method='post' action='/admin/goods/category-edit' >
						@if (isset($detail->parent))
							<div class='form-group'>
								<label class='col-sm-2 control-label'>父类名称</label>
								<div class="col-sm-10">
									<span>{{$detail->parent->cat_name}}</span>
									<input name="pid"  type="hidden" value="{{$detail->parent->id}} "/>
								</div>
							</div>
						@endif
						<div class='form-group'>
							<label class='col-sm-2 control-label'>名称</label>
							<div class="col-sm-3">
								<input type="hidden"  name="id" value="{{$detail->id}}" />
								<input type="text"  name="cat_name" value="{{$detail->cat_name}}" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">icon图</label>
							<div class="controls col-md-9">
								<img  src="{{ $detail->img_url }}" />
								@include('admin.qiniu_js_upload_img')
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>描述</label>
							<div class="col-sm-10">
								<textarea name="description" class="col-sm-10">{{$detail->description}}</textarea>
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


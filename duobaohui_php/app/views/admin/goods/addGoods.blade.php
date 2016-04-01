@include('admin.header')

@include('admin.menu')
<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading">
					添加商品
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
					<form class='form-horizontal tasi-form' method='post' action='/admin/goods/save-goods' >
						<div class='form-group'>
							<label class='col-sm-2 control-label'>商品分类</label>
							<div class="col-sm-10">
								<select class='form-control' name='category_id' >
									<option>选择分类</option>
									@if ($category)
									@foreach ($category as $cv)
									<option value='category_id'>{{ $cv->cat_name }}</option>
									@endforeach
									@endif
								</select>
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>商品名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="goods_name" />
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>市场价</label>
							<div class="col-sm-10">
								<input type="text" id='market_price' class="form-control" name='market_price' />
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>本店价</label>
							<div class="col-sm-10">
								<input type="text" id='shop_price' class="form-control" name='shop_price' />
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>商品描述</label>
							<div class="col-sm-10">
								@include('admin.umeditor' , array('content'=>''))	
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">商品图片(默认第一张为头图)</label>
							<div class="controls col-md-9">
								@include('admin.qiniu_js_upload_img')	
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>采购链接</label>
							<div class="col-sm-10">
								<input type="text" id='purchase_url' class="form-control" name='purchase_url' />
							</div>
						</div>
						<div class='form-group'>
							<label class='col-sm-2 control-label'>真实/虚拟商品</label>
							<div class="col-sm-10">
								<label class='col-sm-2 control-label'>真实商品
									<input type='radio' name='is_real' value='1' checked/>
								</label>
								<label class='col-sm-2 control-label'>虚拟商品
									<input type='radio' name='is_real' value='0' />
								</label>
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


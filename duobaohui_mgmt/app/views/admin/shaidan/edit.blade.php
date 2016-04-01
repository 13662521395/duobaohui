@include('admin.header')

@include('admin.menu')

<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class='row'>
			<div class='col-lg-12'>
				<section class="panel" id='tip'>
					<header class="panel-heading">
						编辑晒单
						<button type="button" id='wangyi' goods_id="{{ $info->goods_id }}" class="btn btn-success" style='margin-left:20px;' data-toggle='modal' href='wangyiModal'>查看其他网站本商品的晒单</button>
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
						
						<form class='form-horizontal tasi-form' method='post' action='/admin/shaidan/update-shaidan' >
							<input type='hidden' value="{{ $info->id}}" name="shaidan_id" />
							<div class='form-left' style='width:49%;float:left'>
								<div class='form-group'>
									<label class='col-sm-2 control-label'>用户昵称</label>
									<div class="col-sm-10">
										<input type="hidden" class="form-control" name='user_id' id='user-id' value="{{ $info->user_id }}" />
										<input type="text" style='width:200px;float:left;margin-right:20px;' id='user-name' class="form-control" value="{{ $info->nick_name }}" />
										<button type="button" id='update-user-name' style='display:none' class="btn btn-round btn-danger">修改昵称</button>	
									</div>
								</div>
								<div class='form-group'>
									<label class='col-sm-2 control-label'>活动期数ID</label>
									<div class="col-sm-10">
										<input type="text" style='width:200px;display:inline' class="form-control" name="period_result_id" value="{{ $info->sh_period_result_id }}" />
										<input type="hidden" class="form-control" name='order_id' value="{{ $info->order_id }}" />
									</div>
								</div>
								<div class='form-group'>
									<label class='col-sm-2 control-label'>标题</label>
									<div class="col-sm-10">
										<input type="text" id='title' class="form-control" name='title' value="{{ $info->title}}" />
									</div>
								</div>
								<div class='form-group'>
									<label class='col-sm-2 control-label'>内容</label>
									<div class="col-sm-10">
										<textarea class="form-control" id='content' name='content' style='height:225px;resize:none'>{{ $info->content}}</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">已有晒图</label>
									<div class="controls col-md-9">
										@if (!empty($info->img_url))
											@foreach ($info->img_url as $imgUrl)
											<img src="{{ $imgUrl->img_url }}" id={{ $imgUrl->id }} style='height:200px;'/>
											@endforeach
										@else
											<div>没有晒图</div>	
										@endif
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3">晒图</label>
									<div class="controls col-md-9">
										@include('admin.qiniu_js_upload_img')	
									</div>
								</div>
								<div>
									<button type="submit" class="btn btn-shadow btn-success" id='shaidan' style='float:right;'>晒单</button>
								</div>
							</div>
							<div class='form-right' style='float:right;width:49%;'>
								<input type='hidden' name='goods_id' />
								<div class='form-group'>
									<label class='col-sm-2 control-label'>商品名称</label>
									<div class="col-sm-10">
										<input type="text" readonly class="form-control" value="{{ $info->goods_name }}"/>
										<input type="hidden" class="form-control" name='goods_id' value="{{ $info->goods_id }}"/>
									</div>
								</div>
								<div class='form-group'>
									<label class='col-sm-2 control-label'>商品图片</label>
									<div class="col-sm-10">
										<div><img src="{{ $info->goods_img }}" /></div>
									</div>
								</div>
							</div>
						</form>
					</div>	
				</div>
			</div>
		</section>
	</section>
</section>

@include('admin.shaidan.wangyi')
@include('admin.tip_information')


<script>
	seajs.use('page/admin/shaidan/add' , function(mod){
		mod.wangyi();	
		mod.select();	
		mod.updateUserName();	
	});
</script>

<!--main content end-->
@include('admin.footer')


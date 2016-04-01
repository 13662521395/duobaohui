@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>

<!--main content start-->
<section id="main-content">
  	<section class="wrapper">
		<section class="panel">
			<header class="panel-heading">
				商品列表
				
				<form action='/admin/goods/list' method='get' style='float:right'>
					<div style='float:right'>
						<button type="submit" class="btn btn-shadow btn-success" id='shaidan' style='float:right;margin:-5px 0 0 10px;'>搜索</button>
					</div>
					<div class='form-group' style="float:right;margin-right:10px;">
						<label>商品分类
							<select class="form-control" style='float:right;width:150px;margin:-5px 0 0 10px;' name="category_id">
								<option value='0'>选择分类</option>
								@foreach ($category as $cv)
								<option value={{ $cv->id }} @if ( $category_id == $cv->id ) selected @endif ><?php for($i=0;$i<$cv->level;$i++)echo '&nbsp;&nbsp;'; ?>{{ $cv->cat_name }}</option>
								@endforeach
							</select>
						</label>
					</div>
					<div class='form-group' style="float:right;margin-right:10px;">
						<label>商品名称
							<input type="text" class="form-control" style='float:right;width:150px;margin:-5px 0 0 10px;' name="goods_name" value="{{ $goods_name }}"/>
						</label>
					</div>
				</form>
			</header>
			<table class="table table-striped table-advance table-hover">
				<thead>
					<tr>
					  <th></i>商品ID</th>
					  <th></i>商品名称</th>
					  <th></i>商品图片</th>
					  <th></i>销售价格</th>
					  <th></i>市场价格</th>
					  <th></i>商品分类</th>
					  <th>操作</th>
					</tr>
				</thead>
				<tbody>
				<?php foreach($list as $item){?>
					<tr goods_id = "{{$item->id;}}">
						<td><a href="#">{{$item->id;}}</a></td>
						<td>{{$item->goods_name;}}</td>
						<td><img src="{{$item->goods_img;}}" height=50 /></td>
						<td>{{$item->shop_price;}}</td>
						<td>{{$item->market_price;}}</td>
						<td>{{$item->cat_name;}}</td>
						<td>
							<a href="/admin/goods/edit-goods?goods_id={{$item->id}}" class="btn btn-primary btn-xs"><i class="icon-pencil"></i></a>
							<button class="btn btn-danger btn-xs  delete_goods"><i class="icon-trash"></i></button>
						</td>
					</tr>
				<?php }?>							
				</tbody>
			</table>

			{{$list->appends(array('goods_name'=>$goods_name,'category_id'=>$category_id))->links('admin.pageInfo')}}

		</section>
  	</section>
</section>
<!--main content end-->
<script type="text/javascript">
	$('.delete_goods').bind('click' , function(){
		var goods_parent = $(this).parent().parent();
		var goods_id = goods_parent.attr('goods_id')
		if(confirm('是否要删除？')){
			$.get('/admin/goods/delete-goods' , {'goods_id' : goods_id } , function(res){
				if(res.code == 1){
					goods_parent.remove();
				}else{
					alert(res.message);
				}
			});			
		}


	})
</script>

@include('admin.footer')


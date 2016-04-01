@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>

<!--main content start-->
<section id="main-content">
  	<section class="wrapper">
		<section class="panel">
			<header class="panel-heading">
					<a title="新增大分类" href="/admin/goods/category-add" class="btn  btn-primary btn-xs"><i class="icon-plus-sign"></i></a>
			</header>
			<table class="table table-striped table-advance table-hover">
				<thead>
					<tr>
					  <th></i>ID</th>
					  <th></i>名称</th>
					  <th></i>图片</th>
					  <th></i>状态</th>
					  <th></i>描述</th>
					  <th>操作</th>
					</tr>
				</thead>
				<tbody>
				<?php foreach($list as $item){?>
					<tr category_id = "{{$item->id;}}">
						<td><a href="#">{{$item->id;}}</a></td>
						<td style="padding-left:<?php echo $item->level*15; ?>px">{{$item->cat_name;}}</td>
						<td><img style="max-width:20px" src="{{$item->img_url;}}" /></td>
						<td>{{$item->status;}}</td>
						<td>{{$item->description;}}</td>
						<td>
							<a title="新增子分类" href="/admin/goods/category-add?pid={{$item->id}}" class="btn btn-primary btn-xs"><i class="icon-plus-sign"></i></a>
							<a title="编辑" href="/admin/goods/category-edit?id={{$item->id}}" class="btn btn-primary btn-xs"><i class="icon-pencil"></i></a>
							<button class="btn btn-danger btn-xs  delete_btn" pkid='{{$item->id}}'><i class="icon-trash"></i></button>
						</td>
					</tr>
				<?php }?>							
				</tbody>
			</table>
		</section>
  	</section>
</section>
<!--main content end-->
<script type="text/javascript">
	$('.delete_btn').bind('click' , function(){
		var id = $(this).attr('pkid');
		if(confirm('是否要删除？')){
			$.get('/admin/goods/category-del' , {'id' : id } , function(res){
				if(res.code == 1){
					location.reload();
				}else{
					alert(res.msg);
				}
			});			
		}


	})
</script>

@include('admin.footer')


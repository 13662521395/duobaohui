@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>

<!--main content start-->
<section id="main-content">
  	<section class="wrapper">
		<section class="panel">
			<header class="panel-heading">
				支付记录
				<form action='/admin/bill/pay-list' method='get' style='float:right'>
					<div style='float:right'>
						<button type="submit" class="btn btn-shadow btn-success" id='search' style='float:right;margin:-5px 0 0 10px;'>搜索</button>
					</div>
					<div class='form-group' style="float:right;margin-right:10px;">
						<label>用户ID
							<input type="text" class="form-control" style='float:right;width:150px;margin:-5px 0 0 10px;' name="user_id" value=""/>
						</label>
					</div>
					<!-- <div class='form-group' style="float:right;margin-right:10px;">
						<label>活动期数
							<input type="text" class="form-control" style='float:right;width:150px;margin:-5px 0 0 10px;' name="activity_period_id" value=""/>
						</label>
					</div> -->
				</form>
			</header>
				<table class="table table-striped table-advance table-hover">
				  	<thead>
				  		<tr>
						  <th></i>ID</th>
						  <th></i>用户ID</th>
						  <th></i>用户名称</th>
						  <th></i>用户余额</th>
						  <th></i>活动期数</th>
						  <th></i>参与物品</th>
						  <th></i>支付金额</th>
						  <!-- <th></i>支付类型</th> -->
						  <th></i>支付时间</th>
						  <th></th>
				 		</tr>
				  	</thead>
					<tbody>
				  	<?php foreach($list as $item){?>
				  		<tr pkid = "{{$item->id}}">
							<td><a href="#">{{$item->id}}</a></td>
							<td><a href="?user_id={{$item->user_id;}}&page={{$pageinfo->page-1;}}">{{$item->user_id}}</a></td>
							<td><a href="?user_id={{$item->user_id;}}&page={{$pageinfo->page-1;}}" >{{$item->nick_name}}</a></td>
							<td><a href="?user_id={{$item->user_id;}}&page={{$pageinfo->page-1;}}" >{{$item->money}}</a></td>
							<td>{{$item->activity_period_id}}</td>
							<td>{{$item->subject}}</td>
							<td>{{$item->total_fee}}</td>
							
							<td>{{$item->notify_time}}</td>
							<td> 
	
			
								<!-- <button class="btn btn-danger btn-xs  delete"><i class="icon-trash"></i></button> -->
								@if (!empty($item->bii_id)) 
									<a href="/admin/bill/alipay-account?nick_name={{$item->nick_name}}" class="btn btn-danger btn-xs"><i class="icon-pencil">有异常</i></a> 
								@else 
									<a href="/admin/bill/alipay-account" class="btn btn-primary btn-xs"><i class="icon-pencil">无异常</i></a> 
								@endif  
					  		</td>
				  		</tr>
					<?php }?>	
				  	</tbody>
			</table>
			<ul class="pagination pagination-lg">
					<?php ?> 
						@if(!empty($user_id)) 
							<li><a href="?user_id={{$user_id;}}&page={{$pageinfo->page-1;}}">«</a></li>
							<li><a href="?user_id={{$user_id;}}&page={{$pageinfo->page+1;}}">»</a></li>
						@else
							<li><a href="?page={{$pageinfo->page-1;}}">«</a></li>
							<li><a href="?page={{$pageinfo->page+1;}}">»</a></li>
						@endif
					<?php ?>	
					
			</ul>
		</section>
  	</section>
</section>
<!--main content end-->
<script type="text/javascript">
	$('.delete').bind('click' , function(){
		var goods_parent = $(this).parent().parent();
		var pkid = goods_parent.attr('pkid')
		if(confirm('是否要删除？')){
			$.get('/admin/goods/delete-goods' , {'id' : pkid } , function(res){
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


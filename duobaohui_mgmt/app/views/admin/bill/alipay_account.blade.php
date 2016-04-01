@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>

<!--main content start-->
<section id="main-content">
  	<section class="wrapper">
		<section class="panel">
			<header class="panel-heading">
				支付宝账务明细异常
				<form action='/admin/bill/alipay-account' method='get' style='float:right'>
					<div style='float:right'>
						<button type="submit" class="btn btn-shadow btn-success" id='search' style='float:right;margin:-5px 0 0 10px;'>搜索</button>
					</div>
					<div class='form-group' style="float:right;margin-right:10px;">
						<label>用户ID
							<input type="text" class="form-control" style='float:right;width:150px;margin:-5px 0 0 10px;' name="user_id" value=""/>
						</label>
					</div>
				</form>
			</header>
				<table class="table table-striped table-advance table-hover">
				  	<thead>
				  		<tr>
						  <th>ID</th>
						   <th>alipay_id</th>
						  <th>交易号</th>
						  <th>异常类型</th>
						  <th>支付宝买家Id</th>
						  <th>支付宝金额</th>
						   <th>alipay金额</th>
						  <th>用户ID</th>
						  <th>用户昵称</th>
						  <th>参与期数</th>
						   <th>修复状态</th>
						  <th>账务开始时间</th>
						  <th>账务结束时间</th>
						  <th>交易记录时间</th>
						<!--   <th></i>备注</th> -->
						  <th></th>
				 		</tr>
				  	</thead>
					<tbody>
				  	<?php 
				  		if (!empty($list)){
				  		foreach($list as $item){?>
				  		<tr id = "{{$item->id}}">
							<td><a href="#">{{$item->id}}</a></td>
							<td><a href="#">@if ($item->out_trade_no === 0)   @else {{$item->out_trade_no}}  @endif</a></td>
							<td><a href="{{$item->id}}" >{{$item->trade_no}}</a></td>
							<td><a href="{{$item->id}}" >@if ($item->type === 0) 漏单行为异常  @elseif ($item->type === 1) 虚假订单  @else 真实交易重复异常 @endif</a></td>
							<td>{{$item->buyer_id}}</td>
							<td>{{$item->total_fee}}</td>
							<td>{{$item->alipay_total}}</td>
							<td>{{$item->user_id}}</td>
							<td>@if ($item->nick_name == '')   @else {{$item->nick_name}}  @endif</td>
							<td>{{$item->sh_activity_period_id}}</td>
							<td>@if ($item->status == 0) <i class="label label-danger status">未处理 </i> @else 已处理  @endif</td>
							<td>{{$item->start_date}}</td>
							<td>{{$item->end_date}}</td>
							<td>{{$item->notify_time}}</td>
							<!-- <td><a href="{{$item->id}}" ></a></td> -->
							<td>
								@if ($item->type == 0) <button shaidan_id="{{ $item->id }}" title="不可操作" class="btn btn-danger btn-xs no-change"><i class="label label-danger ">不可操作</i></button>
								@elseif ($item->type == 1)  
									@if ($item->status == 1) 
										<button bill_id="{{ $item->id }}" title="已处理" class="btn btn-danger btn-xs"><i class="label label-info ">已处理</i></button>  
									@else 
										<button bill_id="{{ $item->id }}" title="处理" class="btn btn-danger btn-xs change-status"><i class="label label-info ">处理</i></button>  
									@endif  
								@else 
									@if ($item->status == 1) 
										<button bill_id="{{ $item->id }}" title="修复" class="btn btn-danger btn-xs"><i class="label label-warning ">已修复</i></button> 
									@else 
										<button bill_id="{{ $item->id }}" title="修复" class="btn btn-danger btn-xs edit-alipay"><i class="label label-warning ">修复</i></button> 
									@endif  
								@endif
								
								<!-- <button class="btn btn-danger btn-xs  delete"><i class="icon-trash"></i></button> -->

					  		</td>
				  		</tr>
					<?php 
						}
					}?>	
				  	</tbody>
			</table>
			<ul class="pagination pagination-lg">
				<?php ?> 
					@if(!empty($user_id))
						{{$list->appends(array('user_id'=>$user_id))->links('admin.pageInfo')}}
					@else
						{{$list->links('admin.pageInfo')}}
					@endif
				<?php ?>	
			</ul>
		</section>
  	</section>
</section>
<!--main content end-->
<script type="text/javascript">
	seajs.use('page/admin/bill/change_status' , function(mod){
		mod.getChangeStatus();	
		mod.editAlipay();
	});

	
</script>

@include('admin.footer')


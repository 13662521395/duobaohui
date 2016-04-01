@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>

<!--main content start-->
<section id="main-content">
  	<section class="wrapper">
		<section class="panel">
			<header class="panel-heading">
				
				<form action='/admin/bill/pay-list' method='get' style='float:right'>
					<div style='float:right'>
						<button type="submit" class="btn btn-shadow btn-success" id='search' style='float:right;margin:-5px 0 0 8px;'>搜索</button>
					</div>
					<div class='form-group' style="float:right;margin-right:10px;">
						<label>用户ID
							<input type="text" class="form-control" style='float:right;width:150px;margin:-5px 0 0 10px;' name="user_id" value=""/>
						</label>
					</div>
				</form>

				<form action='/admin/bill/pay-list' method='get' style='float:right'>
					<div style='float:right'>
						<button type="submit" class="btn btn-shadow btn-success" id='search' style='float:right;margin:-10px 30px 0 8px;'>搜索</button>
					</div>
					<div class='form-group' style="float:right;margin-right:10px;">
						<label>流水号
							<input type="text" class="form-control" style='float:right;width:150px;margin:-5px 0 0 10px;' name="jnl_no" value=""/>
						</label>
					</div>
				</form>

				<form action='/admin/bill/pay-list' method='get' style='float:right'>
					<div style='float:right'>
						<button type="submit" class="btn btn-shadow btn-success" id='search' style='float:right;margin:-5px 40px 0 10px;'>搜索</button>
					</div>
					<div class='form-group' style="float:right;margin-right:10px;">
						<label>手机号
							<input type="text" class="form-control" style='float:right;width:150px;margin:-5px 0 0 10px;' name="tel" value=""/>
						</label>
					</div>
				</form>

				<form action='/admin/bill/search-bill' method='get' style='float:left'>
					支付记录
					<div style='float:right'>
						<button type="submit" class="btn btn-shadow btn-success" id='search' style='float:left;margin:-10px 30px 50px 40px;'>搜索异常</button>
					</div>
				</form>

				
			</header>
				<table class="table table-striped table-advance table-hover">
				  	<thead>
				  		<tr>
						  <th></i>流水号ID</th>
						  <th></i>用户ID</th>
						  <th></i>用户名称</th>
						  <th></i>手机号</th>
						  <th></i>用户余额</th>
						  <th></i>活动期数</th>
						  <th></i>参与物品</th>
						  <th></i>支付金额</th>
						  <th></i>支付类型</th>
						  <th></i>支付时间</th>
						  <th></i>支付状态</th>
						  <th>操作</th>
				 		</tr>
				  	</thead>
					<tbody>
				  	<?php foreach($list as $item){?>
				  		<tr pkid = "{{$item->user_id}}">
							<td><a href="#">{{$item->jnl_no}}</a></td>
							<td><a href="?user_id={{$item->user_id;}}&page={{$pageinfo->page-1;}}">{{$item->user_id}}</a></td>
							<td><a href="?user_id={{$item->user_id;}}&page={{$pageinfo->page-1;}}" >{{$item->nick_name}}</a></td>
							<td>{{$item->tel}}</td>
							<td><a href="?user_id={{$item->user_id;}}&page={{$pageinfo->page-1;}}" >{{$item->money}}</a></td>
							<td>@if($item->subject =='duobao') {{$item->sh_activity_period_id}} @else  未参与夺宝  @endif </td>
							<td>@if($item->subject =='duobao') 夺宝 @elseif($item->subject =='recharge') 充值  @else 有问题 @endif</td>
							<td>@if(!empty($item->total_fee)) {{$item->total_fee}} @else  {{$item->amount}}  @endif</td>
							<td>@if($item->pay_type == 0) 余额支付  @elseif($item->pay_type == 1) 支付宝   @else 其他待扩展 @endif </td>
							<td>{{$item->create_time}}</td>
							<td>@if($item->status == 0)  新建订单  @elseif($item->status == 1) 充值成功   @elseif($item->status == 2) 充值失败 @elseif($item->status == 3) 超时未处理  @elseif($item->status == 4) 夺宝交易完成 @else 其他待扩展  @endif </td>
							<td> 
	
			
								<!-- <button class="btn btn-danger btn-xs  delete"><i class="icon-trash"></i></button> -->
								@if (!empty($item->bii_id)) 
									<a href="/admin/bill/alipay-account?user_id={{$item->user_id}}" class="btn btn-danger btn-xs"><i class="icon-pencil">明细(充值)账异常</i></a> 
								@else
									<a href="#" class="btn btn-primary btn-xs"><i class="icon-pencil">无异常</i></a> 
								@endif  
					  		</td>
				  		</tr>
					<?php }?>	
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

@include('admin.footer')


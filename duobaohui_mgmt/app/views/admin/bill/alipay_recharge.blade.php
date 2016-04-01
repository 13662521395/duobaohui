@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>

<!--main content start-->
<section id="main-content">
  	<section class="wrapper">
		<section class="panel">
			<header class="panel-heading">
				
				<!-- <form action='/admin/bill/pay-list' method='get' style='float:right'>
					<div style='float:right'>
						<button type="submit" class="btn btn-shadow btn-success" id='search' style='float:right;margin:-5px 0 0 8px;'>搜索</button>
					</div>
					<div class='form-group' style="float:right;margin-right:10px;">
						<label>用户ID
							<input type="text" class="form-control" style='float:right;width:150px;margin:-5px 0 0 10px;' name="user_id" value=""/>
						</label>
					</div>
				</form> -->

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
					充值记录
					<div style='float:right'>
						<button type="submit" class="btn btn-shadow btn-success" id='search' style='float:left;margin:-10px 30px 50px 40px;'>搜索异常</button>
					</div>
				</form>


			</header>
				<table class="table table-striped table-advance table-hover">
				  	<thead>
				  		<tr>
						  <th></i>充值ID</th>
						  <th></i>交易号</th>
						  <th></i>用户ID</th>
						  <th></i>用户名称</th>
						  <th></i>手机号</th>
						  <th></i>充值商品</th>
						  <th></i>充值渠道</th>
						  <th></i>充值金额</th>
						  <th></i>充值后的余额</th>
						   <th></i>用户当前余额</th>
						  <th></i>创建时间</th>
						  <th>操作</th>
				 		</tr>
				  	</thead>
					<tbody>
					@if(isset($list))
				  		<tr pkid = "{{$list->id}}">
							<td>{{$list->id}}</td>
							<td>{{$list->trans_jnl_no}}</td>
							<td>{{$list->user_id}}</td>
							<td>{{$list->nick_name}}</td>
							<td>{{$list->tel}}</td>
							
							<td>{{$list->subject}}</td>
							<td>@if($list->recharge_channel == 0) 支付宝  @elseif($list->recharge_channel == 1) 微信   @else 其他待扩展 @endif </td>
							<td>￥{{$list->total_fee}}</td>
							<td>￥{{$list->latest_balance}}</td>
							<td>￥{{$list->money}}</td>
							<td>{{$list->create_time}}</td>

							<td> 
								@if (!empty($list->bii_id)) 
									<a href="/admin/bill/alipay-account?user_id={{$list->user_id}}" class="btn btn-danger btn-xs"><i class="icon-pencil">有异常</i></a> 
								@else
									<a href="#" class="btn btn-primary btn-xs"><i class="icon-pencil">无异常</i></a> 
								@endif
					  		</td>
				  		</tr>
					@endif
				  	</tbody>
			</table>
			<ul class="pagination pagination-lg">
			
					
			</ul>
		</section>
  	</section>
</section>
<!--main content end-->


@include('admin.footer')


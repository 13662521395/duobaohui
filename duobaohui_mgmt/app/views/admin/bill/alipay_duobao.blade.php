@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>

<!--main content start-->
<section id="main-content">
  	<section class="wrapper">
		<section class="panel">
			<header class="panel-heading">
			<!-- 	<form action='/admin/bill/pay-list' method='get' style='float:right'>
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
					夺宝记录
					<div style='float:right'>
						<button type="submit" class="btn btn-shadow btn-success" id='search' style='float:left;margin:-10px 30px 50px 40px;'>搜索异常</button>
					</div>
				</form>


			</header>
				<table class="table table-striped table-advance table-hover">
				  	<thead>
				  		<tr>
						  <th>夺宝ID</th>
						  <th>流水号</th>
						  <th>用户ID</th>
						  <th>用户名称</th>
						  <th>手机号</th>
						 
							<th>夺宝商品</th>
						  <th>夺宝数量</th>
						  <th>状态</th>
						  <th>总交易额</th>
						  <th>夺宝后的余额</th>
						   <th>用户当前余额</th>
						  <th>创建时间</th>
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
							
							<td>{{$list->goods_name}}</td>
							<td>{{$list->num}}件</td>
							<td>@if($list->status == 0) 新建 @elseif($list->status == 1) 夺宝成功 @elseif($list->status == 2) 夺宝失败 @endif</td>
							<td>￥{{$list->amount}}</td>
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


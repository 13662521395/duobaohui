@include('admin.header')
@include('admin.menu')

<!--main content start-->
<section id="main-content">
  	<section class="wrapper">
		<section class="panel">
			<header class="panel-heading">


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
				支付记录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;


				<form action='/admin/bill/pay-list' method='get' style='float:right;'>
					<div style='float:right'>
						<button type="submit" class="btn btn-shadow btn-success" id='search' style='float:right;margin:-5px 30px 0 0px;'>搜索</button>
					</div>
					@if(isset($start_time))
						<div class="input-append date dpYears" style='float:right;width:170px;margin:-0px 20px 0 10px;'>
							<input class="form-control form-control-inline input-append form_date" data-date-format="yyyy-mm-dd hh:ii:ss" data-date="{{$end_time}}" name="end_time" size="16" type="text" value="{{$end_time}}"  />
						</div>

						<div class="input-append date dpYears" style='float:right;width:170px;margin:-0px 30px 0 10px;'>
							<input class="form-control form-control-inline input-append form_date" data-date-format="yyyy-mm-dd hh:ii:ss" data-date="{{$start_time}}" name="start_time" size="16" type="text" value="{{$start_time}}"   />
						</div>
				    @else
						<div class="input-append date dpYears" style='float:right;width:170px;margin:-0px 20px 0 10px;'>
							<input class="form-control form-control-inline input-append form_date" data-date-format="yyyy-mm-dd hh:ii:ss" data-date="{{$now_date}}" name="end_time" size="16" type="text" value="{{$tomorow}}"   />
						</div>

						<div class="input-append date dpYears" style='float:right;width:170px;margin:-0px 30px 0 10px;'>
							<input class="form-control form-control-inline input-append form_date" data-date-format="yyyy-mm-dd hh:ii:ss" data-date="{{$now_date}}" name="start_time" size="16" type="text" value="{{$now_date}}"   />
						</div>
				    @endif

				    <div class="btn-group" style='float:right;'>
				    	<select name="choice">
				    		@if(isset($choice))
				    			<option value="0" @if($choice == 0) SELECTED @endif >全部</option>
					    		<option value="1" @if($choice == 1) SELECTED @endif>充值</option>
					    		<option value="2" @if($choice == 2) SELECTED @endif>夺宝</option>
				    		@else
				    			<option value="0" SELECTED>全部</option>
					    		<option value="1">充值</option>
					    		<option value="2">夺宝</option>
				    		@endif
				    	</select>
				    </div>

				</form>


				<i class="btn-danger">总交易金额：{{$total_money}}&nbsp;&nbsp;元&nbsp;&nbsp;</i>
			</header>
				<table class="table table-striped table-advance table-hover">
				  	<table class="table table-striped table-advance table-hover">
				  	<thead>
				  		<tr>
						  <th>交易流水号</th>
						<!--   <th>用户ID</th>
						  <th>用户名称</th> -->
						  <th>手机号</th>
						 
						  <th>交易类型</th>
						  <th>交易状态</th>
						  <!-- <th>状态描述</th> -->
						  <th>支付类型</th>
						  <th>交易渠道</th>
						  <th>交易总金额</th>
						  <th>交易时间</th>
						  <th>操作</th>
				 		</tr>
				  	</thead>
					<tbody>
					<?php
					if (!empty($list)){
						foreach($list as $item){?>
				  		<tr pkid = "{{$item->id}}">
							<td><a href="#">{{$item->trans_jnl_no}}</a></td>

							<td><a href="?tel={{$item->tel;}}&page=1" ><i class="icon-phone btn-primary"></i>&nbsp;&nbsp;{{$item->tel}}</a></td>
							
							<td><a hrfe="#" class="pie-foot" >@if($item->trans_code =='duobao') 夺宝 @elseif($item->trans_code =='recharge') 充值  @else 有问题 @endif</td>
							<td>@if($item->jnl_status == 0)  新建订单  @elseif($item->jnl_status == 1) 充值成功   @elseif($item->jnl_status == 2) 充值失败 @elseif($item->jnl_status == 3) 超时未处理  @elseif($item->jnl_status == 4) 夺宝交易完成 @else 其他待扩展  @endif </a></td>
							<td>@if($item->pay_type == 0) 余额支付  @elseif($item->pay_type == 1) 充值   @else 其他待扩展 @endif </td>
							<td>@if($item->recharge_channel == 0) 支付宝  @elseif($item->recharge_channel == 1) 微信  @elseif($item->recharge_channel == 2) 红包转余额  @else 其他待扩展 @endif </td>
							<td>￥{{$item->amount}}</td>
							<td>{{$item->create_time}}</td>
							<td>

								@if(!empty($item->bill_id))
										<a href="/admin/bill/alipay-account?user_id={{$item->user_id}}" class="btn btn-danger"><i class="icon-warning-sign">有异常</i></a>
								@endif

								@if(!empty($item->duobao_id) && $item->jnl_status != 0)

									@if($item->pay_type == 0)
										<a href="/admin/bill/alipay-duobao?duobao_id={{$item->duobao_id}}" class="btn btn-primary"><i class="icon-cloud">夺宝信息</i></a>
									@else
										<a href="/admin/bill/alipay-recharge?jnl_no={{$item->trans_jnl_no}}" class="btn btn-info"><i class="icon-refresh">充值信息</i></a>
										<a href="/admin/bill/alipay-duobao?duobao_id={{$item->duobao_id}}" class="btn btn-primary"><i class="icon-cloud">夺宝信息</i></a>
									@endif
								@elseif(empty($item->duobao_id) && $item->jnl_status != 0)

									<a href="/admin/bill/alipay-recharge?jnl_no={{$item->trans_jnl_no}}" class="btn btn-info"><i class="icon-refresh">充值信息</i></a>
								@endif

					  		</td>
				  		</tr>
					<?php
					}
					}?>
					</tbody>
			</table>

		
						@if(isset($user_id))

							{{$list->appends(array('user_id'=>$user_id))->links('admin.pageInfo')}}

						@elseif(isset($tel))

							{{$list->appends(array('tel'=>$tel))->links('admin.pageInfo')}}

						@elseif(isset($jnl_no))

							{{$list->appends(array('jnl_no'=>$jnl_no))->links('admin.pageInfo')}}

						@elseif(isset($start_time))

							{{$list->appends(array('start_time'=>$start_time,'end_time'=>$end_time,'choice' =>$choice))->links('admin.pageInfo')}}

						@else

							{{$list->links('admin.pageInfo')}}

						@endif


		</section>
  	</section>
</section>
<!--main content end-->
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/sample/jquery/jquery-1.8.3.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="/flatlib/js/bootstrap-paginator.js"></script>
<script type="text/javascript">
	$('.form_date').datetimepicker({
		language:  'fr',
		format:'yyyy-mm-dd hh:ii:ss',
		weekStart: 1,
		todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 1,
		minView: 1,
		forceParse: 0
	});
</script>

@include('admin.footer')


@include('admin.header')

@include('admin.menu')
<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class='row'>
			<section class='col-lg-12'>
				<section class="panel">
					<header class="panel-heading">
						用户列表（累计注册用户: &nbsp;{{ $sum }} 人,&nbsp;&nbsp;今日新增: &nbsp;{{ $todaySum }} 人）
					</header>
					<table class="table table-striped table-advance table-hover">
						<thead>
							<tr>
								<th >ID</th>
								<th >用户昵称</th>
								<th >手机号</th>
								<th >是否锁定</th>
								<th >是否删除</th>
								<th >余额</th>
								<th >头像</th>
								<th >创建时间</th>
								<th >系统版本</th>
								<th >来源</th>
								<th >操作</th>
							</tr>
						</thead>
						<tbody>
							@foreach ($userList as $lv)
							<tr>
								<td><a href="#">{{ $lv->id }}</a></td>
								<td>{{ $lv->nick_name }}</td>
								<td>{{ $lv->tel }}</td>
								<td>@if ($lv->locked === 0) 否  @else 是  @endif</td>
								<td>@if ($lv->is_delete === 0) 否  @else 是  @endif</td>
								<td>{{ $lv->money }}</td>
								<td><img src="{{ $lv->head_pic!=''?$lv->head_pic:'/img/icon_head_big.png'  }}" height=50 /></td>
								<td>{{ date('Y-m-d H:i:s',$lv->create_time) }}</td>
{{--								<td>{{ $lv->create_time}}</td>--}}
								<td>{{ $lv->os_type }}</td>
								<td>
									@if ($lv->channel == 1)
										微信朋友圈
									@elseif ($lv->channel == 2)
										微信好友
									@elseif ($lv->channel == 3)
										新浪微博
									@elseif ($lv->channel == 4)
										QQ
									@elseif ($lv->channel == 5)
										QQ空间
									@elseif ($lv->channel == 6)
										电子邮件
									@elseif ($lv->channel == 7)
										{{$lv->branch_name!=null && $lv->branch_name !=''?$lv->branch_name:$lv->head_name}}
									@else
										第三方
									@endif
								</td>
								<td>
									<a href="/admin/user/edit?id={{$lv->id}}" title="修改信息" class="btn btn-primary btn-xs"><i class="icon-pencil"></i></a>
									<button user_id="{{ $lv->id }}" title="删除记录"  pkid={{$lv->id}} class="btn btn-danger btn-xs delete_btn delete-user"><i class="icon-trash "></i></button>

								</td>
							</tr>
							@endforeach
						</tbody>
					</table>
					@if(isset($is_real))
						{{$userList->appends(array('is_real'=>$is_real))->links('admin.pageInfo')}}
					@else
						{{$userList->links('admin.pageInfo')}}
					@endif
				</section>
			</section>
		</div>
	</section>
</section>
<!--main content end-->
<script>
seajs.use('page/admin/user/list' , function(mod){
	//mod.user();	
});

	$('.delete_btn').bind('click' , function(){
		var id = $(this).attr('pkid');
		if(confirm('是否要删除？')){
			$.get('/admin/user/del' , {'id' : id } , function(res){
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


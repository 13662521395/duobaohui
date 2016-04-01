	<!--sidebar start-->
	  <aside>
		  <div id="sidebar"  class="nav-collapse ">
			  <!-- sidebar menu start-->
				<?php	
				function checkActive($selected,$active){
					if($selected == $active){
						return "active";
					}else{
						return "";
					}
				}
				?>	
				<ul class="sidebar-menu" id="nav-accordion">
					<li class="sub-menu">
						<a class="{{ checkActive($selected , "index") }}" href="/admin/index">
							<i class="icon-dashboard"></i>
							<span>首页</span>
						</a>
						<ul class="sub">
							<li><a  href="/admin/banner/index">Banner</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a class="dcjq-parent @if(isset($parentMenu)) {{checkActive($parentMenu,"activity")}} @endif" href="javascript:;" >
							<i class="icon-laptop"></i>
							<span>活动管理</span>
						</a>
						<ul class="sub">
							<li  class="{{ checkActive($selected , "activity-list-pre") }}"><a href="/admin/activity/list-pre?test=1">活动列表</a></li>
							<li  class="{{ checkActive($selected , "activity-add-activity") }}"><a href="/admin/activity/add-activity">添加活动</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a class="{{ checkActive($selected , "goods") }}" href="javascript:;" >
							<i class="icon-laptop"></i>
							<span>商品管理</span>
						</a>
						<ul class="sub">
							<li><a  href="/admin/goods/list">商品列表</a></li>
							<li><a  href="/admin/goods/add-goods">添加商品</a></li>
						</ul>
					</li>

					<li class="sub-menu">
						<a class="{{ checkActive($selected , "order") }}" href="javascript:;" >
							<i class="icon-laptop"></i>
							<span>订单管理</span>
						</a>
						<ul class="sub">
							<li><a  href="/admin/order/order-list">订单列表</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a class="{{ checkActive($selected , "shaidan") }}" href="javascript:;" >
							<i class="icon-laptop"></i>
						<span>晒单管理</span>
						</a>
						<ul class="sub">
							<li><a  href="/admin/shaidan/list">晒单列表</a></li>
							<li><a  href="/admin/shaidan/win-user-list">中奖用户晒单</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a class="{{ checkActive($selected , "bill") }}" href="javascript:;" >
							<i class="icon-laptop"></i>
						<span>出入账管理</span>
						</a>
						<ul class="sub">
							<li><a  href="/admin/bill/index">统计</a></li>
							<li><a  href="/admin/bill/pay-list">支付记录</a></li>
							<li><a  href="/admin/bill/alipay-account">账务明细异常</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a class="{{ checkActive($selected , "client") }}" href="javascript:;" >
							<i class="icon-laptop"></i>
						<span>客户端管理</span>
						</a>
						<ul class="sub">
							<li><a  href="/admin/client/release-new-version-pre">发布新版本</a></li>
							<li><a  href="/admin/client/version-list">客户端版本管理</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a class="{{ checkActive($selected , "shop") }}" href="javascript:;" >
							<i class="icon-laptop"></i>
						<span>商户管理</span>
						</a>
						<ul class="sub">
							<li><a  href="/admin/shop/add-one-shop">添加商户</a></li>
							<li><a  href="/admin/shop/shop-list">商户列表</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a class="{{ checkActive($selected , "user") }}" href="javascript:;" >
							<i class="icon-laptop"></i>
						<span>用户管理</span>
						</a>
						<ul class="sub">
							<li><a  href="/admin/user/user-list">用户列表</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a class="{{ checkActive($selected , "feedback") }}" href="javascript:;" >
							<i class="icon-laptop"></i>
						<span>用户反馈</span>
						</a>
						<ul class="sub">
							<li><a  href="/admin/opinion/feedback-list">用户反馈列表</a></li>
						</ul>
					</li>
					<li class="sub-menu">
						<a class="{{ checkActive($selected , "admin") }}" href="javascript:;" >
							<i class="icon-laptop"></i>
						<span>管理员</span>
						</a>
						<ul class="sub">
							<li><a  href="/admin/authority/authority-list">访问权限设置</a></li>
							<li><a  href="/admin/authority/authority-user-list">管理员设置</a></li>
							<li><a  href="/admin/authority/authority-group-list">管理组设置</a></li>
						</ul>
					</li>


				</ul>	
				<!-- sidebar menu end-->
			</div>
		</aside>
		<!--sidebar end-->

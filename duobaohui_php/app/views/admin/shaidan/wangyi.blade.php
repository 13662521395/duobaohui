<script id="shaidan_with_wangyi" type="text/html">
	<div class="modal fade in" id="wangyiModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false" style='display:block;z-index:2002'>
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">晒单列表</h4>
				</div>
				<div class="modal-body" style='overflow:auto;'>

							
						<table class="table table-striped table-advance table-hover">
							<thead>
								<tr>
									<th >ID</th>
									<th >用户昵称</th>
									<th >晒单标题</th>
									<th >晒单内容</th>
									<th >操作</th>
								</tr>
							</thead>
							<tbody>
								<%for (var i = 0; i<list.length ;i++){%>
								<tr>
									<td><a href="#"><%= list[i].id %></a></td>
									<td class='nickname_<%= list[i].id %>'><%= list[i].nickname %></td>
									<td class='title_<%= list[i].id %>'><%= list[i].title %></td>
									<td class='content_<%= list[i].id %>'><%= list[i].content %></td>
									<td>
										<button title="查看详情" id='select-wangyi' wangyi-id=<%= list[i].id %> class="btn btn-success btn-xs"><i class=" icon-thumbs-up"></i></button>
									</td>
								</tr>
								<%}%>
							</tbody>
						</table>
						<ul class="pagination pagination-lg">
							<li><a href="?page=">«</a></li>
							<li><a href="?page=">»</a></li>
						</ul>
					
				</div>
				<!--
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default" type="button">Close</button>
					<button class="btn btn-success" type="button">Save changes</button>
				</div>
				-->
			</div>
		</div>
	</div>
</script>

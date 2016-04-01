
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a height="580" class="add" href="/main/add_banner?" target="dialog" mask="true"><span>添加</span></a> </li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="90">
		<thead>
			<tr>
				<th width="80">ID</th>
				<th width="250">标题</th>
				<th>描述 </th>
				<th>bnner图</th>
				<th></th>
			</tr>
		</thead>
		</tbody>
			<?php foreach($bannerList as $vc): ?>
				<tr class="<?php echo $vc->id;?>">
					<td><?php echo $vc->id; ?> </td>
					<td><?php echo $vc->title; ?> </td>
					<td><?php echo $vc->description; ?> </td>
					<td><img src="<?php echo $vc->pic; ?>" width='120'> </td>
					<td>
						<ul class="toolBar">
							<li><a height="580" class="edit" title="编辑" href="/main/edit_banner?id=<?php echo $vc->id ?>" target="dialog" mask="true"><span>&nbsp;&nbsp;</span></a> </li>
							<li><a class="delete" title="确定要删除吗?" target="ajaxTodo"  href="/main/edit_banner?id=<?php echo $vc->id;?>&dt=del"  mask="true"><span>&nbsp;&nbsp;</span></a></li>
						</ul>
					</td>
				</tr>	
			<?php endforeach; ?>
		</tbody>
	</table>
</div>

<?php
/**
* FEROS™ PHP template engine
* @author feros<admin@feros.com.cn>
* @copyright ©2015 feros.com.cn
* @link http://www.feros.com.cn
* @version 2.0.2
*/
?><h3 class="smaller lighter blue">节点管理</h3><div class="ui-pg-div"><a class="purple " href="<?php echo $fromDomain;?>node_add" title="添加根节点"><i class="icon-plus-sign bigger-130"></i></a></div><div class=""><ul id="inbox-tabs" class="inbox-tabs nav nav-tabs tab-space-1"><li><a href="<?php echo $fromDomain;?>node_list" ><i class="blue icon-inbox bigger-130"></i><span class="bigger-110 ">节点</span></a></li><li><a href="<?php echo $fromDomain;?>node_list?tag=menu" ><i class="pink icon-tags bigger-130"></i><span class="bigger-110">菜单</span></a></li></ul></div><div class="table-responsive"><table id="sample-table-2" class="table table-striped table-bordered table-hover"><thead><tr><th class="center"><label><input type="checkbox" class="ace" /><span class="lbl"></span></label></th><th>ID</th><th>名称</th><th>action</th><th class="hidden-480">状态</th><th></th></tr></thead><tbody><?php foreach($nodeList as $val):?><tr><td class="center"><label><input type="checkbox" class="ace" value=""/><span class="lbl"></span></label></td><td><?php echo $val->node_id;?></td><td style="padding-left:<?php echo $val->level*15; ?>px"><?php echo $val->name?></td><td><?php echo $val->action;?></td><td><?php echo $val->status==0 ? '<i class="icon-ok green"></i>' : '<i class="red icon-ban-circle"></i>';?></td><td><div class="visible-md visible-lg hidden-sm hidden-xs action-buttons"><a class="" title="添加子节点" href="<?php echo $fromDomain;?>node_add?node_id=<?php echo $val->node_id;?>"><i class="icon-plus-sign bigger-130"></i></a> &nbsp;&nbsp;

																<a class="green" href="<?php echo $fromDomain;?>node_edit?node_id=<?php echo $val->node_id;?>"><i class="icon-pencil bigger-130"></i></a>
																&nbsp;&nbsp;

																<a class="red dialog del_btn"  dialog_content_id="node_del_dialog" iid="<?php echo $val->node_id;?>" iname="<?php echo $val->name;?>" iurl='<?php echo $fromDomain;?>node_del'  href="javascript:void(0)"><i class="icon-trash bigger-130"></i></a></div></td></tr><?php endforeach;?></tbody></table></div><?php echo $this->fetch('block/model');?>

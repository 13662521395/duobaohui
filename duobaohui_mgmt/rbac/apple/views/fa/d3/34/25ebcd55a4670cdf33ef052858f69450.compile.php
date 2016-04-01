<?php
/**
* FEROS™ PHP template engine
* @author feros<admin@feros.com.cn>
* @copyright ©2015 feros.com.cn
* @link http://www.feros.com.cn
* @version 2.0.2
*/
?><h3 class="smaller lighter blue">权限组管理</h3><div class="ui-pg-div"><a class="purple " href="<?php echo $fromDomain;?>role_add" title="添加权限组"><i class="icon-plus-sign bigger-130"></i></a></div><div class="table-responsive"><table id="sample-table-2" class="table table-striped table-bordered table-hover"><thead><tr><th class="center"><label><input type="checkbox" class="ace" /><span class="lbl"></span></label></th><th>ID</th><th>名称</th><th class="hidden-480">状态</th><th></th></tr></thead><tbody><?php foreach($roleList as $val):?><tr><td class="center"><label><input type="checkbox" class="ace" value="<?php echo $val->role_id;?>"/><span class="lbl"></span></label></td><td><a href="#"><?php echo $val->role_id;?></a></td><td ><?php echo $val->name?></td><td class="hidden-480"><span class="label label-sm label-warning"><?php echo $val->status;?></span></td><td><div class="visible-md visible-lg hidden-sm hidden-xs action-buttons"><a class="green" href="<?php echo $fromDomain;?>role_edit?role_id=<?php echo $val->role_id;?>"><i class="icon-pencil bigger-130"></i></a>																&nbsp;&nbsp;
																<a class="red dialog del_btn"  dialog_content_id="node_del_dialog" iid="<?php echo $val->role_id;?>" iname="<?php echo $val->name;?>" iurl='<?php echo $fromDomain;?>role_del'  href="javascript:void(0)"><i class="icon-trash bigger-130"></i></a></div></td></tr><?php endforeach;?></tbody></table></div><?php echo $this->fetch('block/model');?>

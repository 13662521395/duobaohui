<?php
/**
* FEROS™ PHP template engine
* @author feros<admin@feros.com.cn>
* @copyright ©2015 feros.com.cn
* @link http://www.feros.com.cn
* @version 2.0.2
*/
?><h3 class=" smaller lighter blue">权限用户管理</h3><div class="ui-pg-div"><a class="purple " href="<?php echo $fromDomain;?>user_add" title="添加根节点"><i class="icon-plus-sign bigger-130"></i></a></div><div class="table-responsive"><table id="sample-table-2" class="table table-striped table-bordered table-hover"><thead><tr><th class="center"><label><input type="checkbox" class="ace" /><span class="lbl"></span></label></th><th>ID</th><th>登录名</th><th>昵称</th><th>权限组</th><th></th></tr></thead><tbody><?php foreach($roleUserList as $val):?><tr><td class="center"><label><input type="checkbox" class="ace" value=""/><span class="lbl"></span></label></td><td><a href="#"><?php echo $val->user_id;?></a></td><td><?php echo $val->login_name?></td><td><?php echo $val->nick_name?></td><td class="hidden-480"><?php 
																foreach($val->role as $vr){
																	echo '['.$vr->name . ']';
																}
															?></td><td><div class="visible-md visible-lg hidden-sm hidden-xs action-buttons"><a class="green" href="<?php echo $fromDomain;?>user_edit?user_id=<?php echo $val->user_id;?>"><i class="icon-pencil bigger-130"></i></a>
																&nbsp;&nbsp;

																<a class="red dialog del_btn"  dialog_content_id="node_del_dialog" iid="<?php echo $val->user_id;?>" iname="<?php echo $val->login_name;?>" iurl='<?php echo $fromDomain;?>user_del'  href="javascript:void(0)"><i class="icon-trash bigger-130"></i></a></div></td></tr><?php endforeach;?></tbody></table></div><?php echo $this->fetch('block/model');?>

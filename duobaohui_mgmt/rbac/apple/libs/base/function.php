<?php
function dump($p,$die=1){
    header("Content-type: text/html; charset=utf-8");
	echo '<pre>';
    var_dump($p);
	echo '</pre>';
	if($die) die();
}

function pr($p,$die=1){
	echo '<pre>';
	empty($p) ? var_dump($p)  :  print_r($p);
	echo '</pre>';
	if($die) die();
}

function nodeEncrypt($salt,$nodeId){
	return substr(md5($salt.$nodeId),0,6);
}

//menu
function getLi($v, $activeIds){
	$active = in_array($v->node_id, $activeIds) ? 'class="active"' : '';
	$action = $v->action;
	$action .= strpos($v->action, '?') ? '&mid='.$v->node_id : '?mid='.$v->node_id;
	$li =  <<<EOF

						<li {$active}>
							<a href="{$action}">
								<i class="icon-text-width"></i>
								<span class="menu-text">{$v->name}</span>
							</a>
						</li>
EOF;

	return $li;
}

function getUl($menuList, $activeIds ){
	$str = '';
	foreach($menuList as $v){
		//$open = in_array($v->node_id, $activeIds) ? 'class="active open"' : '';
		$open = in_array($v->node_id, $activeIds) ? 'active"' : '';
		if($v->type==0){
			$li = isset($v->child) ? getUl($v->child, $activeIds) : '';

			$str .=  <<<EOF

						<li >
							<a href="#" class="dropdown-toggle $open">
								<i class="icon-file-alt"></i>
								<span class="menu-text">
									{$v->name}
								</span>
								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu">
								{$li}
							</ul>

EOF;

		}else{
			$str .= getLi($v, $activeIds);
		}
	}
	return $str;
}


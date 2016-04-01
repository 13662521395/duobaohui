INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path) VALUES('客户端配置','/admin/client/config','10','0','1','12','2','0','12');
UPDATE sh_rbac_node SET type='0' WHERE node_id='12';


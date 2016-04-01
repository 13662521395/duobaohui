#!/bin/bash
#	author			吴辉
#	time			2014-12-04
#	说明:
#			该脚本是生成helper目录下的文件的
#	注意:
#			需要在$HOME目录下建一个.my.cnf的文件
#			在.my.cnf里面添加:
#				[client]
#				password = ********
#			确定数据库和密码是对的

MYSQL=`which mysql`

DATABASE='sh_duobaohui'
USER='root'

echo "输入表名字,多个以空格隔开,如果需要全部的表,请输入'all'"
read TABLES

if [ "$TABLES" = "all" ];then
	TABLES=`$MYSQL test -u $USER <<EOF
	use $DATABASE;
	show tables;

EOF`
	TABLES_SQL=$TABLES
	OLD_IFS="$IFS"
	IFS=$'\x0A'
	tables=($TABLES)

	unset tables[0]
else
	IFS=' '
	TABLES_SQL=`$MYSQL test -u $USER <<EOF
	use $DATABASE;
	show tables;

EOF`
	tables=($TABLES)
fi


for table in ${tables[@]}
do
	if echo "${TABLES_SQL}" | grep -w "$table" &>/dev/null;then
		echo "正在为生成文件做准备..."
	else
		echo -e "表$table不存在\n\n";
		continue
	fi
	FIELDS=`$MYSQL test -u $USER <<EOF
	use $DATABASE;
	SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = "$DATABASE" AND TABLE_NAME = "$table";
EOF`
	
	IFS=$'\x0A'
	fields=($FIELDS)
	unset fields[0]

	column=''
	i=0
	for field in ${fields[@]}
	do
		if [ $i -eq 0 ];then
			column=$field
			((i++))
		else
			column=$column','$field
		fi
	done
	
	IFS='_'
	file=($table)
	fileName='DB'
	unset file[0]
	for string in ${file[@]}
	do
		fileName=$fileName`echo $string | sed 's/\<[a-z]/\U&/g'`
	done
	className=$fileName
	fileName=$fileName".class.php"
	if [ -f $fileName ];then
		`rm $fileName`
	fi
	echo "正在创建$fileName中...."
	echo -e "<?php \n\n\n namespace Gate\Package\Helper;\n\n\n\t class $className  extends \Phplib\DB\DBModel { \n\n\t\t const _DATABASE_\t= '$DATABASE'; \n\n\t\t const _TABLE_\t\t= '$table'; \n\n\t\t const _FIELDS_\t\t= '$column';\n}" >> $fileName
	echo -e "$fileName创建完成\n\n"


done
IFS=$OLD_IFS

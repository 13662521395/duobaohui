#!/bin/bash

path_log="/home/wuhui/test_online/online_log"
file_log="guang_onlone.log"
if [ ! -e $path_log/$file_log ];then
	echo "没有日志文件哦"
	exit
fi

i=1
for line in `cat $path_log/$file_log`
do
	echo $line;
	if [ $i -eq 1 ];then
		path_local=$line
		i=`expr $i + 1`
	elif [ $i -eq 2 ];then
		path_line=$line
		i=`expr $i + 1`
	elif [ $i -eq 3 ];then
		path_backup=$line
		i=`expr $i + 1`
	elif [ $i -eq 4 ];then
		dir=$line
		i=`expr $i + 1`
	fi
done

if [ ! -e $path_backup/$dir ];then
	echo "没有备份文件"
fi

echo "确定要从$path_backup恢复$dir到$path_line"
echo -e "请确认(y/n)\c"
read queren
if [ -e $queren ];then
	exit
fi
if [ $queren = "y" ];then
	echo "开始恢复....."
elif [ $queren = "n" ];then
	exit
fi

`cp -rf $path_backup/$dir $path_line`

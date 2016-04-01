#!/bin/bash

#by wuhui 2014/05/29

#要上线的代码路径
#这里要写绝对路径,要精确到项目目录的上一层(只需要写到上一层)
path_local="/home/wuhui"

#线上的代码路径
#这里要写绝对路径,要精确到项目目录的上一层(只需要写到上一层)
#可以写远程地址
path_line="/home/work"

#项目目录
dir="d9quan.com"

#备份的代码目录
year=`date '+%Y'`
month=`date '+%m'`
day=`date '+%d'`
time=`date '+%H-%M'`

#备份的代码文件名,是用这个文件名来压缩的
backupFileName=`date '+%S'`

#备份的代码目录路径
path_backup="/home/backup/d9quan/$year/$month/$day/$time"

echo `mkdir -p $path_backup`

#上线前的准备
echo "请输入要上线的代码文件夹或文件名的路径,路径从d9quan.com开始"
echo "如gate/views/header.view.php,这是只上线header.view.php"
echo "如gate/views,这是只上线views文件夹"
echo "如果上线整个项目,就直接回车"


read path

if [ -e $path ];then
	dir="d9quan.com"
else
	OLD_IFS="$IFS"
	IFS="/"
	arr=($path)
	IFS="$OLD_IFS"

	len=${#arr[*]}
	path="d9quan.com"
	#为了判断在上线路径上要不要加"d9quan.com"
	i=0
	for x in ${arr[@]} 
	do 
		if [ $i -eq `expr $len - 1` ];then
			if [ -e $path_local/$path/$x ];then
				dir="$x"
			fi
		else
			path="$path/$x"
			#这里要注意,线上路径要比线下路径少一层
			echo "计算路径和上线目录"
			if [ -e $path_local/$path ];then
				dir="$x"
			else
				path="$x"
			fi
		fi
		i=`expr $i + 1`
	done
fi

path_local="$path_local/$path"
path_line="$path_line/$path"

echo "要上线的代码路径为$path_local"
echo "线上的代码路径为$path_line"
echo "要上线的目录为$dir"

if [ ! -e $path_local ];then
	echo "$path_local不存在"
	exit
fi
#上线前的准备end



queren=
echo -e "请确认(y/n)\c"
read queren
if [ -e $queren ];then
	exit
fi
if [ $queren = "y" ];then
	echo "开始上线准备....."
elif [ $queren = "n" ];then
	exit
fi



#开始上线

#echo "正在压缩线上文件..."
#echo `cd $path_line && tar zcvf $backupFileName.tar.gz $dir`
#if [ -e "$path_line/$backupFileName.tar.gz" ];then
#	echo "正在备份..."
#	echo "创建备份文件夹,以时间来命名"
#	echo `mkdir -p $path_backup`
#	if [ -d $path_backup ];then
#		echo "准备移动"
#		echo `mv $path_line/$backupFileName.tar.gz $path_backup`
#		if [ -e "$path_backup/$backupFileName.tar.gz" ];then
#			echo "备份成功,备份的代码路径为$path_backup/$backupFileName.tar.gz"
#		else
#			echo `rm -rf $path_line/$backupFileName.tar.gz`
#			echo "备份失败,请重试..."
#			exit
#		fi
#	fi
#fi
#备份文件
echo "备份文件"
echo `rsync -vzrtopgl --size-only --progress --delete -e ssh root@223.4.240.81:$path_line/$dir $path_backup`
echo "备份成功"
echo "小心咯,准备同步代码咯"
echo `rsync $path_local/$dir -bav --exclude-from=exclude.list root@223.4.240.81:$path_line`
echo "好像成功了!去看看吧"

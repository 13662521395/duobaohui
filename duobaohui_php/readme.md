#### 注意事项###
#检查新环境是不是php5.6 以上！

#给app/storage写权限
chmod -R 775 appname/app/storage

# 每次安装新项目代码，或更新命名空间（router,controller,model,lib）,都要执行这个, !上线尤其注意不要忘记
php composer.phar dump-autoload

#测试脚本，在项目根目录下
php phpunit.phar
或: /Applications/MAMP/bin/php/php5.6.7/bin/php 

#logs
use Illuminate\Support\Facades\Log;//引入日志类
代码加入：Log::info('==============='.$msg);
linux: tail -f app/storage/logs/laravel.log



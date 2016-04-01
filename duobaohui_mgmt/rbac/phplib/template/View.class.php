<?php

/**
 * FerOS PHP template engine
 * @author feros<admin@feros.com.cn>
 * @copyright ©2014 feros.com.cn
 * @link http://www.feros.com.cn
 * @version 2.0.2
 */
namespace Phplib\Template;
if (version_compare(PHP_VERSION, '5.3.0', '<'))
    die('require PHP > 5.3.0 !');


/**
 * 模板引擎
 * @author sanliang
 */
class View {

    const VERSION = '2.0.2';
    const ENCODING = 'UTF-8';
    const NAME = 'FEROS™ PHP template engine';
    const DS = DIRECTORY_SEPARATOR;
    const DIR = __DIR__;
    const CACHE_SUFFIX = '.cache.php';
    const COMPILE_SUFFIX = '.compile.php';

    //配制
    public $config;
    //语言包
    public $language;
    //模板样式
    public $theme;
    //模板变量
    public $__vars = array();
    public static $cache_file = array(), $runtime, $runtime_start, $runtime_end, $_content;

    public function __construct($config_file = null) {
        $this->config = (object) include (is_file($config_file) ? $config_file : (self::DIR . self::DS . 'Config' . self::DS . 'global.php'));
        $this->language = (object) include (self::DIR . self::DS . 'Lang' . self::DS . $this->config->template_lang . '.php');
    }

    public function __set($var, $value) {
        $this->assign($var, $value);
    }

    /**
     * 增加模板目录
     * @param string $dir
     * @return \feros\view
     */
    public function set_template_path($dir) {
        array_merge($this->config->template_path, $dir);
        return $this;
    }

    /**
     * 返回模板目录
     * @return array
     */
    public function get_template_path() {
        return (array) $this->config->template_path;
    }

    /**
     * 返回缓存文件
     * @param string $template 指定要调用的模板文件
     * @param string $cacheid 缓存ID
     * @return string
     */
    public function get_cache_file($template, $cacheid = NULL) {
        return rtrim($this->config->cache_dir, '\\//') . self::DS . $this->resolve_file($template, $cacheid) . self::CACHE_SUFFIX;
    }

    /**
     * 返回编译文件
     * @param string $template 指定要调用的模板文件
     * @param string $cacheid 缓存ID
     * @return string
     */
    public function get_compile_file($template, $cacheid = NULL) {
        return rtrim($this->config->compile_dir, '\\//') . self::DS . $this->resolve_file($template, $cacheid) . self::COMPILE_SUFFIX;
    }

    /**
     * 返回执行时间
     * @return string
     */
    public function get_runtime() {
        return self::$runtime = number_format((self::$runtime_end? : $this->get_microtime()) - self::$runtime_start, 4) . 's';
    }

    private function get_microtime() {
        list($microtime_1, $microtime_2) = explode(' ', microtime());
        return $microtime_1 + $microtime_2;
    }

    /**
     * 设置当前输出的模板主题
     * @access public
     * @param  mixed $theme 主题名称
     * @return View
     */
    public function theme($theme) {
        $this->theme = $theme . self::DS;
        return $this;
    }

    /**
     * 注入变量
     * @access public
     * @param string|NULL $var 变量名称
     * @param * $value 值
     */
    public function assign($var, $value = NULL) {
        is_array($var) ? ($this->__vars = array_merge($this->__vars, $var)) : $this->__vars[$var] = $value;
        return $this;
    }

    /**
     * 模板显示 调用内置的模板引擎显示方法，
     * @access public
     * @param string $template 指定要调用的模板文件
     * @param string $cacheid 缓存ID
     * @param int|boog $cachetime 缓存|缓存时间
     * @param string $charset 模板输出字符集
     * @param string $type 输出类型
     * @return void
     */
    public function display($template, $cacheid = NULL, $cachetime = NULL, $charset = NULL, $type = NULl) {
        header('Content-Type:' . ($type? : $this->config->template_type) . ';charset=' . ($charset? : $this->config->template_charset));
        $content = $this->fetch($template, $cacheid, $cachetime);
//        if ($this->config->header_gzip)
//            $this->ob_gzip($content);
        return self::$_content.=$content;
    }

    /**
     *  获取输出页面内容
     * @access public
     * @param string $template 指定要调用的模板文件
     * @param int|bool $cachetime 缓存|缓存时间
     * @return string
     */
    public function fetch($template, $cacheid = NULL, $cachetime = NULL) {
        self::$runtime_start = self::$runtime_start? : $this->get_microtime();
        if ($cachetime) {
            if ($this->is_cached($template, $cacheid)) {
                self::$runtime_end = $this->get_microtime();
                return file_get_contents($this->get_cache_file($this->get_template_file($template), $cacheid));
            }
        }

        ob_start();
        ob_implicit_flush(0);
        extract($this->__vars, \EXTR_OVERWRITE);
        $this->get_template_file($template);
        include $this->compile($template, $cacheid);
        self::$runtime_end = $this->get_microtime();
        $content = ob_get_clean();
        if ($cachetime) {
            $cache_file = $this->get_cache_file($template, $cacheid);
            $this->mkdir(dirname($cache_file));
            file_put_contents($cache_file, $content);
            $this->deny($this->config->cache_dir);
            if ((int) $cachetime > 0)
                $cachetime = $cachetime + time();
            touch($cache_file, $cachetime? : time());
        }

        return $content;
    }

    /**
     * Gzip数据压缩传输 如果客户端支持
     * @param string $content
     * @return string
     */
    public function ob_gzip(&$content) {
        if (!headers_sent() && extension_loaded("zlib") && isset($_SERVER["HTTP_ACCEPT_ENCODING"])   &&  strstr($_SERVER["HTTP_ACCEPT_ENCODING"], "gzip")) {
            $content = gzencode($content, 9);
            header("Content-Encoding: gzip");
            header("Vary: Accept-Encoding");
            header("Content-Length: " . strlen($content));
        }
        return $content;
    }

    /**
     * 检测缓存是否存在
     * @access public
     * @param string $template 指定要调用的模板文件
     * @param string $cacheid 缓存ID
     * @return boolean
     */
    public function is_cached($template, $cacheid = NULL) {
        static $cached = array();
        $temp = md5($template . $cacheid);
        if (isset($cached[$temp]))
            return $cached[$temp];
        $this->get_template_file($template);
        $cache_file = $this->get_cache_file($template, $cacheid);
        if (!file_exists($cache_file)) {
            $cached[$temp] = false;
            return false;
        }if ($this->config->cache_lifetime == -1) {
            $cached[$temp] = true;
            return true;
        } elseif (filemtime($cache_file) + $this->config->cache_lifetime < time()) {
            $cached[$temp] = false;
            return false;
        } else {
            $savet = filemtime($template);
            $fromt = filemtime($cache_file);
            if ($savet > $fromt) {
                $cached[$temp] = false;
                return false;
            }
            $cached[$temp] = true;
            return true;
        }
    }

    /**
     * 编译
     * @param string $template
     */
    private function compile($template, $cacheid = NULL) {
        $compile = $this->get_compile_file($template, $cacheid);
        if (file_exists($compile)) {
            $savet = filemtime($template);
            $fromt = filemtime($compile);
            if ($savet <= $fromt) {
                return $compile;
            }
        }
        $content = file_get_contents($template);
        require_once self::DIR . self::DS . 'Compile.class.php';
        new \Phplib\Template\Compile($this, $content, $template);
        $this->mkdir(dirname($compile));
        if ($this->config->strip_space) {
            $content = preg_replace(array('~>\s+<~', '~>(\s+\n|\r)~'), array('><', '>'), $content);
            $content = str_replace('?><?php', '', $content);
        }
        file_put_contents($compile, $content);
        $this->deny($this->config->compile_dir);
        return $compile;
    }

    /**
     * 删除缓存
     * @access public
     * @param string $template 指定要调用的模板文件
     * @param string $cacheId 缓存ID
     * @return boolean
     */
    public function delete_cached($template, $cacheId = null) {
        $cache = $this->get_cache_file($template, $cacheId);
        return file_exists($cache) ? unlink($cache) : false;
    }

    /**
     * 删除编译
     * @access public
     * @param string $template 指定要调用的模板文件
     * @param string $cacheId 缓存ID
     * @return boolean
     */
    public function delete_compile($template, $cacheId = null) {
        $compile = $this->get_compile_file($template, $cacheId);
        return file_exists($compile) ? unlink($compile) : false;
    }

    /**
     * 清空缓存
     * @access public
     * @return boolean
     */
    public function flush_cached() {
        return $this->clear_recur($this->config->cache_dir);
    }

    /**
     * 清空编译
     * @access public
     * @return boolean
     */
    public function flush_compile() {
        return $this->clear_recur($this->config->compile_dir);
    }

    /**
     * 解释引擎文件
     * @access public
     * @param string $template
     * @param string $cacheid
     * @return string
     */
    public function resolve_file($template, $cacheid = NULL) {
        static $resolve = array();
        //$template = md5($template . $cacheid);
		$template = substr($template, strpos($template, '/tpls/')+6);
		$template = str_replace($this->config->template_suffix, '',$template);
        if (isset($resolve[$template]))
            return $resolve[$template];
        if ($this->config->use_sub_dirs) {
            $dir = '';
            for ($i = 0; $i < 6; $i++)
                $dir .= ($template{$i}) . ($template{ ++$i}) . self::DS;
            $template = $dir . md5($template);
        }
        return $resolve[$template] = $template;
    }

    /**
     *  获取模板文件
     * @access public
     * @param string $template  模板
     * @return string
     */
    private function get_template_file(&$template) {
        static $templateFile = array();
        $template = str_replace('\\//', self::DS, $template);
        if (isset($templateFile[$template])) {
            $template = $templateFile[$template];
            return $template;
        }
        if (file_exists($template)) {
            $t = $template;
        } else {
            $suffix = $this->config->template_suffix;
            foreach ($this->get_template_path() as $row) {
                $t = rtrim($row, '\\//') . self::DS . ($this->theme? : '') . $template . $suffix;
                if (file_exists($t)) {
                    break;
                }
            }
            if (!file_exists($t))
                throw new \Exception($this->language->template_not_exist);
        }
        if (\filesize($t) > ((int) $this->config->template_size * 1024 * 1024))
            throw new \Exception($this->language->template_too);
        return $template = $templateFile[$template] = $t;
    }

    /**
     * 递归的创建目录
     * @param string $path 目录路径
     * @param int $permissions 权限
     * @return boolean
     */
    public function mkdir($path, $permissions = 0777) {
        if (is_dir($path))
            return true;
        $_path = dirname($path);
        if ($_path !== $path)
            self::mkdir($_path, $permissions);
        return @mkdir($path, $permissions);
    }

    /**
     * 递归的删除目录
     * @param string $dir 目录
     * @param Boolean $delFolder 是否删除目录
     */
    public function clear_recur($dir, $delFolder = false) {
        if (!is_dir($dir))
            return false;
        if (!$handle = @opendir($dir))
            return false;
        while (false !== ($file = readdir($handle))) {
            if ('.' === $file || '..' === $file)
                continue;
            $_path = $dir . self::DS . $file;
            if (is_dir($_path)) {
                $this->clear_recur($_path, $delFolder);
            } elseif (is_file($_path))
                @unlink($_path);
        }
        $delFolder && @rmdir($dir);
        @closedir($handle);
        return true;
    }

    /**
     * 写入安全文件
     */
    private function deny($dir) {
        is_file($dir . self::DS . '.htaccess')? : file_put_contents($dir . self::DS . '.htaccess', 'Deny from all');
        is_file($dir . self::DS . 'index.html')? : file_put_contents($dir . self::DS . 'index.html', '');
    }

    public function __destruct() {
        $this->show();
    }

    private function show() {
        if (!empty(self::$_content)) {
            header('Cache-control:private ');
            //header('X-Powered-By:FEROS PHP template engine');
			//if($this->checkError()){
				if ($this->config->header_gzip)
					$this->ob_gzip(self::$_content);
			//}
            echo self::$_content;
        }
    }

	private function checkError(){
			$arr = error_get_last();//获取刚发生的错误信息，并返回数组，无错返回null.
			if(!is_null($arr)) //不为null,则表示出错了
			{
				echo '<pre>';
				print_r($arr); //具体错误信息，可根据需要修改。
				echo '</pre>';
				return 0;
			}
			return 1;
	}

}

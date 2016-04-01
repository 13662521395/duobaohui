<?php

namespace Laravel\Service;

class HelpService {

    /**
     * 数组转对象
     * @param $e
     * @return object|void
     */
    public static function arrayToObject($e){
        if( gettype($e)!='array' ) return;
        foreach($e as $k=>$v){
            if( gettype($v)=='array' || getType($v)=='object' )
                $e[$k]=(object)HelpService::arrayToObject($v);
        }
        return (object)$e;
    }

    /**
     * 对象转数组
     * @param $e
     * @return array|void
     */
    public static function objectToArray($e){
        $e=(array)$e;
        foreach($e as $k=>$v){
            if( gettype($v)=='resource' ) return;
            if( gettype($v)=='object' || gettype($v)=='array' )
                $e[$k]=(array)HelpService::objectToArray($v);
        }
        return $e;
    }

}
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="Author" content="shinc" />
    <meta name="Copyright" content="shinc" />
    <meta name="keywords" content="shinc" />
    <meta name="description" content="shinc" />
    <title></title>
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
    <link href="/flatlib/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
        html,body {TEXT-ALIGN: center;width:100%;height:100%;background-color: #efefef; margin: 0;padding: 0;}
        div.main{ width: 100%; height: 100%;  background-color: #efefef; font-size: 62.5%; }
        div.top,div.content,div.msg{background-color: #f7f7f7;}
        div.content{width: 100%; height: 150px;;text-align: center;}
        div.content span{height: 100%; display: inline-block; vertical-align: middle;}
        div.content img{width:127px; height: 70%;vertical-align: middle;}
        div.msg{padding-left: 10%; padding-right: 10%; line-height: 1.5; font-size: 4vw; color: #666666; padding-bottom: 20px;}
        div.detail{width: 100%;margin-top: 15px;}
        div.detail .title{text-align: left; font-size: 3.5vw; line-height: 3.5vw; width: 90%; min-height:3.5vw; margin: 10px auto 0 auto; color: gray;}
        div.title{padding-top: 10px; padding-bottom: 10px;}
        hr { margin-top: 0px; margin-bottom: 10px; border: 0; border-top: 1px solid #eeeeee; }
        div.detail,div.detail_dbh{background-color: #ffffff;}
        .tbl_title{color: #6ad3ff;font-size: 3.5vw;}
        .tbl_title_left{white-space:nowrap; text-overflow:ellipsis; width: 100%; overflow: hidden; text-align: left;}
        .nowrap{white-space: nowrap}
        table.dbh{width: 90%;table-layout: fixed;}
        .div_dbh{width: 100%; height: auto; margin: 6px auto;}
        .dbh_number{float: left; width: 25%; height:auto; min-height: 1.8em; font-size: 4vw;}
        .clear{clear: both}
        .dbh_more{color: #6ad3ff;}
        .hide{display: none;}
    </style>
</head>
<body>
<div class="main">
    <div class="top">
        <div class="content">
            <span></span>
            <img src="/imgs/icon_payok2.png" id="ok_img">
        </div>
        <div class="msg">恭喜您,参与成功！<br/> 请等待系统为您揭晓！</div>
    </div>
    <div class="detail">
        <div class="title">您成功参与了{{$renci}}件商品共{{$renci}}人次夺宝，信息如下:</div>
        <hr/>
        <div class="detail_dbh">
            <table border="0" class="dbh" align="center">
                <tr class="tbl_title">
                    <td align="left" valign="middle" width="80%">
                        <div class="tbl_title_left">(第{{$nperStr}}期) {{$goodName}}</div>
                    </td>
                    <td class="nowrap" align="right">{{$renci}}人次</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div class="div_dbh">


                                        {{--<div class="dbh_number show_code">{{$code}}</div>--}}


                                <div class="dbh_number more hide"></div>
                                <div class="dbh_number dbh_more hide" id="more" val="1">查看更多</div>
                                <div class="clear"></div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>

<script>
    $(document).ready(function(){
        var img = $('#ok_img');
        var height = img.height();
        img.width(height+3);
        console.log(height);

        var more = $('#more');
        more.bind('click', function(){
            var val = $(this).attr('val');
            if(val=='1'){
                $('.more').each(function(){
                    $(this).removeClass('hide');
                });
                $(this).html('隐藏部分');
                $(this).attr('val','0');
            }else{
                $('.more').each(function(){
                    $(this).addClass('hide');
                });
                $(this).html('查看更多');
                $(this).attr('val','1');
            }
        });



        //获取夺宝号
        $.get('/alipay/notifyUrl/get-user-code-list', {'buy_id':'{{$jnl_no}}','pay_type':0}, function(data) {
            //alert(data.data.codeList);
            if(data.code  == 1){
                var forMax = data.data.codeList.length;

                for(i=0;i<forMax;i++){
                    var createobj = $("<div class='dbh_number show_code'>"+data.data.codeList[i]+"</div>");
                    $(".div_dbh").append(createobj);
                }

                if(forMax > 5 ){
                    $('.dbh_more').each(function(){
                        $(this).removeClass('hide');
                    });
                }
            }
        });

        //获取夺宝号
        $.get('/activity/index/detail-usercode', {'period_id':{{$period_id}},'page':1,'length':300}, function(data) {
            //alert(data.code);
            if(data.code  == 1){
                var forMax = data.data.length;
                //alert(forMax);
                for(i=0;i<forMax;i++){
                    var createobj = $("<div class='dbh_number more hide'>"+data.data[i]+"</div>");
                    $(".div_dbh").append(createobj);
                }
            }
        });

        $('.dbh_number').bind('click', function(){
            var val = $(this).attr('val');
            if(val=='0'){
                $('.show_code').each(function(){
                    $(this).addClass('hide');
                    $('.dbh_more').addClass('hide');
                });
            }
        });
//        }
    });


</script>
</body>
</html>

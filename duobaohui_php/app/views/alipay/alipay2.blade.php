<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="Author" content="shinc" />
    <meta name="Copyright" content="shinc" />
    <meta name="keywords" content="shinc" />
    <meta name="description" content="shinc" />
    <title></title>
    <!--introduce sea module-->
    {{ HTML::script('/flatlib/js/jquery-1.8.3.min.js') }}
    {{ HTML::style('/flatlib/css/bootstrap.css') }}
    {{ HTML::style('/css/style.css') }}
</head>
<body>
    <div class="main">
        <div class="top">
            <table width="100%">
                <tr>
                    <td align="left">总需支付</td>
                    <td align="right">{{ $total }}元</td>
                </tr>
            </table>
        </div>
        <div class="middle">
            <table width="90%" align="center" cellspacing="10" class="table-detail">
                <tr><td>&nbsp;</td></tr>
                <tr style="line-height: 1.5">
                    <td align="left">
                        (第{{ $nperStr }}期)<br>
                        {{ $goodDescribe }}
                    </td>
                    <td>&nbsp;</td>
                    <td align="right" valign="middle" style="white-space: nowrap">{{ $renci }}人次</td>
                    <td>&nbsp; </td>
                </tr>
                <tr><td>&nbsp; </td></tr>
            </table>
        </div>
        <div class="money">
            {{--<div>--}}
                {{--<table width="100%" align="center" cellspacing="10" width="100%">--}}
                    {{--<tr>--}}
                        {{--<td align="left">--}}
                            {{--夺宝币支付 ( 余额 : {{$userMoney}} )--}}
                        {{--</td>--}}
                        {{--<td align="right" valign="middle">--}}
                            {{--{{$userMoney}}元 <input name="Fruit" class="checkbox1" onclick="check()" num="0" type="checkbox" value="" />--}}
                        {{--</td>--}}
                    {{--</tr>--}}
                {{--</table>--}}
            {{--</div>--}}
            <hr>
            <div class="money-res">
                <div>结算: &nbsp;<label class="money-label">{{$total}}</label>&nbsp;元</div>
            </div>
        </div>
        <div class="other-pay">
            <div class="table-detail">
                其它支付方式
            </div>
            <div>
                <table border="0" style="padding: 0;margin:0;">
                    <tr>
                        <td width="30"><input type='radio' name='enabled' value='1' checked /></td>
                        <td align="right"><img src="/imgs/alipay_icon.png" width="30" height="30"/></td>
                        <td align="left">&nbsp;支付宝支付</td>
                    </tr>
                </table>
            </div>
        </div>
        <div>
            <button type="submit" onclick="submit()" class="btn btn-danger btn-confirm">确认支付</button>
        </div>
    </div>
</body>
</html>
<script>
    {{--function check()--}}
    {{--{--}}
        {{--var money = $('.checkbox1').attr('num');--}}
        {{--if(money == 0){--}}
            {{--if({{$userMoney}} > {{$total}}){--}}
                {{--var a = 0.00;--}}
            {{--}else{--}}
                {{--var a = {{$total}}-{{$userMoney}}+'.00';--}}
            {{--}--}}
            {{--var checkbox = $('.money-label').text(a);--}}
            {{--$('.checkbox1').attr('num',1);--}}
        {{--}else{--}}
            {{--var a = {{$total}}+'.00';--}}
            {{--var checkbox = $('.money-label').text(a);--}}
            {{--$('.checkbox1').attr('num',0);--}}
        {{--}--}}
    {{--}--}}

    function submit(){
        var money = $('.money-label').text();

        {{--if(money == 0 ){--}}
            {{--$.get('/period/duobao/duobao-balance', {'jnl_no':'{{$out_trade_no}}'}, function(data) {--}}
                {{--//alert(data.code);--}}
                {{--if(data.code == 1){--}}
                    {{--window.location="http://dev.api.duobaohui.com/alipay/webapi/return-info?out_trade_no={{$out_trade_no}}&nperStr={{ $nperStr }}&goodName{{$goodName}}";--}}
                {{--}else{--}}
                    {{--window.location="http://dev.api.duobaohui.com/alipay/webapi/return-faild?msg="+data.msg;--}}
                {{--}--}}

            {{--});--}}
        {{--}else{--}}
            //发起支付
            window.location="/alipay/webapi/webpay?out_trade_no={{$out_trade_no}}&subject={{$goodName}}&total_fee="+money+"&body={{$goodDescribe}}&payment_type=1";
        {{--}--}}
    }

</script>

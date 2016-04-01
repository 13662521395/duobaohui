<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>火炉火晒单</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/shaidan.css?v=1234526" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/dish.js" type="text/javascript"></script>
    {{--<script src="/js/bootstrap.min.js" type="text/javascript"></script>--}}
</head>

<body>
    <header>
        <div class="title">
            火炉火幽灵牛肉2-3人精品套餐
        </div>
        <div class="time clearfix">
            <div class="col-xs-6 nickname fontBlue">若再见说再见</div>
            <div class="col-xs-6 nowtime">{{$date}}</div>
        </div>
        <div class="address">(北京 望京火炉火)</div>
    </header>
    <section class="details">
        <table align="center" border="0" cellpadding="3">
            <tr>
                <td class="tb_left">
                    获奖商品:
                </td>
                <td class="tb_right fontBlue" align="left">
                    (第520期)火炉火幽灵牛肉2-3人精品套餐
                </td>
            </tr>
            <tr>
                <td class="tb_left">
                    本期参与:
                </td>
                <td class="tb_right fontBlue" align="left">
                    <label class="fontRed">10</label>人次
                </td>
            </tr>
            <tr>
                <td class="tb_left">
                    幸运号码:
                </td>
                <td class="tb_right fontRed" align="left">
                    32021
                </td>
            </tr>
            <tr>
                <td class="tb_left">
                    揭晓时间:
                </td>
                <td class="tb_right fontBlue" align="left">
                    2016-01-06 12:50:49
                </td>
            </tr>
        </table>
    </section>
    <div class="content">
        菜有点多，两妹子吃不了，味道还不错。幽灵牛肉赞一个，自助汉堡太油了，小菜不错，部队火锅很辣很辣，早知道换鱼饼火锅了，元祖拌饭很一般的说。
    </div>
    <div class="imgs"><img src="/image/dish/sd_hlh_1.png" class="alignCenter img-responsive"/></div>

    @include('dish.downPannel')
</body>
</html>
<script type="application/javascript">
    $(document).ready(function(){
        var downBtn = $('.goDownload');

        downBtn.on('click',function(){
            window.location.href = "http://www.duobaohui.com/system/download/share?channel=2";
        });
    });
</script>
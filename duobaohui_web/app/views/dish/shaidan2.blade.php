<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>便宜坊晒单</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/shaidan.css?v=1214566" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/dish.js" type="text/javascript"></script>
    {{--<script src="/js/bootstrap.min.js" type="text/javascript"></script>--}}
</head>

<body>
    <header>
        <div class="title">
            矮油，菜还是蛮不错的
        </div>
        <div class="time clearfix">
            <div class="col-xs-6 nickname fontBlue">地球保卫长</div>
            <div class="col-xs-6 nowtime">{{$date}}</div>
        </div>
        <div class="address">(北京 学府路便宜坊)</div>
    </header>
    <section class="details">
        <table align="center" border="0" cellpadding="3">
            <tr>
                <td class="tb_left">
                    获奖商品:
                </td>
                <td class="tb_right fontBlue" align="left">
                    (第20期)便宜坊2-3人餐
                </td>
            </tr>
            <tr>
                <td class="tb_left">
                    本期参与:
                </td>
                <td class="tb_right fontBlue" align="left">
                    <label class="fontRed">5</label>人次
                </td>
            </tr>
            <tr>
                <td class="tb_left">
                    幸运号码:
                </td>
                <td class="tb_right fontRed" align="left">
                    52321
                </td>
            </tr>
            <tr>
                <td class="tb_left">
                    揭晓时间:
                </td>
                <td class="tb_right fontBlue" align="left">
                    2016-01-04 14:50:49
                </td>
            </tr>
        </table>
    </section>
    <div class="content">
        数不清第几次中奖了，但是奖品是美食还是头一次，而且上菜好快的，第一次来便宜坊，感觉不错哟，那个口水鱼真绝了，以后还想来
    </div>
    <div class="imgs"><img src="/image/dish/sd_pyf_1.png" class="alignCenter img-responsive"/></div>
    <div class="imgs"><img src="/image/dish/sd_pyf_2.png" class="alignCenter img-responsive"/></div>
    <div class="imgs"><img src="/image/dish/sd_pyf_3.png" class="alignCenter img-responsive"/></div>

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
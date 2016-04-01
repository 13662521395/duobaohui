<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>蟹老宋香锅晒单</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/dish.js" type="text/javascript"></script>
    {{--<script src="/js/bootstrap.min.js" type="text/javascript"></script>--}}
    <link href="/css/shaidan.css?v=13677" rel="stylesheet">
</head>

<body>
    <header>
        <div class="title">
            深夜放毒，看看本王中的大餐
        </div>
        <div class="time clearfix">
            <div class="col-xs-6 nickname fontBlue">今夜山高水深</div>
            <div class="col-xs-6 nowtime">{{$date}}</div>
        </div>
        <div class="address">(北京 学府路蟹老宋香锅)</div>
    </header>
    <section class="details">
        <table align="center" border="0" cellpadding="3">
            <tr>
                <td class="tb_left">
                    获奖商品:
                </td>
                <td class="tb_right fontBlue" align="left">
                    (第120期)蟹老宋香锅2-3人餐(晚餐)
                </td>
            </tr>
            <tr>
                <td class="tb_left">
                    本期参与:
                </td>
                <td class="tb_right fontBlue" align="left">
                    <label class="fontRed">1</label>人次
                </td>
            </tr>
            <tr>
                <td class="tb_left">
                    幸运号码:
                </td>
                <td class="tb_right fontRed" align="left">
                    10021
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
        一个不小心就中了一桌子菜，有虾有蟹有蔬菜，有滋有味有颜值，小的们，快来膜拜，捏哈哈哈哈~
    </div>
    <div class="imgs"><img src="/image/dish/sd_xls_1.png" class="alignCenter img-responsive"/></div>
    <div class="imgs"><img src="/image/dish/sd_xls_2.png" class="alignCenter img-responsive"/></div>

    @include('dish.downPannel')
</body>
</html>
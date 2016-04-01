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
    <style type="text/css">
        html,body{background-color: #f7f7f7;font: normal 100% Helvetica, Arial, sans-serif;font-size:5vw;}
        .banner {position:absolute;top:0;left:0;width:100%;min-height:40px;
            font-weight: 400;color: #fe5a59;overflow:hidden;text-overflow:ellipsis;}
        .banner {position: relative;padding: 20px 0px 0px 20px;}
        .product{background-color: #fff;color:#b7b7b7; padding: 5px 0px 5px 20px;text-overflow:hidden;font:bold 20px/100% '宋体';font-size:4vw;}
        .product-name{text-overflow:hidden;font-size:4vw;}
        .money-detail{color:#000;padding: 20px 0px 5px 20px;font-size:4vw;}
        .money-total{color:#fe5a59;padding: 20px 0px 5px 20px;float: right;margin-right: 30px;}
        .money-total i{font-size:7vw;}
        .comList_checkbox{opacity:0;}
        .comList_forCheckbox{width:20px;height:20px;color:Red;cursor:pointer;text-align:center;font:bold 20px/100% '宋体'; display:inline-block;
            border-radius:.2em;-webkit-box-shadow:inset .08em .08em .1em #000;background-color:#fff;float: right;margin-right: 20px;}
        .pay-total{color:#b7b7b7;padding: 70px 0px 5px 20px;font:bold 20px/100% '宋体';}
        .pay-name{color:#000;padding: 5px 0px 5px 20px;}
        .alipay{line-height:30px;text-align: center; font:normal 27px/100% '宋体';}
        .pay-Fruit{ width:30px;height:30px;}
        .pay-name img{width:30px; height:30px; margin-left: -20px;}
        .pay-sub{width: 90%;height: 40px;padding: 30px 0px 0px 20px;text-align:center;}
        button{ font-size:5.5vw;background-color:#fe5a59;color: #fff;border: 0px;padding: 15px 10px 15px 10px;border-radius: 2%;}
        /* Safari and Chrome */
        @-webkit-keyframes banner_hide_animation {
            0%   {top:0px;}
            100% {top:-30px;}
        }
        @-webkit-keyframes banner_show_animation {
            0%   {top:-30px;}
            100% {top:0px;}
        }
        .banner {
            -webkit-animation:banner_hide_animation 0.5s;
            -webkit-animation-timing-function:linear;
            -webkit-animation-fill-mode:forwards;
        }
        .banner {
            -webkit-animation:banner_show_animation 0.5s;
            -webkit-animation-timing-function:linear;
            -webkit-animation-fill-mode:forwards;
        }
    </style>
    <script>
        function changeCheckbox() {
            var mylabel = document.getElementById('formyCheckBox');
            if (mylabel.innerHTML == "√")
                mylabel.innerHTML = "";
            else
                mylabel.innerHTML = "√";
        }
    </script>

</head>
<body>
<div class="container-fluid index-page">

    <div class="row" style="">
            <div class="banner">
                <div class="row v-row">
                    <div class="col-xs-6 col-md-6 " style="float: left;">
                        总需支付
                    </div>
                    <div class="col-xs-6 col-md-6" style="float: right;margin-right: 40px;">
                        {{ $total }}元
                    </div>
                </div>
            </div>

            <div class="product">
                <p> 第{{ $nperStr }}期</p>
                <p><span class="product-name">{{ $goodName }} </span>  <span style="float: right;margin-right: 20px;"">{{ $total }}人次<i></p>
                <p>{{ $goodDescribe }}</p>
            </div>
        <form action="#" method="post">
            <div class="row v-row">

                <div class="col-xs-7 col-md-7 v-elem money">
                    <div class="money-detail">
                        <div>夺宝币支付 ( 余额 : {{$userMoney}} )<span style="float: right;margin-right: 20px;">
                                <label id="formyCheckBox" for="modeCheckBox" onclick="changeCheckbox()" class="comList_forCheckbox"></label>
 　　                          <input type="checkbox" id="myCheckBox" class="comList_checkbox" /> {{$userMoney}}元&nbsp;&nbsp;&nbsp;</span></div>
                           <span><hr style="width: 90%;float: left;top:-20px;" >   </span>

                    </div>

                    <div class="money-total">
                        <div><span>结算: <i>{{ $total }}</i>元</span></div>
                    </div>
                </div>
            </div>


            <div class="row v-row">
                <div class="col-xs-7 col-md-7 v-elem pay">

                    <div class="pay-total">
                        <div>其他支付方式</div>
                    </div>

                    <div class="pay-name">
                        <input name="Fruit" class="pay-Fruit" type="radio" value="" />
                        <img alt="" src="/upload/alipay/alipay_easyicon.png"><span class="alipay">支付宝支付</span>
                    </div>


                    <div class="pay-sub">
                        <button class="sub" disabled>支付功能正在开发中，敬请期待！</button>
                    </div>


                </div>
            </div>
        </form>
                    <!-- End -->
    </div>
</div>
</body>
</html>

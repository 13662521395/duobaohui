<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>夺宝会-签到</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/sign.css" rel="stylesheet" type="text/css">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
    <header>
        <input type="hidden" value="{{$user_id}}" id="user_id"/>
        <input type="hidden" value="{{$has_sign}}" id="has_sign"/>
        <input type="hidden" value="{{$is_today}}" id="is_today"/>
        <img src="/image/sign/ok手势_720.png" class="alignCenter img-responsive"/>
        <div class="title" id="sign_title">
            @if ($is_today === 0)
                今天尚未签到
            @else
                今日已签到!
            @endif
        </div>
        <div class="title_sub">已连续签到&nbsp;<lavel id="signNum">{{$has_sign}}</lavel>&nbsp;天</div>
    </header>
    <div class="days clearfix">
        <div class="col-xs-3 day_one">
            <div class="day_desc">第一天</div>
        </div>
        <div class="col-xs-3 day_one">
            <div class="day_desc">第二天</div>
        </div>
        <div class="col-xs-3 day_one">
            <div class="day_desc">第三天</div>
        </div>
        <div class="col-xs-3 day_one">
            <div class="day_desc">第四天</div>
        </div>
        <div class="col-xs-3 day_one">
            <div class="day_desc">第五天</div>
        </div>
    </div>
    <div class="days2 clearfix">
        @for ($i = 0; $i < $has_sign; $i++)
            <div class="col-xs-3 day_one">
                <div class="circle signColor center-block">
                    已签
                </div>
            </div>
        @endfor
        @for ($i = 0; $i < $no_sign; $i++)
            @if($i != $no_sign-1)
                <div class="col-xs-3 day_one">
                    <div class="circle noSignColor center-block">
                        未签
                    </div>
                </div>
            @else
                <div class="col-xs-3 day_one">
                    <div class="circle noSignColor center-block closeRedPacket"></div>
                </div>
            @endif
        @endfor
    </div>
    <div class="rules">
        <div class="rules_title">签到规则</div>
        <div>
            <ol>
                <li>每个账号每天可签到1次</li>
                <li>账号连续签到5天，系统将奖励1元夺宝红包，可用于参与夺宝会任意商品夺宝</li>
                <li>签到满5天，系统将重新开启新一轮连续签任务</li>
            </ol>
        </div>
    </div>
    @include('common.alertModel')
</body>
</html>
<script src="/js/sign.js" type="text/javascript"></script>
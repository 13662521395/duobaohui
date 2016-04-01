<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="description" content="夺宝会，只需1元就有机会获得一件商品，不要让大奖和您擦肩而过！"/>
    <meta name="keywords" content="夺宝会,1元,一元,1元夺宝,1元购,1元购物,1元云购,一元夺宝,一元购,一元购物,一元云购"/>
    <link rel="shortcut icon" type="image/x-icon" href="image/common/icon108.png">
    <title>夺宝会-小树科技</title>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="css/index2.0.css" rel="stylesheet">
    <link href="css/protocol.css" rel="stylesheet">
    <script src="js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
    <script src="js/common.js" type="text/javascript"></script>
</head>
<body>
    <section class="banner">
        <header></header>
        <div class="install">
            <div class="install_title"></div>
            <div class="install_logo row">
                <div class="col-sm-4 col-md-4 install_logo_sub">
                    <img class="alignCenter img-responsive" src="/image/version_2/icon_usb.png">
                    <div class="install_desc">通过电脑USB连接安装</div>
                    <div class="btn_download" id="btn_download">
                        {{--<img class="alignCenter img-responsive" src="image/version_2/btn_download.png">--}}
                        <button class="btnDownload center-block">立即下载</button>
                    </div>
                </div>
                <div class="col-sm-4 col-md-4 install_logo_sub">
                    <img class="alignCenter img-responsive" src="/image/version_2/icon_scan.png">
                    <div class="install_desc">用手机扫描二维码安装</div>
                    <div class="btn_scan"><img class="alignCenter img-responsive" src="image/version_2/qr.png"></div>
                </div>
                <div class="col-sm-4 col-md-4 install_logo_sub">
                    <img class="alignCenter img-responsive" src="/image/version_2/icon_link.png">
                    <div class="install_desc">通过短信链接安装</div>
                    <div class="btn_tel">
                        <input type="tel" class="form-control input_tel" id="telNum" placeholder="请输入您的手机号" maxlength="11">
                        <button id="btnSend" class="btnSend center-block">发&nbsp;送</button>
                        <button id="btnSend2" class="btnSend2 center-block" disabled>重新发送</button>
                        <input type="hidden" id="downloadUrl" value="{{ $download_url }}"/>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <div class="process">
        <div class="padding_60"></div>
        <div class="process_title">开启夺宝之旅，惊喜到你碗里</div>
        <div class="sub_title"><img class="alignCenter img-responsive" src="/image/version_2/夺宝流程.png"></div>
        <div class="process_detail">
            <div class="process_detail_white"></div>
            <div class="process_detail_blue"></div>
            <div class="layer">
                <div class="layer_content row">
                    <div class="col-sm-3 col-md-3 process_detail_sub">
                        <div class="process_detail_num">
                            <img src="/image/version_2/icon_no1.png" class="alignCenter img-responsive"/>
                            <div class="cover"></div>
                        </div>
                        <div class="process_detail_pic">
                            <img class="alignCenter img-responsive leftPx" src="/image/version_2/step_1_default.png">
                            <img class="alignCenter img-responsive noneDisplay leftPx2" src="/image/version_2/step_1_over.png">
                        </div>
                        <div class="process_detail_word">选择您心仪的物品</div>
                    </div>
                    <div class="col-sm-3 col-md-3 process_detail_sub">
                        <div class="process_detail_num">
                            <img src="/image/version_2/icon_no2.png" class="alignCenter img-responsive"/>
                        </div>
                        <div class="process_detail_pic">
                            <img class="alignCenter img-responsive leftPx" src="/image/version_2/step_2_default.png">
                            <img class="alignCenter img-responsive noneDisplay leftPx2" src="/image/version_2/step_2_over.png">
                        </div>
                        <div class="process_detail_word">
                            确定参与人次提交订单<br><label class="red">注：人次越多机会越大</label>
                        </div>
                    </div>
                    <div class="col-sm-3 col-md-3 process_detail_sub">
                        <div class="process_detail_num">
                            <img src="/image/version_2/icon_no3.png" class="alignCenter img-responsive"/>
                        </div>
                        <div class="process_detail_pic">
                            <img class="alignCenter img-responsive leftPx" src="/image/version_2/step_3_default.png">
                            <img class="alignCenter img-responsive noneDisplay leftPx2" src="/image/version_2/step_3_over.png">
                        </div>
                        <div class="process_detail_word">选择支付方式</div>
                    </div>
                    <div class="col-sm-3 col-md-3 process_detail_sub">
                        <div class="process_detail_num">
                            <img src="/image/version_2/icon_no4.png" class="alignCenter img-responsive"/>
                        </div>
                        <div class="process_detail_pic">
                            <img class="alignCenter img-responsive leftPx" src="/image/version_2/step_4_default.png">
                            <img class="alignCenter img-responsive noneDisplay leftPx2" src="/image/version_2/step_4_over.png">
                        </div>
                        <div class="process_detail_word">看到参与成功提示后即可静候佳音或者"继续夺宝"选择其他心仪的物品</div>
                    </div>
                    <div class="col-sm-3 col-md-3 process_detail_sub">
                        <div class="process_detail_num">
                            <img src="/image/version_2/icon_no5.png" class="alignCenter img-responsive"/>
                            <div class="cover2"></div>
                        </div>
                        <div class="process_detail_pic">
                            <img class="alignCenter img-responsive leftPx" src="/image/version_2/step_5_default.png">
                            <img class="alignCenter img-responsive noneDisplay leftPx2" src="/image/version_2/step_5_over.png">
                        </div>
                        <div class="process_detail_word">您的中奖提示，点击则可进行领奖操作</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="weCan">
        <div class="padding_60"></div>
        <div class="process_title">我们能做的</div>
        <div class="weCan_three row">
            <div class="col-sm-4 col-md-4">
                <div><img src="/image/version_2/icon_icando_1.png" class="alignCenter img-responsive"/></div>
            </div>
            <div class="col-sm-4 col-md-4">
                <div><img src="/image/version_2/icon_icando_2.png" class="alignCenter img-responsive"/></div>
            </div>
            <div class="col-sm-4 col-md-4">
                <div><img src="/image/version_2/icon_icando_3.png" class="alignCenter img-responsive"/></div>
            </div>
        </div>
        <div class="padding_60"></div>
    </div>
    <footer>
        <div id="footer_content">
            版权所有&copy; &nbsp;哈尔滨小树科技有限公司&nbsp;京ICP备13034049号-1<br/>
            <a href="javascript:void(0);" id="showProtocol">隐私政策</a>
        </div>
    </footer>
    @include('common.protocol')
    @include('common.alertModel')
</body>
</html>
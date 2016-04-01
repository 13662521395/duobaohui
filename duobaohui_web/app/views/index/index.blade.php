<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title></title>
    <link rel="stylesheet" href="/css/index.css">
</head>
<body>
    <div class="dbh-header">
        <div class="dbh-title"><img src="/image/title.png"></div>
    </div>
    <div class="dbh-body">
        <div class="dbh-content">
            <div class="dbh-content-download">
                <div class="dbh-phone"><img src="/image/phone.png"></div>
                <div class="dbh-logo"><img src="/image/logo.png"></div>
                <div class="dbh-download"><a href="{{ $download_url }}"><img src="/image/download.png"></a></div>
                <div class="dbh-line"></div>
            </div>
            <div class="dbh-content-tel">
                <div class="dbh-qrcode-word">扫描二维码下载</div>
                <div class="dbh-qrcode"><img src="/image/qrcode.png"></div>
                <div class="dbh-tel-word">免费发送到手机</div>
                <div class="dbh-tel">
                    <input type="text" maxlength="11">
                    <a href="javascript:void(0);">发送</a>
                </div>
            </div>
        </div>
    </div>
    <div class="dbh-partner" style="display:none;">
        <!--TODO: my partner-->
    </div>
    <div class="dbh-footer">
        <div class="dbh-info">
            <div class="dbh-partner-link" style="display:none;">
                <span>友情链接</span> :&nbsp;&nbsp;苹果助手 | iOS8.4越狱 | 苹果手机助手 | 手机助手 |
            </div>
            <div class="dbh-line"></div>
            <div class="dbh-copyright">
                <p style="display:none;">教程与帮助 | 安卓开发者平台 | 意见反馈 | 投诉举报 | 诚聘英才 | 友情链接 | 联系我们</p>
                <p>夺宝会，仅需1元钱，夺得好商品</p>
                <p>哈尔滨小树科技有限公司 版权所有 京ICP备13034049号-1</p>
            </div>
        </div>
    </div>

    <script src="/plugin/jquery-1.11.3.min.js"></script>
    <script src="/js/index.js"></script>
    <script>
        var _hmt = _hmt || [];
        (function() {
            var hm = document.createElement("script");
            hm.src = "//hm.baidu.com/hm.js?527c59e6db020981dbd0e738ac5f9f09";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
</body>
</html>
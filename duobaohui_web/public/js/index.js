/**
 * Created by gaodawei on 15/11/3.
 */

$(document).ready(function() {
    //点击发送短信按钮
    $('.dbh-tel > a').click(function() {
        sendMsg();
    });
    //输入框响应回车
    $('.dbh-tel > input').keydown(function(event) {
        if (event.which == '13') sendMsg();
    });
});

function sendMsg() {
    var tel = $('.dbh-tel > input').val();
    if (tel == '') {
        alert('手机号不能为空');
        return;
    }
    if (tel.length != 11) {
        alert('手机号长度应为11位');
        return;
    }
    var tel_pattern = /^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/i;
    if (tel.match(tel_pattern) == null) {
        alert('手机号码格式不正确');
        return;
    }
    $.post('/index', {tel: tel}, function(data) {
        switch (data.code) {
            case '1':alert('发送成功');break;
            case '0':alert('发送失败');break;
            case '-1':alert(data.msg);break;
        }
    });
}

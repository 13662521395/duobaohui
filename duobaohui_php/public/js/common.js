$(document).ready(function(){
    var btnDownload=$('#btn_download');
    var btnSend=$('#btnSend');
    var btnSend2=$('#btnSend2');
    var showProtocol=$('#showProtocol');
    var modal=$('#modal');
    var alertModel=$('#alertModel');
    var alertMsg=$('#alertMsg');
    var downloadUrl=$('#downloadUrl').val();
    var countdown=60;

    btnDownload.on('click',function(){
        location.href = downloadUrl;
    });

    btnSend.on('click',function(){
        sendMsg();
    });

    showProtocol.on('click',function(){
        modal.modal('show');
    });

    $('.process_detail_pic').hover(function(){
        $(this).children().eq(1).show();
        $(this).children().eq(0).hide();
    },function(){
        $(this).children().eq(0).show();
        $(this).children().eq(1).hide();
    });

    $(".input_tel").keypress(function(event) {
        var keyCode = event.which;
        if (keyCode >= 48 && keyCode <=57)
            return true;
        else
            return false;
    }).focus(function() {
        this.style.imeMode='disabled';
    });

    function sendMsg() {
        var tel=$('#telNum').val();
        if (tel == '') {
            showAlert('手机号不能为空!');
            return;
        }
        if (tel.length != 11) {
            showAlert('手机号长度应为11位!');
            return;
        }
        var tel_pattern = /^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/i;
        if (tel.match(tel_pattern) == null) {
            showAlert('手机号码格式不正确!');
            return;
        }
        $.post('/index', {tel: tel}, function(data) {
            switch (data.code) {
                case '1':setTime();break;
                case '0':showAlert('发送失败,请稍候再试！');break;
                case '-1':showAlert(data.msg);break;
            }
        });
    }

    function showAlert(msg){
        alertMsg.html(msg)
        alertModel.modal('show');
    }

    function setTime() {
        console.log(btnSend2);
        if (countdown == 0) {
            switchBtn(false);
            countdown = 60;
            return;
        } else {
            btnSend2.html("重新发送(" + countdown + ")");
            switchBtn(true);
            countdown--;
        }
        setTimeout(function() {
            setTime()
        },1000);
    }

    function switchBtn(flag){
        if(flag){
            btnSend.css("display","none");
            btnSend2.css("display","block");
        }else{
            btnSend2.css("display","none");
            btnSend.css("display","block");
        }
    }
});


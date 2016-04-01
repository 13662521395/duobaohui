$(document).ready(function(){
    var alertModel=$('#alertModel');
    var alertMsg=$('#alertMsg');
    var sign_title = $('#sign_title');
    var signNum = $('#signNum');
    var noSign = $('.noSignColor');
    var hadSign = $('#has_sign').val();
    var is_today = $('#is_today').val();
    var today = noSign.eq(0);
    var user_id = $('#user_id').val();

    if(hadSign==5 && is_today==1){
        openRedPactet($('.signColor').last());
    }

    today.on('click',function(){
        addSign($(this));
    });

    function addSign(obj){
        var os_type = isIphone()?2:1;
        var array = {
            'userId' : user_id,
            'os_type' : os_type,
            'userAgent' : navigator.userAgent
        }
        $.post('/client/sign/add-sign',array,function(data){
            var signRes = data.signRes;
            if(signRes==1){//1：成功   2：失败   3：今日已签到
                if(data.continue_num < 5){
                    obj.removeClass('noSignColor');
                    obj.addClass('signColor');
                    obj.html('已签');
                    sign_title.html('签到成功!');
                    signNum.html(data.continue_num);
                }else{
                    openRedPactet(obj);
                    showAlert('1元红包已发放至您的账户，请注意查收')
                }
            }
            if(signRes==3){
                sign_title.html('今日已签到!');
            }
        },'json');
    }

    function openRedPactet(obj){
        $url='http://dev.api.duobaohui.com/redpacket/redpacket/sign-send-redpacket'
        $.post($url,{user_id:user_id},function(data){
            console.log(data);

            obj.html('');
            obj.removeClass('closeRedPacket');
            //obj.addClass('openRedPacket');
            obj.append('<div class="openRedPacket bigRedPacket center-block"></div>');
        },'json');
    }

    function isIphone(){
        if(navigator.userAgent.match(/iPhone/i)){
            return true;
        }
        return false;
    }

    function showAlert(msg){
        alertMsg.html(msg);
        alertModel.modal('show');
    }
});

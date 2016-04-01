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

    //手动签到
    //today.on('click',function(){
    //    addSign($(this));
    //});

    //自动签到
    addSign(today);

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
                signNum.html(data.continue_num);
                if(data.continue_num < 5){
                    obj.removeClass('noSignColor');
                    obj.addClass('signColor');
                    obj.html('已签');
                    sign_title.html('签到成功!');
                }else{
                    giveRedPactet(obj);
                    sign_title.html('签到成功!');
                }
            }
            if(signRes==2){
                sign_title.html('服务器忙..请稍候');
            }
            if(signRes==3){
                sign_title.html('今日已签到!');
            }
        },'json');
    }

    function openRedPactet(obj){
        obj.html('');
        obj.removeClass('closeRedPacket');
        //obj.addClass('openRedPacket');
        obj.append('<div class="openRedPacket bigRedPacket center-block"></div>');
    }

    function giveRedPactet(obj){
        openRedPactet(obj);
        showAlert('1元红包已发放至您的账户，请注意查收');
        //$url='/redpacket/redpacket/sign-send-redpacket';
        //var array = {
        //    'user_id' : user_id
        //}
        //$.post('/redpacket/redpacket/sign-send-redpacket',array,function(data){
        //    if(data.code == "1"){
        //        openRedPactet(obj);
        //        showAlert('1元红包已发放至您的账户，请注意查收')
        //    }else{
        //        showAlert('系统繁忙,请稍后再试')
        //    }
        //},'json');
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

define(function(require,exports,module){
    var $ = require ('jquery');
    var layer = require('../../../app/layer');

    /*
     * 创建红包提交方法
     */
    var addRedPacket = function(){

        $('.sub-money').bind('click',function(){
            var aMoney = $('#a-money').val();
            var redNum = $('#red-num').val();
            if(aMoney == '' || redNum == '' || aMoney <=0 || redNum <=0){
                alert('Error:Please Rewrite the moneys and num');
                return false;
            }

            var total = aMoney * redNum;
            if(!isNaN(total)){
                var totalPrice = $('.budget').attr('num');
                totalPrice = Number(totalPrice) + Number(total);
                $('.budget').attr('num',totalPrice);
                $('.budget').text(totalPrice + '元');

                $('.money-label').prepend("&nbsp;<label class='total-money-budget' for='tatoa-money-budget' num='"+totalPrice+"' number='"+total+"' style='margin: 0px 0px 0px 10px; border: 1px solid #000; padding: 3px 10px;' >"+aMoney+" &times; "+redNum+"<input type='hidden' class='price' name='money_a[]' value='"+aMoney+','+redNum+"' /></label>")
                $('.del-money').html("<button class='delete-Money' onclick='deleteMoney()' style='color: red;'>删除</button>");

            }else{
                alert('Error:Illegal character');
                return false;
            }
        });


        /*
         * 提交所有数据  return redName
         */
        $('.sub-red').bind('click',function(){

            //批次ID
            var batchId = $('#batch_id').val();

            //名称
            var redName = $('#red-name').val();
            if(redName == ''){
                alert('Error:批次名称不能为空');
                return false;
            }

            //方案  return optionVal
            if(optionVal == 0){
                alert('Error:请选择发放方案');
                return false;
            }


            //有效期设置 return validity
            var  validity = new Array();
            var val_payPlatform = $('.validity-time input[name="radio-btn1"]:checked ').val();

            if(val_payPlatform == undefined) {
                alert('请设置有效期');
                return false;
            }else if(val_payPlatform ==0 ){
                validity[0] = val_payPlatform;

            }else if(val_payPlatform == 1){
                var validityDay = $('#validity-day').val();
                if(validityDay == '' || validityDay <= 0){
                    alert('Error:请输入有效天数');
                    return false;
                }
                validity[0] = validityDay;
            }else if(val_payPlatform == 2){
                validityStart = $('#validity-start').val();
                validityEnd = $('#validity-end').val();
                if(validityStart == '' || validityEnd == '' || validityStart <= 0 || validityEnd <= 0){
                    alert('Error:请输入开始至结束有效日期!');
                    return false;
                }
                validity[0] = validityStart;
                validity[1] = validityEnd;
            }




            //金额  return price
            var price = $('.price');
            if(price == 'undefined'){
                alert('Error:请设置红包金额');
                return false;
            }else{
                //console.log(price);
                //数组
                //alert(price.0.price);
                //return false;
                var prices = new Array();
                for(var i=0;i<price.length;i++){
                    prices[i] = $('.price').eq(i).val();
                }

                price = prices;
                //console.log(price.length);   //2
            }

            //消费限制
            var buyMoney = '';
            var buyLimit = $('.buy-limit input[name="radio-btn4"]:checked ').val();
            if(buyLimit == 'undefined'){
                alert('请设置消费限制!');
                return false;
            }else if(buyLimit == 1){
                var buys =  $('#buy-num').val();
                if(buys == 'undefined' || buys == '' || buys <= 0){
                    alert('请填写消费金额!');
                    return false;
                }
                buyMoney = buys;
            }else{
                buyMoney = buyLimit;
            }

            //活动条件
            var activityInfo  = '';
            var activityLimit = $('.limit-activity input[name="radio-btn5"]:checked ').val();
            if(activityLimit == 'undefined'){
                alert('请设置活动限制!');
                return false;
            }else if(activityLimit == 1){
                var activitys =  $('.activity-info').val();
                if(activitys == 'undefined' || activitys == ''){
                    alert('请选择活动商品!');
                    return false;
                }
                activityInfo = activitys;
            }else{
                activityInfo = activityLimit;
            }


            //发起批次生成红包请求

            //打印出数据
            console.log('批次名称'+redName+"\n\n");
            console.log('发放方案'+optionVal+"\n\n");
            console.log('设置有效期'+validity+"\n\n");
            console.log('金额'+price+"\n\n");
            console.log('消费限制'+buyMoney+"\n\n");
            console.log('活动条件'+activityInfo+"\n\n");

            layer.createLayer();  //loadding
            $.get('/admin/operate/create-redpacketforbatch', {'batch_id':batchId,'red_name':redName,'option_val':optionVal,'validity':validity,'price':price,'buy_money':buyMoney,'activity_info':activityInfo}, function (data) {
                layer.allowScroll();
                layer.closeLayer();
                if(data.code == 1){
                    if(confirm("添加成功,为您跳转到红包管理页面!请点击确定?")){
                        window.location="/admin/operate/red-manage";
                        return true;
                    }else{
                        window.location.reload();//刷新当前页面.
                        return false;
                    }
                }else if(data.code == 0){
                    alert('添加失败');
                }else if(data.code == 10005){
                    alert('参数错误');
                }else{
                    alert('添加失败');
                }
            });


        });

    }







    exports.addRedPacket = addRedPacket;
});
@include('admin.header')
@include('admin.menu')

        <!--main content start-->
<section id="main-content" style="color: #000; background-color: #fff">
    <section class="wrapper">
        <section class="panel">
            <header class="panel-heading fet-title">
                新建红包活动
            </header>
        </section>
    </section>
    <div class="row column-to-style">
        <span>
            <div class="key column-to-left">红包名称</div>
            <div class="key-values column-to-right">
                <div class="form-group">
                    <span class="form-inline"><input type="text" id="red-name" placeholder="" style="width:30vw;"> &nbsp;（批次：{{$bat_id}}）<input type="hidden" id="batch_id" placeholder="" value="{{$bat_id}}" /></span>
                    <p><label for="exampleInputName2" style="margin-top: 10px;">简述该批红包的用途</label></p>
                </div>
            </div>
        </span>

         <span>
            <div class="key column-to-left">发放方案</div>
            <div class="key-values column-to-right">
                <select id="u91_input" style="font-size: 16px;">
                    @foreach($tag as $k => $val)
                        <option value="{{$k}}">{{$val}}</option>
                    @endforeach
                </select>
                <p><label for="exampleInputName2" style="margin-top: 10px;">除手动发放外，其他发放方案只能同时关联一个批次的红包</label></p>
            </div>
        </span>

        <span>
            <div class="key column-to-left">有效期设置</div>
            <div class="key-values column-to-right">
                <ul style="font-size: 14px;" class="validity-time">
                    <li>
                        <input type="radio" name="radio-btn1" value="0" />&nbsp;  无限期</li>
                    <li>
                        <input type="radio" name="radio-btn1" value="1" />&nbsp;  指定天数 &nbsp;&nbsp;<span class="form-inline"><input type="text" class="max-lenth" id="validity-day" placeholder="" style="width:40%;"> 天 (红包从充入用户账号开始计算，超过指定天数后过期)</span>
                    </li>
                    <li">
                    <input type="radio" name="radio-btn1" value="2" />&nbsp;  指定日期 &nbsp;&nbsp;<span class="form-inline"><input type="text" class="max-lenth" id="validity-start" placeholder="" style="width:40%;"> ~ <input type="text" class="max-lenth" id="validity-end" placeholder="" style="width:40%;"> 天</span>
                    <p></p>
                    </li>
                </ul>
            </div>
        </span>

        <span>
            <div class="key column-to-left">金额和数量</div>
            <div class="key-values column-to-right">
                <ul style="font-size: 14px;">
                    <li>
                        预算金额:<span class="budget" num="0" style="color: red;font-size: 16px;"> &nbsp;</span>
                    </li>
                    <li style="margin-top: 5px; border: 1px solid #000; padding: 5px 0px 5px 0px; width: 315px;height: 75px; float: left">
                        <div class="left-money">
                            &nbsp;单个红包金额：<input type="text" class="max-lenth-sm" id="a-money" placeholder="" style="width:20vw;"> &nbsp;
                            &nbsp;生成红包数量：<input type="text" class="max-lenth-sm" id="red-num" placeholder="" style="width:20vw;"> &nbsp;
                        </div>
                        <div class="right-money">
                            <button type="button" class="btn active sub-money">确定</button>
                        </div>
                    </li>
                    <li>
                        <p><label for="exampleInputName2" style="margin: 10px 0px 0px 10px;">已添加</label></p>
                        <span class="money-label">

                            <span class="del-money"></span>
                        </span>
                    </li>
                </ul>
            </div>
        </span>
        <br /><br /><br />
         <span>
            <div class="key column-to-left">消费限制</div>
            <div class="key-values column-to-right">
                <ul style="font-size: 14px;" class="buy-limit" >
                    <li>
                        <input type="radio" name="radio-btn4" value="0" />&nbsp;  无限制</li>
                    <li>
                    <input type="radio" name="radio-btn4" value="1" />&nbsp;  消费满 &nbsp;&nbsp;<input type="text" class="max-lenth" id="buy-num" placeholder="" style="width:30vw;"> &nbsp;元可用
                    </li>
                </ul>
            </div>
        </span>

         <span>
            <div class="key column-to-left">可用活动限制</div>
            <div class="key-values column-to-right">
                <ul style="font-size: 14px;" class="limit-activity">
                    <li>
                        <input type="radio" name="radio-btn5" value="0" />&nbsp;  无限制</li>
                    <li>
                        <input type="radio" name="radio-btn5" value="1" onclick="selectActivity()"/>&nbsp;  指定抽奖活动可用 &nbsp;&nbsp;

                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">
                            设置
                        </button>
                        <input type="hidden" name="activity-info" class="activity-info" value=""/>
                    </li>
                    <li style="margin-top: 15px; float: right;margin-right: 40vw;">
                        <button type="button" class="btn btn-default btn-lg active sub-red">确定</button>
                    </li>
                </ul>
            </div>
        </span>

    </div>

    <div id="check-activity"></div>



</section>
<!--main content end-->
<script type="text/javascript">

    seajs.use('page/admin/operate/list' , function(mod){
        mod.addRedPacket();
    });


    /*
     * 选择方案
     */
    var optionVal = 0;
    $('#u91_input').bind('change',function(){
        var val = $('#u91_input option:selected').val();
        optionVal = val;
    });


    /*
     * 点击指定抽奖活动可用时才会加载活动信息
     */
    function selectActivity(){
        $.get('/admin/operate/get-all-activity', { }, function (data) {
            $('#check-activity').html('');
            $('#check-activity').html(data);
        });
    }

    /*
     * 删除金额和数量
     */
    function deleteMoney(){
        $('.budget').attr('num',0);
        $('.budget').text('');
        $('.money-label label').remove();
        $('.del-money').html('')
        $('#a-money').val('');
        $('#red-num').val('');
    }


    /*
     * 多级选择
     */
    $('.checkAll').live('click',function(){

        var status   =  $(this).attr('checked');
        var bigClick =  $(this).attr('name');

        if(status == 'checked'){
            $('input:checkbox[patternName='+bigClick+']').attr("checked", true);
        }else{
            $('input:checkbox[patternName='+bigClick+']').attr("checked", false);
        }

    });


    /*
     * 点击提交选中的活动
     */
    $('.activiry-sub').live('click',function(){
        var text="";
        $("input[name=item]").each(function() {
            if ($(this).attr("checked")) {
                text += $(this).val()+",";
            }
        });


        $('.activity-info').val('');
        $('.activity-info').val(text);

        if(confirm("确定提交您所勾选的数据吗?")){
            return true;
        }else{
            $('.activity-info').val('');
            return false;
        }
    });


</script>

@include('admin.footer')
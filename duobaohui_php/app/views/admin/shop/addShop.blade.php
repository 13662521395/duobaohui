@include('admin.header')

@include('admin.menu')

<!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading">
                    添加商户
                </header>
                <div class="panel-body">
                    @if (!empty($msg))
                        <div class="alert alert-success alert-block fade in">
                            <button data-dismiss="alert" class="close close-sm" type="button">
                                <i class="icon-remove"></i>
                            </button>
                            <h4>
                                <i class="icon-ok-sign"></i>
                                {{ $msg }}
                            </h4>
                        </div>
                    @endif
                    <form class='form-horizontal tasi-form' method='post' action='/admin/shop/add-shop' id="addShopForm" >
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>商家名称</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="head_name" />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>分店名称</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='branch_name' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>分店地址</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='address' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>店铺面积（㎡）</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='area' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>餐桌数（张）</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='table_num' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>可容纳堂食人数（人）</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='max_customer_num' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>日均翻桌率（次）</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='avg_repeat_table' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>日均账单数/桌数（单）</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='avg_orders_num' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>平均每桌用餐时间（分钟）</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='avg_meal_time' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>日均人流量(人)</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='avg_customer_num' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>工作日人流量(人)</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='workday_customer_rate' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>节假日人流量(人)</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='holiday_customer_rate' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>每日高峰时段</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='rush_hours' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>高峰时段平均人流量(人)</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='rush_hours_rate' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>是否提供外送服务</label>
                            <div class="col-sm-8">
                                <label class='col-sm-2 control-label'>
                                    <input type='radio' name='is_outside_order' value='1'/>&nbsp;是
                                </label>
                                <label class='col-sm-2 control-label'>
                                    <input type='radio' name='is_outside_order' value='0' checked/>&nbsp;否
                                </label>
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>日均外送订单数（单）</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='avg_outside_order' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>店铺WiFi网络名称</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='wifi_name' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>店铺WiFi网络密码</label>
                            <div class="col-sm-8">
                                <input type="text" id='version_desc' class="form-control" name='wifi_pwd' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>是否启用</label>
                            <div class="col-sm-8">
                                <label class='col-sm-2 control-label'>
                                    <input type='radio' name='enabled' value='1' checked />&nbsp;是
                                </label>
                                <label class='col-sm-2 control-label'>
                                    <input type='radio' name='enabled' value='0' />&nbsp;否
                                </label>
                            </div>
                        </div>
                        <div>
                            <button type="submit" class="btn btn-shadow btn-success" id='' style='float:left;'>添加</button>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    </section>
</section>
<!--main content end-->

<script type="text/javascript">

    var addShopForm = $('#addShopForm');

    var addShop_validate = addShopForm.validate({
        rules: {
            head_name: {
                required: true
            },
            address: {
                required: true
            }
        },
        messages: {
            head_name: {
                required: "请输入商家名称"
            },
            address: {
                required: "请输入分店地址"
            }
        }
    });

    var uploadApp = $('#uploadApp');
    var ios_app = $('#ios_app');
    $(document).ready(function(){
        $("input[name=os_type]").bind("click",function(event){
            switchOsType($(this).val());
            event.stopPropagation();
        });

        $('#ios_app').bind("click",function(event){
            event.stopPropagation();
        });
    });

    function switchOsType(flag){
        if(flag==1){
            uploadApp.show();
            ios_app.addClass('hidden');
            $('#ios_url').attr('disabled',true);
            $('#apk_url').attr('disabled',false);
        }else{
            uploadApp.hide();
            ios_app.removeClass('hidden');
            $('#ios_url').attr('disabled',false);
            $('#apk_url').attr('disabled',true);
        }

    }

</script>

@include('admin.footer')


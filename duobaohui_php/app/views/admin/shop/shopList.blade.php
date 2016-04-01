@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>

<!--main content start-->
<section id="main-content">
    <section class="wrapper site-min-height">
        <div class="col-xs-12 panel">
            <div id="_msg"></div>
            <section class="panel">
                <header class="panel-heading">
                    商户列表
                    <a href="/admin/shop/add-one-shop" class="btn btn-danger add_banner pull-right"><i class="icon-plus"></i>&nbsp;新增商户</a>
                </header>

                <table class="table table-striped table-advance table-hover" id="android_table">
                    <thead>
                    <tr>
                        <th>商家id</th>
                        <th>商家名称</th>
                        <th>分店名称</th>
                        <th>分店地址</th>
                        <th>店铺面积(㎡)</th>
                        <th>餐桌数(张)</th>
                        <th>可容纳堂食人数(人)</th>
                        <th>日均翻桌率(次)</th>
                        <th>日均账单数/桌数(单)</th>
                        <th>平均每桌用餐时间(分钟)</th>
                        <th>日均人流量(人)</th>
                        <th>工作日人流量(人)</th>
                        <th>节假日人流量(人)</th>
                        <th>每日高峰时段</th>
                        <th>高峰时段平均人流量(人)</th>
                        <th>是否提供外送服务</th>
                        <th>日均外送订单数(单)</th>
                        <th>店铺WiFi网络名称</th>
                        <th>店铺WiFi网络密码</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    @foreach($list as $value)
                        <tr>
                            <td>{{$value->id}}</td>
                            <td>{{$value->head_name}}</td>
                            <td>{{$value->branch_name}}</td>
                            <td>{{$value->address}}</td>
                            <td>{{$value->area}}</td>
                            <td>{{$value->table_num}}</td>
                            <td>{{$value->max_customer_num}}</td>
                            <td>{{$value->avg_repeat_table}}</td>
                            <td>{{$value->avg_orders_num}}</td>
                            <td>{{$value->avg_meal_time}}</td>
                            <td>{{$value->avg_customer_num}}</td>
                            <td>{{$value->workday_customer_rate}}</td>
                            <td>{{$value->holiday_customer_rate}}</td>
                            <td>{{$value->rush_hours}}</td>
                            <td>{{$value->rush_hours_rate}}</td>
                            <td>
                                @if($value->is_outside_order == '1')
                                    是
                                @else
                                    否
                                @endif
                            </td>
                            <td>{{$value->avg_outside_order}}</td>
                            <td>{{$value->wifi_name}}</td>
                            <td>{{$value->wifi_pwd}}</td>
                            <td>
                                <a href="/admin/shop/edit?shopId={{ $value->id }}" class="btn btn-primary btn-xs"><i class="icon-pencil"></i></a>
                                <button class="btn btn-danger btn-xs  del_banner" shopid="{{ $value->id }}"><i class="icon-trash"></i></button>
                            </td>
                        </tr>
                    @endforeach
                    </tbody>
                </table>

            </section>
        </div>
    </section>
</section>

<!--main content end-->
@include('admin.tip_information')
<script type="text/javascript">
    seajs.use('page/admin/activity/list' , function(mod){
        mod.init()
    });

    $('body').on('click', '.del_banner', function(){
        var shopid = $(this).attr('shopid');
        if(confirm('是否要删除【id = ' + shopid + '】的商户?')) {
            $.post('/admin/shop/del', { shopid:shopid }, function(data) {
                console.log(data);
                if (data.code == '1')
                    location.replace('/admin/shop/shop-list');
                else {
                    alert('删除失败');
                    console.log(data);
                }
            });
        }
    });
</script>

@include('admin.footer')


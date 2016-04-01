@include('admin.header')

@include('admin.menu')

        <!--main content start-->
<section id="main-content">
    <section class="wrapper site-min-height">
        <div class="col-xs-12 panel">
            <div id="_msg"></div>
            <section class="panel">
                <header class="panel-heading">
                    {{$date}} 商户推广明细
                </header>

                <div>
                    <table class="table table-striped table-advance table-hover" id="android_table">
                        <thead>
                        <tr>
                            <th style="text-align: center;">商家id</th>
                            <th style="text-align: center;">商家</th>
                            <th style="text-align: center;">日均客流量</th>
                            <th style="text-align: center;">扫码人数</th>
                            <th style="text-align: center;">领红包人数</th>
                            <th style="text-align: center;">注册人数</th>
                            <th style="text-align: center;">夺宝人次</th>
                        </tr>
                        </thead>
                        <tbody>
                        @foreach($daily as $value)
                            @if ($value->shop_id != -1 && $value->shop_id != -2)
                                <tr>
                                    <td align="center">{{$value->shop_id}}</td>
                                    <td align="center">{{$value->branch_name!=null? $value->branch_name : $value->head_name}}</td>
                                    <td align="center">{{$value->avg_customer_sum}}</td>
                                    <td align="center">{{$value->scan_sum}}</td>
                                    <td align="center">{{$value->scan_num}}</td>
                                    <td align="center">{{$value->real_sum}}</td>
                                    <td align="center">{{$value->duobao_sum}}</td>
                                </tr>
                            @elseif($value->shop_id == -1)
                                <tr style="font-weight: bolder;font-size: 18px;">
                                    <td colspan="2" align="left">统计日期：{{$date}}</td>
                                    <td align="center">{{$value->avg_customer_sum}}</td>
                                    <td align="center">{{$value->scan_sum}}</td>
                                    <td align="center">{{$value->red_packet_sum}}</td>
                                    <td align="center">{{$value->real_sum}}</td>
                                    <td align="center">{{$value->duobao_sum}}</td>
                                </tr>
                            @else
                                <tr>
                                    <td colspan="12" align="center">暂无数据</td>
                                </tr>
                            @endif
                        @endforeach
                        </tbody>
                    </table>
                    <input type="hidden" name="now_date" id="now_date" value="{{$today}}" />
                    <input type="hidden" name="sel_date" id="sel_date" value="{{$date}}" />
                </div>
            </section>

            <section class="panel">
                <header class="panel-heading">
                    {{$date}} 商户推广占比
                </header>
                <div>
                    <table class="table table-striped table-advance table-hover" id="android_table">
                        <thead>
                        <tr>
                            <th style="text-align: center;" colspan="2">商家id</th>
                            <th style="text-align: center;"></th>
                            <th style="text-align: center;">商家</th>
                            <th style="text-align: center;">进店扫码比例<br>(扫码人数/日均客流量)</th>
                            <th style="text-align: center;">扫码领红包比例<br>(领红包人数/扫码人数)</th>
                            <th style="text-align: center;">红包注册比例<br>(注册人数/领红包人数)</th>
                            <th style="text-align: center;">人均夺宝次数<br>(夺宝人次/注册人数)</th>
                        </tr>
                        </thead>
                        <tbody>
                        @foreach($daily as $value)
                            @if ($value->shop_id != -1 && $value->shop_id != -2)
                                <tr>
                                    <td align="center" colspan="2">{{$value->shop_id}}</td>
                                    <td align="center"></td>
                                    <td align="center">{{$value->branch_name!=null? $value->branch_name : $value->head_name}}</td>
                                    <td align="center">{{$value->scanSum_divide_avgCustomerSum}}</td>
                                    <td align="center">{{$value->redSum_divide_scanSum}}</td>
                                    <td align="center">{{$value->registerSum_divide_redSum}}</td>
                                    <td align="center">{{$value->dbSum_divide_registerSum}}</td>
                                </tr>
                            @endif
                        @endforeach
                        </tbody>
                    </table>
                </div>
            </section>

            <section class="col-xs-12 panel">
                <header class="panel-heading">
                    {{$date}} 当日收入
                </header>
                <div>
                    <table class="table table-striped table-advance table-hover" id="android_table">
                        <thead>
                            <tr>
                                <th style="text-align: center;">真实用户</th>
                                <th style="text-align: center;">虚拟用户</th>
                                <th style="text-align: center;">累计</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td align="center">￥{{$money->count_buy_true}}</td>
                                <td align="center">￥{{$money->count_buy_flase}}</td>
                                <td align="center">￥{{$money->count_buy_true + $money->count_buy_flase}}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </section>

        </div>
    </section>
</section>

<!--main content end-->

@include('admin.footer')


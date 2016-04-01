@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/sample/jquery/jquery-1.8.3.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/flatlib/js/Chart.js" charset="UTF-8"></script>

<!--main content start-->
<section id="main-content">
    <section class="wrapper site-min-height">
        <div class="col-xs-12 panel">
            <div id="_msg"></div>
            <section class="panel">
                <header class="panel-heading">
                    {{$date}}推广详情

                    <div class="input-append date dpYears pull-right" style="width:200px;">
                        <input class="form-control form-control-inline input-append  form_date" data-date-format="yyyy-mm-dd" data-date="{{$date}}"  size="16" type="text" value="{{$date}}" readonly/>
                    </div>
                    <div class="pull-right input-append" style="margin-left: 10px;">
                        日期筛选：
                    </div>
                </header>

                <div class="row">
                    <div class="col-md-6" id="scan_osType_pie" style="display: none">
                        <section class="panel" id="sel_div">
                            <header class="panel-heading">
                                {{$date}}(扫码人群)手机系统占比总计
                            </header>
                            <div class="panel-body" style="text-align: left;" id="pie_body">
                                <canvas id="chart-scan" width="600" height="200"></canvas>
                            </div>
                        </section>
                    </div>
                    <div class="col-md-6" id="osType_div" style="display: none">
                        <section class="panel">
                            <header class="panel-heading">
                                {{$date}}(领红包人群)手机操作系统占比
                            </header>
                            <div class="panel-body" style="text-align: left">
                                <canvas id="chart-area" height="200" width="600"></canvas>
                            </div>
                        </section>
                    </div>
                </div>

                <div>
                    <table class="table table-striped table-advance table-hover" id="android_table">
                        <thead>
                        <tr>
                            <th style="text-align: center;">商家id</th>
                            <th style="text-align: center;">商家名称</th>
                            <th style="text-align: center;">分店名称</th>
                            <th style="text-align: center;">日均客流量</th>
                            <th style="text-align: center;">平均每天<br>扫码次数</th>
                            <th style="text-align: center;">累计<br/>扫码数</th>
                            <th style="text-align: center;">android<br/>扫码数</th>
                            <th style="text-align: center;">iphone<br/>扫码数</th>
                            <th style="text-align: center;">累计<br/>领红包</th>
                            <th style="text-align: center;">android<br/>领红包</th>
                            <th style="text-align: center;">iphone<br/>领红包</th>
                            <th style="text-align: center;">红包领取率<br>(领红包数/客流量)</th>
                            <th style="text-align: center;">平均每天<br>注册人数</th>
                            <th style="text-align: center;">注册<br>人数</th>
                            <th style="text-align: center;">用户转化率<br>(注册人数/扫码次数)</th>
                            <th style="text-align: center;">桌贴<br>扫码(领红包)</th>
                            <th style="text-align: center;">桌立<br>扫码(领红包)</th>
                            <th style="text-align: center;">易拉宝<br>扫码(领红包)</th>
                            <th style="text-align: center;">卫生间<br>扫码(领红包)</th>
                        </tr>
                        </thead>
                        <tbody>
                        @foreach($list as $value)
                            @if ($value->shop_id != -1 && $value->shop_id != -2)
                                <tr>
                                    <td align="center">{{$value->shop_id}}</td>
                                    <td align="center">{{$value->head_name}}</td>
                                    <td align="center">{{$value->branch_name}}</td>
                                    <td align="center">{{$value->avg_customer_sum}}</td>
                                    <td align="center">{{$value->avg_scan_sum}}</td>
                                    <td align="center">{{$value->scan_sum}}</td>
                                    <td align="center">{{$value->scan_sum_android}}</td>
                                    <td align="center">{{$value->scan_sum_ios}}</td>
                                    <td align="center">{{$value->scan_num}}</td>
                                    <td align="center">{{$value->red_packet_android}}</td>
                                    <td align="center">{{$value->red_packet_ios}}</td>
                                    <td align="center">{{$value->percent_scan}}</td>
                                    <td align="center">{{$value->avg_real_sum}}</td>
                                    <td align="center">{{$value->real_sum}}</td>
                                    <td align="center">{{$value->percent}}</td>
                                    <td align="center">{{$value->scene_one_scan}}({{$value->scene_one}})</td>
                                    <td align="center">{{$value->scene_two_scan}}({{$value->scene_two}})</td>
                                    <td align="center">{{$value->scene_three_scan}}({{$value->scene_three}})</td>
                                    <td align="center">{{$value->scene_four_scan}}({{$value->scene_four}})</td>
                                </tr>
                            @elseif($value->shop_id == -1)
                                <tr style="font-weight: bolder;font-size: 18px;">
                                    <td colspan="3" align="left">统计日期：{{$date}}</td>
                                    <td align="center">{{$value->avg_customer_sum}}</td>
                                    <td align="center">{{$value->avg_scan_sum}}</td>
                                    <td align="center">{{$value->scan_sum}}</td>
                                    <td align="center">{{$value->scan_sum_android}}</td>
                                    <td align="center">{{$value->scan_sum_ios}}</td>
                                    <td align="center">{{$value->red_packet_sum}}</td>
                                    <td align="center">{{$value->red_packet_android}}</td>
                                    <td align="center">{{$value->red_packet_ios}}</td>
                                    <td align="center">{{$value->percent_scan}}</td>
                                    <td align="center">{{$value->avg_real_sum}}</td>
                                    <td align="center">{{$value->real_sum}}</td>
                                    <td align="center">{{$value->percent}}</td>
                                    <td align="center">{{$value->scene_one_scan}}({{$value->scene_one}})</td>
                                    <td align="center">{{$value->scene_two_scan}}({{$value->scene_two}})</td>
                                    <td align="center">{{$value->scene_three_scan}}({{$value->scene_three}})</td>
                                    <td align="center">{{$value->scene_four_scan}}({{$value->scene_four}})</td>
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
        </div>
    </section>
</section>

<!--main content end-->
@include('admin.tip_information')
<script type="text/javascript" src="/js/shop/shopShareByDay.js" charset="UTF-8"></script>

@include('admin.footer')


@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/flatlib/js/Chart.js" charset="UTF-8"></script>

<!--main content start-->
<section id="main-content">
    <section class="wrapper site-min-height">
        <div class="col-xs-12 col-sm-12 panel">
            <div id="_msg"></div>
            <section class="panel">
                <header class="panel-heading">
                    商户推广总计
                    <div class="pull-right" style="margin-left: 10px;">
                        <Input type="button" class="btn btn-danger submit_action" id="allBtn" value="取消筛选"/>
                    </div>
                    <div class="pull-right" style="margin-left: 10px;">
                        <button class="btn btn-danger submit_action" type="button" id="betweenBtn">提交</button>
                    </div>
                    <div class="input-append date dpYears pull-right" style="width:200px;margin-left: 10px;">
                        <input id="endDate" class="form-control form-control-inline input-append  form_date" data-date-format="yyyy-mm-dd" data-date="{{$endDate}}"  size="16" type="text" value="{{$endDate}}" readonly/>
                    </div>
                    <div class="input-append date dpYears pull-right" style="margin-left: 10px;">
                        结束日期:
                    </div>
                    <div class="input-append date dpYears pull-right" style="width:200px;margin-left: 10px;">
                        <input id="beginDate" class="form-control form-control-inline input-append  form_date" data-date-format="yyyy-mm-dd" data-date="{{$beginDate}}"  size="16" type="text" value="{{$beginDate}}" readonly/>
                    </div>
                    <div class="input-append date dpYears pull-right">
                        开始日期:
                    </div>
                </header>

                <div class="row" id="chart_div">
                    <div class="col-lg-3" id="chart_div_1">
                        <section class="panel">
                            <header class="panel-heading">
                                推广用户总数-TOP<lavel id="lab_reg">5</lavel>
                            </header>
                            <div class="panel-body">
                                <canvas id="canvas"  width="500" height="350"></canvas>
                            </div>
                        </section>
                    </div>
                    <div class="col-lg-3" id="middle_div">
                        <section class="panel" id="">
                            <header class="panel-heading">
                                红包注册率(%)-TOP<lavel id="lab_per">5</lavel>
                            </header>
                            <div class="panel-body">
                                <canvas id="canvas_bar" width="500" height="350" ></canvas>
                            </div>
                        </section>
                    </div>
                    <div class="col-lg-3" id="osType_div">
                        <section class="panel" id="sel_div">
                            <header class="panel-heading">
                                (领红包人群)手机系统占比总计
                            </header>
                            <div class="panel-body" style="text-align: center;" id="pie_body">
                                <canvas id="chart-area" width="" height=""></canvas>
                            </div>
                        </section>
                    </div>
                    <div class="col-lg-3" id="scanType_div">
                        <section class="panel" id="sel_div">
                            <header class="panel-heading">
                                (扫码人群)手机系统占比总计
                            </header>
                            <div class="panel-body" style="text-align: center;" id="pie_body">
                                <canvas id="chart-scan" width="" height=""></canvas>
                            </div>
                        </section>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-striped table-advance table-hover" id="android_table">
                        <thead>
                        <tr>
                            <th>商家id</th>
                            <th style="text-align: center;">商家名称</th>
                            <th style="text-align: center;">分店名称</th>
                            <th style="text-align: center;">推广<br>天数</th>
                            <th style="text-align: center;">累计<br/>扫码数</th>
                            <th style="text-align: center;">android<br/>扫码数</th>
                            <th style="text-align: center;">iphone<br/>扫码数</th>
                            <th style="text-align: center;">平均每天<br>扫码次数</th>
                            <th style="text-align: center;">累计<br/>领红包</th>
                            <th style="text-align: center;">android<br/>领红包</th>
                            <th style="text-align: center;">iphone<br/>领红包</th>
                            <th style="text-align: center;">红包领取率<br>(领红包数/扫码数)</th>
                            <th style="text-align: center;">推广注册<br>总人数</th>
                            <th style="text-align: center;">平均每天<br>注册人数</th>
                            <th style="text-align: center;">红包注册率<br>(注册数/领红包数)</th>
                            <th style="text-align: center;">桌贴<br>扫码(领红包)</th>
                            <th style="text-align: center;">桌立<br>扫码(领红包)</th>
                            <th style="text-align: center;">易拉宝<br>扫码(领红包)</th>
                            <th style="text-align: center;">卫生间<br>扫码(领红包)</th>
                            {{--<th style="text-align: center;">最后统计时间</th>--}}
                        </tr>
                        </thead>
                        <tbody>
                        @foreach($list as $value)
                            <tr>
                                <td>{{$value->sh_shop_id}}</td>
                                <td align="center">{{$value->head_name}}</td>
                                <td align="center">{{$value->branch_name}}</td>
                                <td align="center">{{$value->days}}</td>
                                <td align="center">{{$value->scan_sum}}</td>
                                <td align="center">{{$value->scan_sum_android}}</td>
                                <td align="center">{{$value->scan_sum_ios}}</td>
                                <td align="center">{{$value->avg_scan_sum}}</td>
                                <td align="center">{{$value->scan_num}}</td>
                                <td align="center">{{$value->red_packet_android}}</td>
                                <td align="center">{{$value->red_packet_ios}}</td>
                                <td align="center">{{$value->red_packet_percent}}</td>
                                <td align="center">{{$value->real_num}}</td>
                                <td align="center">{{$value->avg_real_num}}</td>
                                <td align="center">{{$value->percent}}</td>
                                <td align="center">{{$value->scene_one_scan}}({{$value->scene_one}})</td>
                                <td align="center">{{$value->scene_two_scan}}({{$value->scene_two}})</td>
                                <td align="center">{{$value->scene_three_scan}}({{$value->scene_three}})</td>
                                <td align="center">{{$value->scene_four_scan}}({{$value->scene_four}})</td>
                                {{--<td align="center">{{$value->insert_date}}</td>--}}
                            </tr>
                        @endforeach
                        <tr>
                            <td colspan="19">最后统计时间: {{$value->insert_date}}</td>
                        </tr>
                        </tbody>
                    </table>
                    <input type="hidden" name="now_date" id="now_date" value="{{$today}}" />
                    <input type="hidden" name="transName" id="transName" value="{{$transName}}" />
                </div>
            </section>
        </div>
    </section>
</section>

<!--main content end-->
@include('admin.tip_information')
<script type="text/javascript" src="/js/shop/shopShare.js" charset="UTF-8"></script>

@include('admin.footer')


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
                    历史推广记录
                </header>

                <div class="row">
                    <div class="col-lg-6">
                        <section class="panel">
                            <header class="panel-heading">
                                领红包人数&注册人数
                            </header>
                            <div class="panel-body">
                                <canvas id="canvas" height="200" width="600"></canvas>
                            </div>
                        </section>
                    </div>
                    <div class="col-lg-6">
                        <section class="panel">
                            <header class="panel-heading">
                                用户转化率(%)
                            </header>
                            <div class="panel-body">
                                <canvas id="canvas_bar" height="200" width="600"></canvas>
                            </div>
                        </section>
                    </div>
                </div>

                <div style="">
                    <table class="table table-striped table-advance table-hover" id="android_table">
                        <thead>
                        <tr style="text-align: center;">
                            <th style="text-align: center;">日期</th>
                            <th style="text-align: center;">日均客流量</th>
                            <th style="text-align: center;">平均每天<br>扫码次数</th>
                            <th style="text-align: center;">扫码次数</th>
                            <th style="text-align: center;">领红包人数</th>
                            <th style="text-align: center;">红包领取率<br>(领红包人数/客流量)</th>
                            <th style="text-align: center;">平均每天<br>注册人数</th>
                            <th style="text-align: center;">注册人数</th>
                            <th style="text-align: center;">用户转化率<br>(注册人数/扫码次数)</th>
                            <th style="text-align: center;">桌贴<br>扫码(领红包)</th>
                            <th style="text-align: center;">桌立<br>扫码(领红包)</th>
                            <th style="text-align: center;">易拉宝<br>扫码(领红包)</th>
                            <th style="text-align: center;">卫生间<br>扫码(领红包)</th>
                        </tr>
                        </thead>
                        <tbody>
                        @foreach($list as $value)
                            <tr style="" align="center">
                                <td>{{$value->date}}</td>
                                <td>{{$value->avg_customer_sum}}</td>
                                <td>{{$value->avg_scan_sum}}</td>
                                <td>{{$value->scan_sum}}</td>
                                <td>{{$value->red_packet_sum}}</td>
                                <td>{{$value->percent_scan}}</td>
                                <td>{{$value->avg_real_sum}}</td>
                                <td>{{$value->real_sum}}</td>
                                <td>{{$value->percent}}</td>
                                <td>{{$value->scene_one_scan}}({{$value->scene_one_sum}})</td>
                                <td>{{$value->scene_two_scan}}({{$value->scene_two_sum}})</td>
                                <td>{{$value->scene_three_scan}}({{$value->scene_three_sum}})</td>
                                <td>{{$value->scene_four_scan}}({{$value->scene_four_sum}})</td>
                            </tr>
                        @endforeach
                        </tbody>
                    </table>
                    {{$list->links('admin.pageInfo')}}
                    <input type="hidden" name="now_date" id="now_date" value="{{$today}}" />
                </div>
            </section>
        </div>
    </section>
</section>

<!--main content end-->
@include('admin.tip_information')
<script>

    $.post('/admin/shop/shop-share-history', null , function(data) {
        if (data.code == '1' && data.data.list){
            var x_arr = new Array();
            var scan_data = new Array();
            var register_date = new Array();
            var percent_array = new Array();
            var percent_scan_array = new Array();

            $.each(data.data.list,function(n,value) {
                x_arr.push(value.date);
                scan_data.push(value.red_packet_sum);
                register_date.push(value.real_sum);

                var percent = value.percent;
                var i = percent.indexOf('%');
                percent_array.push(percent.substring(0,i));
                percent_scan_array.push(value.percent_scan);
            });
            generateChart(x_arr,scan_data,register_date);
            generateBar(x_arr,percent_array);
        }else{
            alert('请求失败');
        }
    });

    function generateChart(x_arr,scan_data,register_date){
        console.log(x_arr);
        console.log(scan_data);
        var lineChartData = {
            labels : x_arr,
            datasets : [
                {
                    label: "扫码人数",
                    fillColor : "rgba(220,220,220,0.2)",
                    strokeColor : "rgba(220,220,220,1)",
                    pointColor : "rgba(220,220,220,1)",
                    pointStrokeColor : "#fff",
                    pointHighlightFill : "#fff",
                    pointHighlightStroke : "rgba(220,220,220,1)",
                    data : scan_data
                },
                 {
                 	label: "注册人数",
                 	fillColor : "rgba(151,187,205,0.2)",
                 	strokeColor : "rgba(151,187,205,1)",
                 	pointColor : "rgba(151,187,205,1)",
                 	pointStrokeColor : "#fff",
                 	pointHighlightFill : "#fff",
                 	pointHighlightStroke : "rgba(151,187,205,1)",
                 	data : register_date
                 }
            ],
        }

        var ctx = document.getElementById("canvas").getContext("2d");
        window.myLine = new Chart(ctx).Line(lineChartData, {
            responsive: true
        });
    }

    function generateBar(x_arr,percent_array){
        var barChartData = {
            labels : x_arr,
            datasets : [
                {
                    fillColor : "rgba(220,220,220,0.5)",
                    strokeColor : "rgba(220,220,220,0.8)",
                    highlightFill: "rgba(220,220,220,0.75)",
                    highlightStroke: "rgba(220,220,220,1)",
                    data :percent_array
                }
            ]

        }
        var ctx = document.getElementById("canvas_bar").getContext("2d");
        window.myBar = new Chart(ctx).Bar(barChartData, {
            responsive : true
        });
    }

</script>

@include('admin.footer')


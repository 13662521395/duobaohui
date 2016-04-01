@include('admin.header')

@include('admin.menu')


<!--main content start-->
<section id="main-content">
    <section class="wrapper">
    <section class="panel">
      <header class="panel-heading">
        
      </header>
        <div class="row">
            <div class="col-lg-6" style="width:100%;float:left;">
                <!--breadcrumbs start -->
                 <header class="panel-heading breadcrumb" style="width:96%;margin:0 auto 10px;">
                          购买和消费
                 </header>
                <!--breadcrumbs end -->
            </div>
            {{--<form action='/admin/bill/index' method='get' style='width:98%;margin:0 50px 10px;float:left'>--}}
                 {{--<div class="input-append date dpYears" style='float:left;width:170px;margin:-0px 30px 0 10px;'>--}}
                   {{--<input class="form-control form-control-inline input-append form_date" data-date-format="yyyy-mm-dd hh:ii:ss" data-date="{{$start_time}}" name="start_time" size="16" type="text" value="{{$start_time}}"   readonly/>--}}
                {{--</div>--}}
                {{--<div class="input-append date dpYears" style='float:left;width:170px;margin:-0px 20px 0 10px;'>--}}
                    {{--<input class="form-control form-control-inline input-append form_date" data-date-format="yyyy-mm-dd hh:ii:ss" data-date="{{$end_time}}" name="end_time" size="16" type="text" value="{{$end_time}}"   readonly/>--}}
                {{--</div>--}}
                {{--<div style='float:left'>--}}
                    {{--<button type="submit" class="btn btn-shadow btn-success" id='search' style='float:right;margin:-5px 50px 0 8px;'>搜索</button>--}}
                {{--</div>--}}

                {{--<div class="input-append date dpYears" style='float:right;width:170px;margin:-0px 30px 0 10px;'>--}}
                    {{--<button class="btn btn btn-shadow btn-warning" style='float:right;margin:-5px 50px 40px 8px;' name="export_data">导出数据</button>--}}
                {{--</div>--}}
            {{--</form>--}}
            @if(!empty($list['count_buy_true'])){
            <div class="row state-overview" style="width:90%;margin:0 auto 10px;">

                <div class="col-lg-4 col-sm-6">

                  <button type="button" class="btn btn-round btn-default">总购买次数</button>
                    <section class="panel">
                        <div class="symbol terques">
                            <p>真实用户</p>
                            <h3 class="">{{$list['count_buy_true']->trade_num}}</h3>
                        </div>
                        <div class="symbol" >
                            <p>虚拟用户</p>
                            <h3 class="">{{$list['count_buy_false']->trade_num}}</h3>
                        </div>
                    </section>
                </div>
                <div class="col-lg-4 col-sm-6">
                  
                  <button type="button" class="btn btn-round btn-default">总消费金额(红包除外)</button>
                    <section class="panel">
                        <div class="symbol red">
                             <p>真实用户</p>
                            <h3 class="">￥{{$list['count_buy_true']->trade_money}}</h3>
                        </div>
                        <div class="symbol">
                            <p>虚拟用户</p>
                            <h3 class="">￥{{$list['count_buy_false']->trade_money}}</h3>
                        </div>
                    </section>
                </div>
                <div class="col-lg-4 col-sm-6">
                  
                  <button type="button" class="btn btn-round btn-default">奖品总价</button>
                    <section class="panel">
                        <div class="symbol yellow">
                             <p>真实用户</p>
                            <h3 class="">￥{{$list['count_buy_true']->luck_money}}</h3>
                        </div>
                        <div class="symbol">
                             <p>虚拟用户</p>
                            <h3 class="">￥{{$list['count_buy_false']->luck_money}}</h3>
                        </div>
                    </section>
                </div>
                @elseif(!empty($list['buy_true'])){
                    <div class="row state-overview" style="width:90%;margin:0 auto 10px;">

                        <div class="col-lg-4 col-sm-6">

                            <button type="button" class="btn btn-round btn-default">总购买次数</button>
                            <section class="panel">
                                <div class="symbol terques">
                                    <p>真实用户</p>
                                    <h3 class="">{{$list['buy_true']}}</h3>
                                </div>
                                <div class="symbol" >
                                    <p>虚拟用户</p>
                                    <h3 class="">{{$list['buy_flase']}}</h3>
                                </div>
                            </section>
                        </div>
                        <div class="col-lg-4 col-sm-6">

                            <button type="button" class="btn btn-round btn-default">总消费金额(红包除外)</button>
                            <section class="panel">
                                <div class="symbol red">
                                    <p>真实用户</p>
                                    <h3 class="">￥{{$list['count_amount_true']}}</h3>
                                </div>
                                <div class="symbol">
                                    <p>虚拟用户</p>
                                    <h3 class="">￥{{$list['count_amount_flase']}}</h3>
                                </div>
                            </section>
                        </div>
                        <div class="col-lg-4 col-sm-6">

                            <button type="button" class="btn btn-round btn-default">奖品总价</button>
                            <section class="panel">
                                <div class="symbol yellow">
                                    <p>真实用户</p>
                                    <h3 class="">￥{{$list['count_total_fee_true']}}</h3>
                                </div>
                                <div class="symbol">
                                    <p>虚拟用户</p>
                                    <h3 class="">￥{{$list['count_total_fee_flase']}}</h3>
                                </div>
                            </section>
                        </div>

                @else
                        <div class="row state-overview" style="width:90%;margin:0 auto 10px;">

                            <div class="col-lg-4 col-sm-6">

                                <button type="button" class="btn btn-round btn-default">总购买次数</button>
                                <section class="panel">
                                    <div class="symbol terques">
                                        <p>真实用户</p>
                                        <h3 class="">0</h3>
                                    </div>
                                    <div class="symbol" >
                                        <p>虚拟用户</p>
                                        <h3 class="">0</h3>
                                    </div>
                                </section>
                            </div>
                            <div class="col-lg-4 col-sm-6">

                                <button type="button" class="btn btn-round btn-default">总消费金额(红包除外)</button>
                                <section class="panel">
                                    <div class="symbol red">
                                        <p>真实用户</p>
                                        <h3 class="">￥0</h3>
                                    </div>
                                    <div class="symbol">
                                        <p>虚拟用户</p>
                                        <h3 class="">￥0</h3>
                                    </div>
                                </section>
                            </div>
                            <div class="col-lg-4 col-sm-6">

                                <button type="button" class="btn btn-round btn-default">奖品总价</button>
                                <section class="panel">
                                    <div class="symbol yellow">
                                        <p>真实用户</p>
                                        <h3 class="">￥0</h3>
                                    </div>
                                    <div class="symbol">
                                        <p>虚拟用户</p>
                                        <h3 class="">￥0</h3>
                                    </div>
                                </section>
                            </div>

                @endif

               <!--  <div class="col-lg-3 col-sm-6">
                    <section class="panel">
                        <div class="symbol blue">
                            <p>New Users</p>
                            <h1 class="count">495</h1>
                        </div>
                        <div class="symbol">
                            <p>New Users</p>
                            <h1 class="count">495</h1>
                        </div>
                    </section>
                </div> -->
          
            </div>


        </div>
    </section>


  <!--main content start-->

   
  <section id="main">
        <!-- page start-->
           <div class="row">
                  <section class="panel">
                      <header class="panel-heading">
                          真实用户详细基础统计数据
                      </header>
                      <div class="panel-body">
                           <div id="tradeNum" style="height:400px"></div>
                      </div>
                  </section>
           </div>
           <div class="row">
                  <section class="panel">
                      <header class="panel-heading">
                          真实用户和虚拟用对比--交易金额
                      </header>
                      <div class="panel-body">
                          <div id="messenger"  style="height:400px"  ></div>
                      </div>
                  </section>
           </div>
           <div class="row">
                  <section class="panel">
                      <header class="panel-heading">
                          真实用户和虚拟用对比--奖品金额
                      </header>
                      <div class="panel-body">
                          <div id="total_fee"  style="height:400px"  ></div>
                      </div>
                  </section>
           </div>


        <!-- page end-->
  </section>

  <!--main content end-->
    </section>


    </section>
</section>
<!--main content end-->
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/sample/jquery/jquery-1.8.3.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>

<script type="text/javascript">
$('.form_date').datetimepicker({
    language:  'fr',
    format:'yyyy-mm-dd hh:ii:ss',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 1,
    minView: 1,
    forceParse: 0
});
  seajs.use('page/admin/bill/index' , function(mod){
    mod.getIndex();  
  });

</script>


@include('admin.footer')
{{ HTML::script('/flatlib/js/echarts-all.js') }}


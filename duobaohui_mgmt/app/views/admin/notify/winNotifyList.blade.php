@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="/flatlib/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<!--main content start-->
<section id="main-content">
    <section class="wrapper site-min-height">
        <div class="col-xs-12 panel">
            <div id="_msg"></div>
            <section class="panel">
                <header class="panel-heading">
                    中奖消息推送列表
                </header>

                <div class="panel-body">
                    <form class="form-horizontal tasi-form" method="post" >
                        <div class="row col-xs-10">
                            <div class="col-xs-2">
                                <div class='form-group'>
                                    <label for="goods">开奖时间</label>
                                    <div class="col-sm-12" style="padding-left: 0px;">
                                        <input type="text" id='luck_code_create_time' name='luck_code_create_time' readonly class="form-control form-control-inline input-medium default-date-picker" value="{{$begin_date}}"/>
                                        <label class="error">{{$errors->first('begin_date')}}</label>
                                        {{--<input class="form-control form-control-inline input-append  form_date" data-date-format="yyyy-mm-dd" data-date="{{$begin_date or ''}}"  size="16" type="text" value="{{$begin_date or ''}}" readonly/>--}}
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-2">
                                <div class="form-group">
                                    <label for="goods">APP推送状态</label>
                                    <select class="form-control col-sm-10" name="app_status" id="app_status">
                                        <option value="" @if($app_status == -1) selected="selected" @endif>请选择</option>
                                        <option value="0" @if($app_status == 0) selected="selected" @endif>待推送</option>
                                        <option value="1" @if($app_status == 1) selected="selected" @endif>推送中</option>
                                        <option value="2" @if($app_status == 2) selected="selected" @endif>已推送</option>
                                        <option value="3" @if($app_status == 3) selected="selected" @endif>推送失败</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xs-2" style="padding-left: 30px;">
                                <div class="form-group">
                                    <label for="goods">短信发送状态</label>
                                    <select class="form-control col-sm-10" name="sms_status" id="sms_status">
                                        <option value="" @if($sms_status == -1) selected="selected" @endif>请选择</option>
                                        <option value="0" @if($sms_status == 0) selected="selected" @endif>待发送</option>
                                        <option value="1" @if($sms_status == 1) selected="selected" @endif>发送中</option>
                                        <option value="2" @if($sms_status == 2) selected="selected" @endif>已发送</option>
                                        <option value="3" @if($sms_status == 3) selected="selected" @endif>发送失败</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xs-2" style="padding-left: 30px;">
                                <div class="form-group">
                                    <label for="goods">&nbsp;</label>
                                    <div class="col-sm-12">
                                        <button class="btn btn-info" id="queryIt" type="button" style="" >查询</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {{--<div class="form-group">--}}
                            {{--<div class="col-xs-2" >--}}
                                {{--<button class="btn btn-info" id="queryIt" type="button" >查询</button>--}}
                                {{--<button class="btn btn-info col-xs-offset-1" id="reset" type="reset" >重置</button>--}}
                            {{--</div>--}}
                        {{--</div>--}}
                    </form>
                </div>

                <table class="table table-striped table-advance table-hover">
                    <thead>
                    <tr>
                        <th style="white-space:nowrap;">活动期数ID</th>
                        <th>获奖者</th>
                        <th style="white-space:nowrap;">奖品</th>
                        <th>奖品图</th>
                        <th>开奖时间</th>
                        <th>APP推送状态</th>
                        <th>APP推送时间</th>
                        <th>短信发送状态</th>
                        <th>短信发送时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    @foreach($list as $item)
                        <tr>
                            <td>{{$item->sh_activity_period_id}}</td>
                            <td>{{$item->nick_name}}</td>
                            <td>(第{{$item->period_number}}期){{$item->goods_name}}</td>
                            <td><img  src="{{$item->goods_img;}}" height=50 /></td>
                            <td style="white-space:nowrap;">{{$item->pre_luck_code_create_time}}</td>
                            <td>{{$item->app_send_flag}}</td>
                            <td>{{$item->app_send_time}}</td>
                            <td>{{$item->sms_send_flag}}</td>
                            <td>{{$item->sms_send_time}}</td>
                        </tr>
                    @endforeach
                    </tbody>
                </table>
                {{$list->links('admin.pageInfo')}}
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

    $(document).ready(function() {
        $(function() {
            $(".default-date-picker").datepicker({
                format: 'yyyy-mm-dd',
                weekStart: 1,
                todayBtn: 1,
                todayHighlight: 1,
                startView: 2,
                minView: 2,
                forceParse: 0,
                autoClose: 1,
                maxDate: 0,
            });
        });

        var queryIt = $('#queryIt');
        $('body').on('click','#queryIt',function(event) {
            var url = '/admin/notify/client-win-notify-list?1=1';
            var appval = $('#app_status').find(':selected').attr('value');
            var smsval = $('#sms_status').find(':selected').attr('value');
            var luck_code_create_time = $('#luck_code_create_time').val();

            console.log(appval,smsval);
            if(appval) {
                url = url + "&app_status="+appval;
            }
            if(smsval) {
                url = url + "&sms_status="+smsval;
            }
            if(luck_code_create_time){
                url = url + '&luck_code_create_time=' + luck_code_create_time;
            }
            location.href = url;
        });
    });

</script>

@include('admin.footer')


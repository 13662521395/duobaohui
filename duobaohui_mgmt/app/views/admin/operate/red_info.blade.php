@include('admin.header')
@include('admin.menu')

        <!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <section class="panel">
            <header class="panel-heading">
                批次红包明细
            </header>
            <table class="table table-hover">
                <tr>
                    <td>名称：{{$desc[0]->red_money_name}}</td>
                    <td>总金额：{{$desc[0]->total_price}}</td>
                    <td>有效时间：{{$desc[0]->begin_date}} &nbsp;~ &nbsp;{{$desc[0]->end_date}}</td>
                </tr>
                <tr>
                    <td>批次：{{$desc[0]->id}}</td>
                    <td>红包金额：{{$desc[0]->price}}</td>
                    <td>消费限制：{{$desc[0]->consumption}}</td>
                </tr>
                <tr>
                    <td>创建时间：{{$desc[0]->create_time}}</td>
                    <td>红包总数量：{{$desc[0]->num}} &nbsp;&nbsp;<button type="button" class="btn btn-sm" data-toggle="modal" data-target=".bs-example-modal-lg">查看使用情况</button></span></td>
                    <td>发放方案：{{$desc[0]->recharge}}</td>
                </tr>
                <tr>
                    <td>创建者：@if(empty($activity[1])) ————  @else {{$activity[1]}} @endif</td>
                    <td>批次状态：@if($desc[0]->is_start ==1) 该批次已启用 @else 该批次未启用  @endif</td>
                    <td>可用活动限制：@if(empty($activity[0]) || strlen($activity[0]) < 4 ) 无限制  @else {{$activity[0]}} @endif</td>
                </tr>
            </table>

            <form action='' method='get' style="height: 70px;">
                <div class="btn-group red-form-select" >
                    <select name="choice" class="select-style" style="width: 150px;font-size: 16px;">
                        <option value="0" @if(isset($choice) && $choice == 0) SELECTED @endif>全部</option>
                        <option value="1" @if(isset($choice) && $choice == 1) SELECTED @endif>未发放</option>
                        <option value="2" @if(isset($choice) && $choice == 2) SELECTED @endif>已发放</option>
                        <option value="3" @if(isset($choice) && $choice == 3) SELECTED @endif>已领取</option>
                        <option value="4" @if(isset($choice) && $choice == 4) SELECTED @endif>已使用</option>
                        <option value="5" @if(isset($choice) && $choice == 5) SELECTED @endif>已过期</option>
                    </select>
                </div>

                <div class="btn-group red-form-select" >
                    <select name="choice" class="select-style" style="width: 150px;font-size: 16px;"  disabled="disabled" >
                        <option value="0" SELECTED>金额</option>
                        {{--<option value="1">自动</option>--}}
                        {{--<option value="2">手动</option>--}}
                    </select>
                </div>
                <input type="hidden" name="id" value="{{$desc[0]->id}}">
                <button type="submit" class="btn btn-success" style='margin:20px 30px 0 28px;'>检索</button>
            </form>
    <div class="table-bordered">
        <table class="table">
            <th class="success">红包编号</th>
            <th class="error">金额</th>
            <th class="warning">红包状态</th>
            <th class="success">发放时间</th>
            <th class="info">领取时间</th>
            <th class="danger">使用时间</th>
            <th class="active">过期时间</th>
            <th class="success">发放至账号</th>
            <th class="error">明细操作</th>
            @if(isset($info) && !empty($info))

                @foreach($info as $val)
                    <tr>
                        <td>{{$val->id}}</td>
                        <td>{{$val->price}}</td>
                        <td>@if($val->status == 0) 未启用  @else   已启用 @endif</td>
                        <td>@if($val->issue_time == '0000-00-00 00:00:00') ————  @else   {{$val->issue_time}} @endif</td>
                        <td>@if($val->receive_time == '0000-00-00 00:00:00') ————  @else   {{$val->receive_time}} @endif</td>
                        <td>@if($val->use_time == '0000-00-00 00:00:00') ————  @else   {{$val->use_time}} @endif</td>
                        <td>@if($val->overdue_time == '0000-00-00 00:00:00') ————  @else   {{$val->overdue_time}} @endif</td>
                        <td>@if($val->nick_name == '') ————  @else   {{$val->nick_name}} @endif</td>
                        <td><a href="/admin/operate/look-redpacket-infomation?id={{$val->id}}">查看</a> </td>
                        {{--<td><button type="button" class="btn btn-sm look-info" onclick="lookRedpacketInfomation({{$val->id}})"  data-toggle="modal" data-target=".redpacket-modal-lg">查看</button></td>--}}
                    </tr>
                @endforeach
            @elseif(isset($list) && !empty($list))
                @foreach($list as $val)
                    <tr>
                        <td>{{$val->id}}</td>
                        <td>{{$val->price}}</td>
                        <td>@if($val->status == 0) 未启用  @else   已启用 @endif</td>
                        <td>@if($val->issue_time == '0000-00-00 00:00:00') ————  @else   {{$val->issue_time}} @endif</td>
                        <td>@if($val->receive_time == '0000-00-00 00:00:00') ————  @else   {{$val->receive_time}} @endif</td>
                        <td>@if($val->use_time == '0000-00-00 00:00:00') ————  @else   {{$val->use_time}} @endif</td>
                        <td>@if($val->overdue_time == '0000-00-00 00:00:00') ————  @else   {{$val->overdue_time}} @endif</td>
                        <td>@if($val->nick_name == '') ————  @else   {{$val->nick_name}} @endif</td>
                        <td><a href="/admin/operate/look-redpacket-infomation?id={{$val->id}}">查看</a> </td>
                        {{--<td><button type="button" class="btn btn-sm look-info" onclick="lookRedpacketInfomation({{$val->id}})"  data-toggle="modal" data-target=".redpacket-modal-lg">查看</button></td>--}}
                    </tr>
                @endforeach

            @endif
        </table>
    </div>
            @if(isset($info))
                {{$info->appends(array('id'=>$desc[0]->id))->links('admin.pageInfo')}}
            @elseif(isset($list))
                {{$list->appends(array('id'=>$desc[0]->id,'choice'=>$choice))->links('admin.pageInfo')}}
            @endif

        </section>
    </section>
</section>

<div id="look-redpacket" batch_id="{{$desc[0]->id}}"></div>

<div id="look-redpacket-info"></div>


<!--main content end-->
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/sample/jquery/jquery-1.8.3.min.js" charset="UTF-8"></script>
<script src="/flatlib/js/bootstrap-paginator.js"></script>

<script>
    seajs.use('page/admin/operate/look' , function(mod){
        mod.lookInfo();
    });


//    function lookRedpacketInfomation(id){
//
//        $.get('/admin/operate/look-redpacket-infomation', {'id':id}, function (data) {
//            $('#look-redpacket-info').html('');
//            $('#look-redpacket-info').html(data);
//        });
//    }

</script>


@include('admin.footer')
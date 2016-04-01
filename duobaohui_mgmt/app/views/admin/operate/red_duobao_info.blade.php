@include('admin.header')
@include('admin.menu')

        <!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <section class="panel">
            <header class="panel-heading">
                红包管理
            </header>
                <div class="table-responsive">
                    <table class="table">
                        <tr>
                            <td>红包编号:&nbsp;&nbsp;{{$list->id}}</td>
                        </tr>
                        <tr>
                            <td>红包金额:&nbsp;&nbsp;{{$list->price}}</td>
                        </tr>
                        <tr>
                            <td>红包状态:
                                <table class="table" style="margin-left: 30px;">
                                    <th class="success">状态</th>
                                    <th class="active">时间</th>
                                    <th class="warning">操作者</th>
                                    <tr>
                                        <td>@if($list->status == 0) 未发放 @else  已发放   @endif</td>
                                        <td>@if($list->issue_time == '0000-00-00 00:00:00') ———— @else  {{$list->issue_time}}   @endif</td>
                                        <td>@if($list->status == 0) ———— @else  {{$list->nickname}}   @endif</td>
                                    </tr>
                                    <tr>
                                        <td>@if($list->is_receive == 0) 未领取 @else  已领取   @endif</td>
                                        <td>@if($list->receive_time == '0000-00-00 00:00:00') ———— @else  {{$list->receive_time}} @endif</td>
                                        <td>@if($list->nick_name == '') ———— @else  {{$list->nick_name}}   @endif</td>
                                    </tr>
                                    <tr>
                                        <td>@if($list->is_receive == 0) 未使用 @else  已使用   @endif</td>
                                        <td>@if($list->use_time == '0000-00-00 00:00:00') ———— @else  {{$list->use_time}}  @endif</td>
                                        <td>@if($list->nick_name == '') ———— @else  {{$list->nick_name}}   @endif</td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>红包使用详情:</td>
                        </tr>
                        @if(!empty($use_info))
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;夺宝活动：{{$use_info->goods_name}}</td>
                            </tr>
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;购买人次：{{$use_info->num}}</td>
                            </tr>
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;补充金额：夺宝币￥{{$use_info->redpacket_price}}&nbsp;&nbsp;&nbsp;
                                    @if($use_info->pay_type == 0)

                                        余额支付￥{{$use_info->amount - $use_info->redpacket_price}}
                                    @else
                                        @if($use_info->recharge_channel == 0)
                                            支付宝支付￥{{$use_info->amount - $use_info->redpacket_price}}
                                        @elseif($use_info->recharge_channel == 1)
                                            微信支付￥{{$use_info->amount - $use_info->redpacket_price}}
                                        @elseif($use_info->recharge_channel == 2)
                                            红包支付￥{{$use_info->amount - $use_info->redpacket_price}}
                                        @else
                                            其他支付￥{{$use_info->amount - $use_info->redpacket_price}}
                                        @endif </td>
                                    @endif
                            </tr>
                        @endif
                    </table>
                </div>
            </div>
        </section>
    </section>
</section>

<!--main content end-->
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/sample/jquery/jquery-1.8.3.min.js" charset="UTF-8"></script>
<script src="/flatlib/js/bootstrap-paginator.js"></script>

@include('admin.footer')
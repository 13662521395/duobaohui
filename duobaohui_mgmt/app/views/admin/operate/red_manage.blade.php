@include('admin.header')
@include('admin.menu')

        <!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <section class="panel">
            <header class="panel-heading">
                红包管理
            </header>
            <form action='' method='get' style="height: 70px;">
                <div class="btn-group red-form-select" >
                    <select name="money" class="select-style">
                            <option value="0"  @if(isset($param['money']) && $param['money']==0) SELECTED  @endif>--金额分配--</option>
                            <option value="1"  @if(isset($param['money']) && $param['money']==1) SELECTED  @endif>单个金额</option>
                            <option value="2"  @if(isset($param['money']) && $param['money']==2) SELECTED  @endif>多个金额</option>
                    </select>
                </div>

                <div class="btn-group red-form-select" >
                    <select name="programme" class="select-style">
                        <option value="0" @if(isset($param['programme']) && $param['programme']==0) SELECTED  @endif>--发放方式--</option>
                        <option value="1" @if(isset($param['programme']) && $param['programme']==1) SELECTED  @endif>自动</option>
                        <option value="2" @if(isset($param['programme']) && $param['programme']==2) SELECTED  @endif>手动</option>
                    </select>
                </div>

                <div class="btn-group red-form-select" >剩余:
                    <select name="end_num" class="select-style">
                        <option value="0" @if(isset($param['end_num']) && $param['end_num']==0) SELECTED  @endif>大于</option>
                        <option value="1" @if(isset($param['end_num']) && $param['end_num']==1) SELECTED  @endif>小于</option>
                        <option value="2" @if(isset($param['end_num']) && $param['end_num']==2) SELECTED  @endif>全部</option>
                    </select>
                    <input type="text" name="end_num_input" placeholder="输入数量" >
                </div>

                <div class="btn-group red-form-select" >总金额:
                    <select name="total_price" class="select-style">
                        <option value="0" @if(isset($param['total_price']) && $param['total_price']==0) SELECTED  @endif>大于</option>
                        <option value="1" @if(isset($param['total_price']) && $param['total_price']==1) SELECTED  @endif>小于</option>
                        <option value="2" @if(isset($param['total_price']) && $param['total_price']==2) SELECTED  @endif>全部</option>
                    </select>
                    <input type="text" name="total_price_input" placeholder="输入总金额" >
                </div>


                <button type="submit" class="btn btn-shadow btn-success" id='search' style='margin:20px 30px 0 28px;'>检索</button>
            </form>
    <div class="table-bordered">
        <table class="table">
            <th class="success">红包名称</th>
            <th class="error">批次</th>
            <th class="warning">总金额(￥)</th>
            <th class="success">单个金额(￥)</th>
            <th class="info">数量(个)</th>
            <th class="danger">剩余(个)</th>
            <th class="active">创建时间</th>
            <th class="success">发放方案</th>
            <th class="error">批次状态</th>
            <th class="danger">操作</th>
                @if(isset($info))
                    @foreach($info as $val)
                        @if(!empty($val->price))
                        <tr>
                            <td>{{$val->red_money_name}}</td>
                            <td>{{$val->id}}</td>
                            <td>{{$val->total_price}}</td>
                            <td>{{$val->price}}</td>
                            <td>{{$val->num}}</td>
                            <td>{{$val->end_num}}</td>
                            <td>{{$val->create_time}}</td>
                            <td>{{$val->recharge}}</td>
                            <td>@if($val->is_start == 0) 未启用   @else   启用  @endif</td>
                            <td><a href="/admin/operate/red-manage-info?id={{$val->id}}">查看明细</a>&nbsp;&nbsp;&nbsp; <a href="#" class="sub-start" onclick="subStart({{$val->id}},{{$val->is_start}})">@if($val->is_start == 0) 启用 @else  停止   @endif</a></td>
                        </tr>
                        @else

                        @endif
                    @endforeach
                @elseif(isset($search))
                    @foreach($search as $val)
                    @if(!empty($val->price))
                        <tr>
                            <td>{{$val->red_money_name}}</td>
                            <td>{{$val->id}}</td>
                            <td>{{$val->total_price}}</td>
                            <td>{{$val->price}}</td>
                            <td>{{$val->num}}</td>
                            <td>{{$val->end_num}}</td>
                            <td>{{$val->create_time}}</td>
                            <td>{{$val->recharge}}</td>
                            <td>@if($val->is_start == 0) 未启用   @else   启用  @endif</td>
                            <td><a href="/admin/operate/red-manage-info?id={{$val->id}}">查看明细</a>&nbsp;&nbsp;&nbsp; <a href="#" class="sub-start" onclick="subStart({{$val->id}},{{$val->is_start}})">@if($val->is_start == 0) 启用 @else  停止   @endif</a></td>
                        </tr>
                    @else

                    @endif
                    @endforeach
                @else

                    <tr>
                        <td>1</td>
                        <td>2</td>
                        <td>3</td>
                        <td>4</td>
                        <td>5</td>
                        <td>6</td>
                        <td>7</td>
                        <td>8</td>
                        <td>9</td>
                        <td><a href="#">查看明细</a>&nbsp;&nbsp;&nbsp; <a href="#">启动</a></td>
                    </tr>
                @endif
        </table>
    </div>
            @if(isset($info))
                {{$info->links('admin.pageInfo')}}
            @elseif(isset($param['money']) && !isset($param['programme']))
                {{$search->appends(array('money'=>$param['money'],'end_num'=>$param['end_num'],'end_num_input'=>$param['end_num_input'],'total_price'=>$param['total_price'],'total_price_input'=>$param['total_price_input']))->links('admin.pageInfo')}}
            @elseif(isset($param['programme']) && !isset($param['money']))
                {{$search->appends(array('programme'=>$param['programme'],'end_num'=>$param['end_num'],'end_num_input'=>$param['end_num_input'],'total_price'=>$param['total_price'],'total_price_input'=>$param['total_price_input']))->links('admin.pageInfo')}}
            @elseif(isset($param['programme']) && isset($param['money']))
                {{$search->appends(array('money'=>$param['money'],'programme'=>$param['programme'],'end_num'=>$param['end_num'],'end_num_input'=>$param['end_num_input'],'total_price'=>$param['total_price'],'total_price_input'=>$param['total_price_input']))->links('admin.pageInfo')}}
            @elseif(!isset($param['programme']) && !isset($param['money']))
                {{$search->appends(array('end_num'=>$param['end_num'],'end_num_input'=>$param['end_num_input'],'total_price'=>$param['total_price'],'total_price_input'=>$param['total_price_input']))->links('admin.pageInfo')}}
            @endif
        </section>
    </section>
</section>

<!--main content end-->
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/sample/jquery/jquery-1.8.3.min.js" charset="UTF-8"></script>
<script src="/flatlib/js/bootstrap-paginator.js"></script>
<script>
    function subStart(id,start){
        if(start == 1){
            var is_start = 0;
        }else{
            var is_start = 1;
        }

        $.get('/admin/operate/red-manage-send', {'id':id,'is_start':is_start}, function (data) {
            if(data.code == 1 && is_start == 1 ){
                alert('success,启用发放成功!');
                location.reload()
            }else if(data.code == 1 && is_start == 0){
                alert('success,停止发放成功!');
                location.reload()
            }else if(data.code == 0 && is_start == 1){
                alert('fail,启用发放失败，请重新提交!');
                location.reload()
            }else if(data.code == 0 && is_start == 0){
                alert('fail,停止发放失败，请重新提交!');
                location.reload()
            }
        });
    }
</script>

@include('admin.footer')
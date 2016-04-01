@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/sample/jquery/jquery-1.8.3.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="/flatlib/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <div class='row'>
            <section class='col-lg-12'>
                <section class="panel">
                    <header class="panel-heading">
                        {{$date}}用户邀请好友排行
                        <div class="input-append date dpYears pull-right" style="width:200px;">
                            <input class="form-control form-control-inline input-append  form_date" data-date-format="yyyy-mm-dd" data-date="{{$date}}"  size="16" type="text" value="{{$date}}" readonly/>
                        </div>
                        <div class="pull-right input-append" style="margin-left: 10px;">
                            日期筛选：
                        </div>
                    </header>

                    <table class="table table-striped table-advance table-hover">
                        <thead>
                        <tr>
                            <th >邀请人ID</th>
                            <th >邀请人头像</th>
                            <th >邀请人昵称</th>
                            <th >邀请人手机号</th>
                            <th >邀请用户总数</th>
                        </tr>
                        </thead>
                        <tbody>
                        @foreach ($list as $lv)
                            <tr>
                                <td><a href="#">{{ $lv->id }}</a></td>
                                <td><img src="{{ $lv->head_pic!=''?$lv->head_pic:'/img/icon_head_big.png'  }}" height=50 /></td>
                                <td>{{ $lv->nick_name }}</td>
                                <td>{{ $lv->tel }}</td>
                                <td>{{ $lv->sum }}</td>
                            </tr>
                        @endforeach
                        </tbody>
                    </table>
                    {{$list->links('admin.pageInfo')}}
                </section>
            </section>
        </div>
    </section>
</section>
<!--main content end-->
<script type="text/javascript">
    var now_date = $('#now_date');
    var sel_date = $('#sel_date');

    $('.form_date').datetimepicker({
        language:  'fr',
        format:'yyyy-mm-dd',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        endDate: now_date.val()
    });

    $('.form_date').change(function(event) {
        ajaxGetHistoryNews();
    });

    $('.submit_action').click(function (){
        ajaxGetHistoryNews();
    });

    function  ajaxGetHistoryNews(){
        var createTime = $('.form_date').val();
        if(createTime =="请选择时间"){
            alert("请选择时间和昵称");
            return;
        }
        location.href = '/admin/userFriends/user-friends-num-by-date?date='+createTime;
    }

</script>

@include('admin.footer')


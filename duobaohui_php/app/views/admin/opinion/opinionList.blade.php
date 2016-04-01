@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>

<!--main content start-->
<section id="main-content">
    <section class="wrapper site-min-height">
        <div class="col-xs-10 panel">
            <div id="_msg"></div>
            <section class="panel">
                <header class="panel-heading">
                    反馈列表
                </header>
                <table class="table table-striped table-advance table-hover">
                    <thead>
                    <tr>
                        <th>反馈ID</th>
                        <th>反馈内容</th>
                        <th>反馈时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    @foreach($list as $value)
                        <tr>
                            <td>{{$value->id}}</td>
                            <td>{{$value->content}}</td>
                            <td>{{$value->create_time}}</td>
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
</script>

@include('admin.footer')


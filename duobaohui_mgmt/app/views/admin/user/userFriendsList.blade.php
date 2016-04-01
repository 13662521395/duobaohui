@include('admin.header')

@include('admin.menu')
        <!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <div class='row'>
            <section class='col-lg-12'>
                <section class="panel">
                    <header class="panel-heading">
                        用户邀请好友排行
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
<script>
</script>

@include('admin.footer')


@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>

<!--main content start-->
<section id="main-content">
    <section class="wrapper site-min-height">
        <div class="col-xs-12 panel">
            <div id="_msg"></div>
            <section class="panel">
                <header class="panel-heading">
                    系统通知列表
                    <a href="/admin/systemNotice/add-system-notice-pre" class="btn btn-danger add_banner pull-right"><i class="icon-plus"></i>&nbsp;发布通知</a>
                </header>

                <table class="table table-striped table-advance table-hover" id="android_table">
                    <thead>
                    <tr>
                        <th style="white-space: nowrap;">通知标题</th>
                        <th>通知内容</th>
                        <th style="white-space: nowrap;">发布时间</th>
                        <th style="white-space: nowrap;">更新时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    @foreach($list as $value)
                        <tr>
                            <td>{{$value->title}}</td>
                            <td style="word-break:break-all;"><label>{{$value->content}}</label></td>
                            <td style="white-space: nowrap;">{{$value->create_time}}</td>
                            <td style="white-space: nowrap;">{{$value->update_time}}</td>
                            <td>
                                <a href="/admin/systemNotice/system-notice-by-id?noticeId={{ $value->id }}" class="btn btn-primary btn-xs"><i class="icon-pencil"></i></a>
                                <button class="btn btn-danger btn-xs  del_banner" notice="{{ $value->id }}"><i class="icon-trash"></i></button>
                            </td>
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

    $('body').on('click', '.del_banner', function(){
        var notice = $(this).attr('notice');
        if(confirm('是否要删除该通知?')) {
            $.post('/admin/systemNotice/del-notice', { noticeId:notice }, function(data) {
                console.log(data);
                if (data.code == '1')
                    location.replace('/admin/systemNotice/system-notice-list');
                else {
                    alert('删除失败');
                    console.log(data);
                }
            });
        }
    });
</script>

@include('admin.footer')


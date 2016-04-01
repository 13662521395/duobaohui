@include('admin.header')

@include('admin.menu')

        <!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <div class='row'>
            <div class='col-lg-12'>
                <section class="panel">
                    <header class="panel-heading">
                        被举报晒单列表
                    </header>

                    @if (count($list)>0)
                        <table class="table table-striped table-advance table-hover">
                            <thead>
                            <tr>
                                <th >晒单ID</th>
                                <th >用户昵称</th>
                                <th >晒单标题</th>
                                <th >晒单内容</th>
                                <th >晒单图片(前三张)</th>
                                <th >共被举报次数</th>
                                <th >被举报详情</th>
                                <th >操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            @foreach ($list as $lv)
                                <tr>
                                    <td><a href="#">{{ $lv->sh_shaidan_id }}</a></td>
                                    <td>{{ $lv->nick_name }}</td>
                                    <td>{{ $lv->title }}</td>
                                    <td><div style="width:auto;white-space:normal;text-overflow:ellipsis;">{{{ $lv->content }}}</div></td>
                                    <td>
                                        <div style="width:150px;">
                                            @for ($i = 1 ; !empty($lv->img_url) && $i <= count($lv->img_url) && $i < 4 ; $i++)
                                                <img src="{{ $lv->img_url[$i - 1] }}" style="width:40px;height:50px;"/>
                                            @endfor
                                        </div>
                                    </td>
                                    <td>{{ $lv->sum }}</td>
                                    <td>
                                        @foreach($lv->report_array as $item)
                                            {{$item->content}}({{$item->report_num}}次)&nbsp;&nbsp;
                                        @endforeach
                                    </td>
                                    <td>
                                        <button shaidan_id="{{ $lv->sh_shaidan_id }}" title="删除记录" class="btn btn-danger btn-xs delete-shaidan2"><i class="icon-trash "></i></button>
                                    </td>
                                </tr>
                            @endforeach
                            </tbody>
                        </table>
                        {{$list->links('admin.pageInfo')}}
                    @else
                        <div class="alert alert-warning fade in">
                            <button data-dismiss="alert" class="close close-sm" type="button">
                                <i class="icon-remove"></i>
                            </button>
                            没有被举报的晒单
                        </div>
                    @endif
            </div>
        </div>
    </section>
</section>
</section>
<!--main content end-->

<script>
    seajs.use('page/admin/shaidan/list' , function(mod){
        mod.deleteShaidan();
    });

    $('.delete-shaidan2').bind('click' , function(){
        if(confirm('是否要删除该晒单?')) {
            var _this = this;
            var shaidanId = $(_this).attr('shaidan_id');
            $.ajax({
                type:'GET',
                dataType:'json',
                url:'/admin/shaidan/delete-shaidan',
                data:{
                    shaidan_id:shaidanId
                }
            })
            .done(function(data){
                $(_this).parent().parent().remove();
            })
            .fail(function(data){
            });
        }
    });
</script>

@include('admin.footer')


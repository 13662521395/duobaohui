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
                    客户端版本列表
                    <a href="/admin/client/release-new-version-pre" class="btn btn-danger add_banner pull-right"><i class="icon-plus"></i>&nbsp;新增</a>
                </header>

                <form class='form-horizontal tasi-form' method='post' action='/admin/client/version-list' id="osForm">
                    <div class='form-group'>
                        <label class='col-sm-1 control-label'>请选择系统类型:</label>
                        <div class="col-sm-11 panel">
                            @if($os_type == '1')
                                <label class='col-sm-1 control-label'>
                                    <input type='radio' name='os_type' value='1' checked />&nbsp;Android
                                </label>
                                <label class='col-sm-1 control-label'>
                                    <input type='radio' name='os_type' value='2' />&nbsp;IOS
                                </label>
                            @else
                                <label class='col-sm-1 control-label'>
                                    <input type='radio' name='os_type' value='1' />&nbsp;Android
                                </label>
                                <label class='col-sm-1 control-label'>
                                    <input type='radio' name='os_type' value='2' checked />&nbsp;IOS
                                </label>
                            @endif
                        </div>
                    </div>
                </form>

                <table class="table table-striped table-advance table-hover" id="android_table">
                    <thead>
                    <tr>
                        <th>版本号</th>
                        <th>版本描述</th>
                        <th>是否强制更新</th>
                        <th>系统版本</th>
                        <th>发布时间</th>
                        <th>安装包地址</th>
                    </tr>
                    </thead>
                    <tbody>
                    @foreach($list as $value)
                        <tr>
                            <td>{{$value->version_number}}</td>
                            <td>{{$value->version_desc}}</td>
                            <td>
                                @if($value->is_force == '1')
                                    是
                                @else
                                    否
                                @endif
                            </td>
                            <td>
                                @if($value->os_type == '1')
                                    Android
                                @else
                                    IOS
                                @endif
                            </td>
                            <td>{{$value->create_time}}</td>
                            <td><a href="{{$value->url}}">{{$value->url}}</a></td>
                        </tr>
                    @endforeach
                    </tbody>
                </table>

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

    $(document).ready(function(){
        var osForm = $('#osForm');
        $("input[name=os_type]").bind("click",function(event){
            osForm.submit();
        });
    });

</script>

@include('admin.footer')


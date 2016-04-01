@include('admin.header')

@include('admin.menu')

<!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading">
                    发布客户端新版本
                </header>
                <div class="panel-body">
                    @if (!empty($msg))
                        <div class="alert alert-success alert-block fade in">
                            <button data-dismiss="alert" class="close close-sm" type="button">
                                <i class="icon-remove"></i>
                            </button>
                            <h4>
                                <i class="icon-ok-sign"></i>
                                {{ $msg }}
                            </h4>
                        </div>
                    @endif
                    <form class='form-horizontal tasi-form' method='post' action='/admin/client/release-new-version' id="addVersion">
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>版本号</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="version_number" />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>版本说明</label>
                            <div class="col-sm-10">
                                <input type="text" id='version_desc' class="form-control" name='version_desc' />
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>是否强制更新</label>
                            <div class="col-sm-10">
                                <label class='col-sm-2 control-label'>
                                    <input type='radio' name='is_force' value='1'/>&nbsp;强制
                                </label>
                                <label class='col-sm-2 control-label'>
                                    <input type='radio' name='is_force' value='0' checked/>&nbsp;不强制
                                </label>
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>系统类型</label>
                            <div class="col-sm-10">
                                <label class='col-sm-2 control-label'>
                                    <input type='radio' name='os_type' value='1' checked />&nbsp;Android
                                </label>
                                <label class='col-sm-2 control-label'>
                                    <input type='radio' name='os_type' value='2' />&nbsp;IOS
                                </label>
                            </div>
                        </div>
                        <div class="form-group" id="uploadApp">
                            <label class="control-label col-md-3">上传安装包</label>
                            <div class="controls col-md-9">
                                @include('admin.client.qiniu_js_upload_apk')
                            </div>
                        </div>
                        <div class='form-group hidden' id="ios_app">
                            <label class='col-sm-2 control-label'>IOS下载地址</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="apk_url" id="ios_url" disabled="disabled"/>
                            </div>
                        </div>
                        <div>
                            <button type="submit" class="btn btn-success" id='' style='float:left;'>添加</button>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    </section>
</section>
<!--main content end-->

<script type="text/javascript">

    var uploadApp = $('#uploadApp');
    var ios_app = $('#ios_app');

    var addVersionForm = $('#addVersion');
    var version_validate = addVersionForm.validate({
        rules: {
            version_number: {
                required: true
            },
            version_desc: {
                required: true
            }
        },
        messages: {
            version_number: {
                required: "请输入版本号"
            },
            version_desc: {
                required: "请输入版本描述"
            }
        }
    });


    $(document).ready(function(){
        $("input[name=os_type]").bind("click",function(event){
            switchOsType($(this).val());
            event.stopPropagation();
        });

        $('#ios_app').bind("click",function(event){
            event.stopPropagation();
        });
    });

    function switchOsType(flag){
        if(flag==1){
            uploadApp.show();
            ios_app.addClass('hidden');
            $('#ios_url').attr('disabled',true);
            $('#apk_url').attr('disabled',false);
        }else{
            uploadApp.hide();
            ios_app.removeClass('hidden');
            $('#ios_url').attr('disabled',false);
            $('#apk_url').attr('disabled',true);
        }

    }

</script>

@include('admin.footer')


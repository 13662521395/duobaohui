@include('admin.header')

@include('admin.menu')

<!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading">
                    客户端配置
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
                    <form class='form-horizontal tasi-form' method='post' action='/admin/client/config' id="addVersion">
						<input type="hidden" name="is_save" value="1" />
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>IOS审核开启</label>
                            <div class="col-sm-10">
								<input type="checkbox"  name="ios_audit" value="1"  @if($ios_audit) checked  @endif />
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


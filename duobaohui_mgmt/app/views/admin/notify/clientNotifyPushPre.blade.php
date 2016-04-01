@include('admin.header')

@include('admin.menu')

        <!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading">
                    新建消息
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
                    <form class='form-horizontal tasi-form' method='post' action='/admin/notify/client-notify-push' id="pushNotifyForm">
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>消息描述</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" name="notify_ticker" placeholder="请输入消息描述"/>
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>标题</label>
                            <div class="col-sm-4">
                                <input type="text" id='version_desc' class="form-control" name='notify_title' placeholder="请输入标题"/>
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>内容</label>
                            <div class="col-sm-4">
                                <textarea class="form-control" rows="3" name="notify_content" placeholder="请输入内容"></textarea>
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>发送给</label>
                            <div class="col-sm-10">
                                <label class='col-sm-2 control-label'>
                                    <input type='radio' name='alias_type' value='Broadcast' />&nbsp;所有人
                                </label>
                                <label class='col-sm-2 control-label'>
                                    <input type='radio' name='alias_type' value='userId' checked />&nbsp;用户ID（Alias）
                                </label>
                                <input type='hidden' name='push_type' id="push_type" value="Customizedcast" />
                            </div>
                        </div>
                        <div class="form-group" id="uploadApp">
                            <label class="control-label col-md-2"></label>
                            <div class="controls col-md-3">
                                <input type="text" id='version_desc' class="form-control" name='notify_alias' placeholder="请输入用户ID"/>
                            </div>
                        </div>
                        <div>
                            <button type="submit" class="btn btn-success" id='' style='float:left;'>提交</button>
                            <button type="reset" class="btn btn-warning" id='' style='margin-left: 30px;'>重置</button>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    </section>
</section>
<!--main content end-->

<script type="text/javascript">

    var notify_ticker = $('#notify_ticker');
    var notify_title = $('#notify_title');
    var notify_content = $('#notify_content');

    var pushNotifyForm = $('#pushNotifyForm');
    var notify_validate = pushNotifyForm.validate({
        rules: {
            notify_ticker: {
                required: true
            },
            notify_title: {
                required: true
            },
            notify_content: {
                required: true
            }
        },
        messages: {
            notify_ticker: {
                required: "请输入消息描述"
            },
            notify_title: {
                required: "请输入标题"
            },
            notify_content: {
                required: "请输入内容"
            }
        }
    });

    $(document).ready(function(){
        $("input[name=alias_type]").bind("click",function(event){
            var value = $(this).val();
            if(value == 'Broadcast'){
                $('#push_type').val(value);
                $('#uploadApp').hide();
            } else{
                $('#push_type').val('Customizedcast');
                $('#uploadApp').show();
            }
            event.stopPropagation();
        });
    });
</script>

@include('admin.footer')


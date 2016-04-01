@include('admin.header')

@include('admin.menu')

        <!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading">
                    修改通知内容
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
                    <form class='form-horizontal tasi-form' method='post' action='/admin/systemNotice/update-system-notice' id="addSystemNoticeForm" onsubmit="return check();">
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>通知标题</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" name="title" id="title" value="{{ $notice->title }}"/>
                                <input type="hidden" class="form-control" name="content" id="content" value='{{ $notice->content }}'/>
                                <input type="hidden" class="form-control" name="noticeId" id="noticeId" value="{{ $notice->id }}"/>
                            </div>
                        </div>
                        <div class='form-group'>
                            <label class='col-sm-2 control-label'>通知内容</label>
                            <div class="col-sm-9">
                                @include('admin.umeditor' , array('content'=>''))
                            </div>
                        </div>
                        <div>
                            <button type="submit" class="btn btn-success" id='' style='float:left;'>确认修改</button>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    </section>
</section>
<!--main content end-->

<script type="text/javascript">

    var title = $('#title');
    var content = $('#content');
    var description_input = $('#description');
    var description = $('#umeditor_textarea_description');


    var addSystemNoticeForm = $('#addSystemNoticeForm');
    var version_validate = addSystemNoticeForm.validate({
        rules: {
            title: {
                required: true
            }
        },
        messages: {
            title: {
                required: "请输入通知标题"
            }
        }
    });

    var content_val = content.val();
    description_input.append(content_val);

    function check() {
        var content = description.val();
        if(content){
            return true;
        }else{
            alert("请输入通知内容");
            return false;
        }
    }

</script>

@include('admin.footer')


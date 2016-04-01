<!-- Modal -->
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width: 60%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&nbsp;关闭</span></button>
                <h4 class="modal-title" id="myModalLabel" style="text-align: center;">查看红包使用情况</h4>
            </div>
            <div class="modal-body">
                <div class="table-bordered">
                    <table class="table">
                        <th class="success">金额</th>
                        <th class="error">总量</th>
                        <th class="warning">已发放</th>
                        <th class="success">已使用</th>
                        <th class="info">已过期</th>
                        <th class="danger">剩余</th>
                        @foreach($list as $val)
                            <tr>
                                <td>{{$val->price}}</td>
                                <td>{{$val->num}}</td>
                                <td>{{$val->status}}</td>
                                <td>{{$val->is_use}}</td>
                                <td>{{$val->is_overdue}}</td>
                                <td>{{$val->end_num}}</td>
                            </tr>
                        @endforeach
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary activiry-sub" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width: 60%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&nbsp;关闭</span></button>
                <h4 class="modal-title" id="myModalLabel" style="text-align: center;">设置红包可用活动</h4>
            </div>
            <div class="modal-body">
                <ul class="big-check" style="font-size: 15px;margin-bottom: 10px;">
                    @foreach($list as $k=>$val)
                        <li>
                            <span class="one1"><input type="checkbox" class="checkAll" name="{{$k}}">&nbsp;&nbsp;{{$k}}</span>
                            <ul style="margin-left: 20px;">
                            @foreach($val as $key=>$value)

                                    <li><p><input type="checkbox" patternName="{{$k}}" name="item" value="{{$value->id}}">&nbsp;&nbsp;{{$value->goods_name}}                   @if($value->is_online == 1) <button  style='color: blue;'>上线</button> @else  <button  style='color: green;'>下线</button>   @endif</p></li>

                            @endforeach
                            </ul>
                        </li>
                    @endforeach
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary activiry-sub" data-dismiss="modal">确认</button>
            </div>
        </div>
    </div>
</div>
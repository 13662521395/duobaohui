@include('admin.header')

@include('admin.menu')
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="/flatlib/js/jquery.validate.min.js"></script>

<!--main content start-->
<section id="main-content">
  	<section class="wrapper">
  	     <section class="panel">
             <header class="panel-heading">
                查询条件
             </header>
                <div class="panel-body">
                <form class="form-horizontal tasi-form" method="post" >
                  <div class="row col-xs-8">
                      <div class="col-xs-6">
                          <div class='form-group'>
							<label for="goods">开奖时间</label>
							<div class="col-sm-12">
								<input type="text" id='luck_code_create_time' name='luck_code_create_time' readonly class="form-control form-control-inline input-medium default-date-picker" value="{{Input::old('begin_date')}}"/>
								<label class="error">{{$errors->first('begin_date')}}</label>
							</div>
						  </div>
                      </div>
                      <div class="col-xs-6">
                          <div class="form-group">
                              <label for="goods">发货状态</label>
                              <select class="form-control m-bot15" name="shipping_status" id="shipping_status">
                                  <option>请选发货状态</option>
                                  <option value="0">未发货</option>
                                  <option value="1">已发货</option>
                                  <option value="2">已收货</option>
                              </select>
                          </div>
                      </div>
                  </div>
                  <div class="form-group">
                      <div class="col-xs-2 col-xs-offset-10" >
                          <button class="btn btn-info btn-lg" id="queryIt" type="button" >查询</button>
                          <button class="btn btn-info btn-lg" id="reset" type="reset" >重置</button>
                      </div>
                  </div>
                </form>
                </div>
        </section>
		<section class="panel">
			<header class="panel-heading">
				订单列表
			</header>
			<table class="table" id="dataTable">
			  	<thead>
			  		<tr>
					  <th></i>奖项ID</th>
					  <th></i>订单号</th>
					  <th></i>活动ID</th>
					  <th></i>活动期数ID</th>
					  <th></i>开奖时间</th>
                      <th></i>奖项名称</th>
                      <th></i>奖项图片</th>
					  <th></i>奖品分类</th>
                      <th></i>中奖期号</th>
                      <th></i>收货地址</th>
                      <th></i>收货手机号</th>
                      <th></i>收货人姓名</th>
                      <th></i>采购链接</th>
					  <th></i>信息确认时间</th>
					  <th></i>发货状态</th>
					  <th></i>操作</th>
					  <th></th>
			 		</tr>
			  	</thead>
				<tbody id="tablebody">
			  	<?php foreach($list as $item){?>
			  		<tr goods_id = "{{$item->id;}}">
						<td><a href="#">{{$item->id;}}</a></td>
						<td>{{$item->order_sn;}}</td>
						<td>{{$item->activity_id;}}</td>
						<td>{{$item->activity_period_id;}}</td>
						<td>{{$item->luck_code_create_time;}}</td>
                        <td>{{$item->goods_name;}}</td>
                        <td><img  src="{{$item->goods_img;}}" height=50 /></td>
                        <td>{{$item->cat_name;}}</td>
                        <td>{{$item->period_number;}}</td>
                        <td>{{$item->address;}}</td>
                        <td>{{$item->mobile;}}</td>
                        <td>{{$item->consignee;}}</td>
                        <td><a href="{{$item->purchase_url;}}" target="_blank" >{{$item->purchase_url;}}</a></td>
                        <td>{{$item->create_time;}}</td>
						<td id="{{$item->order_id;}}">{{$item->shipping_status_view;}}</td>
						<td>
						 @if ($item->shipping_status == '0')
						  <button type="button" class="btn btn-primary" send="{{$item->order_id;}}">发货</button>
						 @else
					      <button type="button" class="btn btn-default" detail="{{$item->order_id;}}">查看明细</button>
						 @endif
				  		</td>
			  		</tr>
				<?php }?>							
			  	</tbody>
			</table>
			{{$list->links('admin.pageInfo')}}
			<div class="modal fade" id="invoiceInfoModal" tabindex="-1" role="dialog" aria-labelledby="添加发货信息" aria-hidden="true" style="display: none;">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                              <h4 class="modal-title">已确认发货信息</h4>
                        </div>
                        <div class="modal-body">
                            <form class=' tasi-form cmxform' role="form" id="orderModify">
                                <div class="form-group">
                                    <label for="exampleInputEmail1"><h4>中奖信息：</h4></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">奖品类别：</label><label id="catName" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">奖品名称：</label><label id="goodsName" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">奖品期数：</label><label id="periodNumber" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">中奖者昵称：</label><label id="nickName" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">中奖者ID：</label><label id="userId" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">中奖者账号：</label><label id="tel" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">信息确认时间：</label><label id="createTime" for="exampleInputEmail1"></label>
                                </div>
                                <hr/>
                                <div class="form-group">
                                    <label for="exampleInputEmail1"><h4>收货信息：</h4></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">地址：</label><label id="address" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword1">手机：</label><label id="mobile" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputFile">收货人：</label><label id="consignee" for="exampleInputFile"></label>
                                </div>
                                <hr/>
                                <div class="form-group">
                                    <label for="exampleInputEmail1"><h4>物流信息：</h4></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputFile">快递公司（必填）：</label><input type="text" value="" class="form-control" name="expressCompany" id="expressCompany" placeholder="快递公司">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputFile">快递单号（必填）：</label><input type="text" class="form-control" name="invoiceNo" id="invoiceNo" placeholder="快递单号">
                                </div>
                                <input type="hidden" class="form-control" id="orderId">
                                <input type="hidden" class="form-control" id="nickNameHide">
                                <input type="hidden" class="form-control" id="telHide">

                                <input type="button" id="sm_sm" class="btn btn-default" value="提交" />
                            </form>
                          </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="invoiceDetailModal" tabindex="-1" role="dialog" aria-labelledby="订单详情" aria-hidden="true" style="display: none;">
              <div class="modal-dialog modal-sm">
                  <div class="modal-content">
                      <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                          <h4 class="modal-title">订单详情</h4>
                      </div>
                      <div class="modal-body">
                            <form role="form">
                                <div class="form-group">
                                    <label for="exampleInputEmail1"><h4>中奖信息：</h4></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">奖品类别：</label><label id="catNameDetail" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">奖品名称：<label id="goodsNameDetail" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">奖品期数：</label><label id="periodNumberDetail" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">中奖者昵称：</label><label id="nickNameDetail" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">中奖者ID：</label><label id="userIdDetail" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">中奖者账号：</label><label id="telDetail" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">信息确认时间：</label><label id="createTimeDetail" for="exampleInputEmail1"></label>
                                </div>
                                <hr/>
                                <div class="form-group">
                                    <label for="exampleInputEmail1"><h4>收货信息：</h4></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputEmail1">地址：</label><label id="addressDetail" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword1">手机：</label><label id="mobileDetail" for="exampleInputEmail1"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputFile">收货人：</label><label id="consigneeDetail" for="exampleInputFile"></label>
                                </div>
                                <hr/>
                                <div class="form-group">
                                    <label for="exampleInputEmail1"><h4>物流信息：</h4></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputFile">快递公司：</label><label id="expressCompanyDetail" for="exampleInputFile"></label>
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputFile">快递单号：</label><label id="invoiceNoDetail" for="exampleInputFile"></label>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
		</section>
  	</section>
</section>
{{ HTML::script('/flatlib/assets/bootstrap-datepicker/js/bootstrap-datepicker.js') }}
<script>
    $(document).ready(function() {
        var orderModify = $('#orderModify')
        var orderModifyValidate = orderModify.validate({
            rules: {
                expressCompany: {
                    required: true,
                },
                invoiceNo: {
                    required: true
                }
            },
            messages: {
                expressCompany: {
                    required: "请输入快递公司",
                },
                invoiceNo: {
                    required: "请输入快递单号"
                }
            }
        });

    $(function() {
        $(".default-date-picker").datepicker({
            format: "yyyy-mm-dd"
        });
    })

	var queryIt = $('#queryIt');
    var tablebody = $('#tablebody');
    var invoiceInfoModal = $('#invoiceInfoModal');
    var invoiceDetailModal = $('#invoiceDetailModal');
    var dataTable = $('#dataTable')
    var tb = $('#dataTable tbody');
    var address = $('#address');
    var mobile = $('#mobile');
    var consignee = $('#consignee');
    var catName = $('#catName');
    var goodsName = $('#goodsName');
    var periodNumber = $('#periodNumber');
    var nickName = $('#nickName');
    var userId = $('#userId');
    var tel = $('#tel');
    var createTime = $('#createTime');
    var catNameDetail = $('#catNameDetail');
    var goodsNameDetail = $('#goodsNameDetail');
    var periodNumberDetail = $('#periodNumberDetail');
    var nickNameDetail = $('#nickNameDetail');
    var userIdDetail = $('#userIdDetail');
    var telDetail = $('#telDetail');
    var createTimeDetail = $('#createTimeDetail');

    var addressDetail = $('#addressDetail');
    var mobileDetail = $('#mobileDetail');
    var consigneeDetail = $('#consigneeDetail');
    var expressCompanyDetail = $('#expressCompanyDetail');
    var invoiceNoDetail = $('#invoiceNoDetail');

    var sm = $('#sm_sm');
    var orderId = $('#orderId');
    var nickNameHide = $('#nickNameHide');
    var telHide = $('#telHide');
    var expressCompany = $('#expressCompany');
    var invoiceNo = $('#invoiceNo');
    var beginDate = $('#begin_date').val();

    //     var pageDealer = new PageDealer();
	//发货
	tb.on('click', 'button[send]', function(event) {
        var target = event.target
        var order_id = $(target).attr('send')
        console.log(order_id);
        queryInvoiceDetail(order_id,'send');
        
    });

    //查看明细
	tb.on('click', 'button[detail]', function(event) {
        var target = event.target
        var order_id = $(target).attr('detail')
        queryInvoiceDetail(order_id,'detail');
        
    });

    //提交
	sm.on('click',function(){
        if(orderModify.valid()){
            addInvoiceInfo();
        }
    });
    
	var queryInvoiceDetail = function(order_id,flag){
        var param = {};
        param['order_id'] = order_id;
        $.ajax({
            url: '/admin/order/order-detail',
            type: 'post',
            dataType: 'json',
            data: param
          })
        .done(function(result) {
        	var mes="";
        	console.log(result.order_detail[0]);
          if(result.order_detail[0]) {
            address.html(result.order_detail[0].address);
            mobile.html(result.order_detail[0].mobile);
            consignee.html(result.order_detail[0].consignee);
            orderId.val(result.order_detail[0].order_id);

              catName.html(result.order_detail[0].cat_name);
              goodsName.html(result.order_detail[0].goods_name);
              periodNumber.html('第'+result.order_detail[0].period_number+'期');
              nickName.html(result.order_detail[0].nick_name);
              nickNameHide.val(result.order_detail[0].nick_name);

              userId.html(result.order_detail[0].user_id);
              tel.html(result.order_detail[0].tel);
              telHide.val(result.order_detail[0].tel);

              createTime.html(result.order_detail[0].create_time);

            addressDetail.html(result.order_detail[0].address);
            mobileDetail.html(result.order_detail[0].mobile);
            consigneeDetail.html(result.order_detail[0].consignee);
              catNameDetail.html(result.order_detail[0].cat_name);
              goodsNameDetail.html(result.order_detail[0].goods_name);
              periodNumberDetail.html('第'+result.order_detail[0].period_number+'期');
              nickNameDetail.html(result.order_detail[0].nick_name);
              userIdDetail.html(result.order_detail[0].user_id);
              telDetail.html(result.order_detail[0].tel);
              createTimeDetail.html(result.order_detail[0].create_time);

            expressCompanyDetail.html(result.order_detail[0].express_company);
            invoiceNoDetail.html(result.order_detail[0].invoice_no);
          }
          if(flag == 'send'){
            invoiceInfoModal.modal();
          }else if(flag == 'detail'){
            invoiceDetailModal.modal();
          }
          console.log("success");
        })
        .fail(function(err) {
          console.log('queryInvoiceDetail error')
          console.log(err);
        });
      }

	//点击查询按钮
// 	queryIt.on('click', function(event) {
//         var param = {
// //             isReal:$('#isReal').find(':selected').attr('value'),
//             shipping_status:$('#shipping_status').find(':selected').attr('value')
//         }

//         $('body').data('param', param);
//         queryTable();
//       });

	$('body').on('click','button[id="queryIt"]',function(event) {
		var target = event.target
		var shipping_status = $('#shipping_status').find(':selected').attr('value');
		var luck_code_create_time = $('#luck_code_create_time').val();
        var url = '/admin/order/order-list?1=1';
		if(shipping_status != undefined){
			url = url + '&shipping_status=' + shipping_status;
		}
		if(luck_code_create_time != undefined && luck_code_create_time != ''){
			url = url + '&luck_code_create_time=' + luck_code_create_time;
		}
        location.href = url;
	})
	
// 	var queryTable = function(page,pageSize) {
//         var param = $('body').data('param')
//         param = param ? param : {}
//         page = page || param.page || 1
//         pageSize = pageSize || param.pageSize || 10
//         param['page'] = page
//         param['pageSize'] = pageSize
//         param['flag'] = 'ajax';
//         $.ajax({
//             url: '/admin/order/order-list',
//             type: 'post',
//             dataType: 'json',
//             data: param
//           })
//           .done(function(data) {
//               console.log(data);
//             if(data.tablebody) {
//               tablebody.html(data.tablebody)
//             }
//             if(data.pageInfo && data.pageInfo.totalPages > 1) {
// //               pageDealer.renderPage('__page__',data.pageInfo,queryTable)
//             } else {
// //               pageDealer.destroyPage()
//             }
//             console.log("success");
//           })
//           .fail(function(err) {
//             console.log('noDeal error')
//             console.log(err);
//           });
//       }
    
    //添加快递公司、订单号
    var addInvoiceInfo = function(){
      var url = '/admin/order/order-modify';
      var param = {
        "order_id" : orderId.val(),
        "express_company" : expressCompany.val(),
        "invoice_no" : invoiceNo.val(),
        "nickName" : nickNameHide.val(),
        "tel" : telHide.val(),
      };
      var callback = function(response){
        console.log(response);
        if(response != null){
          invoiceInfoModal.modal('hide');
          location.href = location.href
        }else{
          alert('更新失败');
        }
      }
      $.post(url , param , callback);

    };

    });


//     queryTable();
</script>
<!--main content end-->
@include('admin.footer')


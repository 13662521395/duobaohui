@include('admin.header')

@include('admin.menu')


<!--main content start-->
<section id="main-content">
  	<section class="wrapper">
		<section class="panel">
			<header class="panel-heading">
				
			</header>
				<table class="table table-striped table-advance table-hover">
					<tbody>
				  		<tr >
						    <th class="col-sm-2"></i>用户支付总金额</th>
							<td>{{$pay_conut['total_fee_count']}}￥</td>
				  		</tr>
				  	</tbody>
				</table>
		</section>


	<!--main content start-->

   
  <section id="main">
        <!-- page start-->
          <div class="row">
              <div class="col-lg-6">
                  <section class="panel">
                      <header class="panel-heading">
                          用户一周交易数据统计
                      </header>
                      <div class="panel-body">
                           <div id="tradeNum" style="height:400px"></div>
                      </div>
                  </section>
              </div>
              <div class="col-lg-6">
                  <section class="panel">
                      <header class="panel-heading">
                          每天Top10畅销商品统计数据
                      </header>
                      <div class="panel-body">
                          <div id="hotProduct" style="height:400px"></div>
                      </div>
                  </section>
              </div>
          </div>
          <div class="row">
              <div class="col-lg-6">
                  <section class="panel">
                      <header class="panel-heading">
                          Quarterly Apple iOS device unit sales
                      </header>
                      <div class="panel-body">
                          <div id="graphic" style="height:400px"></div>
                  </section>
              </div>
              <div class="col-lg-6">
                  <section class="panel">
                      <header class="panel-heading">
                          Donut flavours
                      </header>
                      <div class="panel-body">
                           <div id="money" style="height:400px"></div>
                      </div>
                  </section>
              </div>
          </div>

        <!-- page end-->
  </section>

  <!--main content end-->
    </section>


  	</section>
</section>
<!--main content end-->
<script type="text/javascript">
  seajs.use('page/admin/bill/index' , function(mod){
    mod.getIndex();  
  });

</script>
@include('admin.footer')
{{ HTML::script('/echarts/build/dist/echarts.js') }}
{{ HTML::script('/echarts/build/dist/echarts-all.js') }}
{{ HTML::script('/echarts/doc/asset/js/echartsHome.js') }}
{{ HTML::script('/echarts/doc/asset/js/echartsExample.js') }}


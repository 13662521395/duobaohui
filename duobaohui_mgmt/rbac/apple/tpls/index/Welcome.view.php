<div class="pageContent j-resizeGrid">
	<div class="grid">
		<div class="gridScroller" layouth="10">
			<div class="page unitBox">
				<div class="box appBox">		
						<div class="title">APP概况</div>
						<table width="100%" class="indexTable" >
							<thead>
								<tr class="th">
									<th width="20%">信息</th>
									<th width="20%">销量</th>
									<th width="20%">扫码量</th>
									<th width="20%">用户数</th>
									<th width="20%">兑换量</th>
								</tr>
							</thead>
							<tbody>
								<tr class="today bc">
									<td>今日</td>
									<td>134</td>
									<td>121</td>
									<td>1312</td>
									<td>121</td>
								</tr>
								<tr class="bc">
									<td>昨日</td>
									<td>1200</td>
									<td>2000</td>
									<td>3102</td>
									<td>3092</td>
								</tr>
								<tr class="bc">
									<td>今日预计</td>
									<td>1234</td>
									<td>2131</td>
									<td>3213</td>
									<td>3212</td>
								</tr>
								<tr class="grey bc">
									<td>近90日平均</td>
									<td>1258</td>
									<td>2200</td>
									<td>3389</td>
									<td>3219</td>
								</tr>
								<tr class="grey bc">
									<td>历史最高</td>
									<td>2808 (2013-09-12)</td>
									<td>2600 (2013-01-02)</td>
									<td>3489 (2013-04-10)</td>
									<td>3498 (2013-11-26)</td>
								</tr>
								<tr class="grey bc">
									<td>历史累计</td>
									<td>28081</td>
									<td>22300</td>
									<td>44890</td>
									<td>6498</td>
								</tr>
							</tbody>
						</table>
				</div>
				<div class="box">		
						<div class="title">全国10月销量 扫码量 兑换量走势图</div>
						<div id="indexChartHolder" style="height: 300px"></div>
						<div class="shuoming"></div><div class="info">销量</div>
						<div class="shuoming shuoming_two"></div><div class="info">扫码量</div>
						<div class="shuoming shuoming_three"></div><div class="info">兑换量</div>
				</div>
				<div class="box pieBox">		
					<div class="box_left">
						<div class="title">全国店员用户与终端用户比例</div>
						<div id="indexPioeChartHolder"></div>
					</div>
					<div class="box_right">
						<div class="title">今日药品销量top</div>
						<table width="100%" class="indexPieTable indexTable">
							<thead>
								<tr class="th">
									<th width="10%"></th>
									<th width="10%">药品名称</th>
									<th width="10%">今日销量</th>
									<th width="10%">今日环比</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="rank">1</td>
									<td>哈药六牌钙加锌口服液</td>
									<td>13121</td>
									<td>3</td>
								</tr>
								<tr>
									<td class="rank">2</td>
									<td>新盖中盖牌高钙片</td>
									<td>3102</td>
									<td>19</td>
								</tr>
								<tr>
									<td class="rank">3</td>
									<td>999皮炎平</td>
									<td>12213</td>
									<td>2</td>
								</tr>
								<tr>
									<td class="rank">4</td>
									<td> 哈药牌银杏叶软胶囊</td>
									<td>10989</td>
									<td>5</td>
								</tr>
								<tr>
									<td class="rank">5</td>
									<td>双黄连糖浆</td>
									<td>9890</td>
									<td>11</td>
								</tr>
								<tr>
									<td class="rank">6</td>
									<td>小儿氨酚黄那敏颗粒</td>
									<td>8890</td>
									<td>13</td>
								</tr>
								<tr>
									<td class="rank">7</td>
									<td>三精牌睡美宁片</td>
									<td>7870</td>
									<td>9</td>
								</tr>
								<tr>
									<td class="rank">8</td>
									<td>阿奇霉素分散片</td>
									<td>6990</td>
									<td>14</td>
								</tr>
								<tr>
									<td class="rank">9</td>
									<td>哈药六牌天然维E软胶囊</td>
									<td>6820</td>
									<td>22</td>
								</tr>
								<tr>
									<td class="rank">10</td>
									<td>头孢羟氨苄胶囊</td>
									<td>5990</td>
									<td>12</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>



<script type="text/javascript">
var options = {
	axis: "0 0 1 1", // Where to put the labels (trbl)
	axisxstep: 29, // How many x interval labels to render (axisystep does the same for the y axis)
	axisxlables: ['01' , '02' , '03' , '04' , '05' , '06' , '07' , '08' , '09' , '10' , '11' , '12' , '13' , '14' , '15' , '16' , '17' , '18' , '19' , '20' , '21' , '22' , '23' , '24' , '25' , '26' , '27' , '28' , '29' , '30'],
	axisystep: 10,
	shade:false, // true, false
	smooth:true, //曲线
	symbol:"circle"
};

$(function () {
	//$('#layout').css('height' , $(document).height());

	var dateNum = new Array();
	var salesNum = new Array();
	var scanNum = new Array();
	var exchangeNum = new Array();
	for(var i=0;i<30;i++){
		dateNum.push(i);
		salesNum.push(Math.round(10 + 1000 * Math.random()));
		scanNum.push(Math.round(10 + 100 * Math.random()));
		exchangeNum.push(Math.round(10 + 100 * Math.random()));
	}
	

	// Make the raphael object
	var r = Raphael("indexChartHolder"); 

	var chartWidth = $('.box').width()-100;

	var lines = r.linechart(
		40, // X start in pixels
		20, // Y start in pixels
		chartWidth, // Width of chart in pixels
		250, // Height of chart in pixels
		dateNum, // Array of x coordinates equal in length to ycoords
		[salesNum , scanNum , exchangeNum], // Array of y coordinates equal in length to xcoords
		options // opts object
	).hoverColumn(function () {
		this.tags = r.set();

		var box_x = this.x, box_y = 30,
			box_w = 100, box_h = 80;
		if (box_x + box_w > r.width) box_x -= box_w;
		var box = r.rect(box_x,box_y,box_w,box_h).attr({stroke: "#ff0", "stroke-width": 0, r:5});
		this.tags.push(box);

		for (var i = 0; i < this.y.length; i++) {
			//this.tags.push(r.blob(this.x, this.y[i], "$"+this.values[i]).insertBefore(this).attr([{ fill: "#ffa500", stroke: "#000"}, { fill: this.symbols[i].attr("fill") }]));
			var t = r.text(box_x+20, box_y+10 + i*16,this.values[i]).attr({fill: this.symbols[i].attr("fill")})
			this.tags.push(t);
		}
		
		
	}, function () {
		this.tags && this.tags.remove();
	});

	lines.symbols.attr({ r: 3 });
});
</script>



<script type="text/javascript" charset="utf-8">
/* Title settings */		
title = "全国店员用户与终端用户比例";
titleXpos = 390;
titleYpos = 85;

/* Pie Data */
pieRadius = 130;
pieXpos = 150;
pieYpos = 180;
pieData = [670, 330];
pieLegend = [
"店员数量为670 ,占比%%.%%", 
"终端数量为330 ,占比%%.%%"];

pieLegendPos = "east";

$(function () {
	var r = Raphael("indexPioeChartHolder");
	 
	r.text(titleXpos, titleYpos, title).attr({"font-size": 20});
	
	var pie = r.piechart(pieXpos, pieYpos, pieRadius, pieData, {legend: pieLegend, legendpos: pieLegendPos});
	pie.hover(function () {
		this.sector.stop();
		this.sector.scale(1.1, 1.1, this.cx, this.cy);
		if (this.label) {
			console.log(this.label);
			this.label[0].stop();
			this.label[0].attr({ r: 7.5 });
			this.label[1].attr({"font-weight": 800});
		}
	}, function () {
		this.sector.animate({ transform: 's1 1 ' + this.cx + ' ' + this.cy }, 500, "bounce");
		if (this.label) {
			this.label[0].animate({ r: 5 }, 500, "bounce");
			this.label[1].attr({"font-weight": 400});
		}
	});
	
});
</script>

<script>
$(function(){

	var color = ['#FF0000'];
	$('.rank').each(function(){
		$(this).css('color' , color[0]);
	});

	var i = 0;
	var bc_color = ['#fff' , '#eee'];
	$('.bc').each(function(){
		if(i % 2 == 0){
			$(this).css('background-color',bc_color[0]);
		}else{
			$(this).css('background-color',bc_color[1]);
		}
		i++;
	});
	var thiscolor = '';
	$('.bc').bind('mouseenter' , function(){
		thiscolor = $(this).css('background-color');
		$(this).css('background-color' , '#EE7621');
	}).bind('mouseleave' , function(){
		$(this).css('background-color' , thiscolor);
	});
});
</script>

define(function(require , exports , module) {
   var $ = require('jquery');


	var getIndex =  function() {
		//真实用户详细基础统计数据
		var myChart;
		var echarts;
		$.get('/admin/bill/detail-count-data', {'page': 1}, function (data) {
			// console.log(data['list']);
			var countBuyTrue = json_to_js(data['list'].count_buy_true);
			var countAmountTrue = json_to_js(data['list'].count_amount_true);
			var countTotalFeeTrue = json_to_js(data['list'].count_total_fee_true);
			var startTime = json_to_js(data['list'].start_time);
			var option  = {
				tooltip : {
					trigger: 'axis'
				},
				legend: {
					data:['交易次数','交易金额','奖品金额']
				},
				toolbox: {
					show : true,
					feature : {
						mark : {show: true},
						dataView : {show: true, readOnly: false},
						magicType : {show: true, type: ['line', 'bar']},
						restore : {show: true},
						saveAsImage : {show: true}
					}
				},
				calculable : true,
				xAxis : [
					{
						type : 'category',
						// data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
						data : startTime
					}
				],
				yAxis : [
					{
						type : 'value',
						splitArea : {show : true}
					}
				],
				series : [
					{
						name:'交易次数',
						type:'bar',
						data:countBuyTrue
					},
					{
						name:'交易金额',
						type:'bar',
						data:countAmountTrue
					},
					{
						name:'奖品金额',
						type:'bar',
						data:countTotalFeeTrue
					}
				]
			};
			chartInit(option,'tradeNum');
		});


		//2
		$.get('/admin/bill/compare-user-count', {'page': 1}, function (data) {
			// console.log(data['list']);
			// var countBuyTrue = json_to_js(data['list'][0].count_buy_true);
			//消费金额对比  countAmountTrue1假   countAmountTrue2 真

			var countAmountTrue  = json_to_js(data['list'].count_amount_true);

			var countAmountFalse = json_to_js(data['list'].count_amount_false);
			// var countTotalFeeTrue = json_to_js(data['list'].count_total_fee_true);
			var startTime = json_to_js(data['list'].start_time);

			option = {
			    // title : {
			    //     text: '某地区蒸发量和降水量',
			    //     subtext: '纯属虚构'
			    // },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['真实用户','虚拟用户']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            data : startTime
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'真实用户',
			            type:'bar',
			            data:countAmountTrue,
			            markPoint : {
			                data : [
			                    {type : 'max', name: '最大值'},
			                    {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name: '平均值'}
			                ]
			            }
			        },
			        {
			            name:'虚拟用户',
			            type:'bar',
			            data:countAmountFalse,
			            markPoint : {
			                data : [
			                    {type : 'max', name: '最大值'},
			                    {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name : '平均值'}
			                ]
			            }
			        }
			    ]
			};
			chartInit(option,'messenger');
		});



		//3
		$.get('/admin/bill/compare-user-count', {'page': 1}, function (data) {
			// console.log(data['list']);
			// var countBuyTrue = json_to_js(data['list'][0].count_buy_true);
			//消费金额对比  countAmountTrue1假   countAmountTrue2 真

			var countTotalTrue  = json_to_js(data['list'].count_total_fee_true);
			var countTotalFalse = json_to_js(data['list'].count_total_fee_false);
			// var countTotalFeeTrue = json_to_js(data['list'].count_total_fee_true);
			var startTime = json_to_js(data['list'].start_time);

			option = {
			    // title : {
			    //     text: '某地区蒸发量和降水量',
			    //     subtext: '纯属虚构'
			    // },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['真实用户','虚拟用户']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            data : startTime
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'真实用户',
			            type:'bar',
			            data:countTotalTrue,
			            markPoint : {
			                data : [
			                    {type : 'max', name: '最大值'},
			                    {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name: '平均值'}
			                ]
			            }
			        },
			        {
			            name:'虚拟用户',
			            type:'bar',
			            data:countTotalFalse,
			            markPoint : {
			                data : [
			                    {type : 'max', name: '最大值'},
			                    {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name : '平均值'}
			                ]
			            }
			        }
			    ]
			};
			chartInit(option,'total_fee');
		});
   }

	function chartInit(option,id) {
		myChart = echarts.init(document.getElementById(id),'infographic');
		myChart.setOption(option);
	}


	//json转js数组
	function json_to_js(str){
		var strs= new Array(); 
			s=str.substring(0,str.length-1)
			strs=s.split(",");
			return strs;
	}

   exports.getIndex = getIndex;
});
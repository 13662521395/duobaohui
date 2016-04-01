var now_date = $('#now_date');
var input_beginDate = $('#beginDate');
var input_endDate = $('#endDate');
var transName = $('#transName');
var allBtn = $('#allBtn');

if(transName.val() == 'getShopShareBetweenDate'){
    allBtn.show();
}else{
    allBtn.hide();
}

$('#betweenBtn').bind('click',function(){
    if(input_beginDate.val()&&input_endDate.val()){
        if(input_beginDate.val() >= input_endDate.val() ){
            alert('开始日期不能大于等于结束日期');
        } else {
            location.href = '/admin/shop/shop-share-between-date?beginDate='+input_beginDate.val()+'&endDate='+input_endDate.val();
        }
    }else{
        alert('开始日期和结束日期都不能为空');
    }
});

allBtn.bind('click',function(){
    location.href = '/admin/shop/shop-share';
});

$('.form_date').datetimepicker({
    language:  'fr',
    format:'yyyy-mm-dd',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2,
    forceParse: 0,
    endDate: now_date.val()
});

var param = {
    'beginDate':input_beginDate.val(),
    'endDate':input_endDate.val()
}

$.post('/admin/shop/shop-share-real-sum', param , function(data) {
    if (data.code == '1' && data.data.list){
        var x_arr = new Array();
        var register_array = new Array();
        var sum = 0
        $.each(data.data.list,function(n,value) {
            if(value.branch_name){
                x_arr.push(value.branch_name);
            }else{
                x_arr.push(value.head_name);
            }
            register_array.push(value.real_num);
            sum++;
        });
        generateBar(x_arr,register_array);
        $('#lab_reg').html(sum);
    }else{
        $('#chart_div_1').hide();
    }
});

$.post('/admin/shop/shop-share-percent', param , function(data) {
    if (data.code == '1' && data.data.list){
        var x_arr = new Array();
        var percent_array = new Array();
        var sum = 0
        $.each(data.data.list,function(n,value) {
            if(value.branch_name){
                x_arr.push(value.branch_name);
            }else{
                x_arr.push(value.head_name);
            }
            var percent = value.percent;
            var i = percent.indexOf('%');
            percent_array.push(percent.substring(0,i));
            sum++;
        });
        generateBarPercent(x_arr,percent_array);
        $('#lab_per').html(sum);
    }else{
        $('#middle_div').hide();
    }
});

$.post('/admin/shop/all-os-type-percent', param , function(data) {
    if (data.code == '1' && data.data.list){
        generatePie(data.data.list,'chart-area');
    }else{
        $('#osType_div').hide();
    }
},'json');

$.post('/admin/shop/scan-os-type-percent', param , function(data) {
    if (data.code == '1' && data.data.list){
        generatePie(data.data.list,'chart-scan');
    }else{
        $('#scanType_div').hide();
    }
},'json');

function generateBar(x_arr,register_array){
    var barChartData = {
        labels : x_arr,
        datasets : [
            {
                fillColor : "rgba(220,220,220,0.5)",
                strokeColor : "rgba(220,220,220,0.8)",
                highlightFill: "rgba(220,220,220,0.75)",
                highlightStroke: "rgba(220,220,220,1)",
                data :register_array
            }
        ]
    }
    var ctx = document.getElementById("canvas").getContext("2d");
    window.myBar = new Chart(ctx).Bar(barChartData, {
        responsive : true
    });
}

function generateBarPercent(x_arr,percent_array){
    var barChartData = {
        labels : x_arr,
        datasets : [
            {
                fillColor : "rgba(220,220,220,0.5)",
                strokeColor : "rgba(220,220,220,0.8)",
                highlightFill: "rgba(220,220,220,0.75)",
                highlightStroke: "rgba(220,220,220,1)",
                data :percent_array
            }
        ]
    }
    var ctx = document.getElementById("canvas_bar").getContext("2d");
    window.myBar = new Chart(ctx).Bar(barChartData, {
        responsive : true
    });
}

function generatePie(data,id){
    var chart_div = $('#chart_div');
    var pie_body = $('#pie_body');
    var sel_div = $('#sel_div');
    var chart_area = $('#'+id);

    var pie_height = chart_div.height()*0.4;
    console.log(sel_div.height(),pie_body.height(),pie_height);
    chart_area.width(pie_height);
    chart_area.height(pie_height);

    var pieData = new Array();
    $.each(data,function(n,son) {
        var color;
        var highlight;
        if(son.os_type==1){
            color = "#46BFBD";
            highlight =  "#5AD3D1";
        } else if(son.os_type==2){
            color = "#949FB1";
            highlight = "#A8B3C5";
        } else{
            color = "#FDB45C";
            highlight = "#FFC870";
        }

        var item = {
            value: son.sum,
            color: color,
            highlight: highlight,
            label: son.os_remark
        }
        pieData.push(item);
    });
    var ctx = document.getElementById(id).getContext("2d");
    window.myPie = new Chart(ctx).Pie(pieData);
}
var now_date = $('#now_date');
var sel_date = $('#sel_date');

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

$('.form_date').change(function(event) {
    ajaxGetHistoryNews();
});

$('.submit_action').click(function (){
    ajaxGetHistoryNews();
});

$.post('/admin/shop/os-type-percent', {date:sel_date.val()} , function(data) {
    if (data.code == '1' && data.data.list){
        generatePie(data.data.list,'chart-area','osType_div');
    }
},'json');

$.post('/admin/shop/scan-os-type-percent-by-date', {date:sel_date.val()} , function(data) {
    if (data.code == '1' && data.data.list){
        generatePie(data.data.list,'chart-scan','scan_osType_pie');
    }
},'json');

function  ajaxGetHistoryNews(){
    var createTime = $('.form_date').val();
    if(createTime =="请选择时间"){
        alert("请选择时间和昵称");
        return;
    }
    location.href = '/admin/shop/shop-share-by-date?date='+createTime;
}

function generatePie(data,id,pannelId){
    $('#'+pannelId).show();
    console.log(data);
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
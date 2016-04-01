define(function(require,exports,module){
    var $ = require ('jquery');
    var layer = require('../../../app/layer');

    /*
     * 查看红包信息
     */
    var lookInfo = function(){

        var batch_id = $("#look-redpacket").attr('batch_id');
        $.get('/admin/operate/look-redpacket-info', {'batch_id':batch_id}, function (data) {
            $('#look-redpacket').html('');
            $('#look-redpacket').html(data);
        });
    };

    exports.lookInfo = lookInfo();
});
define(function(require , exports){
    var sjt = require('modules/sjt');
    return function(obj,data){
        data = data || Object;
		try{
			var shareTpl = sjt(obj,data); 
		}catch(e){
			console.log(e);
			//fml.debug(e);	
		}
		return shareTpl;
    };
});

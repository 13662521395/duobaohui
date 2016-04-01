define(function(require , exports , module) {
	var $ = require('jquery');

	var deleteShaidan =  function() {
		$('.delete-shaidan').bind('click' , function(){
			var shaidanId = $(this).attr('shaidan_id');

			$.ajax({
				type:'GET',
				dataType:'json',
				url:'/admin/shaidan/delete-shaidan',
				data:{
					shaidan_id:shaidanId
				}
			})
			.done(function(data){
				console.log(data);
			})
			.fail(function(data){
			});
		});
	}

	exports.deleteShaidan = deleteShaidan;
});

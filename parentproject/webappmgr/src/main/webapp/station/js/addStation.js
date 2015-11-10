$(document).ready(
		function()
		{
			//undo 页面初始化内容
		}
);

/**
 * 自定义校验code
 */
$.extend($.fn.validatebox.defaults.rules, {
    checkCodes: {//自定义校验code
        validator: function(value,param){
        	var rules = $.fn.validatebox.defaults.rules;  
    		//rules.checkCodes.message = "当前站点编码已存在"; 此种方式不合理！
            return !checkCode($("#"+param[1]).val(),value,'');
        },message:'当前站点编码已存在'
    },
    equalTo: { 
    	validator: function (value, param) { 
    		return $(param[0]).val() == value; 
    		}, message: '字段不匹配' }
});
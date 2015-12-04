$(document).ready(
		function()
		{
			initDatagrid();
			closeDialog();
			initQueryProvince();//初始化模糊查询省数据
		}
);

/**
 * 初始化模糊查询“省”下拉框数据
 */
function initQueryProvince()
{
	$('#searchFormProvince').combobox('clear');//清空combobox值
	var param = new Object();
	param.isHasall = true;//包含"全部"
	$('#searchFormProvince').combobox({
			queryParams:param,
			url:contextPath+'/product/getProvinceList.action',
			valueField:'pcode',
			textField:'pname',
			onLoadSuccess: function (data) { //数据加载完毕事件
				$('#searchFormProvince').combobox('select',data[data.length-1].pcode);
            },
            onSelect: function(rec){
            	var url = contextPath+'/product/getCityList.action?pcode='+rec.pcode;
		        $('#searchFormCity').combobox('reload', url);
		    }
		});
	$('#searchFormCity').combobox({
		valueField:'ccode',
		textField:'cname',
		onLoadSuccess: function (data) { //数据加载完毕事件
			$('#searchFormCity').combobox('select',data[data.length-1].ccode);
        }
	}); 
}

/**
 * 初始化省数据
 * @param addOrUpdate:标记当前是添加表单操作还是修改表单的操作,值为"add" 和"update"
 * @param provinceId:当前操作的省份combobox标签的id
 * @param pcode:应该选中的省份的code
 */
function initProvince(addOrUpdate,pcode,oldccode,oldacode)
{
	$('#addFormProvince').combobox('clear');//清空combobox值
	
	var data = new Object();
	data.isHasall = false;//不包含"全部"
	
	$('#addFormProvince').combobox({
			queryParams:data,
			url:contextPath+'/product/getProvinceList.action',
			valueField:'pcode',
			textField:'pname',
			 onLoadSuccess: function (data1) { //数据加载完毕事件
                 if (data1.length > 0 && "add" == addOrUpdate) 
                 {
                	 $("#addFormProvince").combobox('select',data1[0].pcode);
                 }
                 else
            	 {
                	//使用“setValue”设置选中值不会触发绑定事件导致多次加载市级数据，否则会多次触发产生错误
                	 $("#addFormProvince").combobox('select', pcode);
            	 }
             },
             onSelect: function(rec){
            	 var url = contextPath+'/product/getCityList.action?pcode='+rec.pcode;
 		        $('#addFormCity').combobox('reload', url);
 		    }
		});
	//初始化城市combo
	$('#addFormCity').combobox({
		queryParams:data,
		valueField:'ccode',
		textField:'cname',
		 onLoadSuccess: function (data) { //数据加载完毕事件
             if (data.length > 0 && "add" == addOrUpdate) 
             {
            	 $("#addFormCity").combobox('select',data[data.length-1].ccode);
             }
             else
        	 {
            	 if(data.length > 0 &&"update" == addOrUpdate&&"" == oldccode)
            		 {//在修改表单中级联加载市级数据时也要默认选中全部
            		 $("#addFormCity").combobox('select',data[data.length-1].ccode);
            		 }
            	 else
            		 {//当修改产品初始化市级数据时设置选中当前数据值
            		 	$("#addFormCity").combobox('select', oldccode);
            		 	oldccode = "";
            		 }
        	 }
         },
         onSelect: function(rec){
          	var url = contextPath+'/product/getRegionList.action?ccode='+rec.ccode;
		        $('#addFormRegion').combobox('reload', url);
		    }
	}); 
	//初始化乡镇区combo
	$('#addFormRegion').combobox({
		queryParams:data,
		valueField:'acode',
		textField:'aname',
		 onLoadSuccess: function (data) { //数据加载完毕事件
             if (data.length > 0 && "add" == addOrUpdate) 
             {
            	 $("#addFormRegion").combobox('select',data[data.length-1].acode);
             }
             else
        	 {
            	 if(data.length > 0 &&"update" == addOrUpdate&&"" == oldacode)
            		 {
            		 	$("#addFormRegion").combobox('select',data[data.length-1].acode);
            		 }
            	 else
            		 {
            		 	$("#addFormRegion").combobox('select', oldacode);
            		 	oldacode = "";
            		 }
        	 }
				
         }
	}); 
}



/*
 * 	@desc	 
 */
function initDatagrid()
{
	//获取查询form中所有值   $('#com').combobox('getValue')
	var queryParams = {
			searchFormNumber : $('#searchFormNumber').val(),
			searchFormStyle : $('#searchFormStyle').combobox('getValue'),
			searchFormProvince : $('#searchFormProvince').combobox('getValue'),
			searchFormCity : $('#searchFormCity').combobox('getValue'),
			searchFormName : $('#searchFormName').val(),
			searchFormTelephone : $('#searchFormTelephone').val(),
			searchFormAgent : $('#searchFormAgent').val()
	};
	//渲染列表
	$('#stationDataGrid').datagrid({
		singleSelect:false,
		queryParams: queryParams,
		url: contextPath + '/station/getStationList.action',
		method:'get',
		border:false,
		fit:true,
		//fitColumns:true,
		pagination:true,
		pageSize:10,
		striped:true,
		columns:[[
				{field:'id',checkbox:true},
				{field:'stationCode',title:'登录帐号',width:'10%',align:'center'},
				{field:'stationNumber',title:'站点号',width:'10%',align:'center'},
				{field:'province',title:'省',width:'10%',align:'center'},
				{field:'city',title:'市',width:'10%',align:'center'},
				{field:'stationStyle',title:'站点类型',width:'10%',align:'center'},
				{field:'name',title:'站主名称',width:'10%',align:'center'},
				{field:'telephone',title:'站主电话',width:'10%',align:'center'},
				{field:'stationCode',title:'登录帐号',width:'10%',align:'center'},
				{field:'createTime',title:'录入时间',width:'10%',align:'center'},
				{field:'opt',title:'操作',width:'130',align:'center', 
		            formatter:function(value,row,index){
		                var btn = '<a class="editcls" onclick="updateStation(&quot;'+row.id+'&quot;)" href="javascript:void(0)"></a>'
		                	+'<a class="delcls" onclick="delStationById(&quot;'+row.id+'&quot;)" href="javascript:void(0)"></a>'
		                	+'<a class="setOrder" onclick="setOrder(&quot;'+row.id+'&quot;,&quot;'+row.stationNumber+'&quot;,&quot;'+row.stationStyle+'&quot;)" href="javascript:void(0)"></a>';
		                return btn;
		            }
		        }
		    ]],
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});
	        $('.delcls').linkbutton({plain:true,iconCls:'icon-remove'});
	        $('.setOrder').linkbutton({plain:true,iconCls:'icon-search'});
	    }  
	});
}


	//关闭弹框
	function closeDialog()
	{	
		$("#setOrder").dialog('close');
		$("#addOrUpdateStation").dialog('close');
	}

	/**
	 * 权限修改
	 */
	function updateStation(id)
	{
			/**
			 * 站点修改
			 */
			$('.panel-title.panel-with-icon').html('修改站点信息');
			$('#addFormStationCode').attr('readonly','readonly');
			var url = contextPath + '/station/getStationDetail.action';
			var paramData = new Object();
			paramData.id=id;
			$.ajax({
				async: false,   //设置为同步获取数据形式
		        type: "get",
		        cache:false,
		        url: url,
		        data:paramData,
		        dataType: "json",
		        success: function (data) {
					$('#addOrUpdateStationForm').form('load',data);
					initProvince('update',data.addFormProvince,data.addFormCity,data.addFormRegion);
		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		            alert(errorThrown);
		        }
			});
			$("#addOrUpdateStation").dialog("open");//打开修改用户弹框
	}

	//提交添加权限form表单
	function submitAddStation()
	{
		$('#addOrUpdateStationForm').form('submit',{
			url:contextPath+'/station/saveOrUpdate.action',
			onSubmit:function(param){
				return $('#addOrUpdateStationForm').form('validate');
			},
			success:function(data){
				$.messager.alert('提示', eval("(" + data + ")").message);
				closeDialog();
	        	initDatagrid();
			}
		});
	}
	
	//修改帐号form表单
	function submitUpdatestation()
	{
		$('#addOrUpdateStationForm').form('submit',{
			url:contextPath+'/station/saveOrUpdate.action',
			onSubmit:function(param){
				return $('#updatestationForm').form('enableValidation').form('validate');
			},
		    success:function(data){
		    	//data从后台返回后的类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
		    	$.messager.alert('提示', eval("(" + data + ")").message);
		    	$("#updatestation").dialog('close');//初始化修改权限弹框关闭
		    	//在修改权限后刷新权限数据列表
		    	initDatagrid();
		    	$('#updatestationForm').form('clear');
		    }
		});
	}
	
	/**
	 * 校验code,name唯一性
	 */
	function checkCode(id,code)
	{
		var flag = false;//当前值可用，不存在
		var paramData = {
				id : id,
				code : code
		};
		$.ajax({
			async: false,   //设置为同步获取数据形式
	        type: "post",
	        url: contextPath+'/station/checkCode.action',
	        data:paramData,
	        dataType: "json",
	        success: function (data) {
	        	if(data.exist)//若data.isExist==true,则当前校验值已存在，则不可用使用
	        		{
	        			flag = true;
	        		}
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            alert(errorThrown);
	        }
	   });
		return flag;
	}
/**
 * 删除单个记录
 */
	function delStationById(id){
		var url = contextPath + '/station/deleteStationByIds.action';
		$.messager.confirm(" ", "您确认删除选中数据？", function (r) {  
	        if (r) {  
		        	$.ajax({
		        		async: false,   //设置为同步获取数据形式
		                type: "post",
		                url: url,
		                data:{
		                	ids : id
		                },
		                dataType: "json",
		                success: function (data) {
		                	initDatagrid('');
		                	$.messager.alert('提示', data.message);
		                },
		                error: function (XMLHttpRequest, textStatus, errorThrown) {
		                    alert(errorThrown);
		                }
		           });
	        	}  
	    	});  
		}
	/**
	 * 
	 * 批量删除
	 * @param code
	 */
	function deleteStationByIds()
	{
		var url = contextPath + '/station/deleteStationByIds.action';
		var paramObj = new Object();
		
		var idArr = new Array();
		var rows = $('#stationDataGrid').datagrid('getSelections');
		var deleteFlag = true;
		
		for(var i=0; i<rows.length; i++)
		{
			idArr.push(rows[i].id);//code
		}
		
		if(idArr.length>0)
		{
			paramObj.ids=idArr.toString();	//将id数组转换为String传递到后台
			
			$.messager.confirm(" ", "您确认删除选中数据？", function (r) {  
		        if (r) {  
			        	$.ajax({
			        		async: false,   //设置为同步获取数据形式
			                type: "post",
			                url: url,
			                data:paramObj,
			                dataType: "json",
			                success: function (data) {
			                	initDatagrid('');
			                	$.messager.alert('提示', data.message);
			                },
			                error: function (XMLHttpRequest, textStatus, errorThrown) {
			                    alert(errorThrown);
			                }
			           });
			        	
		        }  
		    });  
		}
		else
		{
			$.messager.alert('提示', "请选择数据后操作!");
		}
	}

/**
 * 自定义校验code
 */
$.extend($.fn.validatebox.defaults.rules, {
    checkCodes: {//自定义校验code
        validator: function(value,param){
        	var rules = $.fn.validatebox.defaults.rules;  
    		rules.checkCodes.message = "站点编码已存在"; 
            return !checkCode($("#"+param[1]).val(),value,'');
        }
    },
    equalTo: { 
    	validator: function (value, param) { 
    		return $(param[0]).val() == value;
    		}, message: '两次密码输入不一致！' }
});
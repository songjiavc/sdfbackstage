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
	 * add by songj@sdfcp.com
	 * date 2015-11-27 
	 * desc 初始化上级代理列表
	 */
	function initAddFormParentId(parentId){
		$('#addFormParentId').combobox('clear');//清空combobox值
		$('#addFormParentId').combobox({
			queryParams:{
				
			},//暂时没有任何需要查询的条件
			url:contextPath+'/agent/getSczyList.action',
			valueField : 'id',
			textField : 'name',
			onLoadSuccess: function (data) { //数据加载完毕事件
                 if (parentId == undefined) 
                 {
                	 $("#addFormParentId").combobox('select',data[0].id);
                 }
                 else
            	 {
                	//使用“setValue”设置选中值不会触发绑定事件导致多次加载市级数据，否则会多次触发产生错误
                	 $("#addFormParentId").combobox('setValue', parentId);
            	 }
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
                	 //$("#addFormProvince").combobox('select',data1[0].pcode);
                 }
                 else
            	 {
                	//使用“setValue”设置选中值不会触发绑定事件导致多次加载市级数据，否则会多次触发产生错误
                	 $("#addFormProvince").combobox('select', pcode);
            	 }
             },
             onSelect: function(rec){
            	 initAddFormCity(addOrUpdate,rec.pcode,oldccode,oldacode);
 		    }
		});
}

function initAddFormCity(addOrUpdate,pcode,oldccode,oldacode){
	//初始化城市combo
	$('#addFormCity').combobox({
		url : contextPath+'/product/getCityList.action',
		queryParams:{
			isHasall : false,    //不包含"全部",
			pcode : pcode
		},
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
        	 initAddFormRegion(addOrUpdate,rec.ccode,oldacode);
		    }
	}); 
}

function initAddFormRegion(addOrUpdate,ccode,oldacode){
	
	//初始化乡镇区combo
	$('#addFormRegion').combobox({
		url :  contextPath+'/product/getRegionList.action',
		queryParams:{
			isHasall : false,    //不包含"全部",
			ccode : ccode
		},
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
			searchFormProvince : $('#searchFormProvince').combobox('getValue'),
			searchFormCity : $('#searchFormCity').combobox('getValue'),
			searchFormName : $('#searchFormName').val(),
			searchFormTelephone : $('#searchFormTelephone').val()
	};
	//渲染列表
	$('#agentDataGrid').datagrid({
		singleSelect:false,
		queryParams: queryParams,
		url: contextPath + '/agent/getAgentList.action',
		method:'get',
		border:false,
		fit:true,
		//fitColumns:true,
		pagination:true,
		pageSize:10,
		striped:true,
		columns:[[
				{field:'id',checkbox:true},
				{field:'agentCode',title:'登录帐号',width:'5%',align:'center'},
				{field:'province',title:'省',width:'5%',align:'center'},
				{field:'city',title:'市',width:'5%',align:'center'},
				{field:'region',title:'区',width:'6%',align:'center'},
				{field:'address',title:'详细地址',width:'10%',align:'center'},
				{field:'name',title:'代理名称',width:'8%',align:'center'},
				{field:'telephone',title:'代理电话',width:'10%',align:'center'},
				{field:'parentName',title:'上级名称',width:'8%',align:'center'},
				{field:'creater',title:'录入人',width:'8%',align:'center'},
				{field:'createrTime',title:'录入时间',width:'10%',align:'center'},
				{field:'opt',title:'操作',width:'130',align:'center', 
		            formatter:function(value,row,index){
		                var btn = '<a class="editcls" onclick="updateAgent(&quot;'+row.id+'&quot;)" href="javascript:void(0)"></a>'
		                	+'<a class="delcls" onclick="delAgentById(&quot;'+row.id+'&quot;)" href="javascript:void(0)"></a>';
		                return btn;
		            }
		        }
		    ]],
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});
	        $('.delcls').linkbutton({plain:true,iconCls:'icon-remove'});
	    }  
	});
}


	//关闭弹框
	function closeDialog()
	{	
		$("#addOrUpdateAgent").dialog('close');
		$("#setAgentScope").dialog('close');
	}

	/**
	 * 代理修改
	 */
	function updateAgent(id)
	{
			/**
			 * 站点修改
			 */
			$('.panel-title.panel-with-icon').html('修改代理信息');
			$('#addFormAgentCode').attr('readonly','readonly');
			var url = contextPath + '/agent/getAgentDetail.action';
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
					$('#addOrUpdateAgentForm').form('load',data);
					initProvince('update',data.addFormProvince,data.addFormCity,data.addFormRegion);
					initAddFormParentId(data.parentId);
		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		            alert(errorThrown);
		        }
			});
			$("#addOrUpdateAgent").dialog("open");//打开修改用户弹框
	}

	/**
	 * 商品选购入口，留给班娜补充
	 */
	function setOrder(row)
	{
			/*
			 *   row = {
			 *   	
			 *   }
			 */
			$("#setOrder").dialog("open");
	}
	//提交添加权限form表单
	function submitAddAgent()
	{
		$('#addOrUpdateAgentForm').form('submit',{
			url:contextPath+'/agent/saveOrUpdate.action',
			onSubmit:function(param){
				return $('#addOrUpdateAgentForm').form('validate');
			},
			success:function(data){
				$.messager.alert('提示', eval("(" + data + ")").message);
				closeDialog();
	        	initDatagrid();
			}
		});
	}
	
	//修改帐号form表单
	function submitUpdateagent()
	{
		$('#addOrUpdateAgentForm').form('submit',{
			url:contextPath+'/agent/saveOrUpdate.action',
			onSubmit:function(param){
				return $('#updateagentForm').form('enableValidation').form('validate');
			},
		    success:function(data){
		    	//data从后台返回后的类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
		    	$.messager.alert('提示', eval("(" + data + ")").message);
		    	$("#updateagent").dialog('close');//初始化修改权限弹框关闭
		    	//在修改权限后刷新权限数据列表
		    	initDatagrid();
		    	$('#updateagentForm').form('clear');
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
	        url: contextPath+'/agent/checkCode.action',
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
	function delAgentById(id){
		var url = contextPath + '/agent/deleteAgentByIds.action';
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
	function deleteAgentByIds()
	{
		var url = contextPath + '/agent/deleteAgentByIds.action';
		var paramObj = new Object();
		
		var idArr = new Array();
		var rows = $('#agentDataGrid').datagrid('getSelections');
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
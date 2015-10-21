$(document).ready(
		function()
		{
			initDatagrid();
			closeDialog();
		}
);


function initDatagrid()
{
	$('#accountDataGrid').datagrid({
		singleSelect:false,
//		queryParams: params,
		url: contextPath + '/account/getUserList.action',
		method:'get',
		border:false,
		fit:true,
		//fitColumns:true,
		pagination:true,
		pageSize:10,
		striped:true,
		columns:[[
				{field:'id',checkbox:true	},
				{field:'code',title:'登录帐号',width:'15%',align:'center'},
				{field:'name',title:'名称',width:'15%',align:'center'},
				{field:'telephone',title:'电话',width:'20%',align:'center'},
				{field:'creator',title:'录入人',width:'10%',align:'center'},
				{field:'creatorTime',title:'录入时间',width:'10%',align:'center'},
				{field:'status',title:'启用',align:'center',width:'5%',
					formatter:function(value,row,index){
					var showStatus = "";
					if("1" == row.status)
						{
							showStatus = "是";
						}
					else
						{
							showStatus = "否";
						}
						return showStatus;
					}
				},
				{field:'roles',title:'角色',align:'center',width:'5%',
					formatter:function(value,row,index){
						debugger;
					}
				},
				{field:'opt',title:'操作',width:160,align:'center', 
		            formatter:function(value,row,index){
		                var btn = '<a class="editcls" onclick="updateAccount(&quot;'+row.id+'&quot;)" href="javascript:void(0)">编辑</a>'
		                	+'<a class="delcls" onclick="deleteAccount(&quot;'+row.id+'&quot;)" href="javascript:void(0)">删除</a>';
		                return btn;
		            }
		        }
		    ]],
	    onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});  
	        $('.auth').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
	    }  
	});
}


//关闭弹框
function closeDialog()
{
	$("#addAccount").dialog('close');//初始化添加角色弹框关闭
	$("#updateAccount").dialog('close');//初始化添加角色弹框关闭
}

/**
 * 权限修改
 */
function updateAccount(id)
{
		/**
		 * 用户修改
		 */
		var url = contextPath + '/account/getDetailAccount.action';
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
				$('#updateAccountForm').form('load',{
					code:data.code
				});
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            alert(errorThrown);
	        }
		});
		$("#updateAccount").dialog("open");//打开修改用户弹框
}

/**
 * 删除权限
 */
function deleteAccount()
{
	debugger;
	alert("权限删除");
}

function formCheckout(){
	if($.trim($('#password').val()) != $.trim($('#confirmPassword').val())){
		return false;
	}
	return true;
}


//提交添加权限form表单
function submitAddAccount()
{
	$('#addAccountForm').form('submit',{
		url:contextPath+'/account/saveOrUpdate.action',
		onSubmit:function(param){
			return $('#addAccountForm').form('validate');
		},
		success:function(data){
			$.messager.alert('提示', eval("(" + data + ")").message);
        	closeDialog();
		}
	});
}

//修改帐号form表单
function submitUpdateAccount()
{
	$('#updateAccountForm').form('submit',{
		url:contextPath+'/account/saveOrUpdate.action',
		onSubmit:function(param){
			return $('#updateAccountForm').form('enableValidation').form('validate');
		},
	    success:function(data){
	    	//data从后台返回后的类型为String，要获取信息需要将其转换为json类型，使用eval("(" + data + ")")方法转换
	    	$.messager.alert('提示', eval("(" + data + ")").message);
	    	$("#updateAccount").dialog('close');//初始化修改权限弹框关闭
	    	//在修改权限后刷新权限数据列表
	    	initDatagrid();
	    	$('#updateAccountForm').form('clear');
	    }
	});
}

/**
 * 校验code唯一性
 */
function checkCode(code)
{
	var flag = false;//当前值可用，不存在
	$.ajax({
		async: false,   //设置为同步获取数据形式
        type: "post",
        url: contextPath+'/account/checkValue.action',
        data:{
        	code : code
        },
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
 * 自定义校验code
 */
$.extend($.fn.validatebox.defaults.rules, {
    checkCodes: {//自定义校验code
        validator: function(value,param){
        	var rules = $.fn.validatebox.defaults.rules;  
    		rules.checkCodes.message = "当前权限编码已存在"; 
            return !checkCode($("#"+param[1]).val(),value,'');
        }
    }
});
